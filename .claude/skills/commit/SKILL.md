---
name: commit
description: Create a Git commit with a Jira-prefixed message derived from the staged diff. Use when the user asks to commit, wants to commit changes, or invokes /commit.
argument-hint: "[optional message hint]"
allowed-tools: [Bash, Glob, Grep, Read]
---

# Commit Changes

Compose a well-crafted Git commit for the current set of changes.

## 1. Gather Context

- When Claude modified or created files during this conversation, commit **only** those files. Stage them explicitly by name with `git add <file1> <file2> ...`. Do not include the user's own changes.
- When Claude did **not** modify any files in this conversation, commit **all** changes. Stage modified and deleted files, but leave untracked files alone. When untracked files exist, ask the user whether to include them.

Never use `git add -A` or `git add .`.

When nothing is staged, unstaged, or untracked, inform the user that there is nothing to commit and stop.

## 2. Extract the Jira Ticket

The ticket ID follows the pattern `LPD-12345`, `LCD-12345`, `LRCI-1234`, and similar forms (uppercase letters, hyphen, digits). Resolve the ticket in this order:

1. **Branch Name** — extract the ticket from the current branch (e.g., branch `LPD-83847` yields ticket `LPD-83847`).
2. **Recent Commits** — when the branch name lacks a ticket, scan the last five commit messages for a ticket prefix.
3. **User Argument** — when `${ARGUMENTS}` supplies a ticket ID, prefer that value.
4. **Fallback** — when no ticket surfaces, prompt the user for one.

## 3. Compose the Commit Message

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
- Wrap lines at 72 characters.
- Write plain prose rather than bullet points, matching the repository convention.

When `${ARGUMENTS}` carries a hint or description rather than a ticket ID, incorporate it into the message.

## 4. Confirm and Commit

Present the proposed commit message to the user and request confirmation. Once they approve, create the commit:

```bash
COMMIT_MESSAGE=$(cat <<'EOF'
<title>

<body if applicable>
EOF
)

git commit --message "${COMMIT_MESSAGE}"
```

When the user requests changes, revise the message and reconfirm.

## 5. Post-Commit

Run `git status` to confirm the commit succeeded, then display the result.
