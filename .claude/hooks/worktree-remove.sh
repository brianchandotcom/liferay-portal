#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

source "$(dirname "${BASH_SOURCE[0]}")/_common.sh"

function main {
	local input

	input="$(cat)"

	local worktree_path

	worktree_path="$(jq --exit-status --raw-output '.worktree_path' <<< "${input}")" || _die "missing worktree_path in input: ${input}"

	local bundles_dir=""

	if [[ -d "${worktree_path}" ]]
	then
		local tomcat_dir

		if bundles_dir="$(_find_app_server_parent_dir "${worktree_path}")" &&
			tomcat_dir="$(_find_tomcat_dir "${bundles_dir}")"
		then
			pkill -KILL -f "catalina.base=${tomcat_dir}" >/dev/null 2>&1 || true
		fi
	fi

	_drop_database "${worktree_path}" "${bundles_dir}"

	git worktree remove "${worktree_path}"
}

main "$@"