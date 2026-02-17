# Avoid using `A.merge`

This rule prohibits using `A.merge()` and proposes the usage of `Object.assign()`.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var mergedObject = A.merge(object1, object2);
```

Examples of **correct** code for this rule:

```js
const mergedObject = Object.assign(object1, object2);
```
