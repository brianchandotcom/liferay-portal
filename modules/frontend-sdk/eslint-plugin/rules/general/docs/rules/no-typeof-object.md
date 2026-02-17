# Check for `null` before typeof === 'object' (no-typeof-object)

`typeof null === 'object'` will evaluate to true, which can easily lead to issues in code. It is safest to explicitly check against null before this expression.

## Rule Details

Examples of **incorrect** code for this rule:

```js
typeof someVal === 'object';
```

Examples of **correct** code for this rule:

```js
someVal !== null && typeof someVal === 'object';
```

## See also

-   https://github.com/liferay/liferay-frontend-projects/issues/19
