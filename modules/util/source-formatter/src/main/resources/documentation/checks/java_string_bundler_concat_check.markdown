## JavaStringBundlerConcatCheck

When concatenating less than 3 elements, it is faster to use `+` than
`StringBundler.concat`.

### Example

Incorrect:

```java
_log.error(StringBundler.concat("Message: ", _getMessage()));
```

Correct:

```java
_log.error("Message: " + _getMessage());
```