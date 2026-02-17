# Avoid using `A.Array` and its utilities

This rule prohibits using `A.Array` and its utilities.

## Rule Details

Examples of **incorrect** code for this rule:

```js
A.Array.remove(array, itemIndex);
```

Examples of **correct** code for this rule:

```js
array.splice(itemIndex, 1);
```
