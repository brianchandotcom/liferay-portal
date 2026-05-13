# Commit

Follow these rules before committing any change to the repository. These rules apply to all commits.

## Format Source

Before composing the commit, always invoke the `format-source` skill to ensure all changes follow Liferay's coding standards. After it completes, any edits the formatter applied are part of what gets committed.

## Extract the Jira Ticket

The ticket ID follows the pattern `LPD-12345`, `LCD-12345`, `LRCI-1234`, and similar forms (uppercase letters, hyphen, digits). Resolve the ticket in this order:

1. **Branch Name** — extract the ticket from the current branch (e.g., branch `LPD-83847` yields ticket `LPD-83847`).

1. **Recent Commits** — when the branch name lacks a ticket, scan the last five commit messages for a ticket prefix.

1. **User Argument** — when the user supplies a ticket ID, prefer that value.

1. **Fallback** — when no ticket surfaces, prompt the user for one.

## Compose the Commit Message

Read the diff, understand the actual behavior change, then compose the message.

### Title

Format: `<TICKET> <Summary of behavior change>`

- Lead with the Jira ticket ID.
- Follow with a concise summary of the outcome — the problem resolved or the behavior changed, not the code itself.
- Use sentence case (capitalize the first word only).
- Omit a trailing period.
- Keep the full line under 72 characters, including the ticket prefix.
- When a companion Jira ticket also applies (e.g., a related subtask), append it after the first: `LPD-12345 LPD-67890 Summary`.

Examples:

- `LCD-50509 Grant ArgoCD permission to the correct namespace`
- `LPD-83357 Add validation to prevent folder changes for CMS object definitions`
- `LPD-83630 Fix typo`
- `LPD-84627 Prevent dispatch trigger loss when the Analytics admin user is missing`

### Body

Add a body **only** when the title alone does not fully convey the change. Omit the body for trivial edits such as typo fixes, simple renames, or single-line changes.

When a body is warranted:

- Separate it from the title with a blank line.
- Explain **why** the change is needed — the problem, the prior behavior, or the motivation. Do not restate the code.
- Do not wrap lines in the body.
- Write plain prose rather than bullet points, matching the repository convention.

When the user provides a hint or description, incorporate it into the message.