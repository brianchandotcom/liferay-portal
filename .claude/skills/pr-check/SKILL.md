---

allowed-tools: [Agent, Bash, Edit, Glob, Grep, Read, Skill, Write]
description: Checks that a PR is ready to be sent for review.
name: pr-check

---

# PR Check

Run pre-merge checks against the current branch. The skill iterates through the validations under `validations/`, runs each one whose trigger matches the diff, and reports PASS or FAIL. Integration tests, Playwright tests, and Poshi tests are out of scope. Use the `test-plan` skill when their coverage is needed.

## Preconditions

- **Branch rebased on local `master`.** Compare `git merge-base HEAD master` to `git rev-parse master`; when they differ, abort and tell the developer to rebase (`git rebase master`). The `format-source-current-branch` validation diffs against the local `master` tip to decide which files to format — when the branch's merge-base lags behind that tip, SF picks up reverse-direction diffs from the master commits the branch has not absorbed and the `<TICKET> SF` auto-commit captures unrelated rewrites.

- **Working tree clean.** `git status --porcelain` must return empty. When dirty, abort and ask the developer to commit first — the auto-commit steps inside drift validations would otherwise capture their uncommitted edits.

- **Diff baseline is local `master`.** The skill never fetches from a remote and does not compare against an `upstream/master` ref. Whether local `master` is up to date is the developer's call.

- **Diff is non-empty.** When the three-dot diff produces no files, exit with a one-line message — no validation produces useful signal on a clean branch.

## Input

### Diff

```bash
git diff --name-status "$(git merge-base HEAD master)...HEAD"
```

## Expected Output

**`PASS`** or **`FAIL`**.

The procedure runs in two passes over the validations, in the order below. The order is dependency-driven: drift first (later validations see the post-regen tree), then formatting, then build, then tests.

1. [Instance Wrapper Build](validations/instance-wrapper-build.md)

1. [REST Builder](validations/rest-builder.md)

1. [Service Builder](validations/service-builder.md)

1. [Source Format](validations/source-format.md)

1. [Full Portal Build](validations/full-portal-build.md)

1. [Per-Module Deploy](validations/per-module-deploy.md)

1. [JSP Compile](validations/jsp-compile.md)

1. [Integration Test Compile](validations/integration-test-compile.md)

1. [Workspace Build](validations/workspace-build.md)

1. [Poshi Syntax](validations/poshi-syntax.md)

1. [Structural Smoke](validations/structural-smoke.md)

1. [Java Unit Tests](validations/java-unit-test.md)

1. [JavaScript Unit Tests](validations/javascript-unit-test.md)

Process each validation in a subagent.

### Pass 1: Estimate

For each validation, read its file, evaluate the **Trigger** against the diff, and when it matches, add its **Time Estimate** to the running total. The output of this pass is the matched-validation list and the cumulative estimate.

When the total exceeds 20 minutes, surface the breakdown and ask the developer whether to trim a validation or proceed.

### Pass 2: Execute

For each matched validation from pass 1, run the **Command** and apply **Auto-Commit** when present. Record PASS or FAIL. Do not halt on FAIL — continue so the developer sees the full picture.

If all validations pass, report `PASS`. When any fail, report `FAIL` and surface the failed validations.