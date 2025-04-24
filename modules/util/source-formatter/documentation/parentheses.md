## Use of Parentheses

### If-Statements

In if-statements we use parentheses in the following cases:

* Around expressions that use arithmetic or relational operators.

#### Example

```java
if ((s == null) || ((a + b) > 0)) {
    return true;
}
```

* Grouping expressions when we use different logical operators.

#### Example

```java
if (a || (b && c)) {
    return true;
}
```

When we break one of the above rules, it will result in either missing or
redundant parentheses.

#### Examples of Missing Parentheses

Incorrect:

```java
if (a < 0 || b < 0) {
    return true;
}
```

Correct:

```java
if ((a < 0) || (b < 0)) {
    return true;
}
```

Incorrect:

```java
if (a || b && c) {
    return true;
}
```

Correct:

```java
if (a || (b && c)) {
    return true;
}
```

#### Examples of Redundant Parentheses

Incorrect:

```java
if ((s == null) || (s.equals("test"))) {
    return true;
}
```

Correct:

```java
if ((s == null) || s.equals("test")) {
    return true;
}
```

Incorrect:

```java
if (a || (b || c)) {
    return true;
}
```

Correct:

```java
if (a || b || c) {
    return true;
}
```

Incorrect:

```java
if (((a < 0) || (b < 0))) {
    return true;
}
```

Correct:

```java
if ((a < 0) || (b < 0)) {
    return true;
}
```

### JSP Attributes

For attributes in JSP files, we use the same rules.

#### Example of Missing Parentheses

Incorrect:

```
<aui:option disabled="<%= (user != null) && user.isActive() || user.isFemale() %>" label="name" value="0" />
```

Correct:

```
<aui:option disabled="<%= (user != null) && (user.isActive() || user.isFemale()) %>" label="name" value="0" />
```

#### Example of Redundant Parentheses

Incorrect:

```
<aui:option label="name" selected="<%= (index == 0) %>" value="0" />
```

Correct:

```
<aui:option label="name" selected="<%= index == 0 %>" value="0" />
```