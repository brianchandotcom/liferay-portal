# Avoid using `new A.Url('url')`

This rule warns against using `A.Url`, and suggests use of the native `URL` class.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var url = new A.Url('url');
```

```js
var url = A.Url('url');
```

Examples of **correct** code for this rule:

```js
const url = new URL('url');
```
