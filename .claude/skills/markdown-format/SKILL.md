---
allowed-tools: [Bash, Edit, Glob, Grep, Read, Write]
argument-hint: "[path to Markdown file]"
description: Format a Markdown file to match Liferay conventions — frontmatter order, Title Case headings, braced shell variables, long-form CLI flags, and professional prose. Use when the user asks to format, clean up, polish, or copy-edit a Markdown file destined for the Liferay repository.
name: markdown-format
---

# Markdown Formatter

Apply Liferay Markdown conventions to the target file so structure, casing, and prose land consistent and professional.

## Input

When `${ARGUMENTS}` is a path to a Markdown file, format that file. When no path is supplied, ask the user which file to format.

## Workflow

### 1. Read the File

Read the target file in full before making changes. Note the frontmatter, headings, code blocks, and any conventions already in use.

### 2. Apply the Conventions

Work through the checks below in order. Fix each category before moving to the next.

### 3. Validate

Reread the file after the pass and confirm every check still holds.

### 4. Report

Summarize the changes, grouped by category (frontmatter, headings, prose, shell, lists).

## Conventions

### Frontmatter

- Sort keys alphabetically (e.g., `allowed-tools`, `argument-hint`, `description`, `name`).
- Sort the values inside `allowed-tools` alphabetically.

### Headings

- Apply Title Case at every heading level — including `###` and deeper.
- Follow every heading with a blank line before the body content.
- Use the correct casing for brand and product names: Arquillian, Atlassian, GitHub, Gradle, Jira, JUnit, Liferay, Mockito, Playwright, Poshi, REST API, TypeScript.

### Prose

- Write "ID" in uppercase when the word appears in prose. Reserve lowercase `id` for code contexts such as JSON keys, identifier names, and URL parameters.
- Avoid contractions. Write "do not" rather than "don't", "does not" rather than "doesn't", "it is" rather than "it's".
- Use complete sentences. Avoid fragments that look like sentences but omit parts.
- Do not hyphenate short prefixes that are not standalone words. Write "nonoverlapping", "multiline", "coworking", "predestined".
- Do not end URLs with a trailing slash.
- Use literal UTF-8 characters (em dashes, curly quotes) rather than HTML entities such as `&#8212;` or `&#8220;`.
- When a specific field or feature name appears in prose, bold it so the capitalization reads as a proper name: "set the **Git Pull Request** field".

### Shell Variables

- Brace every shell variable reference as `${VAR}`, both in code and in prose. Write `${JIRA_API_USER}` rather than `$JIRA_API_USER`.

### CLI Commands

- Prefer long-form flags: `--data` instead of `-d`, `--header` instead of `-H`, `--message` instead of `-m`, `--request` instead of `-X`, `--set-upstream` instead of `-u`, `--silent` instead of `-s`, `--user` instead of `-u`.
- Sort flags alphabetically by flag name.
- When a command's arguments span multiple lines, place the command alone on the first line and each argument on its own subsequent line. Do not mix arguments on the command line with arguments below.
- Indent continuation lines with a tab character, matching Liferay's shell-script convention.

### Multiline Heredocs

Lift multiline heredoc values into named variables so the containing command keeps its sorted order:

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

### Inline JSON

Add a space after `:` and `,`, but no space after `{` or before `}`. Example: `{"key": "value", "other": "data"}`.

### Lists

- Sort unordered lists alphabetically when the order does not carry meaning (component catalogs, tool options, brand lists).
- Preserve order for numbered workflow steps, priority rankings, and any sequence where the order conveys information.

### Example Rewrites

Short-form, unsorted, unbraced:

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" -X POST \
  -d '{"key":"value"}' \
  "https://example.com/api"
```

Long-form, sorted, braced, tab-indented:

```bash
curl \
	--data '{"key": "value"}' \
	--request POST \
	--silent \
	--url "https://example.com/api" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```
