# Group `import` statements and `require` calls (group-imports)

This rule enforces (and autofixes) that `import` statements and `require` calls &mdash; henceforth referred to as just "imports" &mdash; appear in the groups described in [our guidelines](https://github.com/liferay/liferay-frontend-guidelines/issues/60).

## Rule Details

Imports appear in groups of the following type, separated by a blank line:

1. "External" imports (ie. NodeJS built-in modules and dependencies specified in "package.json"; these do not start with "." or "..").
2. "Internal" imports (ie. local to the projects, starting with "." or "..").
3. "Side-effect-only" imports.
4. "Type-only" imports (ie. `import type {T} from 'thing';`), either from external modules or internal ones.

Additionally, any import that is preceded by a comment must be prefaced by a blank line for readability.

This rule just enforces the blank lines before and after each group. The ordering between groups, and within each group, is enforced separately, by the [sort-imports](./sort-imports.md) rule.

See the links in the [Further Reading](#further-reading) section below for the rationale behind this pattern.

## Examples

Examples of **incorrect** code for this rule:

```js
import {g, z} from 'one';
import {a} from 'other';
// Comment describing the next import.
import stuff from 'stuff';
import 'side-effect';
import x from './x';
```

Examples of **correct** code for this rule:

```js
import {g, z} from 'one';
import {a} from 'other';

// Comment describing the next import.
import stuff from 'stuff';

import 'side-effect';

import x from './x';
```

## Further Reading

-   [Liferay Frontend Guidelines for sorting imports](https://github.com/liferay/liferay-frontend-guidelines/issues/60)
