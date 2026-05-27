---

allowed-tools: [Bash, Edit, Glob, Grep, Read, Skill, Write]
argument-hint: '<caseResultId | testName | testrayBuildUrl>'
description: Resolve a single Liferay test failure end-to-end.
name: test-fix

---

# Fix a Test Failure

Resolve a single test failure end-to-end.

## Preconditions

- Tomcat is running (required for `Java Integration`, `Playwright`, and `Poshi`). Start it if it is not.

## Input

### Case Result ID

When `${ARGUMENTS}` is a positive integer, use it directly as the Testray case result ID.

### Testray Build URL

When `${ARGUMENTS}` is a URL of the form `https://testray.liferay.com/#/project/<projectId>/routines/<routineId>/build/<buildId>?filter=<urlencoded-json>`, resolve it to a case result ID by following [`references/testray.md`](references/testray.md). The procedure returns a case result ID that the rest of the workflow consumes identically to a user-supplied one.

### Test Name

When `${ARGUMENTS}` is anything else, resolve it to a case result ID by following [`references/testray.md`](references/testray.md). When the resolution aborts, surface the reason and ask the user to retry with the case result ID directly.

### Failure Data

Fetched at the start of the run by following [`references/testray.md`](references/testray.md), which covers authentication, name-to-ID resolution, and how to derive each field. When a test name was passed and the resolution aborts, surface the reason and ask the user to retry with the case result ID directly. When the case result is already `PASSED`, skip the workflow and exit with `Verdict: No fix needed`. When it is `BLOCKED` — a tester deliberately flagged it, so it must not be auto-fixed — skip the workflow and exit reporting that the case is blocked. Otherwise, the procedure returns these fields:

- **buildSha** — commit the failing build was tested against.
- **errorTrace** — error trace produced by the test framework.
- **failureDate** — timestamp when the case result was recorded, used to scope the duplicate-ticket check in **Claim the Failure**.
- **firstFailSha** — first commit where the test failed (may be `null` when the case has no recorded failure history).
- **lastPassSha** — commit where the test last passed (may be `null` when the case has no recent pass on record).
- **name** — test name (class, spec, or method).
- **type** — one of `Java Integration`, `Java Semantic Versioning`, `Java Unit`, `JavaScript`, `Playwright`, `Poshi`.

## Expected Output

### Name

The test name (class, spec, or method) returned by the Testray fetch. When the fetch fails before a name is known, use `case-result <CASE_RESULT_ID>`.

### Type

The test type returned by the Testray fetch (one of the values listed under **Input**). Use `Unknown` when the fetch fails before a type is known.

### Verdict

One of:

- `Bug in portal` — product code carried the fix.
- `No fix needed` — the test passed locally on the first reproduction; nothing was changed.
- `Outdated test` — the test carried the fix.
- `Unresolved` — investigation did not converge, or any step aborted.

### Conclusion

One sentence describing the outcome:

- For `Bug in portal` and `Outdated test`, name the offending commit (short SHA and subject) and what it changed.
- For `No fix needed`, the literal string `Test passes locally`.
- For `Unresolved`, an honest handover summary listing hypotheses considered, attempts made, observed effects, and the most plausible remaining lead.

### Resolution Time

The elapsed time of the run, formatted as `<minutes>m <seconds>s`.

### Jira Tickets

The Task created in **Claim the Failure** is the persistent ticket of record for every verdict. Update it at the end of the run based on the verdict:

- **Bug in portal** — invoke the `jira-bug` skill to create a separate Bug describing the regression. The title summarizes the regression. The description carries the failing test name, the trace, and the reproduction steps derived from the test scenario. Link the Bug to the Task with the **Fix** issue link type so the Task surfaces it as **is fixed by**. Return the Bug URL alongside the Task URL.

- **Outdated test** — return the Task URL.

- **No fix needed** — close the Task as `Won't Do` with a comment containing the literal `Test passes locally`. No PR is opened.

- **Unresolved** — leave the Task in **In Progress**, append the handover summary as a comment, and return its URL so the human picking it up has a single landing page.

### Pull Request

Only when the test was fixed (verdict `Bug in portal` or `Outdated test`): the URL of the pull request opened for the fix.

Make a commit, then find the owner of the changed files using `<repo-root>/.github/CODEOWNERS` and invoke the `pr` skill with it as the target repository. Override the user's title-only default and pass the body content explicitly so the pull request explains the regression.

Use this template. The browse URL on the first line points at the ticket the `pr` skill resolves (the Technical Task subtask, not the parent Task), so reviewers land on the same ticket where the pull request URL is recorded:

```markdown
https://liferay.atlassian.net/browse/<TICKET>

## Failing Test

`<test-name>`

`<test-path>`

\`\`\`
<errorTrace>
\`\`\`

## Root Cause

Commit `<short-sha>` ("<subject>") <one or two sentences>.

## Fix

<one paragraph explaining the change and why it works>.

- `<file-1>`
- `<file-2>`
```

## Workflow

### Claim the Failure

1. Check Jira for an LPD ticket whose summary contains `<test-name>` and is labeled `claude-test-fix`. Decide whether it already covers this failure by its state:

	- **Unresolved** (Open, In Progress, or any non-resolved state) → claimed, skip. Someone is already working it.
	- **Resolved** → find the ticket's PR by following the `pr` skill's rules for where it is recorded, derive its fix commit, and test whether it already reached the build that failed, judging by `git merge-base --is-ancestor <fixSha> <buildSha>`:
		- **Fix commit is an ancestor of `<buildSha>`** → the fix was already present when this build ran, yet the test still failed, so it does not cover this occurrence → proceed.
		- **Fix commit is not yet in `<buildSha>`** → Testray has not retested since the fix merged, so the failure is already addressed → skip.
		- **No fix commit found** (test-only ticket, unmerged commit, or a non-fixing resolution) → fall back to the resolution date: resolved before `<failureDate>` proceeds, resolved on or after skips.

	When skipping and other candidates remain, retry with the next one.

1. Invoke the `jira-task` skill with summary `<test-name>` and a description that names the case result ID, the source build, and the failure trace excerpt. Add the `claude-test-fix` label.

1. Invoke the `start-work` skill on the new Task.

### Reproduce Locally

This step runs **before** any range or commit analysis. The test may already pass locally — when it does, the run ends here without any further investigation.

#### Set Feature Flags

Inspect the test source to discover which feature flags it depends on. Mirror the CI setup before reproducing. Otherwise, the test path differs.

- **Poshi tests** require flags in `<bundles>/portal-ext.properties` with Tomcat restarted to pick them up. Before editing the file for the first time in this run, snapshot it so it can be restored later. Then, strip every existing `feature.flag.*` entry and add only the flags the test requires — the file must end up with the test's flags and nothing else, so unrelated flags left over from previous runs cannot interfere. The original snapshot is restored later in **Restore the Portal**. Bounce Tomcat for the new flag values to take effect.

- **Playwright tests** declare flags through the `featureFlagsTest` fixture under `modules/test/playwright/fixtures`. The fixture toggles them per test — no portal change is needed.

#### Run the Test

Run the test, deploying first when the type requires it. For `Java Semantic Versioning`, the "test" is `<gradlew> baseline` from the failing module — strictly an API contract check, not a behavioral test. For test types that exercise the runtime (`Java Integration`, `Playwright`, `Poshi`), also read the server log after the run; it captures portal-side exceptions, deployment errors, and stack traces that never reach **errorTrace**, and frequently names the real failure. Then compare the local outcome with **errorTrace**:

- **Test passes** → check whether a commit between `${FIRST_FAIL_SHA}` and `HEAD` already addresses the failure. When one does, exit with `Verdict: No fix needed`. Otherwise, reason about why the test failed in CI (the test may be flaky or fail for environmental reasons). Try to fix it and rerun to confirm. When no plausible cause surfaces, exit with `Verdict: No fix needed`. Skip **Identify Suspect Commits** and **Iterate Through Suspects** in either case.
- **Same failure** → continue to **Identify Suspect Commits**.
- **Different failure** → surface the diff and ask the user whether to proceed. When the user is unreachable or declines, mark the failure as `Unresolved` with a `Conclusion` summarizing both traces (the one returned by the Testray fetch and the one observed locally) and exit.

### Identify Suspect Commits

The breaking change lies between `${LAST_PASS_SHA}` and `${FIRST_FAIL_SHA}`. List candidates from the diff between those two commits, then narrow by tracing the line history of the file owning the line nearest the failing assertion or the topmost frame in **errorTrace**.

When that does not point to a single commit, rank candidates: files in the test's own module first, then modules whose packages the test imports, then `*-api` / `portal-kernel` / shared `frontend-js-*`, then `portal-impl` / `petra-*` / shared infrastructure.

### Iterate Through Suspects

Apply candidate fixes as uncommitted changes; the **Pull Request** step commits them later. For each suspect in ranked order:

1. Read its documented intent — the commit message and diff, the linked `LPD-XXXXX` ticket (summary, issue type, description) when the subject carries one, and the body of the merged pull request that introduced the commit:

	```bash
	gh pr list --json number,title,body --repo brianchandotcom/liferay-portal --search "<sha>" --state merged
	```

	Look for explicit references to the failing test or asserted behavior, and for any sign that the change deliberately drops the contract the assertion was checking.

1. Apply a fix that touches the suspect's hunks. The fix must live inside the diff between `${LAST_PASS_SHA}` and `${FIRST_FAIL_SHA}` — that is the only place the regression can live, and a fix outside that range means the diagnosis is wrong. Never escalate the scope of the fix to force convergence. Adapt the test (`Outdated test`) — including removing, weakening, or `@Ignore`-ing an assertion — only when the offending commit's documentation (subject, linked Jira ticket, or PR body) explicitly states the contract change the assertion was checking; without that documented justification, the assertion is correct and the regression lives in product code (`Bug in portal`).

1. Run the test again.

When the test turns green, do **not** lock in the verdict immediately — keep reading the remaining suspects to confirm none of them is a stronger explanation. Settling on the first green fix is how a wrong fix gets shipped; only commit once no better candidate surfaces.

When the current candidate set is exhausted without green, broaden it (next-ranked files, infrastructure) and iterate again — up to **three rounds**. After the third round without convergence, or when candidates are exhausted, mark the failure as `Unresolved` with a `Conclusion` listing the suspects analyzed, attempts made, what each changed about the failure, and the most plausible remaining lead. Run the cleanup in **Restore the Portal** and exit.

Once the verdict is locked in (only ever after a green local run — never commit or open a PR otherwise), record the offending commit (short SHA + subject) and one sentence explaining how it broke the test — reused in the PR body's Root Cause section (see **Pull Request**).

### Restore the Portal

This step is idempotent: the portal must end the run in the same state it started — Tomcat running with the original `portal-ext.properties` loaded.

When **Set Feature Flags** changed `<bundles>/portal-ext.properties`, restore the snapshot and bounce Tomcat to pick the original properties back up.

When **Set Feature Flags** was skipped because the test does not need flag changes, Tomcat keeps running untouched and there is nothing to do.