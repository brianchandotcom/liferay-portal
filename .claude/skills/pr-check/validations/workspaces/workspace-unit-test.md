# Workspace Unit Tests

## Trigger

Always.

## Match

`.`

## Command

```bash
cd "${BUILD_ROOT}"

if git grep --extended-regexp --quiet '"test"[[:space:]]*:' -- ':(glob)**/package.json'
then
	./gradlew --continue packageRunTest test
else
	./gradlew --continue test
fi
```

`test` runs every Java unit test in the workspace, and `packageRunTest` runs every JavaScript unit test, with no integration test and no product bundle. A workspace runs its whole suite in a few minutes, so every test runs rather than a selection by counterpart.

`packageRunTest` exists only in a workspace where some `package.json` declares a `test` script. Naming a task Gradle cannot find fails the whole command before any test runs, so the command names it only then. The two invocations are written out rather than built from a variable, because zsh passes an unquoted variable as a single argument and Gradle would then look for one task named after both.

`--continue` keeps one failing module from hiding the rest.

Judge the Java tests from the `TEST-*.xml` reports under each module's `build/test-results/test`, counting `tests`, `failures`, and `errors`, and the JavaScript tests from the `Tests:` lines, rather than from the Gradle exit status alone.

Report FAIL when the diff changed a failing test or the class it exercises, quoting the test and its assertion. When the diff touches neither the failing test nor the class it exercises, the workspace is already broken, so report **NOT VERIFIED** for it and name the commit that last changed each of them. Decide this from the diff rather than by checking out the base branch, which would disturb the working tree.

When a changed class under `src/main/java` has no test of the same name under the module's `src/test/java`, add a note naming it, since the run passes without anything exercising it.

## Time Estimate

~1-3 min.