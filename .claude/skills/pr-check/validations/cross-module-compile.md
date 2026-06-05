# Cross-Module Compile

## Trigger

A `*-api`, `portal-impl`, or `portal-kernel` signature changed (or a `*Constants`, `*Service`, or `*Util` class). Two kinds of consumer are not compiled by any other validation:

- Modules carrying `.lfrbuild-portal-deprecated`, which the default `portal`/`dxp` profile excludes.

- `testIntegration` sources in `-test` modules a producer did not change. Per-Module Compile skips `-test` modules; Integration Test Compile runs only for a module whose own `testIntegration` changed.

Both depend on the kernel as a binary, not a `project(...)` edge, so Per-Module Compile's expansion cannot reach them. Find them by source symbol.

## Match

`^portal-impl/.+\.java$|^portal-kernel/.+\.java$|^modules/.+-api/.+\.java$|^modules/.+/[^/]*(Constants|Service|Util)\.java$`

## Selection

For each changed file, take its simple type name and search only the two surfaces above:

```bash
for marker in $(find "${REPO_ROOT}/modules" -name .lfrbuild-portal-deprecated); do
	grep --files-with-matches --include=*.java --recursive "<TypeName>" "$(dirname "${marker}")/src" 2>/dev/null | sed "s#/src/.*##"
done | sort --unique
```

```bash
grep --files-with-matches --include=*.java --recursive "<TypeName>" "${REPO_ROOT}/modules" \
	| grep "/src/testIntegration/" | sed "s#/src/testIntegration/.*##" | sort --unique
```

Convert each module root to a Gradle project path. Cap the combined set at 8; when a symbol exceeds the cap, skip the expansion and recommend Full Portal Build plus Integration Test Compile instead.

## Command

Deprecated consumer (the only profile that includes it):

```bash
("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules" \
	-Dbuild.profile=portal-deprecated \
	:<path>:compileJava)
```

testIntegration consumer:

```bash
("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules" \
	:<path>:compileTestIntegrationJava)
```

Relies on local `master` carrying the producing change; pr-check never fetches a remote.

## Checklist

```
- [ ] (One subitem per consumer, capped at 8:) Compile <module path> (deprecated | testIntegration)
```

## Time Estimate

~1 min per consumer compile.