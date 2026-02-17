# Avoid using `A.getBody()`

This rule prohibits using `A.getBody()`.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var body = A.getBody();
```

Examples of **correct** code for this rule:

```js
const body = document.body;
```
