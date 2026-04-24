---

paths:
  - ".claude/**/*.md"

---

# Markdown Style for `.claude`

When creating or editing any Markdown file under `.claude` (CLAUDE.md, SKILL.md, etc.), follow these conventions:

- **CLI flags**: prefer long-form flags (`--execute` instead of `-e`, `--message` instead of `-m`, `--user` instead of `-u`). Sort flags alphabetically. Keep short flags only for tools without portable long-form support (`nc`, `ps`, `rm`, `sed`).
- **Code blocks**: always leave a blank line before a fenced code block (` ``` `). A code block must never immediately follow a line of text.
- **Continuation lines**: indent with a tab character, not spaces.
- **File ending**: files must end without a trailing newline. The last line of content is the last byte of the file.
- **Frontmatter**: sort keys alphabetically. Sort values inside `allowed-tools` alphabetically. Add a blank line after the opening `---` and before the closing `---`.
- **Headings**: Title Case at every level, including table headers. Use correct casing for brand and product names (Arquillian, Git, GitHub, Gradle, Jira, Liferay, Playwright, Poshi, TypeScript).
- **Inline JSON**: space after `:` and `,`, no space after `{` or before `}`.
- **Lists**: sort unordered lists alphabetically when the order does not carry meaning.
- **Ordered lists**: use `1.` for every item (autonumbering) instead of sequential numbers. Separate each item with a blank line (loose list style).
- **Prose**: avoid contractions ("do not" rather than "don't"). Do not hyphenate short prefixes that are not standalone words ("autocreate", "refetch", "rerun"). Write "ID" in uppercase in prose; reserve lowercase `id` for code contexts. Use complete sentences.
- **Shell variables**: brace every reference as `${VAR}`, both in code and prose.