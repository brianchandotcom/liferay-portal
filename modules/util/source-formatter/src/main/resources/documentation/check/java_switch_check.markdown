## JavaSwitchCheck

Avoid using `switch` statements. Instead use `if/else` statements.

### Example

Incorrect:

```java
switch (level) {
    case LocationAwareLogger.DEBUG_INT:
        _log.debug(formattingTuple.getMessage(), t);

        break;

    case LocationAwareLogger.ERROR_INT:
        _log.error(formattingTuple.getMessage(), t);

        break;

    case LocationAwareLogger.WARN_INT:
        _log.warn(formattingTuple.getMessage(), t);

        break;

    default:
        _log.info(formattingTuple.getMessage(), t);
}
```

Correct:

```java
if (level == LocationAwareLogger.DEBUG_INT) {
    _log.debug(formattingTuple.getMessage(), t);
}
else if (level == LocationAwareLogger.ERROR_INT) {
    _log.error(formattingTuple.getMessage(), t);
}
else if (level == LocationAwareLogger.WARN_INT) {
    _log.warn(formattingTuple.getMessage(), t);
}
else {
    _log.info(formattingTuple.getMessage(), t);
}
```