#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

_ROOT_CLOUD_DIR=$(cd "${_SCRIPTS_DIR}/.." && pwd)

_VERSIONS_TFVARS_FILE="${_SCRIPTS_DIR}/versions.tfvars"

function main {
	rm -f "${_VERSIONS_TFVARS_FILE}"

	local terraform_tfvars_files

	terraform_tfvars_files=$(find "${_ROOT_CLOUD_DIR}" -name "terraform.tfvars")

	echo "${terraform_tfvars_files}" | while read -r tfvars_file
	do
		cat "${tfvars_file}" >> "${_VERSIONS_TFVARS_FILE}"

		echo "" >> "${_VERSIONS_TFVARS_FILE}"
	done

	grep . "${_VERSIONS_TFVARS_FILE}" | sort -o "${_VERSIONS_TFVARS_FILE}"
}

main