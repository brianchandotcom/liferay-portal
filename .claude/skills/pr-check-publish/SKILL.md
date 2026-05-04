---

allowed-tools: [Bash]
argument-hint: "<pull-request-url>"
description: Publish pr-check success signals to an existing GitHub pull request. Use when the user wants to mark an existing pull request as pr-check-passed, or when the `pr` skill publishes results after creating a pull request.
name: pr-check-publish

---

# Publish pr-check Results

Record on a GitHub pull request that pr-check passed locally for its head SHA, so reviewers and the GitHub pull request UI can see the signal alongside CI.

## Input

### Pull Request

`${ARGUMENTS}` carries a pull request URL of the form `https://github.com/<target-org>/liferay-portal/pull/<number>`. When missing or malformed, abort and ask the user for the URL. Parse the URL to extract `<target-org>/<target-repo>` and `<PR-number>`.

### Head Fork

Resolve the pull request's head SHA and head repository (`<head-fork-owner>/<head-fork-repo>`). The head fork is the repository that hosts the branch and SHA — typically the developer's fork — and is used as the fallback when the developer lacks write access on the target repository.

## Expected Output

### Posted Commit Status

Post a `pr-check` commit status to the head SHA using the call below. Try the target repository first, because the GitHub pull request UI only renders statuses recorded against the base repository's view of the SHA. Fall back to the head fork only when the target call fails (typically a cross-fork pull request where the developer lacks write access on the target).

```bash
gh api \
	--field "context=pr-check" \
	--field "description=All pr-check validations passed locally" \
	--field "state=success" \
	--method POST \
	"repos/<owner>/<repo>/statuses/<head-SHA>"
```

Substitute `<owner>/<repo>` in this order:

1. `<target-org>/<target-repo>` — the pull request's base repository.

1. `<head-fork-owner>/<head-fork-repo>` — only when the previous call fails.

When both calls fail, surface the error and continue with **Applied Label** — the two signals are independent.

### Applied Label

Apply the `pr-check - success` label to the pull request on the target repository, creating the label first if it does not exist (color `c7e8cb`). On error, continue and note it in the summary.

### Summary

Report back to the user with:

- Whether the commit status posted, which repository it landed on (target repository or head fork fallback), and the SHA.
- Whether the label applied, or that it was skipped because of cross-fork limitations.