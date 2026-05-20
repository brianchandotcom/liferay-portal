---

description: Add a new rule to the format-source skill, derived from a Git commit.
name: format-source-add-rule

---

# Format Source: Add Rule

Add a new rule to the `format-source` skill, derived from a Git commit. Anyone can use this to encode a convention they spotted in someone's commit. Rules can apply to any file type the source formatter handles — Java, `.properties`, XML, JSP, Markdown, Gradle, `.gitignore`, YAML, and so on. Do not bias toward Java.

## Inputs

- A Git commit SHA.

- Optional: a hint about which aspect of the commit to encode. Commits often touch multiple things; a hint narrows the scope.

## Workflow

### Inspect the Commit

Inspect the commit's full diff. Identify a single, learnable formatting pattern — something a future reviewer could apply mechanically to other code. Stop and ask the user to clarify when:

- Multiple distinct rules are present. Ask the user to pick one, or run the skill once per rule.

- The commit mixes formatting changes with logic or refactoring changes that cannot be separated.

- The pattern is not generalizable beyond the specific file or context.

### Check for Duplicates

Read the existing rules in `.claude/skills/format-source/SKILL.md` and compare against the pattern from the previous step. If the pattern duplicates or substantially overlaps an existing rule, flag it to the user and ask whether to skip, refine the existing rule, or proceed anyway.

### Write the Rule

Find the next rule number in `.claude/skills/format-source/SKILL.md`, then append the new rule at the end of the file using this exact markdown template:

````markdown
### Rule <next-number>: <Title Case name, for example "Method Parameter Ordering">

**Why:** <one-sentence explanation of what consistency the rule buys, not what the rule does>

**Examples:**

```diff

- <one or more before lines demonstrating the rule, using abstract identifiers — not the commit's literal code>
+ <matching after lines>

```
````

Use abstract identifiers in the diff rather than the verbatim names from the commit — for example `methodA`, `fieldB`, `Foo` for Java; `some.property`, `myTag`, `someKey` for properties, XML, YAML, or Markdown. The examples should illustrate the general pattern; reproducing the commit's exact text overfits the rule to one case and makes future readers match on names rather than the underlying pattern.

For rules with nuance, use several diff blocks for each additional case. Add a single line of prose before the diff block when a diff alone is unclear.

### Self-Test the Rule

Validate that the appended rule reproduces the input commit when applied to the commit's parent state. To do this, create a new branch at the commit's parent and run `/format-source` scoped to only the files touched by the commit, applying only the manual rules (skip the automatic formatter). Compare the result with the commit. If the rule does not produce an equivalent change, revise the rule and repeat until it does.

### Commit the Rule

After the self-test passes, stage only `.claude/skills/format-source/SKILL.md` and commit with this commit message format:

```
<ticket> Add rule derived from <sha>
```