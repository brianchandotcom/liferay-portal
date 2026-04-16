---
name: test-plan
description: Generate a targeted local test plan for branch changes. Use when the user wants to know which tests to run before merging, asks for a test plan, wants to validate changes locally, or mentions running tests for their branch. This skill analyzes commits on top of master and produces a runnable shell script covering Unit, Integration, Playwright, and Poshi tests within a 20-minute local budget.
allowed-tools: [Agent, Bash, Glob, Grep, Read]
---

# Test Plan Generator

Produce a runnable shell script that executes the tests most likely to regress given the current branch's changes compared to master.

The full test suite takes hours to run, so the team merges aggressively and relies on a daily full-suite run that delivers results within 24 hours of merge. This skill produces a focused pre-merge script (under 20 minutes) that mitigates risk without attempting full coverage — its goal is to catch the most likely regressions, not every possible one.

## How This Project Organizes Tests

Consult the reference file for detailed test organization patterns:

```
${CLAUDE_SKILL_DIR}/references/test-organization.md
```

## Workflow

### 1. Understand the Changes

```bash
git diff master...HEAD --name-only
git log master..HEAD --oneline
```

Read the changed files to understand what the changes actually do — not merely which files were touched, but what behavior changed. This understanding drives the test selection.

### 2. Identify What Could Regress

Consider the blast radius of each change:

- **API or Interface Changes** (`portal-kernel`, `*-api` modules): Anything that depends on the changed API could regress. Search for consumers.
- **Mechanical or Repetitive Changes** (e.g., adding a property across 200 files): The core logic test is essential. Also include a representative sample of end-to-end tests from affected modules to verify the mechanical change does not introduce regressions.
- **Service Implementation Changes**: Tests for the service itself, along with tests for features that depend on it.
- **Shared Infrastructure Changes** (e.g., a registry, a framework class, a base class): Every module that relies on that infrastructure could regress. Select representative tests spanning the affected modules.
- **Web Layer Changes**: Playwright and Poshi tests for the affected UI.

The objective is not to "find every test in modules that were touched" — it is to find tests that exercise the code paths that changed.

### 3. Find the Tests

For each area that could regress, search for test files. Use parallel Agent and Glob calls for speed. Consult `${CLAUDE_SKILL_DIR}/references/test-organization.md` for the exact patterns and conventions.

**Verify every test file exists** using Glob before including it in the plan.

### 4. Prioritize Within the 20-Minute Budget

Apply the following priority order:

**Always include:**

1. Unit tests for directly changed code — fast (approximately 5–15 seconds per class) and highest signal.
2. Integration tests that directly exercise the changed functionality.
3. Tests that exercise the core logic change end-to-end.

**Include when budget allows:**

4. Representative integration tests from affected downstream modules — choose a few that cover distinct usage patterns rather than running them all.
5. Playwright tests for affected web modules (approximately 1–3 minutes per spec).

**Include when still within budget:**

6. Poshi tests (approximately 2–5 minutes each).
7. Additional downstream module tests for broader coverage.

When the change affects many modules (for example, a framework change), do not attempt to test every single module. Choose a diverse sample that covers different usage patterns of the changed code.

### 5. Write the Script

Delete any existing `test.sh` at the repository root, then write a new one. The script must be self-contained and runnable via `bash test.sh`. Use this structure:

```bash
#!/bin/bash
#
# Test plan for branch: <branch-name>
# Generated: <date>
# Estimated time: <X>m / 20m budget
#
# Changes: <N> commits, <N> files changed
# Affected areas: <list of module groups or components>
#

REPO_ROOT="$(cd "$(dirname "${0}")" && pwd)"
EXIT_CODE=0

<test commands — one per line, no blank lines between them>

exit ${EXIT_CODE}
```

**Critical rules for the script:**

- Replace `<test commands>` with the actual commands discovered during selection.
- Suffix every command with `|| EXIT_CODE=1` so failures are recorded without halting execution. The script exits with `${EXIT_CODE}` at the end — `0` when all tests pass, `1` when any fail.
- Use `"${REPO_ROOT}/gradlew" --project-dir "${REPO_ROOT}/modules"` for Gradle tasks (the `gradlew` wrapper lives at the repository root).
- Use `npx --prefix "${REPO_ROOT}/modules/test/playwright" playwright test` for Playwright.
- All test types (Unit, Integration, Playwright, Poshi) run directly — the portal is assumed to be running.
- Precede each command with a single-line comment explaining why it was selected. State the rationale; do not restate the test name or module.

After writing the script, mark it executable with `chmod +x test.sh` and instruct the user to run it via `./test.sh`.

## Guidelines

- Verify every test file exists with Glob before adding it to the script.
- When the changes are purely cosmetic (formatting, comments), say so and generate a script that exits `0` with a header explaining why.
