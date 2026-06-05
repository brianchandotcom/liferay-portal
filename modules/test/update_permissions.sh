#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

function main {
	local dir_name

	find . -type d -print0 |
	while IFS= read -r -d '' dir_name
	do
		chmod 775 "${dir_name}"
	done

	local file_name

	find . -type f -name "*.sh" -print0 |
	while IFS= read -r -d '' file_name
	do
		chmod 744 "${file_name}"
	done
}

main "${@}"