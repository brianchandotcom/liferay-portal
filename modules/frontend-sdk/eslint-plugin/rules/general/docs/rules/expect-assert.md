# Every expect should assert something (expect-assert)

In our unit tests, we want to make sure we actually assert something for every expect.

## Rule Details

Examples of **incorrect** code for this rule:

```js
expect(getByText('report'));
expect(getByRole('button'));
```

Examples of **correct** code for this rule:

```js
expect(getByText('report')).toBeVisible();
expect(getByRole('button')).toBeVisible();
```
