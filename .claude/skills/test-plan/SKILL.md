---
name: test-plan
description: Generate a targeted local test plan for branch changes. Use when the user wants to know what tests to run before merging, asks for a test plan, wants to validate their changes locally, or mentions running tests for their branch. This skill analyzes commits on top of master and produces a runnable shell script with unit, integration, playwright, and poshi tests that fit within a 20-minute local run budget.
allowed-tools: [Read, Glob, Grep, Bash, Agent]
---

# Test Plan Generator

You generate a runnable shell script that executes the tests most likely to break given the current branch's changes compared to master.

The context: our test suite is massive — running it all takes hours. We merge aggressively and rely on a daily full test run (results within 24 hours of merge). This skill produces a focused test script (under 20 minutes) to mitigate risk before merging. It is not meant to catch everything — just the most likely breakages.

## How This Project Organizes Tests

Read the reference file for detailed test organization patterns:

```
${CLAUDE_SKILL_DIR}/references/test-organization.md
```

## Workflow

### 1. Understand the Changes

```bash
git diff master...HEAD --name-only
git log master..HEAD --oneline
```

Read the changed files to understand what the changes actually do — not just which files were touched, but what behavior changed. This understanding drives the test selection.

### 2. Identify What Could Break

Think about the blast radius of the changes:

- **API/interface changes** (portal-kernel, *-api modules): anything that depends on the changed API could break. Search for consumers.
- **Service implementation changes**: tests for that service, plus tests for features that depend on it.
- **Shared infrastructure changes** (e.g., a registry, a framework class, a base class): all modules that use that infrastructure could break. Find representative tests across the affected modules.
- **Mechanical/repetitive changes** (e.g., adding a property to 200 files): the core logic test is essential, but also include a representative sample of end-to-end tests from affected modules to verify the mechanical change doesn't break anything.
- **Web layer changes**: playwright and poshi tests for the affected UI.

The goal is not "find all tests in modules that were touched" — it's "find tests that exercise the code paths that changed."

### 3. Find the Tests

For each area that could break, search for test files. Use parallel Agent/Glob calls for speed. Read `${CLAUDE_SKILL_DIR}/references/test-organization.md` for the exact patterns and conventions.

**Verify every test file you list actually exists** using Glob before including it.

### 4. Prioritize for the 20-Minute Budget

Apply this priority order:

**Always include:**
1. Unit tests for directly changed code — fast (~5-15s per class), highest signal
2. Integration tests that directly test changed functionality
3. Tests that exercise the core logic change end-to-end

**Include if budget allows:**
4. Representative integration tests from affected downstream modules — pick a few that cover different usage patterns rather than running all of them
5. Playwright tests for affected web modules (~1-3 min per spec)

**Include if still within budget:**
6. Poshi tests (~2-5 min each)
7. More downstream module tests for broader coverage

When the change affects many modules (e.g., a framework change), don't try to test every single module. Pick a diverse sample that covers different usage patterns of the changed code.

### 5. Write the Script

Delete any existing `test.sh` in the repository root first, then write a new one. The script must be self-contained and runnable with `bash test.sh`. Use this structure:

```bash
#!/bin/bash
#
# Test plan for branch: <branch-name>
# Generated: <date>
# Estimated time: <X>m / 20m budget
#
# Changes: <N> commits, <N> files changed
# Affected areas: <list of module groups/components>
#

REPO_ROOT="$(cd "$(dirname "$0")" && pwd)"
EXIT_CODE=0

<test commands — one per line, no blank lines between them>

exit $EXIT_CODE
```

**Critical rules for the script:**
- Replace `<test commands>` with the actual test commands you discovered
- Every command must be suffixed with `|| EXIT_CODE=1` so failures are recorded but execution continues. The script exits with `$EXIT_CODE` at the end — 0 if all passed, 1 if any failed.
- Use `"$REPO_ROOT/gradlew" -p "$REPO_ROOT/modules"` for gradle tasks (gradlew is at the repo root)
- Use `npx --prefix "$REPO_ROOT/modules/test/playwright" playwright test` for playwright
- All test types (unit, integration, playwright, poshi) are included directly — the portal is assumed to be running
- Add a single-line comment before each command explaining why it was selected — do not repeat the test or module name in the comment, just state the reason.

After writing the script, run `chmod +x test.sh` to make it executable, and tell the user to run it with `./test.sh`.

## Guidelines

- Verify every test file exists using Glob before adding it to the script
- If changes are purely cosmetic (formatting, comments), say so and generate a script that just exits 0 with a header explaining why
