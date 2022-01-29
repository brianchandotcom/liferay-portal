## GetterUtilCheck

Do not pass default values to `GetterUtil.get*` or `ParamUtil.get*` method
calls.

### Example

Incorrect:

```java
if (ParamUtil.getBoolean(actionRequest, false)) {
    long companyId = GetterUtil.getLong(value, 0);
}

```

Correct:

```java
if (ParamUtil.getBoolean(actionRequest)) {
    long companyId = GetterUtil.getLong(value);
}
```