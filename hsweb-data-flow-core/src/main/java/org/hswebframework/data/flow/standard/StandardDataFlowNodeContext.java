package org.hswebframework.data.flow.standard;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.*;

import java.util.Optional;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class StandardDataFlowNodeContext implements DataFlowNodeContext {
    @Getter
    @Setter
    private DataFlowContext flowContext;

    @Getter
    @Setter
    private String preNodeType;

    @Getter
    @Setter
    private String preNodeName;

    @Getter
    @Setter
    private String preNodeId;

    @Getter
    @Setter
    private Object preNodeResult;

    @Getter
    @Setter
    private boolean success;

    private Logger logger;

    private Progress progress;

    @Getter
    @Setter
    private TypeConverter typeConvertor;

    public StandardDataFlowNodeContext(String name) {
        this.logger = new Slf4jLogger("data.flow.task." + name);
        this.progress = new Slf4jProgress("data.flow.task.progress", name);
    }

    @Override
    public <T> Optional<T> getPreNodeResult(Class<T> type) {
        if (type.isInstance(preNodeResult)) {
            return Optional.ofNullable((T) preNodeResult);
        }
        if (typeConvertor != null) {
            return Optional.ofNullable(typeConvertor.convert(preNodeResult, type));
        }
        return Optional.empty();
    }

    @Override
    public Logger logger() {
        return logger;
    }

    @Override
    public Progress progress() {
        return progress;
    }

    @Override
    public void success(Object result) {
        this.success = true;
    }

    @Override
    public void error(Throwable msg) {
        this.success = false;
    }
}
