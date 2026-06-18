---

allowed-tools: Bash(curl *), Bash(git *), Glob, Grep, Read
argument-hint: "[commit hash or description]"
description: Create a Jira task in the LPD project through the REST API. Use when the user asks to create a Jira task or LPD task ticket.
name: jira-task

---

# Create a Jira Task in LPD

Create a task ticket in the LPD Jira project through the REST API.

## Input

When `${ARGUMENTS}` is a commit hash, inspect the commit with `git show` to understand the work and infer the task. When `${ARGUMENTS}` is a free-form description, use it directly.

## Gather Information

Request any missing details from the user:

- **Summary** — short title describing the task.
- **Description** — what needs to be done and why.

## Required Fields

The LPD project requires the following fields. Apply these defaults unless the user specifies otherwise:

- **Component**: Infer from the code area. Fetch the LPD project components and select the one whose name matches the relevant area or keyword.
- **Issue Type**: `Task` (ID: `10002`).

Note: Unlike bugs, tasks do not require the Affects Version or Cross Cutting Properties fields.

## Create the Ticket

Create the issue in the LPD project with the gathered summary, description, and required fields. Author the description in Atlassian Document Format (ADF) with the following sections, in order: Description, Acceptance Criteria (when applicable). Append a Reference section when a commit is referenced.

## Output

The ticket key and the browse URL: `https://liferay.atlassian.net/browse/<KEY>`.