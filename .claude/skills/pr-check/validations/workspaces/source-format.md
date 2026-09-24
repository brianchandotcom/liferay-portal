# Workspace Source Format

## Trigger

Always.

## Match

`.`

## Command

Run the workspace's own source formatter in current branch mode against `${BASE_BRANCH}`:

```bash
cd "${BUILD_ROOT}"

./gradlew \
	--system-prop formatSource.format.current.branch=true \
	--system-prop "formatSource.git.working.branch.name=${BASE_BRANCH}" \
	--system-prop "formatSource.source.base.dir=${BUILD_ROOT}" \
	formatSource
```

The formatter comes from the workspace's own plugins, so its version is the one the workspace pins. The properties are JVM system properties, so pass them with `--system-prop`, since the plugin reads a Gradle project property as unset without any error and then formats the whole workspace.

Pass `source.base.dir` as an absolute path. From a relative one, the formatter computes the depth of the workspace below the repository root one level too deep, and then either drops changed files silently or fails with a `NullPointerException`.

Report **NOT VERIFIED** when the workspace's `settings.gradle` pins `com.liferay.gradle.plugins.workspace` below `17.1.11`. Those versions ignore `git.working.branch.name` and compare the branch against `master` instead.

A nonzero exit is a finding. Read the violations from the middle of the log, because the terminal Gradle error names the failing task and not the reason for it.

## Autocommit

When `git status --porcelain` is nonempty after the formatter, stage the tracked modifications with `git add --update` and create a commit titled `<TICKET> SF`.

Use `--update` rather than `--all`, since a workspace accumulates `bundles`, `build`, and `node_modules` during a run and `--all` would sweep them in.

## Time Estimate

~1 min.