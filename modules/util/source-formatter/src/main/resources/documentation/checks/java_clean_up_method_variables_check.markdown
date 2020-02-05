## JavaCleanUpMethodVariablesCheck

When a *Tag.java contains a `cleanUp` method, we have to make sure that for all
the variables in the `cleanUp` method, the value is the same as the initial
value for the variable.

### Example

When the class contains the following `cleanUp` method:

```java
@Override
protected void cleanUp() {
    _escape = true;
}
```

the initial value of `_escape` should also be `true`:

```java
private boolean _escape = true;
```