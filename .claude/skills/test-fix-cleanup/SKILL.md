---

allowed-tools: [Bash, Read]
description: Close stale claude-test-fix Jira tickets where the test now passes on master.
name: test-fix-cleanup

---

# Clean Up Stale Test-Fix Tickets

Close open Jira tickets labeled `claude-test-fix` where the associated test is now passing on a commit that is on the `master` branch.

## Preconditions

Verify all of these once at the start of the run. Fail fast with a clear message if any is missing.

- `${JIRA_API_USER}` and `${JIRA_API_TOKEN}` must be set in the environment.
- `${TESTRAY_CLIENT_ID}` and `${TESTRAY_CLIENT_SECRET}` must be set in the environment.
- The current working directory is a git checkout of `liferay-portal`.

## Authenticate With Testray

Fetch a bearer token once and reuse it for every Testray call:

```bash
export ACCESS_TOKEN=$(curl \
	--data "grant_type=client_credentials" \
	--header "Authorization: Basic $(printf '%s:%s' "${TESTRAY_CLIENT_ID}" "${TESTRAY_CLIENT_SECRET}" | base64 --wrap 0)" \
	--header "Content-Type: application/x-www-form-urlencoded" \
	--request POST \
	--silent \
	--url "https://testray.liferay.com/o/oauth2/token" \
	| jq --raw-output '.access_token')
```

## Sync Master

Fetch the latest master from origin once so every subsequent ancestry check is current:

```bash
git fetch --quiet origin master
```

## Fetch Open Tickets

Query Jira for all open issues labeled `claude-test-fix`, paging by incrementing `startAt` by `maxResults` until `isLast` is `true`:

```bash
JIRA_BODY=$(cat <<'EOF'
{
  "jql": "labels = \"claude-test-fix\" AND statusCategory != Done ORDER BY created ASC",
  "fields": ["id", "key", "summary", "created", "description"],
  "maxResults": 100,
  "startAt": 0
}
EOF
)

curl \
	--data "${JIRA_BODY}" \
	--header "Content-Type: application/json" \
	--request POST \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/search/jql" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

Print the total count before proceeding.

## Process Each Ticket

For each ticket, log `Processing <ticket> — <summary>` and run both checks below independently. The closure decision in **Decide Whether to Close** combines their results.

### Resolve the Testray Case ID

**Try description first.** Flatten every `.text` node in the ADF description into a single string and apply a case-insensitive regex to extract the first 7-or-more-digit number that follows `case result id` or `case result`:

```bash
CASE_RESULT_ID=$(echo "${DESC_TEXT}" | grep -oiP '(?:case result(?: id)?[:\s(]+)(\d{7,})' | grep -oP '\d{7,}' | head -1)
```

When a `<caseResultId>` is found, fetch the case result to get the case ID:

```bash
curl \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/c/caseresults/<caseResultId>"
```

Read `r_caseToCaseResult_c_caseId` as `<caseId>`. When the fetch fails or the field is absent, log a warning and set `TESTRAY_PASS=false`; continue to **Check for Fix Commits on Master**.

**Fall back to name lookup** when no case result ID is found in the description. Use the ticket summary as the test name and query the cases endpoint filtered to the canonical master project (ID `35392`):

```bash
curl \
	--data-urlencode "filter=name eq '<testName>' and r_projectToCases_c_projectId eq '35392'" \
	--data-urlencode "pageSize=5" \
	--get \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/c/cases"
```

When the response contains zero items or more than one item, log a warning and set `TESTRAY_PASS=false`; continue to **Check for Fix Commits on Master**.

### Fetch Case History

Fetch the 5 most recent history entries for the resolved case ID, newest first:

```bash
curl \
	--data-urlencode "pageSize=5" \
	--data-urlencode "sort=executionDate:desc" \
	--get \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/testray-rest/v1.0/testray-case-result-history/<caseId>"
```

The response is a paginated object; entries are under `.items`. Walk `.items` newest-first and skip any entry whose `status` is `UNTESTED`. The first non-`UNTESTED` entry is the latest meaningful result.

- When no non-`UNTESTED` entry exists: log a warning and set `TESTRAY_PASS=false`; continue to **Check for Fix Commits on Master**.
- When the first non-`UNTESTED` entry's `status` is not `PASSED`: set `TESTRAY_PASS=false`; continue to **Check for Fix Commits on Master**.
- When the entry's `executionDate` is earlier than or equal to the Jira ticket's `created` date: set `TESTRAY_PASS=false`; continue to **Check for Fix Commits on Master**. A pass that predates the ticket means the test was already failing again when the ticket was opened.

Record the passing entry's `gitHash`, `testrayRoutineId`, `testrayBuildId`, and `testrayCaseResultId` — all are present directly on each history item.

### Check Whether the Passing SHA Is on Master

Only runs when **Fetch Case History** produced a passing entry.

```bash
git merge-base --is-ancestor <gitHash> origin/master
```

Set `TESTRAY_PASS=true` when the command exits zero, `TESTRAY_PASS=false` otherwise. Record the Testray result URL from the history entry fields:

```
https://testray.liferay.com/#/project/35392/routines/<testrayRoutineId>/build/<testrayBuildId>/case-result/<testrayCaseResultId>
```

### Check for Fix Commits on Master

Always runs, regardless of the Testray outcome above.

Fetch the ticket's subtasks:

```bash
curl \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/issue/<ticket>?fields=subtasks" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

For each ID in `{<ticket>} ∪ {subtask keys}`, search master commits:

```bash
git log origin/master --oneline --grep="<id>"
```

Set `COMMITS_FOUND=true` when at least one commit is found for any ID; record those commits as evidence. Set `COMMITS_FOUND=false` otherwise.

### Decide Whether to Close

`TESTRAY_PASS` is the gate: when false, log `SKIP <ticket> — test not passing on master` and move on regardless of `COMMITS_FOUND`. When true, close the ticket — the label depends on commits:

| `COMMITS_FOUND` | Action |
| --- | --- |
| true | Close as **Fixed** |
| false | Close as **Not Reproducible** |

### Close the Ticket

1. Fetch available transitions:

	```bash
	curl \
		--silent \
		--url "https://liferay.atlassian.net/rest/api/3/issue/<ticket>/transitions" \
		--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
	```

	Select the `Done` transition. When closing as **Not Reproducible**, also set the resolution to `Cannot Reproduce`; when closing as **Fixed**, set the resolution to `Fixed`.

1. Add a comment before transitioning:

	For **Fixed**, the comment reads:

	> Fix commits landed on master and the test is now passing. Testray result: `<testrayCaseResultUrl>`
	>
	> `<sha1> <subject1>`
	> `<sha2> <subject2>`

	For **Not Reproducible**, the comment reads:

	> Test no longer reproducible on master. Most recent CI result: PASSED on commit `<gitHash>`. Testray result: `<testrayCaseResultUrl>`

	Author the comment body in ADF.

	```bash
	curl \
		--data "${COMMENT}" \
		--header "Content-Type: application/json" \
		--request POST \
		--silent \
		--url "https://liferay.atlassian.net/rest/api/3/issue/<ticket>/comment" \
		--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
	```

1. Apply the transition:

	```bash
	RESOLUTION=$([ "${CLOSE_AS}" = "Fixed" ] && echo "Fixed" || echo "Cannot Reproduce")

	TRANSITION_BODY=$(cat <<EOF
	{
	  "fields": {"resolution": {"name": "${RESOLUTION}"}},
	  "transition": {"id": "<transitionId>"}
	}
	EOF
	)

	curl \
		--data "${TRANSITION_BODY}" \
		--header "Content-Type: application/json" \
		--request POST \
		--silent \
		--url "https://liferay.atlassian.net/rest/api/3/issue/<ticket>/transitions" \
		--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
	```

1. Log `CLOSED <ticket> — Fixed` or `CLOSED <ticket> — Not Reproducible` accordingly.

## Output

After processing all tickets, print a summary table:

| Result | Ticket | Reason |
| --- | --- | --- |
| CLOSED | LPD-XXXXX | passes on <gitHash> (Testray) |
| CLOSED | LPD-XXXXX | fix commits on master |
| SKIPPED | LPD-XXXXX | <reason> |