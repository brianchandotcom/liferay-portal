---

allowed-tools: [Bash, Glob, Grep, Read]
argument-hint: "[optional target-org/repo or message hint]"
description: Create a GitHub pull request for the current branch, transition the corresponding Jira ticket to review, and record the PR link on the ticket. Use when the user asks to create a PR, send a PR, or invokes /pr.
name: pr

---

# Create a Pull Request

Create a GitHub pull request for the current branch, transition the linked Jira ticket to review, and record the pull request URL on that ticket. Drive every Jira interaction through the Jira Cloud REST API at `liferay.atlassian.net`, authenticated with `${JIRA_API_USER}` and `${JIRA_API_TOKEN}`.

## Preconditions

- At least one commit adds tests. When none do, ask the user for a rationale and refuse to proceed without one. The only exceptions are pull requests with no code changes (e.g., language key updates or markdown changes).

- The current branch is a development branch, not `master` or any other protected branch.

- The working tree has no uncommitted changes. When dirty, abort and ask the user to commit first (suggest `/commit`); do not stash or discard their work.

## Input

### Branch

The current Git branch must contain the commits ready to ship.

### Jira Ticket

Resolve a ticket key in priority order:

1. **User Argument** — when `${ARGUMENTS}` supplies a ticket key, prefer that value.

1. **Branch Name** — extract the ticket from the current branch (e.g., branch `LPD-83847` yields ticket `LPD-83847`).

1. **Recent Commits** — when neither produces a ticket, scan recent commit messages for a ticket prefix.

1. **Fallback** — when nothing surfaces, prompt the user.

The ticket key follows the pattern `LPD-12345`, `LCD-12345`, `LRCI-1234`, and similar forms (uppercase letters, hyphen, digits).

### Target Repository

The target repository defaults to `<fork-owner>/liferay-portal`. When `${ARGUMENTS}` names a different `org/repo`, use that; otherwise, ask the user to choose `<fork-owner>` from one of the team forks:

- `liferay-ac`
- `liferay-appsec`
- `liferay-bpm`
- `liferay-commerce`
- `liferay-content-management`
- `liferay-core-infra`
- `liferay-database-infra`
- `liferay-devtools`
- `liferay-frontend`
- `liferay-headless`
- `liferay-page-management`
- `liferay-platform-experience`
- `liferay-search`
- `liferay-site-management`

The pull request head is `<github-username>:<branch-name>` (the GitHub username is read from the user's `origin` remote URL — e.g., `git@github.com:brianchandotcom/liferay-portal.git` yields `brianchandotcom`), and the base is `master`.

## Expected Output

### Pushed Branch

Push the current branch to the user's remote when it has not been pushed yet or when new local commits exist.

### Pull Request

The title is concise (under 72 characters) and prefixed with the Jira ticket:

```
LPD-83847 Fix OutOfMemoryError during batch engine import
```

The body follows this format:

```markdown
https://liferay.atlassian.net/browse/TICKET-ID

## What is being fixed

Explain the problem or bug that motivated the change — what was going
wrong or what was missing.

## How it is being fixed

Explain the approach taken across all commits. Describe the key changes
and the reasoning behind the approach. Write in plain prose rather than
bullet points.

## Why are there no tests?

This optional section is only included when the commits do not add any
tests. It should contain the rationale provided by the user.
```

Use a direct, to-the-point style. Avoid being verbose. Present the proposed title and body to the user before submitting, and proceed once they approve.

### Transitioned Jira Ticket

Fetch the input ticket (issue type, status, subtasks) and resolve the **target ticket** — the one whose status reflects active work and on which the pull request URL is recorded:

| Ticket Type | Target |
| ------------------------ | ------------------------------------ |
| Bug (`10004`) | The bug itself |
| Task (`10002`) | Its Technical Task (`10153`) subtask |
| Technical Task (`10153`) | Itself |

When the target is not already in an in-progress status, transition it first:

| Target Type | Destination | Transition ID |
| -------------- | ----------- | ------------- |
| Bug | In Progress | `61` |
| Technical Task | In Progress | `41` |

Then transition it to review:

| Target Type | Destination | Transition ID |
| -------------- | -------------- | ------------- |
| Bug | In Review | `71` |
| Technical Task | In Peer Review | `31` |

When the review transition fails (for example, because the ticket is already in a later status), still proceed to record the pull request URL.

Set the **Git Pull Request** field (`customfield_10201`) on the target ticket to the new pull request URL.

### Summary

Report back to the user with:

- The Jira ticket status and link.
- The pull request URL.