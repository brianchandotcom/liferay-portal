---

argument-hint: "<ticket-key-or-url>"
description: Start work on a Jira ticket.
name: start-work

---

# Start Work on a Jira Ticket

Prepare a Liferay ticket for development.

## Resolve the Ticket

Accept a key (`LPD-86295`) or a browse URL from `${ARGUMENTS}`. When nothing is supplied and `${PWD}` matches `*/liferay-portal-<KEY>` where `<KEY>` matches the Jira pattern `[A-Z]+-[0-9]+`, derive the key from the directory name. Otherwise, ask the user.

## Prerequisites

Abort when the working tree has uncommitted changes.

## Determine the Target

Fetch the ticket (issue type, current assignee, subtasks) and resolve the **target** — the ticket where the branch and active work state live:

- **Bug** (`10004`) — the bug itself. There is no child.
- **Story** (`10001`) or **Task** (`10002`) — the **Technical Task** (`10153`) subtask. Jira autocreates it when the parent is moved to an in-progress status, so the target does not exist yet and must be resolved after **Start Work on the Parent**. It may exist from a previous attempt, in which case it becomes the target.

## Start Work on the Parent

Assign the parent to the user and apply the transitions below. If the parent is already in an in-progress status by a different user, refuse to continue.

| Parent Type | Destination | Transition IDs |
| --- | --- | --- |
| Bug | In Progress | `61` |
| Story | In Development | `41`, then `61` |
| Task | In Progress | `21` |

For a Story, apply the two transitions in sequence: `41` moves it to **Ready for Development**, which triggers Jira to autocreate the **Technical Task** subtask, then `61` moves it to **In Development**.

## Start Work on the Child

Skip for **Bug**. For **Story** / **Task**, refetch the parent's subtasks until the **Technical Task** appears, then assign it to the user and transition it:

| Child Type | Destination | Transition ID |
| --- | --- | --- |
| Technical Task | In Progress | `41` |

## Create a Git Branch

Branch off the current HEAD, named after the **target** key. When the branch already exists, check it out instead.

## Make a Plan

Enter plan mode and read the tickets (both parent and child) to make the plan.