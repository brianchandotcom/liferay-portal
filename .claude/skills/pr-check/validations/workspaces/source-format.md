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

The formatter comes from the workspace's own plugins, so its version is the one the workspace pins. The properties are JVM system properties, so pass them with `--system-prop`. The plugin ignores a Gradle project property without an error and then formats the whole workspace.

Pass `source.base.dir` as an absolute path. From a relative one, the formatter miscounts the depth of the workspace below the repository root by one level, and then either drops changed files silently or fails with a `NullPointerException`.

Current branch mode also reads every file the branch changed across the whole repository and strips from each path as many leading directories as the workspace is deep, without checking that the path belongs to the workspace. A changed file outside the workspace that sits two or more directories deep, such as `.claude/skills/pr-check/SKILL.md`, becomes a path inside the workspace. That path usually does not exist, and the formatter fails with a `NullPointerException` in `BaseSourceProcessor.hasGeneratedTag`. When the branch changes such a file, report **NOT VERIFIED** and name the file, since the formatter checked nothing.

A nonzero exit is a finding. Read the violations from the middle of the log, because the terminal Gradle error names the failing task and not the reason for it.

## Autocommit

When `git status --porcelain` is nonempty after the formatter, stage the tracked modifications with `git add --update` and create a commit titled `<TICKET> SF`.

Use `--update` rather than `--all`, since a workspace accumulates `bundles`, `build`, and `node_modules` during a run and `--all` would sweep them in.

## Time Estimate

~1 min.