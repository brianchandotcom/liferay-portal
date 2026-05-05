---

argument-hint: "<component-name>"
description: Shrink a Liferay component's Poshi test suite by merging overlapping tests. Use when the user asks to reduce, merge, or clean up Poshi tests for a @component-name.
name: poshi-shrink

---

# Shrink Poshi Tests

A playbook for shrinking a Liferay component's Poshi test suite before migrating tests to Playwright, Java integration, or unit layer. Typical outcome: a file with ~27 tests ends up with ~8, in ~24 small reviewable commits.

## Preconditions

- The working tree is clean. Abort and ask the user to commit or stash first when dirty.

## Input

### Component Name

The `@component-name` annotation, passed as `${ARGUMENTS}` (e.g., `portal-analytics-cloud`, `portal-commerce`, `portal-content-management`). Ask if missing; do not guess.

Verify the component by enumerating, under `portal-web/test/functional/com/liferay/portalweb/tests/enduser/`, every `.testcase` whose `@component-name` matches; abort when none does. The same enumeration feeds the **Shrink Plan**: per file, capture the path, the `testray.main.component.name` value, and the test-block count.

## Expected Output

### Shrink Plan

Build the plan via plan mode (`EnterPlanMode`) using this format:

```markdown
## Inventory

| File | Test Count | testray.main.component.name |
| --- | --- | --- |
| <path> | <N> | <component> |

## Files to Attack

### <file path> (N → M)

> **Group A — <Context name>** (N → 1):
>
> - Rename `<Keeper>` → `<FinalName>` (commit 1)
> - Merge `<Source2>` → adds `<assertion>` (commit 2)
> - Merge `<Source3>` → adds `<assertion>` (commit 3)
> - … etc

### <next file path> (N → M)

…
```

Sort the inventory ascending by test count so small files (easy wins) surface first. Avoid files with 40+ tests on the first pass.

Pick the **keeper** for each group: the test with the most comprehensive assertions, or the clearest name. The **final name** is usually descriptive of the combined behaviour (e.g., `ContentPerformancePanelInBlogDisplayPage`, `LanguageDropdownInContentPage`), typically different from the keeper's original name.

Common merge-worthy signals — classic patterns from Liferay test names:

- Multiple `AuthorNotShowIn<Context>` tests — usually one per panel-context is enough.
- Multiple `MetricsIconVisibleIn<Context>` + `PanelInformationIn<Context>` — the first checks title+traffic, the second title+URL+language; huge overlap.
- `CheckAllInfo*` or similar catch-all tests that duplicate specific-field tests.
- Tests that share the full `setUp` + first N tasks (navigate, create blog, open panel) and differ only in the final assertion.

#### Merge Signals — DO

- Same setup + same UI surface + different assertion → one test with N assertion tasks.
- Tests differing only in asset type (blog vs document vs web content) when the panel behaviour being asserted is identical — often fully redundant; propose deletion rather than merge.
- Tests where the source's assertion is already fully covered in the target — commit still happens, it just deletes the source.

#### Merge Signals — DON'T

- Tests with test-level property overrides that differ: `property portal.upstream = "quarantine"`, `property test.liferay.virtual.instance = "false"`, `property test.run.type = "single"` (when not inherited).
- Tests with special setup (localized URLs, fragment translations, system settings changes, extra page creation).
- Tests with `@ignore` / `@skip` annotations.

### Shrunk Test Files

After `ExitPlanMode` returns the user's approval, apply each operation in the order it appears in the plan and commit after each one with the `/commit` skill — one commit per operation, never squashed. The per-merge granularity is exactly what makes the diff reviewable.

- **Rename** — change the keeper's `test <OldName>` to `test <FinalName>` with no other edits. Commit message: `<TICKET> Rename test <Keeper> to <FinalName>`.
- **Merge** — delete the source's `test <Source> { ... }` block and fold its **unique** assertions into the target as new `task` blocks. When the source's assertions are already fully covered by the target, simply delete the source. Commit message: `<TICKET> Merge test <Source> into <FinalName>`.

When the file convention keeps tests alphabetical, add a final `<TICKET> Alphabetic order` commit after all merges.

### Summary

After the file is shrunk, report:

- The file shrunk and its `testray.main.component.name`.
- Before/after test counts.
- Total commits made (renames + merges + optional cleanups).
- Tests kept intact and the reason (special setup, differing properties, `@ignore`).

## Anti-Patterns

- **Never rewrite history on pushed/shared branches** unless the user explicitly asks.
- **Don't trust the LPS ticket to identify the fix location blindly** — sometimes the ticket only added the Poshi test and the real fix is elsewhere.
- **Don't drop strong assertions when merging.** When one test uses `AssertTextEquals.assertPartialText("web/<site-path>")` and another uses a weak `AssertVisible value1="http://"`, keep the strong one.
- **Don't merge tests across different property/setup profiles.** Those properties exist for a reason (flaky environment, quarantined, virtual-instance-specific).

## Heuristics

- Identical panel assertions across four asset-type variants (blog / document / widget / content page) are usually overengineered — **one representative often suffices**; propose deletion for the rest instead of merging four tests into four tests.
- The *keeper* does not have to keep its name. Pick the most comprehensive test as the starting point, then rename it.
- When a merge target already has an assertion the source also has, the merge commit is still a valid "documents-that-this-was-redundant" commit.