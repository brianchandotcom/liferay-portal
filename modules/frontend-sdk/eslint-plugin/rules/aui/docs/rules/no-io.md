# Avoid using `A.io.request('url')`

This rule prohibits using `A.io`.

## Rule Details

Examples of **incorrect** code for this rule:

```js
A.io.request('url');
```

Examples of **correct** code for this rule:

```js
Liferay.Util.fetch('url').then((response) => response.json());
```
