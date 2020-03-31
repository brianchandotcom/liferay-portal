## JSPFunctionNameCheck

Function names in `*.jsp` should conform `[a-z0-9][_a-zA-Z0-9]`

### Example

Incorrect:

```java
function <portlet:namespace />PrintPage() {
    window.open('<%= printPageURL %>');
}
```

Correct:

```java
function <portlet:namespace />printPage() {
    window.open('<%= printPageURL %>');
}
```