# Baseline

## Trigger

An exported package API changed: Java under an `*-api` module, `portal-impl`, or `portal-kernel`, a `bnd.bnd` with an `Export-Package`, or a `packageinfo`. The bnd baseline task diffs the exported API against the last release and fails on a missing, excessive, or insufficient `Bundle-Version`/`packageinfo` bump. No drift check catches that, since the `bnd.bnd` carrying the bump never changes.

## Match

`^modules/.+-api/.+\.java$|^portal-kernel/src/.+\.java$|^portal-impl/src/.+\.java$|(^|/)bnd\.bnd$|(^|/)packageinfo$`

## Selection

The Nexus baseline task runs on `*-api` modules whose `bnd.bnd` declares `Export-Package` (the task does nothing otherwise) and whose `Bundle-Version` is above `1.0.0` (baseline is skipped below that). It also runs on `portal-impl` and `portal-kernel`. Both apply the `BaselinePlugin` through `modules/build-portal.gradle` and resolve as standalone Gradle projects, so the same task baselines their Ant-built jars. The local version check below is the no-network fallback and covers the same paths.

## Command

### Module Task

Run the Nexus baseline task per selected `*-api` module, with its directory converted to a Gradle project path:

```bash
("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules" \
	-Dbaseline.ignoreFailures=true \
	:<path>:baseline)
```

A nonempty `modules/<module>/build/reports/baseline/baseline.log` is a FAIL. An empty log is a PASS. The task builds the module jar and resolves the last released artifact from Nexus, so it requires network access.

### Portal Impl and Portal Kernel

Both run as standalone Gradle projects that apply the `BaselinePlugin`. The task baselines the Ant-built jar in the project directory, so build that jar with `ant jar` first, then run the baseline task (substitute `portal-kernel` for the kernel side):

```bash
(cd "${REPO_ROOT}/portal-impl" && ant jar)

("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/portal-impl" \
	-Dbaseline.ignoreFailures=true \
	baseline)
```

A nonempty `baseline-reports/portal-impl.log` is a FAIL. Like the module task, it resolves the last released artifact from Nexus, so it needs network access.

### Local Version Check

This needs no network and reports an advisory note, never a PASS or FAIL.

Look at each changed `.java` under an `*-api` module's `src/main/java`, `portal-impl/src`, or `portal-kernel/src`. When its diff adds or removes a `public` or `protected` line, the exported API changed, so the version should be bumped too. The bump shows up in the diff as a changed `packageinfo`, or a changed `bnd.bnd` `Bundle-Version` for an `*-api` module. If neither changed, flag the package, since the API changed but the version did not.

Flag a lowered `packageinfo` or `Bundle-Version` that has no matching `public` or `protected` removal.

## Checklist

```
- [ ] (One subitem per exporting module:) Baseline <module path>
```

## Time Estimate

~30 sec - 1 min per module. The local version check is instant.