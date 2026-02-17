# No duplicate `import` statements (no-duplicate-imports)

This rule enforces that there is at most one `import` statement for a given module in each file. There is no autofix because it is hoped that this is a relative infrequent mistake.

## Rule Details

Examples of **incorrect** code for this rule:

```js
import {g, z} from 'one';
import x from './x';
import {a} from 'one';
```

Examples of **correct** code for this rule:

```js
import {a, g, z} from 'one';
import x from './x';
```

Note the one exception to this rule, that in TypeScript files it is permitted (and sometimes necessary) to have two imports from a module, one that imports _values_ and another that imports _types_:

```ts
import foo from 'foo';
import type {bar} from 'foo';
```
