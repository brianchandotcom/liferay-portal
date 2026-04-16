---
name: pr
description: Create a GitHub pull request for the current branch, transition the corresponding Jira ticket to review, and record the PR link on the ticket. Use when the user asks to create a PR, send a PR, or invokes /pr.
argument-hint: "[optional target-org/repo or message hint]"
allowed-tools: [Bash, Glob, Grep, Read]
---

# Create Pull Request

Create a GitHub pull request for the current branch and update the linked Jira ticket.

## 1. Gather Context

When uncommitted changes exist, warn the user and stop. They should commit first (suggest `/commit`).

## 2. Extract the Jira Ticket

The ticket ID follows the pattern `LPD-12345`, `LCD-12345`, `LRCI-1234`, and similar forms (uppercase letters, hyphen, digits). Resolve the ticket in this order:

1. **Branch Name** — extract the ticket from the current branch (e.g., branch `LPD-83847` yields ticket `LPD-83847`).
2. **Recent Commits** — when the branch name lacks a ticket, scan recent commit messages for a ticket prefix.
3. **User Argument** — when `${ARGUMENTS}` supplies a ticket ID, prefer that value.
4. **Fallback** — when no ticket surfaces, prompt the user for one.

## 3. Determine the Target

- **Base Branch**: `master`.
- **Fork Owner**: When `${ARGUMENTS}` names a GitHub organization or user, use that. Otherwise, ask the user to choose from `liferay-bpm`, `liferay-content-management`, or `liferay-page-management`.
- **Fork Remote**: Use the user's remote (default: `origin`). Identify the GitHub username from the remote URL (e.g., `git@github.com:brianchandotcom/liferay-portal.git` yields `brianchandotcom`).
- **Head**: `<github-username>:<branch-name>`.
- **Target Repository**: `<fork-owner>/liferay-portal` by default. When `${ARGUMENTS}` names a different `org/repo`, use that.

## 4. Push the Branch

Push the branch to the user's remote when it has not been pushed or when new local commits exist:

```bash
git push --set-upstream origin <branch-name>
```

## 5. Analyze Changes and Create the Pull Request

Read the full diff (`git diff master...HEAD`) and every commit message on the branch to understand the complete scope of changes.

### Pull Request Title

Keep it concise (under 72 characters) and prefix with the Jira ticket:

```
LPD-83847 Fix OutOfMemoryError during batch engine import
```

### Pull Request Body

Use the following format:

```markdown
https://liferay.atlassian.net/browse/TICKET-ID

**What is being fixed:** Explain the problem or bug that motivated the
change — what was going wrong or what was missing.

**How it is being fixed:** Explain the approach taken across all commits.
Describe the key changes and the reasoning behind the approach. Write in
plain prose rather than bullet points.
```

### Create the Pull Request

```bash
PR_BODY=$(cat <<'EOF'
<body>
EOF
)

gh pr create \
	--base master \
	--body "${PR_BODY}" \
	--head <username>:<branch> \
	--repo <target-org/repo> \
	--title "<title>"
```

Present the proposed title and body to the user before submitting. Proceed once they approve.

## 6. Update the Jira Ticket

Once the pull request is created successfully, transition the Jira ticket into review and record the pull request URL in the **Git Pull Request** field via the Jira REST API.

### Determine the Ticket Type and Resolve the Target Ticket

LPD applies different workflows depending on the issue type. Check the issue type first:

```bash
curl \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/issue/<TICKET>?fields=issuetype,subtasks" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

- **Bug** (type id `10004`): Use the bug ticket directly. The transition to **In Review** uses id `71`.
- **Task** (parent, type id `10002`): Locate its Technical Task subtask and use that subtask's key. Every subsequent operation (transition, PR field, PR title, summary) must reference the **subtask's key**. The transition to **In Peer Review** uses id `31`.
- **Technical Task** (subtask, type id `10153`): Use it directly. The transition to **In Peer Review** uses id `31`.

### Ensure the Ticket Is In Progress First

Before transitioning to review, check the ticket's current status. When it is not already "In Progress", transition it to In Progress first. The transition ID depends on the issue type:

- **Bug**: In Progress transition id is `61`.
- **Technical Task**: In Progress transition id is `41`.

```bash
curl \
	--data '{"transition": {"id": "<61-for-bug|41-for-task>"}}' \
	--header "Content-Type: application/json" \
	--request POST \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/issue/<TARGET-TICKET>/transitions" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

### Transition to Review

For **Bug** tickets (In Review, id `71`):

```bash
curl \
	--data '{"transition": {"id": "71"}}' \
	--header "Content-Type: application/json" \
	--request POST \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/issue/<BUG>/transitions" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

For **Technical Task** tickets or a subtask resolved from a Task (In Peer Review, id `31`):

```bash
curl \
	--data '{"transition": {"id": "31"}}' \
	--header "Content-Type: application/json" \
	--request POST \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/issue/<TECHNICAL-TASK>/transitions" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

Then set the Git Pull Request field on the target ticket:

```bash
curl \
	--data '{"fields": {"customfield_10201": "<PR-URL>"}}' \
	--header "Content-Type: application/json" \
	--request PUT \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/issue/<TARGET-TICKET>" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

- `customfield_10201` is the "Git Pull Request" text field.

When the transition fails (for example, when the ticket is already in a later status), still attempt to set the PR field separately. Confirm the update by fetching the ticket status and PR field afterward.

## 7. Summary

Report back with:

- The pull request URL.
- The Jira ticket status and link.
