# Structural Smoke

## Trigger

Always, except when the entire diff is documentation or language properties (`*.md`, `Language*.properties`) — the smoke baseline does not exercise documentation.

## Command

A fixed five-class set: `ConfigurationEnvBuilderTest`, `LibraryReferenceTest`, `Log4jConfigUtilTest`, `ModulesStructureTest`, `SampleSQLBuilderTest`.

The five tests live in three places: two in `portal-impl/test/unit`, two in `portal-kernel/test/unit`, and one in `modules/util/portal-tools-sample-sql-builder`. Run **three** invocations, not five — each invocation forks a single JUnit JVM that runs every class matching its include pattern in one batch:

```bash
(cd "${REPO_ROOT}/portal-impl" && ant test-class -Dtest.class="ConfigurationEnvBuilderTest.class **/Log4jConfigUtilTest")
```

```bash
(cd "${REPO_ROOT}/portal-kernel" && ant test-class -Dtest.class="LibraryReferenceTest.class **/ModulesStructureTest")
```

```bash
"${REPO_ROOT}/gradlew" \
	--continue \
	-Dtest.ignore.failures=false \
	--project-dir "${REPO_ROOT}/modules" \
	--tests "*.SampleSQLBuilderTest" \
	:util:portal-tools-sample-sql-builder:test
```

The `ant test-class` target in `build-common.xml` evaluates `test.includes="**/${test.class}.class"`, and Ant filesets accept space-separated patterns inside that attribute, so passing two classes in one invocation works. The first arg includes the `.class` suffix (so `**/${test.class}.class` expands to `**/ConfigurationEnvBuilderTest.class`); the second arg starts with `**/` so the second pattern reads `**/Log4jConfigUtilTest.class` after the `.class` suffix is appended. This collapses five JVM forks down to three.

## Checklist

```
- [ ] portal-impl: ConfigurationEnvBuilderTest + Log4jConfigUtilTest
- [ ] portal-kernel: LibraryReferenceTest + ModulesStructureTest
- [ ] modules :util:portal-tools-sample-sql-builder: SampleSQLBuilderTest
```

## Notes

Do not re-pick these classes when computing **Java Unit Tests** — they are already covered here.

## Time Estimate

~2-3 min.