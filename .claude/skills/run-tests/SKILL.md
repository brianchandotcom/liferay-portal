---
allowed-tools: [Bash, Glob, Grep, Read]
argument-hint: "[unit|integration|all] [TestClassName]"
description: Run unit and/or integration tests for the modules that were changed in this branch or conversation. Use when the user asks to run tests, verify changes, or invokes /run-tests.
name: run-tests
---

# Run Tests

Run unit and/or integration tests for the modules affected by the current changes.

## 1. Determine Scope

Parse `${ARGUMENTS}`:

- First token (optional): `unit`, `integration`, or `all` (default: `all`)
- Second token (optional): a specific test class name to run — skip discovery and run only that class

## 2. Find Changed Module Roots

If no specific test class was given, find which modules have changed files:

```bash
git diff master...HEAD --name-only
```

For each changed Java file under `modules/`, walk up its directory tree to find the nearest directory containing `build.gradle`. That is the module root. Its parent directory is the **component root** (e.g. `multi-factor-authentication/`). Collect unique component roots.

If `git diff` yields nothing (working tree changes only), fall back to:

```bash
git diff --name-only
```

## 3. Find gradlew

Walk up from any module root until you find a directory containing `gradlew`. Use that path as `<gradlew>`.

## 4. Discover Test Classes

Modules are grouped into components. The **component root** is the parent directory of the module root (e.g. `multi-factor-authentication/` is the component root for `multi-factor-authentication-email-otp-web/`).

Search every subdirectory of the component root that contains a `build.gradle` — not just the changed module:

```bash
# find all module dirs within the component root
find <component-root> -maxdepth 1 -mindepth 1 -type d | while read module; do
    [ -f "$module/build.gradle" ] || continue
    find "$module/src/test/java" -name "*Test.java" 2>/dev/null
    find "$module/src/testIntegration/java" -name "*Test.java" 2>/dev/null
done | sort
```

This automatically covers sibling modules, dedicated `-test` modules, and any other module in the component that may exercise the changed code.

Extract the simple class name (filename without `.java`) and the module root for each test file found. Group by module root for the next step.

If no test files are found anywhere in the component, note that in the output.

## 5. Run the Tests

Run from the module root. For each module with tests:

**Unit tests:**
```bash
cd <module-root> && <gradlew> test --tests <TestClassName> --daemon
```

**Integration tests** (requires a running portal):
```bash
cd <module-root> && <gradlew> testIntegration --tests <TestClassName> --daemon
```

When multiple test classes exist in the same module, run them in a single invocation using comma-separated `--tests` flags:
```bash
<gradlew> test --tests Foo --tests Bar --daemon
```

## 6. Report Results

After all test runs, summarize:

- Which modules were tested
- Which test classes ran
- Pass / fail status for each
- Full output for any failures so the user can diagnose them
