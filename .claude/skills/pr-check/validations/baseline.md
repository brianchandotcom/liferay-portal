# Baseline

## Trigger

Always. The bnd baseline task diffs each exported API against the last release and fails on a missing, excessive, or insufficient `Bundle-Version` or `packageinfo` bump.

This validation used to run only against the branch diff, on the reasoning that an API cannot drift unless someone edits it. That reasoning is wrong, and it is the reason baseline breaks keep reaching master and failing every build until somebody notices. A bump falls due when the API is published, not when it is written, so the failing module is routinely one that the branch never touched:

`site-cms-site-initializer-api` failed every build on master for a day. Its exported `util` package had gained `CMPLicenseUtil` and `CMSObjectEntryUtil` and been carried to `3.5.0`, while the published `4.5.0` artifact contained neither, and the release preparation had moved the bundle only to the micro step `4.5.1`. Every pull request that ran into it failed, and none of them had the module in its diff, so a diff-driven check stayed silent through all of them.

Baselining the whole repository costs about thirty seconds and is quicker than baselining three changed modules one at a time, so there is nothing to buy by narrowing it.

## Match

`.`

## Command

```bash
(cd "${REPO_ROOT}" && ANT_OPTS="-Xmx2560m" ant baseline-all -Dbaseline.all.ant.projects=<true|false>)
```

Pass `false` only when **Full Portal Build** ran in this same pr-check. That property decides whether the seven top level Ant projects are baselined here, and `ant all` has already baselined them on its way through, from its `jar` target. Otherwise leave the property alone, since it defaults to `true`. Skipping them when nothing else has baselined them is the one setting that loses coverage without saying so, which is why it is not the default, and why jarring them again costs five seconds rather than an argument.

One Gradle invocation covers every module. The target collects each `modules` bnd.bnd declaring `Export-Package`, converts its directory to a Gradle project path, and names all of them explicitly, because asking Gradle for `baseline` across the tree makes it configure every module to find the task, which costs far more than the baselining. The top level Ant projects run alongside it, each being its own Gradle build.

One prerequisite, which does not announce itself when missing: each baseline resolves the last released artifact from Nexus, so the run needs network access. Use the local check below when there is none.

The jar used to be a second prerequisite. The top level Ant projects are baselined against the jar in their project directory, and the baseline task does not build it, so the target jars each one it is asked to baseline. Relying on the build validations for it was not enough. They do produce it (`ant all` through its `deploy` target and `ant compile install-portal-snapshots` directly, both reaching `install-portal-snapshot`, which depends on `jar`), but they fire only on a `portal-*`, `util-*`, or `modules` diff, while this validation fires on everything. A documentation or Poshi only branch would otherwise reach this point with whatever jar happened to be on disk. Jarring costs about five seconds against already compiled classes, spent in parallel with the modules.

It also makes the run settle. The repair is written to the source `packageinfo` while the comparison reads the jar, so leaving the jar alone means the next run reads the same old version back out of it and reports the same finding again, without end. The target deletes each `baseline.log` it is about to regenerate for the same reason: the baseline task writes that file on a finding and never removes it, so a log left by an earlier run outlives the repair and reports a failure that no longer exists.

What jarring does not fix is a stale `.class`. Build output that does not correspond to the checked out source deserves more suspicion than a missing jar, because it misleads in both directions and looks like nothing. A worktree holding output from another branch reported six findings against an untouched master, two of them major and one a removed package, and not one was real. Five were the stale `packageinfo` inside the jar. The sixth outlived even a rebuilt jar: an orphaned `.class` from the other branch, whose source does not exist on master, packaged because the compile it belonged to looked up to date. It read as an added class in an exported package and asked for a minor bump, and no `git status` shows such a file, since it is build output rather than source. The same staleness suppresses findings just as easily, since a jar carrying a version that the source has not got matches baseline's own suggestion and reports nothing. Only a clean build clears this, which is what `ant all` does and `ant compile install-portal-snapshots` does not. Treat a finding against a project you have not cleanly built on this branch as unproven.

Both invocations run quiet, which takes the run from roughly thirty nine thousand lines to forty. Nothing is lost by it. Baseline reports every finding on standard output, which Gradle logs at its QUIET level, and a failing module raises at ERROR, so both survive; what quiet drops is the one lifecycle line each of the three thousand tasks emits to say it did nothing. A run that finds something still prints the whole report, ending in `Semantic versioning is incorrect`. Do not restore the default level to bring findings back, because none are missing.

## Interpretation

The exit code does not carry the result on its own, because the baseline task repairs what it finds. `Baseline.updateBundleVersion` writes the suggested version into `bnd.bnd`, and `Baseline.generatePackageInfo` writes a `packageinfo` holding the suggested version, or deletes it when the package is gone. The first run rewrites the tree and every run after it passes. **Read `git status`, not just the exit code.** (`syncVersions`, which the baseline task is `finalizedBy`, is not what repairs the version; it only pushes the resulting `Bundle-Version` out into JS files.)

Baseline has five warnings, and they do not all reach the tree in the same shape:

| Warning | What Lands in the Tree |
| --- | --- |
| `VERSION INCREASE REQUIRED` | version raised |
| `VERSION INCREASE SUGGESTED` | version raised |
| `EXCESSIVE VERSION INCREASE` | version **lowered** |
| `PACKAGE ADDED, MISSING PACKAGEINFO` | **new, untracked** packageinfo |
| `PACKAGE REMOVED` | packageinfo **deleted** |

All five concern the version of an exported package, the one recorded in its `packageinfo`. `Bundle-Version` is not among them. It is raised when it fails to cover the packages beneath it and is otherwise left alone, so a `Bundle-Version` inflated past what its packages need draws no warning and no repair: the run reports nothing and passes. That case reaches this validation only through the classification below, which reads the number out of `bnd.bnd` and calls it a major bump, so do not treat a passing run as evidence the bundle version is right.

Only the first two arrive as an ordinary pair of version lines. A bare diff of those lines cannot tell the rest apart: it drops the filename, renders an addition or a deletion as a lone unpaired line, and reads a lowering as a routine bump. List each file with both of its versions instead:

```bash
version() {
	sed -nE 's/^(Bundle-Version:[[:space:]]*|version[[:space:]]+)//p' | head -1
}

git status --porcelain -uall -- '*bnd.bnd' '*packageinfo' |
while read -r status path; do
	case "${status}" in
	D)    old=$(git show "HEAD:${path}" | version) ; new="<removed>" ;;
	'??') old="<added>" ; new=$(version < "${path}") ;;
	*)    old=$(git show "HEAD:${path}" | version) ; new=$(version < "${path}") ;;
	esac

	printf '%s\t%s\t%s\n' "${old:-<none>}" "${new:-<none>}" "${path}"
done
```

Two details in that loop are load bearing.

`-uall` makes a newly written packageinfo visible even where `status.showUntrackedFiles` is set to `no`. That is a reasonable setting to carry on a tree this size, and under it the same command reports nothing whatsoever (the file is untracked), so the finding disappears in silence rather than as an error. The pathspec is not a substitute. It does stop git collapsing an untracked directory into its parent, but it does not override that setting.

Removal is read from git's status letter rather than from an empty file. Inferring it from a missing version line instead conflates a deleted file with an unreadable one, and the two do occur together: the baseline task rewrites `bnd.bnd` by truncating and storing, so a file read inside that window yields no version line and would be reported as a removed package. `<none>` keeps that case distinct, since it means the file could not be read, which is a defect in the run to repeat, never a finding to act on.

Classify each row, comparing segments as numbers so that `9.5.1` to `10.0.0` counts as a rise and `1.9.0` to `1.10.0` does not:

- Identical versions are not a finding.
- The first segment rises: **major**.
- The second or third rises: **minor** or **micro**.
- Any segment falls: **lowered**.
- `<added>`: a newly exported package.
- `<removed>`: an exported package is gone.
- `<none>` on either side: the file held no version line. Repeat the run rather than classifying it.

## Autocommit

Split on the classification, because the three outcomes carry different obligations.

**Minor or micro only.** Stage the version files and commit them, resolving `<TICKET>` from the branch name the way [commit.md](../../../rules/commit.md) does:

```bash
paths=$(git status --porcelain -uall -- '*bnd.bnd' '*packageinfo' | cut -c4-)

[ -n "${paths}" ] && printf '%s\n' "${paths}" | git add --pathspec-from-file=-

git commit --message "${TICKET} Semantic versioning"
```

Two shorter spellings of that are both wrong. Handing the same two globs straight to `git add` is fatal whenever one of them matches nothing, which is the commonest bump of all, a lone `Bundle-Version`: `git add` stops on `pathspec '*packageinfo' did not match any files` and stages neither. Piping `git status` directly into `git add` fails differently and only sometimes, because the two run concurrently and both want `index.lock`, `git status` taking it to write the index it just refreshed. Measured at six runs in twelve. Collect the paths first, then stage them.

An added API owes a bump and nothing else, so there is no decision for the developer to make and no reason to hold the repository inconsistent while they apply it by hand.

A bump owed to a package that this branch never touched commits under this branch's ticket as well. The ticket that published the API cannot be resolved from the tree, and the alternative is to leave master failing every build until somebody notices, which is the incident above. Report it, though, since the pull request now carries a semantic versioning fix that its author did not write.

**Major, lowered, or removed.** Do not commit. Fail this validation and report each one with its file and both versions. All three carry a decision that is the developer's to make, and each is a breaking change wearing a different disguise:

- A **major** bump means an exported API was removed or changed incompatibly. It needs a breaking change section in the commit message and a deliberate reckoning with the consumers it breaks.
- A **lowered** version is `EXCESSIVE VERSION INCREASE`: the branch claimed more than it delivered, or the number was already published. Committing it retracts a version that consumers may already resolve against.
- A **removed** packageinfo is `PACKAGE REMOVED`, the most breaking outcome of the five, and the one that looks least like anything. It arrives as a bare file deletion carrying no new version at all, so a check that reads version numbers sees nothing to object to.

**A newly exported package.** Report it and leave it uncommitted. The file is the developer's own new API surface and belongs in the commit that adds the package, not under a semantic versioning title.

When several appear in one run, the run fails on the strictest, and every safe bump stays uncommitted with it so the whole set is reviewed together.

## Local Version Check

This needs no network and reports an advisory note, never a PASS or FAIL. Use it when the baseline run above cannot reach Nexus.

Look at each changed `.java` under an `*-api` module's `src/main/java`, `portal-impl/src`, or `portal-kernel/src`. When its diff adds or removes a `public` or `protected` line, the exported API changed, so the version should be bumped too. The bump shows up in the diff as a changed `packageinfo`, or a changed `bnd.bnd` `Bundle-Version` for an `*-api` module. If neither changed, flag the package, since the API changed but the version did not.

Flag a lowered `packageinfo` or `Bundle-Version` that has no matching `public` or `protected` removal.

## Checklist

```
- [ ] Baseline
```

## Time Estimate

~30 sec for the whole repository, measured repeatedly between 26 and 30 sec, on a warm Gradle daemon whose module jars are already built.

Two things move that number, and neither is the Ant heap. Ant only orchestrates here; the work belongs to the Gradle daemon in its own JVM, and raising `ANT_OPTS` from 2.5 GB to 4 GB was measured as no change at all. What costs is a run that has to rebuild jars before it can baseline them, the ordinary case right after a branch change, at around 45 sec; and a cold Gradle daemon, at around 80 sec. Gradle starts a second daemon rather than waiting when the one already up is busy or was given different JVM arguments, so an unexplained slow run is worth checking against the daemon list before it is read as the target being slow.