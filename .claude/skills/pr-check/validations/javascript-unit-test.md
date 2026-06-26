# JavaScript Unit Tests

## Trigger

Fires when both conditions hold:

1. One of these changed:

	- JS or TS source with behavior intent — logic added, removed, or modified. Surface-only edits (renames, formatting, comments, jsdoc) do not fire this validation; the build's bundling step is enough.

	- A JS-relevant `package.json` key (`dependencies`, `devDependencies`, `scripts.build`, `scripts.test`).

	- A lockfile (`package-lock.json`, `yarn.lock`) — fires regardless of intent because a transitive-dependency pin can affect any code path.

1. The module's `package.json` declares a `"test"` script.

## Match

`^modules/.+\.(js|jsx|ts|tsx)$|^modules/.+/(package\.json|package-lock\.json|yarn\.lock)$`

## Selection

Run the module's **full Jest suite** for any matched change — do not select individual specs by name.

**Cross-module consumer snapshots.** The changed module's own suite does not cover a snapshot stored in a *consumer* module. When the changed module is published as a package, also run affected consumers:

1. Read the changed module's `package.json` `name` (for example `@clayui/*` for clay packages).

1. Grep other modules' `package.json` `dependencies`/`devDependencies` for that name.

1. In each consumer with a `"test"` script, grep its `__snapshots__` and spec files for an import of the package; run that consumer's full suite when a match is found.

Cap the consumer set at 8; when more modules depend on the package, note the blast radius rather than running them all.

**Oversized suites.** When a module's suite is large enough to blow the time budget, fall back to the parallel-name specs plus every changed file under the module's `test`, `tests`, or `__tests__` tree, and note the reduced scope in the result.

## Command

Run the module's full suite:

```bash
(cd <module> && npm test)
```

## Checklist

Add one subitem per affected module:

```
- [ ] <module path>: full suite
```

## Time Estimate

~1 - 5 min per module suite; a large module can exceed 5 min, which the oversized-suite fallback bounds.