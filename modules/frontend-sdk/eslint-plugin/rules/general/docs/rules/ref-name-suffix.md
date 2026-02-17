# Naming pattern for useRef hook (ref-name-suffix)

This rule enforces (and autofixes) `useRef` hooks from React so that the variable name is suffixed with `Ref`

## Rule Details

Examples of **incorrect** code for this rule:

```js
const node = useRef(null);
```

Examples of **correct** code for this rule:

```js
const nodeRef = useRef(null);
```
