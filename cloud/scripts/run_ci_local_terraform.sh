#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

_REPO_ROOT=$(cd "${_SCRIPTS_DIR}/../.." && pwd)
_ROOT_CLOUD_DIR=$(cd "${_SCRIPTS_DIR}/.." && pwd)

function main {
	_check_utils checkov terraform tflint yq

	_run_terraform
}

function _check_utils {
	for util in "${@}"
	do
		if (! command -v "${util}" &> /dev/null)
		then
			_log_error "The utility ${util} is not installed."

			exit 1
		fi
	done
}

function _list_terraform_stacks {
	yq '.jobs.test-cloud-terraform.strategy.matrix.stack[]' \
		"${_REPO_ROOT}/.github/workflows/ci-test-cloud-terraform.yaml"
}

function _log {
	printf '[%s] %s\n' "$(date -u +"%Y-%m-%dT%H:%M:%SZ")" "${*}"
}

function _log_error {
	printf '[%s] ERROR: %s\n' "$(date -u +"%Y-%m-%dT%H:%M:%SZ")" "${*}" 1>&2
}

function _run_terraform {
	local stack
	local stacks_list

	stacks_list="$(_list_terraform_stacks)"

	if [ -z "${stacks_list}" ]
	then
		_log_error "No terraform stacks found in CI workflow matrix."

		exit 1
	fi

	while IFS= read -r stack
	do
		_log "=== terraform: ${stack} ==="
		_run_terraform_one "${stack}"
	done <<< "${stacks_list}"
}

function _run_terraform_one {
	local config="${_ROOT_CLOUD_DIR}/terraform/${1%%/*}/.tflint.hcl"
	local stack="${1}"
	local stack_dir="${_ROOT_CLOUD_DIR}/terraform/${stack}"

	pushd "${stack_dir}" > /dev/null

	terraform init -backend=false -input=false
	terraform validate
	tflint --config="${config}" --init
	tflint --config="${config}"
	checkov --directory . --framework terraform

	popd > /dev/null
}

main