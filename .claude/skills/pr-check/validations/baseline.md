# Baseline

## Trigger

An exported-package API changed: Java under an `*-api` module, a `bnd.bnd` with an `Export-Package`, or a `packageinfo`. The bnd baseline task diffs the exported API against the last release and fails on a missing or excessive `Bundle-Version`/`packageinfo` bump — which no drift check sees, since the `bnd.bnd` carrying the bump never changes.

## Match

`^modules/.+-api/.+\.java$|(^|/)bnd\.bnd$|(^|/)packageinfo$`

## Selection

Run only on modules whose `bnd.bnd` declares `Export-Package` (the task no-ops otherwise) and whose `Bundle-Version` is above `1.0.0` (baseline is skipped below that). `portal-kernel` is Ant-built, not a Gradle module, so it is out of scope here.

## Command

Per selected module, with its directory converted to a Gradle project path:

```bash
("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules" \
	-Dbaseline.ignoreFailures=true \
	:<path>:baseline)
```

A non-empty `modules/<module>/build/reports/baseline/baseline.log` is a FAIL; empty is a PASS. The task builds the module jar and resolves the last released artifact from Nexus, so it requires network access.

## Checklist

```
- [ ] (One subitem per exporting module:) Baseline <module path>
```

## Time Estimate

~30 sec - 1 min per module.