---

paths:
  - ".claude/**/*.md"

---

# Markdown Style for `.claude`

When creating or editing any Markdown file under `.claude` (CLAUDE.md, SKILL.md, etc.), follow these conventions. For prose and general style not specific to Markdown (contractions, prefixes, complete sentences, ID casing, trailing slashes, file endings, CLI flags, and inline JSON spacing), follow the canonical rules in `pr-reviewer/rules`.

## Code Blocks

- Always leave a blank line before a fenced code block (` ``` `). A code block must never immediately follow a line of text.
- Indent continuation lines with a tab character, not spaces.

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

## Lists

- Sort unordered lists alphabetically when the order does not carry meaning.
- Use `1.` for every item (autonumbering) instead of sequential numbers.
- Separate each ordered list item with a blank line (loose list style).

## Prose

- Bold specific field or feature names so the capitalization reads as a proper name.
- Use literal UTF-8 characters (em dashes, curly quotes) rather than HTML entities.

## Shell Variables

- Brace every reference as `${VAR}`, both in code and prose.

## Tables

- Use `---` for each separator cell (or `:---`, `---:`, or `:---:` for alignment). Do not pad separator cells to match column widths.
- Use a single space of padding inside each header and data cell.