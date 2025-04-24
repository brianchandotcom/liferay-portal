## RedundantLogCheck

Do not use the redundant log for logging the same thing.

### Examples

```java
if (_log.isWarnEnabled()) {
	_log.warn(exception);
}
```

Instead of

```java
if (_log.isDebugEnabled()) {
	_log.debug(exception);
}
else if (_log.isWarnEnabled()) {
	_log.warn(exception);
}
```