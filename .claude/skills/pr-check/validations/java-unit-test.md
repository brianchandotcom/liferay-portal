# Java Unit Tests

## Trigger

A Java source file changed with behavior intent — logic added, removed, or modified. Surface-only edits (renames, formatting, comments, javadoc) do not fire this validation — the build's compile step plus **Structural Smoke** are enough.

Test code (`**/src/test/**`) is in scope: when a test class itself changed, run it. Integration test sources (`**/src/testIntegration/**`) are not in scope here — IT execution is out of scope; signature breaks in IT are caught by **Integration Test Compile**.

## Match

`^modules/.+\.java$|^portal-(impl|kernel)/.+\.java$`

## Selection

Locate the counterpart test by parallel name: `Foo.java` → `FooTest.java` in the same module's `src/test/java/**` (for OSGi modules) or `portal-impl/test/unit/**` / `portal-kernel/test/unit/**` (for portal-core).

Do not select `Log4jConfigUtilTest` or `SampleSQLBuilderTest`, even when their counterpart source changes. Both are in `test.batch.class.names.excludes.permanent` and neither runs in the normal CI flow, so pr-check does not run them either.

Verify each counterpart file exists before scheduling it. Fall back to the module's full unit suite only when no parallel-name counterpart exists and the module is small enough that running everything is cheap.

## Command

For OSGi modules — run only the specific test class, batching counterparts within the same module:

```bash
"${REPO_ROOT}/gradlew" \
	--continue \
	--project-dir "${REPO_ROOT}/modules" \
	--tests "<FQN1>" \
	--tests "<FQN2>" \
	-Dtest.ignore.failures=false \
	:<path>:test
```

`--continue` keeps Gradle going when a downstream task fails so the test results still surface; `-Dtest.ignore.failures=false` overrides Liferay's default of swallowing test failures.

For portal-core — `test-class` (defined in `build-common.xml`) is the target that filters by `test.class` (`test-unit` ignores it and runs the full suite):

```bash
(cd "${REPO_ROOT}/portal-impl" && ant test-class -Dtest.class="<ClassA>.class **/<ClassB>")
```

The space-separated Ant fileset pattern is the same one used by **Structural Smoke** — see that file for the explanation.

## Checklist

Add one subitem per affected module:

```
- [ ] <module path>: <test class names>
```

## Time Estimate

~30 sec - 2 min depending on counterpart count.