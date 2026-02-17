# Avoid using `A.one('selector')`

This rule prohibits using `A.one`, and suggests use of the native `document.querySelector` utility.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var listItem = A.one('.list-item');
```

Examples of **correct** code for this rule:

```js
const listItem = document.querySelector('.list-item');
```
