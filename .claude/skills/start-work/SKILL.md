---

argument-hint: "<ticket-key-or-url>"
description: Start work on a Jira ticket.
name: start-work

---

# Start Work on a Jira Ticket

Prepare a Liferay ticket for development. Drive every Jira interaction through the Jira Cloud REST API at `liferay.atlassian.net`, authenticated with `${JIRA_API_USER}` / `${JIRA_API_TOKEN}`.

## 1. Resolve the Ticket

Accept a key (`LPD-86295`) or a browse URL from `${ARGUMENTS}`. Ask the user when nothing is supplied.

## 2. Prerequisites

Abort when the working tree has uncommitted changes.

## 3. Determine the Target

Fetch the ticket (issue type, current assignee, subtasks) and resolve the **target** — the ticket where the branch and active work state live:

- **Bug** (`10004`) — the bug itself. There is no child.
- **Story** (`10001`) or **Task** (`10002`) — the **Technical Task** (`10153`) subtask. Jira autocreates it when the parent is moved to an in-progress status, so the target does not exist yet and must be resolved after step 4. It may exist from a previous attempt, in which case it becomes the target.

## 4. Start Work on the Parent

Assign the parent to the user and transition it using the ID below. If the parent is already in an in-progress status by a different user, refuse to continue.

| Parent Type | Destination | Transition ID |
| ----------- | ---------------------- | ------------- |
| Bug | In Progress | `61` |
| Story | Ready for Development | `41` |
| Task | In Progress | `21` |

## 5. Start Work on the Child

Skip for **Bug**. For **Story** / **Task**, refetch the parent's subtasks until the **Technical Task** appears, then assign it to the user and transition it:

| Child Type | Destination | Transition ID |
| -------------- | ----------- | ------------- |
| Technical Task | In Progress | `41` |

## 6. Create a Git Branch

Branch off the current HEAD, named after the **target** key. When the branch already exists, check it out instead.

## 7. Make a Plan

Enter plan mode and read the tickets (both parent and child) to make the plan.