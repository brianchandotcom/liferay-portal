# Testing Changes to PR Check

A change to this skill is prose, so reading it proves nothing. Test it by running the text against changes planted for the purpose on a branch that is never sent. Judge the result from git and from the logs rather than from the summary a run prints.

## Selection

When a change touches a `## Match` section, the routing, or the list in `SKILL.md`, reproduce Pass 1 in bash with `grep --extended-regexp` against real paths, splitting each regex on ` &! ` into its include side and its exclude side as the runner does. Run the old rules and the new rules over the same paths, and include a path the change must select and one it must not, so that a broken comparison reads as a disagreement rather than as agreement.

## Commands

When a change touches a `## Command` or `## Autocommit` section, extract exactly that section, as the runner hands it to a subagent, and confirm the extract carries the change and nothing from `## Trigger` or `## Match`. Give the new text and the text on `master` to two subagents that know nothing about the change, with the same planted diff, and ask each for its verdict and the sentence that decided it. A change that works splits the verdicts. Identical verdicts mean the change made no difference where it is read.

When a command reimplements a rule another tool enforces, run both against the same planted cases and require them to agree, including a case that must fail on both sides. A command that crashes prints nothing, which reads as a pass, so check its exit status as well as its output.

Run every shell command in a validation under both zsh and bash. The Bash tool runs zsh on macOS, and zsh does not split an unquoted variable or command substitution into words, so under zsh a command that builds its arguments in a variable passes them to the tool as a single argument.

## Whole Runs

Run `/pr-check` from the root of a worktree that holds the planted changes, for example with `claude -p "/pr-check"`. A headless session cannot answer the question Pass 1 asks when the estimate exceeds 20 minutes, so say in the prompt to proceed without asking. Plant one change for each outcome to prove, each in its own commit, and check the verdicts against git, for example against the `<TICKET> SF` commit a formatter makes.

A branch in this repository always carries the rule change in its own diff, so a test run here also reports on the `.claude` files the change touched. That is expected.

To test the rules as the private repository runs them, use a worktree of `liferay-portal-ee` on `master-private` whose `pr-check` stub fetches from the local branch holding the change rather than from `upstream master`, by replacing its fetch with:

```bash
git fetch <liferay-portal checkout> <branch>
```