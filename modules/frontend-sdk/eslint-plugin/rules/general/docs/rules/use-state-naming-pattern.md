# Naming pattern for useState should be consistent (use-state-naming-pattern)

This rule enforces (and autofixes) that destructured values from useState follow the pattern `[val, setVal]`(val can be anything).

## Rule Details

Examples of **incorrect** code for this rule:

```js
const [value, updateValue] = useState();
```

Examples of **correct** code for this rule:

```js
const [value, setValue] = useState();
```
