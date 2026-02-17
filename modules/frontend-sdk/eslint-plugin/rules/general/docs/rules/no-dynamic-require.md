# Use only literal arguments in `require` calls (no-dynamic-require)

This rule prohibits non-literal arguments to `require()`.

## Rule Details

`require` calls should use static (string or template literal) arguments only. Dynamic arguments limit our options for static analysis.

Examples of **incorrect** code for this rule:

```js
const thing = require(`./plugins/${myPlugin}`);
const other = require('./plugins/' + otherPlugin);
```

Examples of **correct** code for this rule:

```js
const thing = require(`./plugins/thing`);
const other = require('./plugins/other');
```
