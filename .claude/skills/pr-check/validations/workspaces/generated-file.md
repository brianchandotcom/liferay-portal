# Generated Workspace File

## Trigger

The branch changed a file in a workspace other than `liferay-sample-workspace` that `workspaces/refresh_other_workspaces.sh` regenerates from `liferay-sample-workspace`.

That script copies the sample workspace over every other workspace with `rsync --archive --delete`, in `liferay-portal` and in the private repository alike. Its `--exclude` patterns name what each workspace owns, and everything else is overwritten or deleted on the next refresh. A change to one of those files outside the sample is therefore reverted without a build failure or a warning, so a regenerated file may change only through a refresh, and the edit belongs in `liferay-sample-workspace`.

## Match

`.`

## Command

Skip this validation for `liferay-sample-workspace`, the source the other workspaces are regenerated from.

Build a regex from the `--exclude` patterns of the refresh script at `${SOURCE_SHA}`, rather than keeping a copy of them here, and keep the changed files it does not match. An `rsync` pattern without a slash matches a path component at any depth, which is why the regex is anchored on `/` rather than on the start of the path:

```bash
EXCLUDES_REGEX=$(
	git show "${SOURCE_SHA}:workspaces/refresh_other_workspaces.sh" |
	sed -n 's/^[[:space:]]*--exclude[[:space:]]\{1,\}\([^[:space:]\\]*\).*/\1/p' |
	sed 's/\./\\./g; s/\*/[^\/]*/g' |
	paste -d '|' -s -
)
```

Report **NOT VERIFIED** when `EXCLUDES_REGEX` is empty, naming the unreadable script, since an empty regex would mark every file as regenerated.

For every changed path of the workspace that does not match `(^|/)(${EXCLUDES_REGEX})(/|$)`, compare the branch's copy against the sample workspace at the same commit:

```bash
git show "${SOURCE_SHA}:workspaces/liferay-sample-workspace/<path>" |
	diff - "${BUILD_ROOT}/<path>"
```

Read the sample from `${SOURCE_SHA}` and never from a local branch, which goes stale silently and then reports a file as diverged because the sample moved on rather than because the branch changed anything.

Report PASS for a path whose copies are identical, which is what a refresh produces. Report FAIL for a path whose copies differ, and for a path the sample does not contain, since the refresh deletes it. Name each path, and say that the next refresh reverts it and that the edit belongs in `liferay-sample-workspace`.

## Time Estimate

~10 sec.