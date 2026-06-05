# JavaScript Unit Tests

## Trigger

Fires when both conditions hold:

1. One of the following changed:

	- JS or TS source with behavior intent — logic added, removed, or modified. Surface-only edits (renames, formatting, comments, jsdoc) do not fire this validation; the build's bundling step is enough.

	- A JS-relevant `package.json` key (`dependencies`, `devDependencies`, `scripts.build`, `scripts.test`).

	- A lockfile (`package-lock.json`, `yarn.lock`) — fires regardless of intent because a transitive-dependency pin can affect any code path.

1. The module's `package.json` declares a `"test"` script.

## Match

`^modules/.+\.(js|jsx|ts|tsx)$|^modules/.+/(package\.json|package-lock\.json|yarn\.lock)$`

## Selection

Locate the counterpart spec by parallel name: `Foo.tsx` → `Foo.test.tsx` or `Foo.spec.tsx`, often under a `__tests__` directory or a sibling `tests` tree. The module's `package.json` `jest.testMatch` or `testRegex` declares the exact convention — read it to confirm the spec lookup pattern. Verify each counterpart file exists before scheduling it.

**Lockfile changes.** When `package-lock.json` or `yarn.lock` is in the diff, run the module's full Jest suite regardless of size — parallel-name lookup yields nothing for a lockfile, and a transitive-dependency pin can affect any code path that touches the runtime, so the blast radius is the whole module.

**Deletion handling.** A deleted JS or TS file has no parallel-name counterpart to run, but its consumers can still break. Two layered checks:

1. **Consumer search.** For each deleted file, grep the surrounding module for `import` statements that reference the deleted basename or relative path. For every consumer found, queue the consumer's parallel-name spec (and the consumer's spec itself when it is the consumer). This catches direct-import breakage where a surviving spec imports a now-deleted source.

1. **High-risk-deletion full suite.** When a deletion lands under `__mocks__`, `__tests__`, `test`, or `tests`, fall back to the module's full Jest suite. Those locations are explicit Jest extension points (manual mocks, colocated specs, shared helpers, fixtures) whose removal can change runtime behavior for tests with no naming relationship to the deleted file.

**Cross-module consumer snapshots.** A change to a shared component can drift a snapshot stored in a *consumer* module, which parallel-name lookup inside the changed module misses. When the changed module is published as a package, expand across modules:

1. Read the changed module's `package.json` `name` (for example `@clayui/*` for clay packages).

1. Grep other modules' `package.json` `dependencies`/`devDependencies` for that name.

1. In each consumer with a `"test"` script, grep its `__snapshots__` and spec files for an import of the package; queue the matching suites — the consumer's full suite when the match is in a `__snapshots__` file.

Cap the consumer set at 8; when more modules depend on the package, note the blast radius rather than running them all.

## Command

Run only the selected spec(s):

```bash
(cd <module> && npm test -- <relative-spec-path>)
```

For lockfile changes or high-risk deletions, run the module's full suite instead:

```bash
(cd <module> && npm test)
```

## Checklist

Add one subitem per affected module:

```
- [ ] <module path>: <spec names, or "full suite" for lockfile or high-risk-deletion>
```

## Time Estimate

~30 sec - 2 min for targeted specs; the full module suite can be 5+ min for large modules.