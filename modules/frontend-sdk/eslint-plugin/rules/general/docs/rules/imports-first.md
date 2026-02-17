# `import` statements and `require` calls should be at the top (imports-first)

This rule enforces that `import` statements and `require` calls &mdash; henceforth referred to as just "imports" &mdash; appear at the top of the file, before other statements.

## Rule Details

This rule complains if it finds an import preceded in a file by any non-import statement. Because this is expected to be relatively rare, no auto-fix is provided.

An exception to this rule are `require` calls that occur below the top-level scope (for example, inside functions).

See also the related rules for ordering imports in relation to one another ([sort-imports](./sort-imports.md)) and for organizing them into groups ([group-imports](./group-imports.md)).

## Examples

Examples of **incorrect** code for this rule:

```js
const NAME = 'thing';

import x from './x';
```

Examples of **correct** code for this rule:

```js
import x from './x';

const NAME = 'thing';

function init() {
	// This require() is fine, because
	// it is not at the top-level.
	require('other')();
}
```
