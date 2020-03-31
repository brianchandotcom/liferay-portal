## JavaLogLevelCheck

When logging `debug/info/trace/warn` messages on always use the corresponding
`is*` method.
When logging `error` messages, do not use `isErrorEnabled`. `isErrorEnabled`
always returns `true`.

### Example

```
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

...

public void method(String message) {
    _log.error(message);

    if (_log.isDebugEnabled()) {
        _log.debug(message);
    }

    if (_log.isInfoEnabled()) {
        _log.info(message);
    }

    if (_log.isTraceEnabled()) {
        _log.trace(message);
    }

    if (_log.isWarnEnabled()) {
        _log.warn(message);
    }

...

private static final Log _log = LogFactoryUtil.getLog(getClass());
```