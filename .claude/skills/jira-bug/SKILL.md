---
name: jira-bug
description: Create a Jira bug ticket in the LPD project through the REST API. Use when the user asks to create or file a Jira bug or LPD ticket.
argument-hint: "[commit hash or description]"
allowed-tools: Bash(curl *), Bash(git *), Glob, Grep, Read
---

# Create a Jira Bug in LPD

Create a bug ticket in the LPD Jira project through the REST API, authenticating with credentials from the `${JIRA_API_USER}` and `${JIRA_API_TOKEN}` environment variables.

## Input

When `${ARGUMENTS}` is a commit hash, inspect the commit with `git show` to understand the fix and infer the bug it addresses. When `${ARGUMENTS}` is a free-form description, use it directly.

## Gather Information

Request any missing details from the user:

- **Summary** — short title describing the bug.
- **Steps to Reproduce** — clear, minimal steps.
- **Expected Behavior** — what should have happened.
- **Actual Behavior** — what happened instead.

## Required Fields

The LPD project requires the following fields. Apply these defaults unless the user specifies otherwise:

- **Affects Version**: `Master` (id: `16660`).
- **Component**: Select from the list below, or infer from the code area. Common components include:
	- `Content Publishing > Resource Importer` (id: `15805`)
	- `Data Integration > Export/Import` (id: `16131`)
	- `Headless Batch Engine API` (id: `16022`)
- **Cross Cutting Properties** (`customfield_10979`): `None` (id: `14468`).

When no listed component matches, search by keyword:

```bash
curl \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/project/LPD/components" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}" \
	| python3 -c "import json, sys; [print(f'{c[\"id\"]:>6} {c[\"name\"]}') for c in json.load(sys.stdin) if 'SEARCH_TERM' in c['name'].lower()]"
```

## Create the Ticket

Submit the issue through Jira REST API v3:

```bash
curl \
	--data '<JSON payload>' \
	--header "Content-Type: application/json" \
	--request POST \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/issue" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

Author the description in Atlassian Document Format (ADF) with the following sections, in order: Description, Steps to Reproduce, Expected Behavior, Actual Behavior. Append a Fix section when a commit is referenced.

## Output

Return the ticket key and the browse URL: `https://liferay.atlassian.net/browse/<KEY>`.
