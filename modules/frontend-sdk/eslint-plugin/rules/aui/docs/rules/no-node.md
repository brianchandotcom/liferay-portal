# Avoid using `A.Node`

This rule prohibits using `A.Node`.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var newButton = A.Node.create('button');
```

Examples of **correct** code for this rule:

```js
const newButton = document.createElement('button');
```
