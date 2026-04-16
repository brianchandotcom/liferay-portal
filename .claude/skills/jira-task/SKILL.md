---
name: jira-task
description: Create a Jira task in the LPD project through the REST API. Use when the user asks to create a Jira task or LPD task ticket.
argument-hint: "[commit hash or description]"
allowed-tools: Bash(curl *), Bash(git *), Glob, Grep, Read
---

# Create a Jira Task in LPD

Create a task ticket in the LPD Jira project through the REST API, authenticating with credentials from the `${JIRA_API_USER}` and `${JIRA_API_TOKEN}` environment variables.

## Input

When `${ARGUMENTS}` is a commit hash, inspect the commit with `git show` to understand the work and infer the task. When `${ARGUMENTS}` is a free-form description, use it directly.

## Gather Information

Request any missing details from the user:

- **Summary** — short title describing the task.
- **Description** — what needs to be done and why.

## Required Fields

The LPD project requires the following fields. Apply these defaults unless the user specifies otherwise:

- **Component**: Select from the list below, or infer from the code area. Common components include:
	- `Content Publishing > Resource Importer` (id: `15805`)
	- `Data Integration > Export/Import` (id: `16131`)
	- `Headless Batch Engine API` (id: `16022`)
- **Issue Type**: `Task` (id: `10002`).

Note: Unlike bugs, tasks do not require the Affects Version or Cross Cutting Properties fields.

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

Author the description in Atlassian Document Format (ADF) with the following sections, in order: Description, Acceptance Criteria (when applicable). Append a Reference section when a commit is referenced.

## Output

Return the ticket key and the browse URL: `https://liferay.atlassian.net/browse/<KEY>`.
