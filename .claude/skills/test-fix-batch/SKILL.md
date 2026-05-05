---

allowed-tools: [Bash, Read, Skill, Write]
argument-hint: '<caseResultId> [caseResultId...]'
description: Resolve one or more Liferay test failures by orchestrating `/test-fix` per case result. Use when the user invokes `/test-fix-batch` with one or more Testray case result IDs.
name: test-fix-batch

---

# Fix Multiple Test Failures

Orchestrate one or more Testray case result fixes.

## Input

### Case Result IDs

`${ARGUMENTS}` is one or more positive integers separated by whitespace, each one a Testray case result ID. Tokenise on whitespace into a list and abort immediately when the result is empty or any token is not a positive integer.

## Expected Output

### Markdown Summary

A markdown summary printed in the chat after the run completes. Structure:

```markdown
## Fix run summary (<YYYY-MM-DD HH:MM>)

Failures processed: <total> (<resolved> resolved, <noFixNeeded> no fix needed, <unresolved> unresolved) · Elapsed: <MINUTES>m <SECONDS>s

### 1. <test name> — <verdict>

- **Ticket**: [LPD-XXXXX](https://liferay.atlassian.net/browse/LPD-XXXXX)
- **PR**: [#N](https://github.com/.../pull/N)
- **Resolution time**: <Xm Ys>
- **Conclusion**: <one or two sentences naming the offending commit and what it changed>.
- **Fix**: <one or two sentences describing the change applied and why it resolves the regression>.

### 2. <test name> — No fix needed

- **Resolution time**: <Xm Ys>
- **Conclusion**: Test passes locally.

### 3. <test name> — Unresolved

- **Resolution time**: <Xm Ys>
- **Conclusion**: <handover summary — hypotheses considered, attempts made, observed effects, most plausible remaining lead>.

…
```

Per verdict:

- **Resolved** (`Bug in portal`, `Outdated test`): include all lines (Ticket, PR, Resolution time, Conclusion, Fix).
- **No fix needed**: include only Resolution time and Conclusion (which is always the literal `Test passes locally.`).
- **Unresolved**: include only Resolution time and Conclusion.

`<resolved>` is the count of entries with verdict `Bug in portal` or `Outdated test`. `<noFixNeeded>` is the count of `No fix needed` entries. `<unresolved>` is the count of `Unresolved` entries. Always render all three counts even when one is zero.

### HTML Report

A self-contained HTML table at `output/<YYYY-MM-DD>-fix-report.html`. Always written, even when every failure ended up `Unresolved`, even when only one failure was supplied, and even when nothing succeeded. Runs on the same day append to the same file instead of creating a new one.

The HTML template lives at `references/report.html` — a single self-contained file with all required CSS already validated for column overflow, long `<code>` wrapping, sticky header, and per-verdict colour cues. Do not duplicate or rewrite the markup; read the template, substitute the placeholders, and write the result.

When `output/<YYYY-MM-DD>-fix-report.html` does **not** exist yet, copy the template to it and substitute these top-level placeholders (string replace — they each appear exactly once):

| Placeholder       | Value                                                            |
| ----------------- | ---------------------------------------------------------------- |
| `{{date}}`        | `<YYYY-MM-DD>`                                                   |
| `{{total}}`       | total number of result entries                                   |
| `{{resolved}}`    | count of entries with verdict `Bug in portal` or `Outdated test` |
| `{{noFixNeeded}}` | count of entries with verdict `No fix needed`                    |
| `{{unresolved}}`  | count of entries with verdict `Unresolved`                       |
| `{{minutes}}`     | run-level elapsed minutes                                        |
| `{{seconds}}`     | run-level elapsed seconds                                        |
| `{{rows}}`        | the concatenation of the per-entry `<tr>` snippets               |

When the file **already** exists (an earlier run on the same day wrote it), do not overwrite it. Read it, then update it in place:

1. Parse the existing summary line (`Failures processed: <total> (<resolved> resolved, <noFixNeeded> no fix needed, <unresolved> unresolved) · Elapsed: <Xm Ys>`) to recover the prior counts and elapsed seconds.

1. Add the current run's counts and seconds to the prior values, and rewrite the summary line with the cumulative numbers.

1. Append this run's row snippets immediately before the closing `</tbody>` tag, preserving the existing rows above.

The h1/title (`Fix run (<YYYY-MM-DD>)`) and all other markup stay untouched on append.

Build the row snippets by joining one snippet per result entry. The three row templates live as `<template>` elements at the bottom of `references/report.html`; read the inner markup of each, substitute the placeholders, and join the results. Pick the template by verdict:

- `Bug in portal` / `Outdated test` → `<template id="row-resolved">`. Set `{{verdictClass}}` to the kebab-cased verdict (`bug-in-portal` or `outdated-test`).
- `No fix needed` → `<template id="row-no-fix-needed">`.
- `Unresolved` → `<template id="row-unresolved">`.

All three templates already wrap the test name in `<span class="test-name">{{name}}</span>` so it renders as a monospace pill, and the no-fix-needed and unresolved templates already render the Jira ticket and PR cells as an em dash since neither is created. The no-fix-needed template hardcodes the literal `Test passes locally.` for its conclusion.

Wrap any code identifiers inside `{{conclusion}}` in `<code>…</code>` so they pick up the table's monospace styling — the validated CSS already handles wrapping for them. For commit short SHAs, wrap them in a link to the corresponding commit on the Liferay portal repository: `<a href="https://github.com/liferay/liferay-portal/commit/<full-sha>"><code><short-sha></code></a>`. Use the **full** 40-character SHA in the URL (so the link survives any future short-SHA collisions) and the **short** 13-character SHA inside `<code>` for the visible text.

After writing the file, end the message with a single line linking to it: `Report saved to [file:///<absolute-path>/output/<YYYY-MM-DD>-fix-report.html](file:///<absolute-path>/output/<YYYY-MM-DD>-fix-report.html)`.

## Workflow

For each case result ID in input order:

### 1. Restore a Clean Master

Switch the checkout back to a clean `master` so `/test-fix`'s preconditions pass. After the first iteration, the previous `/test-fix` will have left the checkout on a feature branch with a committed PR; when the previous iteration aborted with uncommitted changes, drop them as well.

### 2. Invoke `/test-fix`

Run the `test-fix` skill with the current case result ID as its sole argument.

An iteration that aborts or exits without a `result` block is recorded as `Unresolved` (with a `Conclusion` describing the failure mode) and the batch continues with the remaining IDs.