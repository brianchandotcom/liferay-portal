---

allowed-tools: [Bash]
argument-hint: "<caseResultId>"
description: Collect failure data for a single Testray case result.
name: test-fix-testray-data

---

# Collect Failure Data

Collect a single Testray case result's failure data.

## Preconditions

- `${TESTRAY_CLIENT_ID}` and `${TESTRAY_CLIENT_SECRET}` are set in the environment.

## Input

### Case Result ID

`${ARGUMENTS}` is the Testray case result ID, a positive integer.

## Expected Output

Each field is derived from the Testray REST API at `https://testray.liferay.com`. Every call uses a bearer token from `/o/oauth2/token` (OAuth2 client credentials grant, authenticated with `${TESTRAY_CLIENT_ID}` and `${TESTRAY_CLIENT_SECRET}`). Fetch the token once per run and reuse it for all calls.

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
