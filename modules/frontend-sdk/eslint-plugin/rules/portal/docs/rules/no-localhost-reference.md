# Disable referencing localhost explicitly (no-localhost-reference)

This rule enforces that no code should explicitly reference `localhost` as a literal value

## Rule Detail

Examples of **incorrect** code for this rule:

```js
const api = 'localhost:8080/some/path';
```

Examples of **correct** code for this rule:

```js
const api = window.location.hostname + '/some/path';
```
