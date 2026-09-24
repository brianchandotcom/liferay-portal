# Workspace Unit Tests

## Trigger

Always.

## Match

`.`

## Command

```bash
(cd "${BUILD_ROOT}" && ./gradlew --continue test packageRunTest)
```

`test` runs every Java unit test in the workspace and `packageRunTest` every JavaScript unit test, with no integration test and no product bundle. A workspace runs its whole suite in a few minutes, so every test runs rather than a selection by counterpart.

`--continue` keeps one failing module from hiding the rest.

Judge the Java tests from the `TEST-*.xml` reports under each module's `build/test-results/test`, counting `tests`, `failures`, and `errors`, and the JavaScript tests from the `Tests:` lines, rather than from the Gradle exit status alone.

Report FAIL when a failing test is in something the diff changed, or tests a class the diff changed, quoting the test and its assertion. When the diff touches neither the failing test nor the class it exercises, the workspace is already broken, so report **NOT VERIFIED** for it and name the commit that last changed each of them. Decide this from the diff rather than by checking out the base branch, which would disturb the working tree.

When a changed class under `src/main/java` has no test of the same name under the module's `src/test/java`, add a note naming it, since the run passes without anything exercising it.

## Time Estimate

~1-3 min.