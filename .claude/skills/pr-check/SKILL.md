---

allowed-tools: [Agent, Bash, Edit, Glob, Grep, Read, Write]
argument-hint: "[optional ticket ID]"
description: Run pre-merge validation against the current branch. Reads the diff, classifies what changed, picks the minimum cheap checks (drift checks, build, source format, Poshi syntax, structural smoke, Java unit tests, JavaScript unit tests), writes a check.sh that runs them in dependency order, executes it in the background while streaming output to a log file, and reports the outcome. Integration tests, Playwright, and Poshi tests are out of scope — those live in the test-plan skill. The pr skill invokes this as a precondition before opening a PR. Use when the user wants to validate a branch before merging, asks whether the branch is ready, says "validate the branch", "pre-merge check", or invokes /pr-check.
name: pr-check

---

# PR Check

Run pre-merge checks against the current branch. Read the diff, decide what to run, and write a runnable `check.sh` at `${REPO_ROOT}/tmp/pr-check/check.sh`. The script covers drift checks, build, source format, structural smoke, Java unit tests, JavaScript unit tests, and Poshi syntax. The developer runs `bash tmp/pr-check/check.sh`; failed commands surface inline and the script exits non-zero if anything failed.

Integration tests, Playwright tests, and Poshi tests are explicitly out of scope. Use the `test-plan` skill when their coverage is needed.

The script writes `${REPO_ROOT}/tmp/pr-check/results/<SHA>.status` (`PASS` or `FAIL <exit-code>`) so the `pr` skill can confirm a clean run for the current HEAD.

## Workflow

### 1. Compute the Diff and Resolve Preconditions

```bash
# Three-dot form: files unique to this branch, baselined on local master
git diff --name-status "$(git merge-base HEAD master)...HEAD"
```

This SKILL works against local `master` deliberately. It does not fetch from any remote and does not compare against an `upstream/master` ref. Whether the developer's local `master` is up to date with the canonical upstream is the developer's call — pr-check stays predictable across detached forks, mirrored repos, and offline workstations.

**The current branch must be rebased on top of local `master`.** Compare:

```bash
test "$(git merge-base HEAD master)" = "$(git rev-parse master)"
```

When they differ, abort and tell the developer to rebase (`git rebase master`). The `format-source-current-branch` validation diffs against the local `master` tip to decide which files to format — when the branch's merge-base lags behind that tip, SF picks up reverse-direction diffs from the master commits the branch has not absorbed and the `<TICKET> SF` auto-commit captures unrelated rewrites. This rule exists because the failure mode actually happened — files that changed between an old and new master tip ended up in an SF commit on a branch that had never touched them.

Resolve the ticket ID. When `${ARGUMENTS}` supplies a ticket, use it (the `pr` skill passes the ticket it already resolved). Otherwise extract `[A-Z]+-[0-9]+` from the branch name; when the branch name lacks one, scan the last five commit messages; when no ticket surfaces, prompt the developer. The ticket is baked into `check.sh` for the auto-commit messages emitted by drift checks and **Source Format**.

Verify the working tree is clean (`git status --porcelain` returns empty). When dirty, abort and ask the developer to commit first — the auto-commit blocks below would otherwise capture their uncommitted edits.

### 2. Classify the Changes

Read each changed file's diff (not just the path) and classify by *kind* and *intent*.

| Change Kind | Examples | Risk |
|---|---|---|
| Configuration | `bnd.bnd`, `gradle.properties`, non-`test` `package.json` keys | Build catches compile-time issues. For OSGi runtime keys (`Bundle-Activator`, `Liferay-*`, `Provide-Capability`, `Require-Capability`), the structural smoke baseline plus **Integration Test Compile** cover wiring breaks; deeper IT execution is out of scope (use `test-plan`). |
| Doc / language | `*.md`, `Language*.properties` | **Source Format** only. The structural smoke baseline does not exercise documentation, so when the entire diff is doc / language, skip **Test Run** as well. |
| Frontend / resource | `*.{css,sass,scss}`, `*.ftl`, `*.jsp`, `*.jspf`, lockfiles (`package-lock.json`, `yarn.lock`), module `*.properties` | **Per-Module Deploy** verifies JSP/FTL parse and resource bundling. Lockfile changes also fire JavaScript unit tests for affected modules. |
| Generated output | `*ModelImpl.java`, `*PersistenceImpl.java`, REST DTOs, any file whose header carries `@generated` | Hand-editing is an anti-pattern — the next regen overwrites it. Flag the change. The drift check fires from input changes; an output-only diff falls under **Output-Only Catch-Up Regen** below. |
| Generator input | `instance_wrappers.xml`, `rest-config.yaml`, `rest-openapi.yaml`, `service.xml` | Regen must run; uncommitted output breaks the build. |
| Poshi DSL | `*.{function,macro,path,testcase}` | Poshi syntax may break. |
| Source — behavior change | Java/JS/TS adding, removing, or changing logic | Test the change at the unit level. Broader integration coverage lives in `test-plan`. |
| Source — surface-only | rename, formatting, comments, javadoc | Skip module tests; build verifies compile. |
| Test code | `**/src/test/**` (unit), `**/src/testIntegration/**` (IT) | Run the changed unit tests; **Integration Test Compile** catches signature breaks in IT. IT execution is out of scope. |
| Workspaces | `workspaces/**` | Workspace build may break. |

### 3. Pick Validations and Confirm the Plan

Map each Step 2 classification to the **Validation Catalog**. Skip validations implied by another — **Full Portal Build** covers **Per-Module Deploy** and **Integration Test Compile** for `.lfrbuild-portal` modules; see **Build Path Selection** below for how to pick between full and per-module builds. Run in dependency order: drift checks first (they regenerate sources), then **Source Format** (formats the post-regen tree), then build, then **Test Run**.

Print the plan — each validation with trigger, one-sentence rationale, and time estimate; show the build cost computation when the path is non-trivial; total estimated wall-clock. The developer can confirm, trim ("skip the build, I just deployed"), or abort.

### 4. Write `check.sh`

Create `${REPO_ROOT}/tmp/pr-check` if missing (the repo-root `tmp` directory is gitignored via the `.gitignore` entry `/tmp`, so nothing accidentally enters version control). Delete any existing `tmp/pr-check/check.sh`, then write a new one per the **`check.sh` Contract** below. Mark it executable (`chmod +x tmp/pr-check/check.sh`).

### 5. Run `check.sh` and Evaluate

Run the script in the background with the `Bash` tool (`run_in_background: true`), capturing stdout and stderr to a log file:

```bash
bash "${REPO_ROOT}/tmp/pr-check/check.sh" > "${REPO_ROOT}/tmp/pr-check/run.log" 2>&1
```

Wait for the harness completion notification — do not poll. When the background task finishes, read the result file at the current HEAD (auto-commits may have advanced it):

```bash
status_file="${REPO_ROOT}/tmp/pr-check/results/$(git rev-parse HEAD).status"
[ -f "${status_file}" ] && head --lines=1 "${status_file}"
```

- **`PASS`** — report success and exit. Do not read the log; keep its content out of context.

- **`FAIL <exit-code>`** — read the log tail (`tail --lines=200 "${REPO_ROOT}/tmp/pr-check/run.log"`) and grep for `BUILD FAILED`, `FAIL`, or `ERROR` markers to identify the failing validation. Surface a concise diagnostic to the developer (which validation failed, the relevant log excerpt, and the next step).

- **File missing** — the script did not complete (interrupted, crashed, or refused to start because the working tree was dirty). Surface the issue to the developer.

The result file is the contract between this skill and the `pr` skill: a `PASS` for the current HEAD means pr-check succeeded; anything else means it did not.

## Validation Catalog

Each row is a validation. Trigger globs match against the changed file set from Step 1; the Step 2 classification narrows further (a surface-only Java edit does not require **Test Run** beyond the smoke baseline).

| Validation | When It Should Fire | Command |
|---|---|---|
| **Full Portal Build** | portal-core changed (`portal-impl/**`, `portal-kernel/**`, `portal-test/**`, `portal-web/**`, `support-tomcat/**`, `util-bridges/**`, `util-java/**`, `util-slf4j/**`, `util-taglib/**`), OR the **Build Path Selection** cost model picks it over **Per-Module Deploy**. `ant all` is `clean` + `compile` + `deploy`; the deploy target's marketplace branch deploys every project with a `.lfrbuild-portal` marker — covers portal-core plus every changed `.lfrbuild-portal` module. | `ant all -Dgradle.stop.daemon.enabled=false` |
| **Instance Wrapper Build** | `portal-impl/src/com/liferay/portal/tools/instance_wrappers.xml` changed, OR a Java file whose fully qualified name appears as a `<class name="...">` value in that XML was modified | `cd portal-impl && ant build-iw`, then `git diff --quiet` excluding `*-portlet-service.jar`, `packageinfo`, `service.properties`, `yarn.lock` |
| **Integration Test Compile** | a Java file changed in an OSGi module (excluding `modules/dxp/apps/saml/saml-admin-rest-test/**` and `modules/sdk/**`) AND **Full Portal Build** did not fire. Catches IT compile breaks without running ITs (IT execution is out of scope). | `../gradlew :<path>:compileTestIntegrationJava --parallel` per affected module |
| **Per-Module Deploy** | a module is in the **Build Path Selection** deploy set: either **Full Portal Build** did not fire and the module has changed Java/JS/TS/resource files, OR **Full Portal Build** fired but the module lacks `.lfrbuild-portal` (so `ant all` did not deploy it) | `ant compile install-portal-snapshots`, then `../gradlew :<path>:deploy --parallel` per affected module |
| **Poshi Syntax** | a Poshi DSL file changed | `ant -buildfile build-test.xml run-poshi-validation` |
| **REST Builder** | a `rest-config.yaml` or `rest-openapi.yaml` actually changed. Auto-generated REST DTOs, resources, and clients are **not** triggers — drift is caught by `git diff --quiet`. | `cd portal-impl && ant build-rests` (faster than `gradlew buildREST` — single JVM scans `${basedir}/../modules` directly via `RESTBuilder`), then `git diff --quiet` over the regen output paths to flag drift |
| **Service Builder** | a Service Builder *input* changed anywhere in the diff: `service.xml`, `service.properties`, `META-INF/module-hbm.xml`, `META-INF/portlet-model-hints.xml`, `META-INF/sql/*.sql`, or a developer-editable `*Impl.java` file under `model/impl` (e.g. `FooImpl.java`) or `service/impl` (e.g. `FooLocalServiceImpl.java`, `FooServiceImpl.java`). `portal-impl` (portal-core) and OSGi `-service` modules are both in scope — `cd portal-impl && ant build-services` regenerates against every `service.xml` it finds. Auto-generated outputs (`*BaseImpl.java`, `*CacheModel.java`, `*ModelArgumentsResolver.java`, `*ModelImpl.java`, `*PersistenceConstants.java`, `*PersistenceImpl.java`, `*ServiceBaseImpl.java`, `*ServiceHttp.java`) are **not** triggers on their own — drift between them and the regen result is caught by `git diff --quiet`, and an **output-only diff** falls under the catch-up-regen rule below. | `cd portal-impl && ant build-services`, then `git diff --quiet` over the regen output paths to flag drift |
| **Source Format** | always | `ant setup-sdk && cd portal-impl && ant format-source-current-branch`. The formatter is dual-mode: it auto-applies fixable violations (captured by the auto-commit block) and lists unfixable ones as errors that exit non-zero, failing pr-check so the developer fixes them manually. |
| **Test Run** | always, except when the entire diff is documentation / language properties | structural smoke baseline (`ConfigurationEnvBuilderTest`, `LibraryReferenceTest`, `Log4jConfigUtilTest`, `ModulesStructureTest`, `SampleSQLBuilderTest`) plus Java unit tests for directly changed code and JavaScript unit tests via `npm test` for affected modules whose `package.json` declares a `"test"` script. |
| **Workspace Build** | a `workspaces/**` file changed | `cd <workspace-dir> && ./gradlew build` per affected workspace |

The Step 2 judgment is what eliminates spurious fires:

- A Java change in an OSGi module is comment-only → **Per-Module Deploy** still runs (build verifies compile), but **Test Run** narrows to the smoke baseline.

- A `service.xml` is renamed but the schema body is unchanged → **Service Builder** fires (output filenames may shift), drift-check is likely clean.

- An auto-generated `*ModelImpl.java` is hand-edited but no Service Builder input changed → **Service Builder** does **not** fire; flag the change as an anti-pattern (the next regen will overwrite it).

## Output-Only Catch-Up Regen

The drift validations above (**Service Builder**, **REST Builder**, **Instance Wrapper Build**) normally fire on input changes because drift is caught by running the regen and `git diff --quiet`. That logic breaks when a PR ships **only** the regenerator's output — e.g., the input changed in an earlier merge and the developer is committing a catch-up regen.

When the diff matches *only* generated-output patterns and contains **no** corresponding input file, fire the matching regen + drift check anyway:

- Output set includes a class referenced by `instance_wrappers.xml` and no XML change → run **Instance Wrapper Build**.
- Output set matches REST Builder patterns (DTO `*.java`, serdes `*SerDes.java`, `OpenAPIResource*.java`, `Base*ResourceTestCase.java`, `packageinfo` adjacent to those) and no `rest-config.yaml`/`rest-openapi.yaml` changed → run **REST Builder**.
- Output set matches Service Builder patterns (`*BaseImpl.java`, `*CacheModel.java`, `*ModelArgumentsResolver.java`, `*ModelImpl.java`, `*PersistenceConstants.java`, `*PersistenceImpl.java`, `*ServiceBaseImpl.java`, `*ServiceHttp.java`, `packageinfo` adjacent to those, `sql/indexes.sql`) and no `service.xml`/`service.properties`/HBM/model-hints/SQL input changed → run **Service Builder**.

This catches the catch-up-regen failure mode where stale outputs land because the developer ran the regen against an old input snapshot.

## Build Path Selection

Pick between **Full Portal Build** and **Per-Module Deploy** by cost. When portal-core is touched, **Full Portal Build** is mandatory — there is no Gradle deploy path for those sources.

Otherwise, estimate both and pick the cheaper:

- Full Portal Build cost = 8 min (the `ant all` baseline)
- Per-Module Deploy cost = 3 min setup (`ant compile install-portal-snapshots`) + 1 min × ⌈N/4⌉ where N is the deploy set size and 4 approximates Gradle's effective parallelism on a dev box

Compute N. Start with the touched OSGi modules. For each `*-api/**/*.java` in the diff, scan its hunks for added or removed `public` method signatures (`^[-+]\s*(public|protected).*\(.*\)`); when found, grep `modules/**/build.gradle` for `project(":<api-path>")` and add those consumers to N. Show the cost math in the plan output so the developer can override.

When **Full Portal Build** fires alongside non-`.lfrbuild-portal` modules in the touched set, **Per-Module Deploy** also runs for those — `ant all`'s marketplace branch only deploys modules with the marker.

## Time Budget

Target a **15-minute nominal** wall-clock budget per run, with **20 minutes as the soft cap**. The plan summary in Step 3 should show the estimate; when it exceeds 20 minutes, surface the breakdown and ask whether the developer wants to trim a validation (e.g., skip **Test Run** because the Jest suite is the long pole, or split the PR).

Typical envelopes from the validation catalog:

- Doc-only diff: ~1 min (Source Format alone).
- Median PR (1-4 OSGi modules, parallel-name unit tests): 5-8 min.
- Portal-core PR (`ant all` mandatory): 10-13 min.
- Worst case (portal-core + many modules + drift, OR a lockfile-driven full Jest suite on a large module): 15-20 min.

Anything past 20 min is a signal that pr-check is being asked to do too much in one run. Integration tests, Playwright, and Poshi runs are out of scope by design — those live in `test-plan` and have their own budget.

## Auto-Commit on Drift

Drift validations and **Source Format** rewrite or regenerate files in place. When that produces working-tree changes, `check.sh` stages and commits them as a separate commit using Liferay's standard message form. The HEAD advances during the script's execution; the result file is written for the final SHA so the `pr` skill's lookup matches.

| Validation | Commit Message |
|---|---|
| **Instance Wrapper Build** | `<TICKET> buildIW` |
| **REST Builder** | `<TICKET> buildREST` |
| **Service Builder** | `<TICKET> buildServices` |
| **Source Format** | `<TICKET> SF` |

Each block follows the pattern: run the regen, check `git status --porcelain`, and when non-empty, `git add --all && git commit --message "<TICKET> <op>"`. The clean-tree precondition from Step 1 ensures the staged content is only the regen output.

When the auto-commit itself fails (for example, a pre-commit hook rejects), the script records the failure (`EXIT_CODE=1`) and continues to the next validation so the developer sees the full picture.

**Source Format** is dual-mode: it auto-applies fixable violations (captured by the auto-commit) and reports others as errors that exit non-zero — those require manual fix and surface as a pr-check failure with the listed violations in the log tail. When both happen in one run, the auto-commit still captures the fixable subset; the unfixable subset blocks the run.

## Test Run Selection

The **Test Run** umbrella has three components:

1. **Structural smoke baseline** — a fixed five-class set that runs whenever **Test Run** fires: `ConfigurationEnvBuilderTest`, `LibraryReferenceTest`, `Log4jConfigUtilTest`, `ModulesStructureTest`, `SampleSQLBuilderTest`. **Test Run** itself is skipped for documentation / language-only diffs (see the Validation Catalog), so the smoke baseline is skipped along with it.

	**Batch by directory** — the five tests live in three places: two in `portal-impl/test/unit`, two in `portal-kernel/test/unit`, and one in `modules/util/portal-tools-sample-sql-builder`. Run **three** invocations, not five — each invocation forks a single JUnit JVM that runs every class matching its include pattern in one batch. The `ant test-class` target in `build-common.xml` evaluates `test.includes="**/${test.class}.class"`, and Ant filesets accept space-separated patterns inside that attribute, so passing two classes in one invocation works:

	```bash
	(cd "${REPO_ROOT}/portal-impl" && ant test-class -Dtest.class="ConfigurationEnvBuilderTest.class **/Log4jConfigUtilTest") || EXIT_CODE=1
	(cd "${REPO_ROOT}/portal-kernel" && ant test-class -Dtest.class="LibraryReferenceTest.class **/ModulesStructureTest") || EXIT_CODE=1
	"${REPO_ROOT}/gradlew" \
		--continue \
		-Dtest.ignore.failures=false \
		--project-dir "${REPO_ROOT}/modules" \
		--tests "*.SampleSQLBuilderTest" \
		:util:portal-tools-sample-sql-builder:test || EXIT_CODE=1
	```

	The first arg includes the `.class` suffix (so `**/${test.class}.class` expands to `**/ConfigurationEnvBuilderTest.class`); the second arg starts with `**/` so the second pattern reads `**/Log4jConfigUtilTest.class` after the `.class` suffix is appended. This collapses five JVM forks down to three.

1. **Java unit tests** — for directly changed Java source files. Locate the counterpart test by parallel name: `Foo.java` → `FooTest.java` in the same module's `src/test/java/**` (for OSGi modules) or `portal-impl/test/unit/**` / `portal-kernel/test/unit/**` (for portal-core). Run only the specific test class — `../gradlew :<path>:test --continue --tests "<FQN>" -Dtest.ignore.failures=false` for OSGi modules, `cd portal-impl && ant test-class -Dtest.class=<ClassName>` (or `portal-kernel`) for portal-core — not the module's full suite. The Gradle flags are required: `--continue` keeps Gradle going if a downstream task fails so we still see the test results, and `-Dtest.ignore.failures=false` overrides Liferay's default of swallowing test failures. The portal-core `test-class` target (defined in `build-common.xml`) is the one that filters by `test.class`; `test-unit` runs the module's full suite, ignoring `test.class`. When multiple Java files in the same module changed, batch their counterparts: for Gradle, `--tests "<FQN1>" --tests "<FQN2>"`; for portal-core, `-Dtest.class="<ClassA>.class **/<ClassB>"` (Ant fileset multi-include — see the smoke-baseline batching pattern below). Fall back to the module's full unit suite only when no parallel-name counterpart exists and the module is small enough that running everything is cheap.

1. **JavaScript unit tests** — Jest, run via the module's `npm test` script. Locate the counterpart spec by parallel name: `Foo.tsx` → `Foo.test.tsx` or `Foo.spec.tsx`, often under a `__tests__` directory or a sibling `tests` tree. The module's `package.json` `jest.testMatch` or `testRegex` declares the exact convention — read it to confirm the spec lookup pattern. Run only the specific spec — `cd <module> && npm test -- <relative-spec-path>` — rather than the module's full Jest suite. Fires when JS or TS source, JS-relevant `package.json` keys (`dependencies`, `devDependencies`, `scripts.build`, `scripts.test`), or lockfile changed and the module's `package.json` declares a `"test"` script.

	**Lockfile changes run the full module suite.** When `package-lock.json` or `yarn.lock` is in the diff, run the module's full Jest suite (`cd <module> && npm test`) regardless of module size. Parallel-name lookup yields nothing for a lockfile, and a transitive-dependency pin can affect any code path that touches the runtime, so blast radius is the whole module.

	**Deletion handling.** A deleted JS or TS file has no parallel-name counterpart to run, but its consumers can still break. Two layered checks:

	- **Consumer search.** For each deleted file, grep the surrounding module for `import` statements that reference the deleted basename or relative path. For every consumer found, queue the consumer's parallel-name spec (and the consumer's spec itself when it is the consumer). This catches direct-import breakage where a surviving spec imports a now-deleted source.

	- **High-risk-deletion full suite.** When a deletion lands under `__mocks__`, `test`, or `tests`, fall back to the module's full Jest suite. Those locations are explicit Jest extension points (manual mocks, shared helpers, fixtures) whose removal can change runtime behavior for tests with no naming relationship to the deleted file.

Java integration tests, Playwright tests, and Poshi tests are out of scope. Use `test-plan` when their coverage is needed.

Do not re-pick the structural smoke baseline classes; they are already covered by **Test Run**. Verify every test file exists before adding it to the script.

## `check.sh` Contract

The script lives at `${REPO_ROOT}/tmp/pr-check/check.sh` (the repo-root `tmp` directory is gitignored). Run via `bash tmp/pr-check/check.sh`. Output streams to the terminal — pipe to `tee` for capture.

```bash
#!/bin/bash
#
# Validation plan for branch: <branch-name>
# Generated: <date>
# Estimated time: <X>m
#
# Changes: <N> commits, <N> files changed
# Affected areas: <list of module groups or components>
#

REPO_ROOT="$(git rev-parse --show-toplevel)"
RESULT_DIR="${REPO_ROOT}/tmp/pr-check/results"
TICKET="<TICKET-ID>"
EXIT_CODE=0

mkdir -p "${RESULT_DIR}"

# Pre-condition: working tree must be clean (auto-commits below capture only regen output).
if [ -n "$(git -C "${REPO_ROOT}" status --porcelain)" ]; then
	echo "Working tree is dirty — commit your changes before running ${0}."
	exit 1
fi

<validations — drift + auto-commit blocks first, then Source Format + auto-commit, then build, then Test Run>

# Result file uses the final SHA so the pr skill's lookup matches after auto-commits advance HEAD.
SHA="$(git -C "${REPO_ROOT}" rev-parse HEAD)"

if [ "${EXIT_CODE}" -eq 0 ]; then
	echo "PASS" > "${RESULT_DIR}/${SHA}.status"
else
	echo "FAIL ${EXIT_CODE}" > "${RESULT_DIR}/${SHA}.status"
fi

exit ${EXIT_CODE}
```

**Critical rules for the script:**

- Replace the `<validations>` placeholder with the commands picked in Step 3.

- Suffix every command with `|| EXIT_CODE=1` so failures are recorded without halting execution. Final exit is `${EXIT_CODE}` (`0` all-pass, `1` any fail).

- Wrap any command that uses `cd` in a subshell — `(cd <dir> && <cmd>) || EXIT_CODE=1` — so the working directory does not leak between commands.

- Order commands by the Step 3 dependency order: drift checks first (regen + auto-commit on drift), then **Source Format** (with auto-commit on drift), then build, then **Test Run**.

- For drift checks and **Source Format**, follow the regen with the auto-commit pattern from **Auto-Commit on Drift**:

	```bash
	(cd "${REPO_ROOT}/portal-impl" && ant build-services) || EXIT_CODE=1
	if [ -n "$(git -C "${REPO_ROOT}" status --porcelain)" ]; then
		(cd "${REPO_ROOT}" && git add --all && git commit --message "${TICKET} buildServices") || EXIT_CODE=1
	fi
	```

- Refresh `SHA` immediately before writing the result file. The auto-commits move HEAD; the `pr` skill looks up `<final-SHA>.status`, so the result must be written under that SHA.

- Use `"${REPO_ROOT}/gradlew" --project-dir "${REPO_ROOT}/modules"` for Gradle tasks that need to run against `modules` without changing the script's working directory.

- The script does not bootstrap a portal — none of the in-scope checks need one.

## Guidelines

- The diff baseline is local `master`. The SKILL never fetches from a remote; the developer is responsible for keeping `master` fresh and for rebasing the branch onto it before invoking pr-check.

- Verify every test file exists before adding it to the script.

- When changes are purely cosmetic (formatting, comments only), the plan is just **Source Format** + **Test Run** (smoke baseline only).

- When the entire diff is documentation or language properties (`*.md`, `Language*.properties`), the plan is just **Source Format**. **Test Run** is skipped — the smoke baseline does not exercise documentation.

- When the diff is empty, exit with a one-line message — no test produces useful signal on a clean branch.