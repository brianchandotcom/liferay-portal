---

allowed-tools: [Bash, Edit, Glob, Grep, Read, Write]
argument-hint: "[path to Markdown file]"
description: Format a Markdown file to match Liferay conventions — frontmatter order, Title Case headings, braced shell variables, long-form CLI flags, and professional prose. Use when the user asks to format, clean up, polish, or copy-edit a Markdown file destined for the Liferay repository.
name: markdown-format

---

# Markdown Formatter

Apply Liferay Markdown conventions to the target file so its structure, casing, and prose are consistent and professional.

## Input

When `${ARGUMENTS}` is a path to a Markdown file, format that file. When no path is supplied, ask the user which file to format.

## Workflow

### Read the File

Read the target file in full before making changes. Note the frontmatter, headings, code blocks, and any conventions already in use.

### Apply the Conventions

Apply all conventions defined in `.claude/rules/markdown-style.md`. Work through each category in order. Fix each category before moving to the next.

### Validate

Reread the file after the pass and confirm every check still holds.

### Report

Summarize the changes, grouped by category (frontmatter, headings, prose, shell, lists).