# Baseline

## Trigger

An exported package API changed: Java under an `*-api` module or `portal-kernel`, a `bnd.bnd` with an `Export-Package`, or a `packageinfo`. The bnd baseline task diffs the exported API against the last release and fails on a missing or excessive `Bundle-Version`/`packageinfo` bump — which no drift check sees, since the `bnd.bnd` carrying the bump never changes.

## Match

`^modules/.+-api/.+\.java$|^portal-kernel/src/.+\.java$|(^|/)bnd\.bnd$|(^|/)packageinfo$`

## Selection

The Nexus baseline task runs only on modules whose `bnd.bnd` declares `Export-Package` (the task does nothing otherwise) and whose `Bundle-Version` is above `1.0.0` (baseline is skipped below that). `portal-kernel` is Ant built, not a Gradle module, so it is out of scope for that task — but the local version check below does cover it.

## Command

Run the Nexus baseline task per selected module, with its directory converted to a Gradle project path:

```bash
("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules" \
	-Dbaseline.ignoreFailures=true \
	:<path>:baseline)
```

A nonempty `modules/<module>/build/reports/baseline/baseline.log` is a FAIL; empty is a PASS. The task builds the module jar and resolves the last released artifact from Nexus, so it requires network access.

### Local Version Check

This needs no network and reports an advisory note, never a PASS or FAIL.

Look at each changed `.java` under an `*-api` module's `src/main/java` or under `portal-kernel/src`. When its diff adds or removes a `public` or `protected` line, the exported API changed, so the version should be bumped too. The bump shows up in the diff as a changed `packageinfo`, or a changed `bnd.bnd` `Bundle-Version` for an `*-api` module. If neither changed, flag the package: the API changed but the version did not. Network access is the only thing the Nexus task above adds.

## Checklist

```
- [ ] (One subitem per exporting module:) Baseline <module path>
```

## Time Estimate

~30 sec - 1 min per module.