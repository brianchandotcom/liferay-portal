---
name: poshi-consolidate
description: Triage and consolidate Poshi .testcase files for a given Liferay @component-name before migrating to Playwright, Java integration, or unit tests. Enumerates tests by component, groups by testray.main.component.name, identifies overlapping tests within a file, and drives the consolidation via reviewable per-merge commits. Use when the user asks to migrate, consolidate, merge, triage, or reduce Poshi tests for their team's component.
---

# Poshi Test Consolidation

A playbook for shrinking a Liferay component's Poshi test suite before migrating tests to Playwright, Java integration, or unit layer. Typical outcome: a file with ~27 tests ends up with ~8, in ~24 small reviewable commits.

## Inputs required from the user

Before doing anything, confirm:

- **`@component-name`**: the annotation at the top of `.testcase` files (e.g., `portal-analytics-cloud`, `portal-knowledge-base`, `portal-commerce`). Check the first line of any `.testcase` file if unsure.
- **LPD ticket prefix**: for commit messages (e.g., `LPD-87004`). Use `LPD-X` if none yet.

If either is not in the skill args, ask. Do not guess.

## Workflow

### Step 1 — Triage all tests for the component

From `portal-web/test/functional/com/liferay/portalweb/tests/enduser/`, list every `.testcase` file tagged with the component, its `testray.main.component.name`, and its test count. Sort ascending by component, then by test count, so small files (easy migration wins) surface first.

```bash
cd portal-web/test/functional/com/liferay/portalweb/tests/enduser
find . -name "*.testcase" | while read f; do
  if grep -q '@component-name = "<COMPONENT>"' "$f"; then
    component=$(grep -oE 'testray\.main\.component\.name = "[^"]*"' "$f" | head -1 | sed -E 's/.*= "([^"]*)"/\1/')
    count=$(grep -cE '^[[:space:]]*test [A-Za-z0-9_]+ \{' "$f")
    printf "%s\t%s\t%d\n" "${f#./}" "${component:-(none)}" "$count"
  fi
done | sort -t$'\t' -k2,2 -k3,3n
```

Save a triage doc at repo root (`<component>-test-triage.md`) with:
- Totals by `testray.main.component.name` (files + tests).
- Full table of files, component, test count.
- "Quick-win candidates" section: files with 1–2 tests.

### Step 2 — Optional bucket-classification per test

For each test, look up its LPS/LPD ticket from `@description` in `git log` and see what files the ticket actually changed. Helps decide the target layer later.

| Bucket | Signal |
|--------|--------|
| `integration-java` | Ticket changed Java in service/validator/permission layer |
| `rest-integration` | Ticket changed Java in `*-rest-impl` |
| `playwright` | Ticket changed only JSP/JS/CSS/fragment files |
| `needs-review` | Ticket only added the Poshi test, or only language keys |
| `inconclusive` | Ticket not in the repo's git log |
| `no-ticket` | No LPS/LPD reference in the description |

This step is useful but not required for the consolidation itself.

### Step 3 — Pick a file and find merge groups

Start with files showing visually obvious overlap — classic signals from Liferay test names:

- Multiple `AuthorNotShowIn<Context>` tests — usually one per panel-context is enough.
- Multiple `MetricsIconVisibleIn<Context>` + `PanelInformationIn<Context>` — the first checks title+traffic, the second title+URL+language; huge overlap.
- `CheckAllInfo*` or similar catch-all tests that duplicate specific-field tests.
- Tests that share the full `setUp` + first N tasks (navigate, create blog, open panel) and differ only in the final assertion.

Avoid files with 40+ tests on the first pass — practice on smaller ones first.

#### Merge signals — DO

- Same setup + same UI surface + different assertion → one test with N assertion tasks.
- Tests differing only in asset type (blog vs document vs web content) when the panel behaviour being asserted is identical — often fully redundant; propose deletion rather than merge.
- Tests where the source's assertion is already fully covered in the target — commit still happens, it just deletes the source.

#### Merge signals — DON'T

- Tests with test-level property overrides that differ: `property portal.upstream = "quarantine"`, `property test.liferay.virtual.instance = "false"`, `property test.run.type = "single"` (when not inherited).
- Tests with special setup (localized URLs, fragment translations, system settings changes, extra page creation).
- Tests with `@ignore` / `@skip` annotations.

### Step 4 — Plan and get approval

Never start editing before the user has seen and approved the consolidation plan. Present it clearly:

> **Group A — <Context name>** (N → 1):
> - Rename `<Keeper>` → `<FinalName>` (commit 1)
> - Merge `<Source2>` → adds `<assertion>` (commit 2)
> - Merge `<Source3>` → adds `<assertion>` (commit 3)
> - ... etc

Pick the **keeper**: the test with the most comprehensive assertions, or the clearest name. The **final name** is usually descriptive of the combined behaviour (e.g., `ContentPerformancePanelInBlogDisplayPage`, `LanguageDropdownInContentPage`), typically different from the keeper's original name.

### Step 5 — Execute with one commit per operation

For each group, make one commit per source test. Message conventions:

- `<TICKET> Rename test <Keeper> to <FinalName>` — pure rename; first commit of the group; no logic change.
- `<TICKET> Merge test <Source> into <FinalName>` — delete the source test block; add its unique assertions as new `task` blocks in the target.

If a source's assertions are already fully covered by the target, still make the "Merge" commit (it simply deletes the source — documents the consolidation).

Do **not** squash, bundle, or batch these commits. The per-merge granularity is exactly what makes the diff reviewable.

After each commit:
```bash
git add <path>
git commit -m "<TICKET> Merge test <Source> into <FinalName>"
```

### Step 6 — Optional cleanup

- If the file convention keeps tests alphabetical, add a final `<TICKET> Alphabetic order` commit after all merges.
- If commits come out unsigned despite `commit.gpgsign=true`, the user's SSH signer (1Password, etc.) may have been locked. Re-sign with `git rebase --exec 'git commit --amend --no-edit -S' <base>` — user must approve in their signer.

## Anti-patterns to avoid

- **Never start editing without an approved plan.** Always present the grouping first.
- **Never rewrite history on pushed/shared branches** unless the user explicitly asks.
- **Don't trust the LPS ticket to identify the fix location blindly** — sometimes the ticket only added the Poshi test and the real fix is elsewhere.
- **Don't drop strong assertions when merging.** When one test uses `AssertTextEquals.assertPartialText("web/<site-path>")` and another uses a weak `AssertVisible value1="http://"`, keep the strong one.
- **Don't merge tests across different property/setup profiles.** Those properties exist for a reason (flaky environment, quarantined, virtual-instance-specific).
- **Don't guess the ticket prefix.** Ask if not provided.

## Heuristics learned from prior runs

- Identical panel assertions across four asset-type variants (blog / document / widget / content page) are usually overengineered — **one representative often suffices**; propose deletion for the rest instead of merging four tests into four tests.
- The *keeper* does not have to keep its name. Pick the most comprehensive test as the starting point, then rename it.
- When a merge target already has an assertion the source also has, the merge commit is still a valid "documents-that-this-was-redundant" commit.

## Reference example

Completed run: `portal-analytics-cloud` / `Content Performance` / `ContentPerformance.testcase`.

- Before: 27 tests.
- After: 8 tests (70% reduction).
- Commits: 24 (~5 renames + ~18 merges + 1 deletion-of-redundant + 1 alphabetic cleanup).
- Groups consolidated:
  - Blog Display Page panel: 5 → 1
  - Content Page panel: 8 → 1
  - Document Display Page panel: 4 → 0 (subsumed by Blog Display Page panel — identical assertions, only asset type differed)
  - Widget Page panel: 3 → 1
  - Language Dropdown: 3 → 1
- Kept intact: 4 tests with unique setup (localized URL config, fragment translations, extra page creation).

The triage doc from that run (`analytics-cloud-test-triage.md` at repo root) is a good template for structure.
