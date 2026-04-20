---
allowed-tools: [Bash, Read, Edit]
argument-hint: "[optional file path]"
description: Run gw formatSource on a Liferay module and fix any violations that the formatter cannot auto-fix. Use when the user asks to format source, run formatSource, or invokes /format-source.
name: format-source
---

# Format Source

Run `formatSource` on the relevant Liferay module and fix any remaining violations.

## 1. Determine the Target File

- If `${ARGUMENTS}` contains a file path, use that.
- Otherwise, use the most recently edited Java file in this conversation.
- If neither applies, ask the user which file or module to format.

## 2. Find the Module Root

Walk up the directory tree from the target file until you find a directory containing `build.gradle`. That is the module root.

## 3. Find gradlew

Walk up from the module root until you find a directory containing `gradlew`. That is the Gradle wrapper to use.

## 4. Run formatSource

Run from the module root:

```bash
cd <module-root> && <gradlew-path>/gradlew formatSource --daemon 2>&1
```

## 5. Handle the Result

**If the build succeeds:** Report success — the file is now formatted correctly.

**If the build fails:** The formatter found violations it could not auto-fix. Read the output carefully to identify each violation (file path, line number, rule name). Then:

1. Read each affected file.
2. Fix each violation manually using Edit.
3. Re-run formatSource to confirm all violations are resolved.
4. Repeat until the build succeeds.
