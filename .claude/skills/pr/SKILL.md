---
name: pr
description: Create a GitHub pull request for the current branch, update the Jira ticket to In Review, and set the PR link. Use when the user asks to create a PR, send a PR, or says /pr.
argument-hint: "[optional target-org/repo or message hint]"
allowed-tools: [Bash, Read, Grep, Glob]
---

# Create Pull Request

Create a GitHub PR for the current branch and update the Jira ticket.

## 1. Gather Context

If there are uncommitted changes, warn the user and stop — they should commit first (suggest `/commit`).

## 2. Extract the Jira Ticket

The ticket ID is a pattern like `LPD-12345`, `LCD-12345`, `LRCI-1234`, etc. (uppercase letters, hyphen, digits).

1. **Branch name** — extract the ticket from the current branch name (e.g., branch `LPD-83847` → ticket `LPD-83847`).
2. **Previous commits** — if the branch name has no ticket, look at the commit messages.
3. **User argument** — if `$ARGUMENTS` contains a ticket ID, use that instead.
4. **Not found** — if no ticket is found anywhere, ask the user for one.

## 3. Determine the Target

- **Fork owner**: If `$ARGUMENTS` specifies a GitHub org/user, use that. Otherwise, ask the user to choose from: `liferay-bpm`, `liferay-content-management`, `liferay-page-management`.
- **Fork remote**: Use the user's remote (default: `origin`). Identify the GitHub username from the remote URL (e.g., `git@github.com:brianchandotcom/liferay-portal.git` → `brianchandotcom`).
- **Target repo**: Default is `<fork-owner>/liferay-portal`. If `$ARGUMENTS` specifies a different `org/repo`, use that.
- **Base branch**: `master`.
- **Head**: `<github-username>:<branch-name>`.

## 4. Push the Branch

Push the branch to the user's remote if not already pushed or if there are new local commits:

```bash
git push -u origin <branch-name>
```

## 5. Analyze Changes and Create the PR

Read the full diff (`git diff master...HEAD`) and all commit messages to understand what changed.

### PR Title

Keep it short (under 72 characters). Use the Jira ticket as prefix:

```
LPD-83847 Fix OutOfMemoryError during batch engine import
```

### PR Body

Use this format:

```markdown
https://liferay.atlassian.net/browse/TICKET-ID

**What is being fixed:** Explain the problem or bug that motivated the
change. What was going wrong or what was missing.

**How it is being fixed:** Explain the approach taken across all commits.
Describe the key changes and why this approach was chosen. Write in plain
prose, not bullet points.
```

### Create the PR

```bash
gh pr create --repo <target-org/repo> --head <username>:<branch> --base master --title "<title>" --body "$(cat <<'EOF'
<body>
EOF
)"
```

Show the user the proposed title and body before creating. If they approve, proceed.

## 6. Update Jira Ticket

After the PR is created successfully, transition the Jira ticket to review and set the **Git Pull Request** field using the Jira REST API.

### Determine the ticket type and resolve the target ticket

LPD uses different workflows depending on the issue type. Check the issue type first:

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" \
  "https://liferay.atlassian.net/rest/api/3/issue/<TICKET>?fields=issuetype,subtasks"
```

- **Bug** (type id `10004`): Use the bug ticket directly. Transition to **In Review** uses id `71`.
- **Technical Task** (subtask, type id `10153`): Use it directly. Transition to **In Peer Review** uses id `31`.
- **Task** (parent, type id `10002`): Find its Technical Task subtask and use that subtask's key instead. All subsequent operations (transition, PR field, PR title, summary) should reference the **subtask's key**. Transition to **In Peer Review** uses id `31`.

### Ensure the ticket is In Progress first

Before transitioning to review, check the ticket's current status. If it is **not** already "In Progress", transition it to In Progress first. The transition ID depends on the issue type:

- **Bug**: In Progress transition id is `61`
- **Technical Task**: In Progress transition id is `41`

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" \
  -X POST \
  -H "Content-Type: application/json" \
  "https://liferay.atlassian.net/rest/api/3/issue/<TARGET-TICKET>/transitions" \
  -d '{
    "transition": {"id": "<61-for-bug|41-for-task>"}
  }'
```

### Transition to review

For **Bug** tickets (In Review, id `71`):

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" \
  -X POST \
  -H "Content-Type: application/json" \
  "https://liferay.atlassian.net/rest/api/3/issue/<BUG>/transitions" \
  -d '{
    "transition": {"id": "71"}
  }'
```

For **Technical Task** tickets or subtask resolved from a Task (In Peer Review, id `31`):

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" \
  -X POST \
  -H "Content-Type: application/json" \
  "https://liferay.atlassian.net/rest/api/3/issue/<TECHNICAL-TASK>/transitions" \
  -d '{
    "transition": {"id": "31"}
  }'
```

Then set the Git Pull Request field on the target ticket:

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" \
  -X PUT \
  -H "Content-Type: application/json" \
  "https://liferay.atlassian.net/rest/api/3/issue/<TARGET-TICKET>" \
  -d '{"fields": {"customfield_10201": "<PR-URL>"}}'
```

- `customfield_10201` = "Git Pull Request" text field

If the transition fails (e.g., ticket is already in a later status), still try to set the PR field separately. Verify the update by fetching the ticket status and PR field.

## 7. Summary

Report back:
- PR URL
- Jira ticket status and link
