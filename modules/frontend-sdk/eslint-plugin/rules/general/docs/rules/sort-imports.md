# Sort `import` statements and `require` calls (sort-imports)

This rule enforces (and autofixes) that `import` statements and `require` calls &mdash; henceforth referred to as just "imports" &mdash; appear in the order described in [our guidelines](https://github.com/liferay/liferay-frontend-guidelines/issues/60).

## Rule Details

Imports appear in the following order:

1. "External" imports (ie. NodeJS built-in modules and dependencies specified in "package.json"; these do not start with "." or "..").
2. "Internal" imports (ie. local to the projects, starting with "." or "..");
3. "Type-only" imports (ie. `import type {A} from 'thing';`), first external ones and then internal ones.

Within each group, we sort lexicographically (and case-sensitively) by the module source (ie. the thing on the right-hand side).

Between groups, we expect a blank line, but that is enforced separately, by the [group-imports](./group-imports.md) rule.

Likewise, we expect imports to appear at the top of the file, before other statements, but that too is enforced separately, by the [imports-first](./imports-first.md) rule.

See the links in the [Further Reading](#further-reading) section below for the rationale behind this pattern.

### A note about side-effect-only imports

> **NOTE:** Imports like those in the following example are made for side-effects only and _cannot_ be reordered because we don't know statically what may depend on those side-effects occurring in a particular order.

```javascript
// A side-effect-only import:
imports 'thing';

// A side-effect-only require:
require('other');
```

Any time this rule encounters such an import, it considers it a boundary and, generally, will not reorder any imports across that boundary. (Type-only imports are a special case, because they are independent of evaluation order and can always be safely moved to the end.)

## Examples

Examples of **incorrect** code for this rule:

```js
import {g, z} from 'one';
import x from './x';
import {a} from 'other';
```

Examples of **correct** code for this rule:

```js
import {g, z} from 'one';
import {a} from 'other';
import x from './x';
```

## Further Reading

-   [Liferay Frontend Guidelines for sorting imports](https://github.com/liferay/liferay-frontend-guidelines/issues/60)
