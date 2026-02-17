# Avoid using `A.Object` utilities

This rule prohibits using `A.Node` and proposes the use of the native Object class instead.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var objectKeys = A.Object.keys(object);
```

Examples of **correct** code for this rule:

```js
const objectKeys = Object.keys(object);
```
