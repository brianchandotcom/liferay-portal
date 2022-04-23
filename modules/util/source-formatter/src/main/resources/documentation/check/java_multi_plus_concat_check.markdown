## JavaMultiPlusConcatCheck

When concatenating more than 3 `String` objects, we should make use of
`StringBundler` instead of using the plus operator.

If a `StringBundler` object does not exist and all the `String` objects are
available, we should call the method `StringBundler.concat(String...)`.

#### Example

Instead of
```java
_log("User " + user.getFirstName() + " " + user.getFirstName() + ".");
```
we should write

```java
_log(
    StringBundler.concat("User ", user.getFirstName(), " ", user.getFirstName(),
    "."));
```

If a `StringBundler` object is already available, we can write

```java
sb.append("User ");
sb.append(user.getFirstName());
sb.append(" ");
sb.append(user.getLastName());
sb.append(".");

_log(sb.toString());
```

When concatenating more than 3 arguments, but not all of those are `String`
objects, we should still use `StringBundler.concat` and convert the arguments
that are not a `String` object first.

#### Example

```java
_log(
    StringBundler.concat("User ", user.getFirstName(), " ", user.getFirstName(),
    " has id ", String.valueOf(user.getUserId()));
```