---

allowed-tools: [Bash, Edit, Glob, Grep, Read, Skill, Write]
argument-hint: '<caseResultId>'
description: Resolve a single Liferay test failure end-to-end. Use when the user invokes `/test-fix` with a single Testray case result ID.
name: test-fix

---

# Fix a Test Failure

Resolve a single test failure end-to-end.

## Preconditions

Verify all of these once at the start of the run. Fail fast with a clear message if any is missing.

- The current working directory is a git checkout of `liferay-portal`.
- The working tree is clean (`git status --porcelain` is empty).
- The checkout is on `master`.
- The Liferay portal is running (required for `Java Integration`, `Playwright`, and `Poshi`). Start it if it is not.

## Input

### Case Result ID

`${ARGUMENTS}` is a single positive integer Testray case result ID. Abort immediately when the argument is missing or not a positive integer.

### Failure Data

Fetched at the start of the run by invoking the `test-fix-testray-data` skill. When the case result is already `PASSED`, skip the workflow and exit with `Verdict: No fix needed`. Otherwise, the skill returns these fields:

- **errorTrace** — error trace produced by the test framework.
- **firstFailSha** — first commit where the test failed (may be `null` when the case has no recorded failure history).
- **lastPassSha** — commit where the test last passed (may be `null` when the case has no recent pass on record).
- **name** — test name (class, spec, or method).
- **type** — one of `Java Integration`, `Java Semantic Versioning`, `Java Unit`, `JavaScript`, `Playwright`, `Poshi`.

## Expected Output

### Name

The test name (class, spec, or method) returned by `test-fix-testray-data`. When the data fetch fails before a name is known, use `case-result <CASE_RESULT_ID>`.

### Type

The test type returned by `test-fix-testray-data` (one of the values listed under **Input**). Use `Unknown` when the data fetch fails before a type is known.

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

### Jira Ticket

Only when the test was fixed (verdict `Bug in portal` or `Outdated test`): the URL of the Jira ticket filed for the fix.

Decide the ticket type from the verdict:

- **Bug** for `Bug in portal` — invoke the `jira-bug` skill. The title summarises the regression. The description carries the failing test name, the trace, and reproduction steps derived from the test scenario. The Bug key is the **commit key**.
- **Task** for `Outdated test` — invoke the `jira-task` skill with title `Fix <test name>`.

Label the ticket with the `claude-test-fix` label so every ticket created by this skill stays searchable as a group.

### Pull Request

Only when the test was fixed (verdict `Bug in portal` or `Outdated test`): the URL of the pull request opened for the fix.

Use the logic in the `start-work` skill to create the branch from the ticket key, then invoke the `commit` skill. Find the owner of the changed files using `<repo-root>/.github/CODEOWNERS` and invoke the `pr` skill with it as the target repository. Override the user's title-only default and pass the body content explicitly so the pull request explains the regression.

Use this template:

```markdown
https://liferay.atlassian.net/browse/LPD-XXXXX

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

When any step aborts (failure data fetch, test does not reproduce, iteration budget exhausted, test file cannot be located, …), run the portal cleanup in step 4 and exit.

### 1. Reproduce Locally

This step runs **before** any range or commit analysis. The test may already pass locally — when it does, the run ends here without any further investigation.

#### 1.1. Set Feature Flags

Inspect the test source to discover which feature flags it depends on. Mirror the CI setup before reproducing, otherwise the test path differs.

- **Poshi tests** require flags in `<bundles>/portal-ext.properties` with Tomcat restarted to pick them up. Before editing the file for the first time in this run, snapshot it so it can be restored later. Then strip every existing `feature.flag.*` entry and add only the flags the test requires — the file must end up with the test's flags and nothing else, so unrelated flags left over from previous runs cannot interfere. The original snapshot is restored later in step 4. Bounce Tomcat for the new flag values to take effect.

- **Playwright tests** declare flags through the `featureFlagsTest` fixture under `modules/test/playwright/fixtures`. The fixture toggles them per test — no portal change is needed.

#### 1.2. Run the Test

Run the test, deploying first when the type requires it. For `Java Semantic Versioning`, the "test" is `<gradlew> baseline` from the failing module — strictly an API contract check, not a behavioural test. Then compare the local outcome with **errorTrace**:

- **Test passes** → exit with `Verdict: No fix needed`. **Do not** investigate further: skip step 2 (diagnosis) and step 3 (iteration). Run the cleanup in step 4 and exit.
- **Same failure** → continue to step 2.
- **Different failure** → surface the diff and ask the user whether to proceed. When the user is unreachable or declines, mark the failure as `Unresolved` with a `Conclusion` summarising both traces (the one returned by `test-fix-testray-data` and the one observed locally) and exit.

### 2. Identify Suspect Commits

The breaking change lies between `${LAST_PASS_SHA}` and `${FIRST_FAIL_SHA}`. List candidates from the diff between those two commits, then narrow by tracing the line history of the file owning the line nearest the failing assertion or the topmost frame in **errorTrace**.

When that does not point to a single commit, rank candidates: files in the test's own module first, then modules whose packages the test imports, then `*-api` / `portal-kernel` / shared `frontend-js-*`, then `portal-impl` / `petra-*` / shared infrastructure.

### 3. Iterate Through Suspects

Work on `master` with uncommitted changes — the branch is created later. For each suspect in ranked order:

1. Read its documented intent — the commit message and diff, the linked `LPD-XXXXX` ticket (summary, issue type, description) when the subject carries one, and the body of the merged pull request that introduced the commit:

	```bash
	gh pr list --json number,title,body --repo brianchandotcom/liferay-portal --search "<sha>" --state merged
	```

	Look for explicit references to the failing test or asserted behaviour, and for any sign that the change deliberately drops the contract the assertion was checking.

1. Apply a fix that touches the suspect's hunks. The fix must live inside the diff between `${LAST_PASS_SHA}` and `${FIRST_FAIL_SHA}` — that is the only place the regression can live, and a fix outside that range means the diagnosis is wrong. Never escalate the scope of the fix to force convergence. Adapt the test (`Outdated test`) — including removing, weakening, or `@Ignore`-ing an assertion — only when the offending commit's documentation (subject, linked Jira ticket, or PR body) explicitly states the contract change the assertion was checking; without that documented justification, the assertion is correct and the regression lives in product code (`Bug in portal`).

1. Run the test again.

When the test turns green, do **not** lock in the verdict immediately — keep reading the remaining suspects to confirm none of them is a stronger explanation. Settling on the first green fix is how a wrong fix gets shipped; only commit once no better candidate surfaces.

When the current candidate set is exhausted without green, broaden it (next-ranked files, infrastructure) and iterate again. Up to **three rounds**. After the third without convergence, or when candidates are exhausted, mark the failure as `Unresolved` with a `Conclusion` listing the suspects analysed, attempts made, what each changed about the failure, and the most plausible remaining lead. Run the cleanup in step 4 and exit.

Once the verdict is locked in (only ever after a green local run — never file a ticket or commit otherwise), record the offending commit (short SHA + subject) and one sentence explaining how it broke the test — reused in the PR body's Root Cause section (see **Pull Request**).

### 4. Restore the Portal

This step is idempotent: the portal must end the run in the same state it started — Tomcat running with the original `portal-ext.properties` loaded.

When step 1.1 changed `<bundles>/portal-ext.properties`, restore the snapshot and bounce Tomcat to pick the original properties back up.

When step 1.1 was skipped because the test does not need flag changes, Tomcat keeps running untouched and there is nothing to do.