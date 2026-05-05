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

The `@component-name` annotation, passed as `${ARGUMENTS}` (e.g., `portal-analytics-cloud`, `portal-knowledge-base`, `portal-commerce`). Ask if missing; do not guess.

Verify the component by enumerating, under `portal-web/test/functional/com/liferay/portalweb/tests/enduser/`, every `.testcase` whose `@component-name` matches; abort when none does. The same enumeration feeds the **Triage Report**: per file, capture the path, the `testray.main.component.name` value, and the test-block count, sorted by component then by ascending test count.

## Expected Output

### Triage Report

A `<component>-test-triage.md` saved at repo root. Lists every `.testcase` tagged with the component, grouped by `testray.main.component.name`, with optional bucket classification per test.

The bucket signals the most likely target layer for a future migration:

| Bucket | Signal |
| --- | --- |
| `integration-java` | Ticket changed Java in service/validator/permission layer |
| `rest-integration` | Ticket changed Java in `*-rest-impl` |
| `playwright` | Ticket changed only JSP/JS/CSS/fragment files |
| `needs-review` | Ticket only added the Poshi test, or only language keys |
| `inconclusive` | Ticket not in the repo's git log |
| `no-ticket` | No LPS/LPD reference in the description |

To classify a test, look up its LPS/LPD ticket from `@description` in `git log` and inspect which files that ticket actually changed. Classification is optional; when skipped, leave the **Bucket** column blank in the **Test Details** table and the **By Bucket** counts at zero.

The report follows this template:

````markdown
# <Component Title> Test Triage Report

**Scope:** All Poshi tests tagged `@component-name = "<component>"`
**Date:** YYYY-MM-DD
**Method:** Enumerated <N> `.testcase` files, parsed <N> test blocks for priority and ticket references, looked up ticket commits in git log, classified buckets by changed file paths.

## Summary

- **Total test blocks:** <N>
- **Unique tickets referenced:** <N>

### By Bucket

| Bucket | Count | Meaning |
| --- | --- | --- |
| integration-java | <N> | Rewrite as Java integration test against service/validator/permission layer |
| rest-integration | <N> | Rewrite as REST-layer integration test in `*-rest-test` |
| playwright | <N> | Keep as functional; migrate to Playwright (not Java-replaceable) |
| needs-review | <N> | Git log alone not conclusive; must read the test body |
| inconclusive | <N> | Ticket not in this repo's git history (often LPD-internal) |
| no-ticket | <N> | Test description has no LPS/LPD reference; must read the test body |

### By Priority

| Priority | Count |
| --- | --- |
| 5 | <N> |
| 4 | <N> |
| 3 | <N> |
| 2 | <N> |
| 1 | <N> |

## Test Details

| File | Test | Priority | Ticket | Bucket | Notes |
| --- | --- | --- | --- | --- | --- |
| <file> | <test> | <priority> | <ticket> | <bucket> | <notes> |

## Files by Component & Test Count

Classification based on `property testray.main.component.name` in each `.testcase`. Sorted ascending by test count within each component so small files (easy migration wins) appear first.

### Totals by Component

| Component | Files | Tests |
| --- | --- | --- |
| <component> | <N> | <N> |
| **Total** | **<N>** | **<N>** |

### Quick-win candidates (1–2 tests, <N> files)

| Component | File | Tests |
| --- | --- | --- |
| <component> | <file> | <N> |

### <Component Name> (<N> files, <N> tests)

| File | Tests |
| --- | --- |
| <file> | <N> |
````

Sort the **Test Details** rows by bucket order (`integration-java`, `rest-integration`, `playwright`, `needs-review`, `inconclusive`, `no-ticket`), then priority descending, then file path and test name. Leave the **Ticket** cell blank when there is none. **Notes** is a short reason taken from the bucket lookup, for example "service/validator/permission impl" or "UI/JSP/frontend fix".

### Merge Plan

For the file the user picks to attack, present the grouping clearly and **wait for explicit approval before any edit**:

> **Group A — <Context name>** (N → 1):
>
> - Rename `<Keeper>` → `<FinalName>` (commit 1)
> - Merge `<Source2>` → adds `<assertion>` (commit 2)
> - Merge `<Source3>` → adds `<assertion>` (commit 3)
> - … etc

Pick the **keeper**: the test with the most comprehensive assertions, or the clearest name. The **final name** is usually descriptive of the combined behaviour (e.g., `ContentPerformancePanelInBlogDisplayPage`, `LanguageDropdownInContentPage`), typically different from the keeper's original name.

Start with files showing visually obvious overlap — classic signals from Liferay test names:

- Multiple `AuthorNotShowIn<Context>` tests — usually one per panel-context is enough.
- Multiple `MetricsIconVisibleIn<Context>` + `PanelInformationIn<Context>` — the first checks title+traffic, the second title+URL+language; huge overlap.
- `CheckAllInfo*` or similar catch-all tests that duplicate specific-field tests.
- Tests that share the full `setUp` + first N tasks (navigate, create blog, open panel) and differ only in the final assertion.

Avoid files with 40+ tests on the first pass — practice on smaller ones first.

#### Merge Signals — DO

- Same setup + same UI surface + different assertion → one test with N assertion tasks.
- Tests differing only in asset type (blog vs document vs web content) when the panel behaviour being asserted is identical — often fully redundant; propose deletion rather than merge.
- Tests where the source's assertion is already fully covered in the target — commit still happens, it just deletes the source.

#### Merge Signals — DON'T

- Tests with test-level property overrides that differ: `property portal.upstream = "quarantine"`, `property test.liferay.virtual.instance = "false"`, `property test.run.type = "single"` (when not inherited).
- Tests with special setup (localized URLs, fragment translations, system settings changes, extra page creation).
- Tests with `@ignore` / `@skip` annotations.

### Edited Test Files

After approval, apply each operation in the order it appears in the plan:

- **Rename** — change the keeper's `test <OldName>` to `test <FinalName>` with no other edits.
- **Merge** — delete the source's `test <Source> { ... }` block and fold its **unique** assertions into the target as new `task` blocks. When the source's assertions are already fully covered by the target, simply delete the source.

### Per-Merge Commits

One commit per operation — never squash. Use the `/commit` skill. Message templates:

- `<TICKET> Rename test <Keeper> to <FinalName>` — pure rename; first commit of the group; no logic change.
- `<TICKET> Merge test <Source> into <FinalName>` — delete the source test block; add its unique assertions as new `task` blocks in the target.

The per-merge granularity is exactly what makes the diff reviewable. Do **not** bundle multiple merges into one commit.

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