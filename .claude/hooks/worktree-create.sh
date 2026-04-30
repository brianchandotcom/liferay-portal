#!/usr/bin/env bash
set -euo pipefail

input=$(cat)
name=$(jq --raw-output '.name' <<< "${input}")
cwd=$(jq --raw-output '.cwd' <<< "${input}")

if [[ "${name}" == "null" || "${cwd}" == "null" ]]; then
	echo "WorktreeCreate hook: missing name or cwd in input: ${input}" >&2
	exit 1
fi

target_path="$(dirname "${cwd}")/liferay-portal-${name}"

if git -C "${cwd}" show-ref --quiet --verify "refs/heads/${name}"; then
	git -C "${cwd}" worktree add "${target_path}" "${name}" >&2
else
	git -C "${cwd}" worktree add -b "${name}" "${target_path}" HEAD >&2
fi

echo "${target_path}"