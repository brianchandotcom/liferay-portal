# Avoid using `A.all('selector')`

This rule prohibits using `A.all`, and suggests use of the native `document.querySelectorAll` utility.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var listItems = A.all('.list-item');
```

Examples of **correct** code for this rule:

```js
const listItems = document.querySelectorAll('.list-item');
```
