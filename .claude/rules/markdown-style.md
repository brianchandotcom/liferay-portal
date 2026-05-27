---

paths:
  - ".claude/**/*.md"

---

# Markdown Style for `.claude`

When creating or editing any Markdown file under `.claude` (CLAUDE.md, SKILL.md, etc.), follow these conventions.

## CLI Commands

- Prefer long-form flags (`--execute` instead of `-e`, `--message` instead of `-m`, `--user` instead of `-u`).
- Sort flags alphabetically.
- Keep short flags only for tools without portable long-form support (`nc`, `ps`, `rm`, `sed`).
- When arguments span multiple lines, place the command alone on the first line and each argument on its own tab-indented line. Do not mix arguments on the command line with arguments on continuation lines.

Before:

```bash
curl -s -u "$JIRA_API_USER:$JIRA_API_TOKEN" -X POST \
  -d '{"key":"value"}' \
  "https://example.com/api"
```

After:

```bash
curl \
	--data "{\"key\": \"value\"}" \
	--request POST \
	--silent \
	--url "https://example.com/api" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

## Code Blocks

- Always leave a blank line before a fenced code block (` ``` `). A code block must never immediately follow a line of text.
- Indent continuation lines with a tab character, not spaces.

## Directory Paths

- Do not append a trailing slash to directory paths (`<BUNDLE>/osgi/configs`, not `<BUNDLE>/osgi/configs/`).

## File Ending

- Files must end without a trailing newline. The last line of content is the last byte of the file.

## Frontmatter

- Sort keys alphabetically.
- Sort values inside `allowed-tools` alphabetically.
- Add a blank line after the opening `---` and before the closing `---`.

## Headings

- Apply Title Case at every level, including table headers.
- Do not number headings (`### Do the Thing`, not `### 1. Do the Thing`).
- Follow every heading with a blank line before the body content.
- Use correct casing for brand and product names (Arquillian, Atlassian, Git, GitHub, Gradle, Jira, JUnit, Liferay, Mockito, Playwright, Poshi, REST API, TypeScript).

## Heredocs

- Lift multiline heredoc values into named variables so the containing command keeps its sorted flag order.

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

## Inline JSON

- Space after `:` and `,`, no space after `{` or before `}`.

## Lists

- Sort unordered lists alphabetically when the order does not carry meaning.
- Use `1.` for every item (autonumbering) instead of sequential numbers.
- Separate each ordered list item with a blank line (loose list style).

## Prose

- Avoid contractions ("do not" rather than "don't").
- Bold specific field or feature names so the capitalization reads as a proper name.
- Do not hyphenate short prefixes that are not standalone words ("autocreate", "refetch", "rerun").
- Use complete sentences.
- Use literal UTF-8 characters (em dashes, curly quotes) rather than HTML entities.
- Write "ID" in uppercase in prose; reserve lowercase `id` for code contexts.

## Shell Variables

- Brace every reference as `${VAR}`, both in code and prose.

## Tables

- Use `---` for each separator cell (or `:---`, `---:`, or `:---:` for alignment). Do not pad separator cells to match column widths.
- Use a single space of padding inside each header and data cell.

## URLs

- Do not end URLs with a trailing slash.