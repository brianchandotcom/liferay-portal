# Prefer exporting named functions (no-anonymous-exports)

To improve debugging and readability, it is preferable to export named functions to that they can be located in a callstack.

## Rule Details

Examples of **incorrect** code for this rule:

```js
export const x = () => {};

export default () => {};
```

Examples of **correct** code for this rule:

```js
export function x() {}

export default function x() {}
```

## See also

-   https://github.com/liferay/liferay-frontend-projects/issues/25
