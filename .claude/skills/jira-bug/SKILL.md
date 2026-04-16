---
name: jira-bug
description: Create a Jira bug ticket in the LPD project. Use when the user asks to create/file a Jira bug or LPD ticket.
argument-hint: "[commit-hash-or-description]"
allowed-tools: Bash(git *), Bash(curl *), Read, Grep, Glob
---

# Create a Jira Bug in LPD

Create a bug ticket in the LPD Jira project using the REST API with credentials from `$JIRA_API_USER` and `$JIRA_API_TOKEN` environment variables.

## Input

If `$ARGUMENTS` is a commit hash, inspect the commit with `git show` to understand the fix and infer the bug it addresses. If it is a description, use that directly.

## Gather Information

Ask the user for any missing details:
- **Summary** — short title describing the bug
- **Steps to Reproduce** — clear, minimal steps
- **Expected Behavior**
- **Actual Behavior**

## Required Fields

The LPD project requires these fields. Use these defaults unless the user specifies otherwise:
- **Affects Version**: `Master` (id: `16660`)
- **Component**: Ask the user or infer from the code area. Common ones:
  - `Headless Batch Engine API` (id: `16022`)
  - `Data Integration > Export/Import` (id: `16131`)
  - `Content Publishing > Resource Importer` (id: `15805`)
  - If unsure, search components: `curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" "https://liferay.atlassian.net/rest/api/3/project/LPD/components" | python3 -c "import json,sys; [print(f'{c[\"id\"]:>6} {c[\"name\"]}') for c in json.load(sys.stdin) if 'SEARCH_TERM' in c['name'].lower()]"`
- **Cross Cutting Properties** (`customfield_10979`): `None` (id: `14468`)

## Create the Ticket

Use the Jira REST API v3 to create the issue:

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" \
  -X POST \
  -H "Content-Type: application/json" \
  "https://liferay.atlassian.net/rest/api/3/issue" \
  -d '<JSON payload>'
```

Use Atlassian Document Format (ADF) for the description with sections: Description, Steps to Reproduce, Expected Behavior, Actual Behavior. Include a Fix section if a commit is referenced.

## Output

Return the ticket key and URL: `https://liferay.atlassian.net/browse/<KEY>`
