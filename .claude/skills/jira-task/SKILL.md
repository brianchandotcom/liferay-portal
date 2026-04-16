---
name: jira-task
description: Create a Jira task in the LPD project. Use when the user asks to create a Jira task or LPD task ticket.
argument-hint: "[commit-hash-or-description]"
allowed-tools: Bash(git *), Bash(curl *), Read, Grep, Glob
---

# Create a Jira Task in LPD

Create a task ticket in the LPD Jira project using the REST API with credentials from `$JIRA_API_USER` and `$JIRA_API_TOKEN` environment variables.

## Input

If `$ARGUMENTS` is a commit hash, inspect the commit with `git show` to understand the work and infer the task. If it is a description, use that directly.

## Gather Information

Ask the user for any missing details:
- **Summary** — short title describing the task
- **Description** — what needs to be done and why

## Required Fields

The LPD project requires these fields. Use these defaults unless the user specifies otherwise:
- **Issue Type**: `Task` (id: `10002`)
- **Component**: Ask the user or infer from the code area. Common ones:
  - `Headless Batch Engine API` (id: `16022`)
  - `Data Integration > Export/Import` (id: `16131`)
  - `Content Publishing > Resource Importer` (id: `15805`)
  - If unsure, search components: `curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" "https://liferay.atlassian.net/rest/api/3/project/LPD/components" | python3 -c "import json,sys; [print(f'{c[\"id\"]:>6} {c[\"name\"]}') for c in json.load(sys.stdin) if 'SEARCH_TERM' in c['name'].lower()]"`

Note: Unlike bugs, tasks do NOT require Affects Version or Cross Cutting Properties fields.

## Create the Ticket

Use the Jira REST API v3 to create the issue:

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" \
  -X POST \
  -H "Content-Type: application/json" \
  "https://liferay.atlassian.net/rest/api/3/issue" \
  -d '<JSON payload>'
```

Use Atlassian Document Format (ADF) for the description with sections: Description, Acceptance Criteria (if applicable). Include a Reference section if a commit is referenced.

## Output

Return the ticket key and URL: `https://liferay.atlassian.net/browse/<KEY>`
