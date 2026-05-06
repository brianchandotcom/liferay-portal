---

allowed-tools: [Bash]
argument-hint: "<caseResultId | testName>"
description: Collect failure data for a single Testray case result. Accepts either a case result ID or a test name.
name: test-fix-testray-data

---

# Collect Failure Data

Collect a single Testray case result's failure data.

## Preconditions

- `${TESTRAY_CLIENT_ID}` and `${TESTRAY_CLIENT_SECRET}` are set in the environment.

## Input

### Case Result ID

`${ARGUMENTS}` identifies a single Testray case result and is accepted in two formats:

- A positive integer — used directly as the case result ID.

- Anything else — treated as a test name and resolved to a case result ID with the steps in **Resolve From Test Name** below.

Abort immediately when `${ARGUMENTS}` is empty.

## Authentication

Every call to the Testray REST API at `https://testray.liferay.com` uses a bearer token from `/o/oauth2/token` (OAuth2 client credentials grant, authenticated with `${TESTRAY_CLIENT_ID}` and `${TESTRAY_CLIENT_SECRET}`). Fetch the token once per run and reuse it for all calls:

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

## Resolve From Test Name

Skipped when `${ARGUMENTS}` is a positive integer. Otherwise turn the name into a case result ID before fetching any field below.

The resolution targets the master project. Each Liferay team owns a routine on it named **`[master] ci:test:<team>`**, where `<team>` is the suffix of the GitHub team handle in `.github/CODEOWNERS` (`@liferay-page-management` maps to `page-management`). Other routines on master (`ci:test:relevant`, `EE Development Acceptance`, …) are not team routines and must not substitute.

Every step that fails aborts the resolution — surface the reason and ask the user to retry with the case result ID directly. Do not silently fall back to a different project or routine.

1. Resolve the master project ID dynamically. The most recent build whose name starts with `[master]` lives in it:

	```bash
	curl \
		--data-urlencode "filter=startswith(name, '[master]')" \
		--data-urlencode "pageSize=1" \
		--data-urlencode "sort=dateCreated:desc" \
		--get \
		--header "Accept: application/json" \
		--header "Authorization: Bearer ${ACCESS_TOKEN}" \
		--silent \
		--url "https://testray.liferay.com/o/c/builds"
	```

	Read `r_projectToBuilds_c_projectId` from the first item as `<masterProjectId>` and reuse it for every step below.

1. Fetch every Case with that exact name:

	```bash
	curl \
		--data-urlencode "filter=name eq '<name>'" \
		--data-urlencode "pageSize=20" \
		--get \
		--header "Accept: application/json" \
		--header "Authorization: Bearer ${ACCESS_TOKEN}" \
		--silent \
		--url "https://testray.liferay.com/o/c/cases"
	```

1. Keep the single Case whose `r_projectToCases_c_projectId` equals `<masterProjectId>`. When zero or more than one match, abort.

1. Derive the team from the test path through `.github/CODEOWNERS` and turn it into the routine name. Resolve its routine ID:

	```bash
	curl \
		--data-urlencode "filter=name eq '[master] ci:test:<team>' and r_routineToProjects_c_projectId eq '<masterProjectId>'" \
		--data-urlencode "pageSize=1" \
		--get \
		--header "Accept: application/json" \
		--header "Authorization: Bearer ${ACCESS_TOKEN}" \
		--silent \
		--url "https://testray.liferay.com/o/c/routines"
	```

	When CODEOWNERS does not point to a single team, or the routine does not exist, abort.

1. List the most recent case results for the case:

	```bash
	curl \
		--data-urlencode "filter=r_caseToCaseResult_c_caseId eq '<caseId>'" \
		--data-urlencode "pageSize=50" \
		--data-urlencode "sort=dateCreated:desc" \
		--get \
		--header "Accept: application/json" \
		--header "Authorization: Bearer ${ACCESS_TOKEN}" \
		--silent \
		--url "https://testray.liferay.com/o/c/caseresults"
	```

1. For each item in order, fetch its build (using the by-ID build endpoint shown in **Expected Output**) and read `r_routineToBuilds_c_routineId`. Return the `id` of the first case result whose build routine matches the team routine ID and whose `dueStatus.key` is not `UNTESTED`. The result may be `PASSED` — that is correct when the test currently passes on the team routine, and the rest of the workflow exits with `Verdict: No fix needed`.

When the loop ends without a match, abort.

## Expected Output

The case result drives every other lookup. Fetch it first:

```bash
curl \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/c/caseresults/<caseResultId>"
```

### Name

Fetch the case using `r_caseToCaseResult_c_caseId` from the case result:

```bash
curl \
	--data-urlencode "filter=id eq '<caseId>'" \
	--data-urlencode "pageSize=1" \
	--get \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/c/cases/"
```

Read `name` from the first item in `items`.

### Type

Skipped when `dueStatus` is `PASSED`.

When `<name>` contains the substring `PortalLogAssertor`, the type is `Java Log Assertor` and **no history is fetched**.

Otherwise, fetch the case type using `r_caseTypeToCases_c_caseTypeId` from the case:

```bash
curl \
	--data-urlencode "filter=id eq '<caseTypeId>'" \
	--get \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/c/casetypes/"
```

Map the matching item's `name` to the label below. Names not in the table pass through unchanged.

| Case Type Name | Label |
| --- | --- |
| Automated Functional Test | Poshi |
| JS Unit Test | JavaScript |
| Modules Integration Test | Java Integration |
| Modules Semantic Versioning Test | Java Semantic Versioning |
| Modules Unit Test | Java Unit |
| Playwright Test | Playwright |

### Error Trace

Skipped when `dueStatus` is `PASSED`.

The `errors` field of the case result.

### Last Pass SHA and First Fail SHA

Skipped when `dueStatus` is `PASSED`. Also skipped for `Java Log Assertor`. Both are `null` when the case name is `Top Level Build` or contains `PortalLogAssertor`. Otherwise, compute them from the case history filtered to the supplied case result's routine.

Resolve the routine ID by fetching the build with `r_buildToCaseResult_c_buildId` from the case result:

```bash
curl \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/c/builds/<buildId>"
```

Read `r_routineToBuilds_c_routineId` as `<routineId>`.

Fetch the case history:

```bash
curl \
	--data-urlencode "pageSize=300" \
	--data-urlencode "sort=executionDate:desc" \
	--get \
	--header "Accept: application/json" \
	--header "Authorization: Bearer ${ACCESS_TOKEN}" \
	--silent \
	--url "https://testray.liferay.com/o/testray-rest/v1.0/testray-case-result-history/<caseId>"
```

Filter to entries where `testrayRoutineId` equals `<routineId>` and walk newest-first:

- `lastPassSha` is the `gitHash` of the first `PASSED` entry, or `null` if none.

- `firstFailSha` is the `gitHash` of the oldest `FAILED` entry before that `PASSED`, or `null` if none.
