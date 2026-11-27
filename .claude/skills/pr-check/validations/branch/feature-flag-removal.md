# Feature Flag Removal

Flags a branch that removes a feature flag: a `feature.flag.<key>=` line that the branch deletes from `portal-impl/src/portal.properties` while no `feature.flag.<key>=` line for the same key remains. Removing a feature flag turns the code behind it on for every installation at once, so every feature flag removal requires a Core Infrastructure performance check before it lands. A removal passes only when someone on the whitelist below runs pr-check, or when this exact commit is the head of an open pull request on `liferay-core-infra/liferay-portal`, from which Core Infrastructure forwards it with `ci:forward` after the review.

## Match

`^portal-impl/src/portal\.properties$`

## Command

This check is static and needs no build. It reads the keys of the `feature.flag.<key>=` lines that the branch diff removes from `portal-impl/src/portal.properties` and keeps each key that no `feature.flag.<key>=` line declares any more at `HEAD`, so a flag whose value or position changes is not a removal. The attribute lines of a flag, such as `feature.flag.<key>.type=`, never match, because the key may not contain a period. It uses `command grep` and `command sed` so the diff is filtered by the system tools rather than shell wrappers, which some environments route to other tools with different options.

```bash
FINDINGS=$(
	git diff "${MERGE_BASE}...HEAD" -- portal-impl/src/portal.properties \
		| command sed -n 's/^-[[:space:]]*feature\.flag\.\([A-Za-z0-9_-]*\)=.*/\1/p' \
		| sort --unique \
		| while IFS= read -r KEY
		do
			if ! git show HEAD:portal-impl/src/portal.properties \
				| command grep \
					--extended-regexp \
					--quiet \
					--regexp="^[[:space:]]*feature\.flag\.${KEY}="
			then
				echo "${KEY}"
			fi
		done
)

echo "${FINDINGS}"
```

When `${FINDINGS}` is empty, the branch removes no feature flag, so the validation passes.

Otherwise, resolve the GitHub login of the person running pr-check with `gh api user --jq '.login'` and check it against the whitelist below. The validation passes when that login is on the whitelist. Add one login per line as the roster changes.

```
shuyangzhou
tinatian
```

Otherwise, check whether this exact commit is the head of an open pull request on `liferay-core-infra/liferay-portal`. The validation passes when the command below prints the commit.

```bash
HEAD_SHA=$(git rev-parse HEAD)

gh pr list \
	--jq ".[] | select(.headRefOid == \"${HEAD_SHA}\") | .headRefOid" \
	--json headRefOid \
	--limit 1000 \
	--repo liferay-core-infra/liferay-portal \
	--state open
```

The validation fails in every other case, including when the login cannot be resolved or `gh` cannot list the pull requests, for example when `gh` is not authenticated, so an unknown initiator never passes. When it fails, report the message below to the developer, with each removed key in `<keys>`, and return it as the failure note.

```markdown
**Feature flag removal requires Core Infrastructure review.** This branch removes the feature flags <keys> from `portal.properties`, which turns the code behind them on for every installation at once, so it requires a Core Infrastructure performance check before it lands. Please send this pull request to @liferay-core-infra and request review from @shuyangzhou or @tinatian; after the review, Core Infrastructure forwards it with `ci:forward`. For additional questions, use #t-core-infrastructure on Slack.
```

## Time Estimate

~5-15 sec (static, no build).