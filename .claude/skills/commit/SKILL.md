---
name: commit
description: Commit staged/unstaged changes with an auto-generated message. Analyzes the diff to produce a Jira-prefixed commit title and optional body. Use when the user asks to commit, wants to commit changes, or says /commit.
argument-hint: "[optional message hint]"
allowed-tools: [Bash, Read, Grep, Glob]
---.

# Commit Changes

Create a well-crafted git commit for the current changes.

## 1. Gather Context

- If Claude modified or created files during this conversation, commit **only** those files. Stage them explicitly by name using `git add <file1> <file2> ...`. Do not include the user's own changes.
- If Claude did **not** modify any files in this conversation, commit **all** changes — stage modified/deleted files (but NOT untracked files). If there are untracked files, ask the user whether to include them.

Never use `git add -A` or `git add .`.

If there are no changes at all (no staged, no unstaged, no untracked), tell the user there is nothing to commit and stop.

## 2. Extract the Jira Ticket

The ticket ID is a pattern like `LPD-12345`, `LCD-12345`, `LRCI-1234`, etc. (uppercase letters, hyphen, digits).

1. **Branch name** — extract the ticket from the current branch name (e.g., branch `LPD-83847` → ticket `LPD-83847`).
2. **Previous commits** — if the branch name has no ticket, look at the last 5 commit messages for a ticket prefix.
3. **User argument** — if `$ARGUMENTS` contains a ticket ID, use that instead.
4. **Not found** — if no ticket is found anywhere, ask the user for one.

## 3. Analyze the Diff and Write the Commit Message

Read and understand the actual code changes (the diff). Then compose the commit message:

### Title (first line)

Format: `<TICKET> <What is being fixed/added/improved>`

- Start with the Jira ticket ID
- Follow with a concise summary of **what** is being fixed or achieved — describe the problem or behavior change, not the code changes
- Use sentence case (capitalize first word only)
- No period at the end
- Keep under 72 characters total (ticket + space + summary)
- If a second Jira ticket is relevant (e.g., the change addresses a sub-task tracked elsewhere), include it after the first: `LPD-12345 LPD-67890 Summary`

Examples of good titles:
- `LPD-84627 Prevent dispatch trigger loss when analytics admin user is missing`
- `LPD-83357 Add validation to prevent folder changes for CMS object definitions`
- `LPD-83630 Fix typo`
- `LCD-50509 Grant ArgoCD permission to correct namespace`

### Body (optional)

Add a body **only** if the title alone doesn't fully explain the change. Skip the body for trivial/obvious changes (typo fixes, simple renames, single-line changes).

When included:
- Separate from title with a blank line
- Explain **why** the change is needed — describe the problem, the previous behavior, or the motivation, not the code changes themselves
- Wrap lines at 72 characters
- Use plain prose, not bullet points (match the repo convention)

If `$ARGUMENTS` contains a hint or description (not a ticket ID), incorporate it into the message.

## 4. Confirm and Commit

Show the user the proposed commit message and ask for confirmation. If they approve (or say nothing needs changing), create the commit:

```bash
git commit -m "$(cat <<'EOF'
<title>

<body if applicable>
EOF
)"
```

If the user wants changes, adjust and re-confirm.

## 5. Post-Commit

Run `git status` to confirm the commit succeeded and show the result.
