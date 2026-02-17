# `import` declarations should not use absolute paths (no-absolute-import)

This rule prohibits using an absolute path with `import` or `require()`.

## Rule Details

Examples of **incorrect** code for this rule:

```js
const thing = require(`/etc/thing`);
const other = require('/home/me/other');
import {x} from '/tmp/x';
```

Examples of **correct** code for this rule:

```js
const thing = require(`../../etc/thing`);
const other = require('other');
import {x} from 'x';
```
