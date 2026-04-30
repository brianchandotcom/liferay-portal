#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

function _die {
	echo "WorktreeCreate hook: ${*}" >&2

	exit 1
}

function main {
	local cwd
	local input
	local name
	local target_path

	input="$(cat)"

	name="$(jq --exit-status --raw-output '.name' <<< "${input}")" || _die "missing name in input: ${input}"
	cwd="$(jq --exit-status --raw-output '.cwd' <<< "${input}")" || _die "missing cwd in input: ${input}"

	target_path="$(git -C "${cwd}" rev-parse --show-toplevel)/../liferay-portal-${name}"

	if git -C "${cwd}" show-ref --quiet --verify "refs/heads/${name}"; then
		git -C "${cwd}" worktree add "${target_path}" "${name}" >&2
	else
		git -C "${cwd}" worktree add -b "${name}" "${target_path}" HEAD >&2
	fi

	echo "${target_path}"
}

main "$@"