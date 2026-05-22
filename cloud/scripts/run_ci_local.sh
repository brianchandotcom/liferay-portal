#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

function main {
	local command="${1:-all}"
	shift || true

	case "${command}" in
		all)
			_check_utils checkov helm kubeconform terraform tflint yq
			_run_terraform
			_run_helm
			;;
		helm)
			_check_utils helm kubeconform yq
			_run_helm ${1+"$@"}
			;;
		shell)
			_log_error "shell subcommand not yet implemented (tracked by LCD-51737)"
			exit 1
			;;
		terraform)
			_check_utils checkov terraform tflint yq
			_run_terraform ${1+"$@"}
			;;
		--help|-h)
			_print_usage
			;;
		*)
			_log_error "Unknown command: ${command}"
			_print_usage
			exit 2
			;;
	esac
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

function _list_helm_charts {
	yq '.jobs.test-cloud-helm-chart.strategy.matrix.chart[]' \
		"${REPO_ROOT}/.github/workflows/ci-test-cloud-helm-chart.yaml"
}

function _list_terraform_stacks {
	yq '.jobs.test-cloud-terraform.strategy.matrix.stack[]' \
		"${REPO_ROOT}/.github/workflows/ci-test-cloud-terraform.yaml"
}

function _log {
	printf '[%s] %s\n' "$(date -u +"%Y-%m-%dT%H:%M:%SZ")" "${*}"
}

function _log_error {
	printf '[%s] ERROR: %s\n' "$(date -u +"%Y-%m-%dT%H:%M:%SZ")" "${*}" 1>&2
}

function _print_usage {
	cat <<EOF
Usage: run_ci_local.sh [command] [args...]

Commands:
  all                    Run all CI checks (default)
  helm [chart...]        Run helm CI checks (lint, kubeconform)
  shell                  Run shellcheck (not yet implemented)
  terraform [stack...]   Run terraform CI checks (validate, tflint, checkov)
  --help, -h             Show this message

Without args, helm and terraform check every chart/stack listed in the CI
workflow matrices. With args, only the specified charts/stacks are checked.

Local runs abort on the first failure; CI runs every matrix entry.
EOF
}

function _run_helm {
	local charts=()

	if [ "${#}" -eq 0 ]
	then
		local charts_list
		charts_list="$(_list_helm_charts)"

		if [ -z "${charts_list}" ]
		then
			_log_error "No charts found in CI workflow matrix."
			exit 1
		fi

		local chart
		while IFS= read -r chart
		do
			charts+=("${chart}")
		done <<< "${charts_list}"
	else
		charts=("${@}")
	fi

	local chart
	for chart in "${charts[@]}"
	do
		_log "=== helm: ${chart} ==="
		_run_helm_one "${chart}"
	done
}

function _run_helm_one {
	local chart="${1}"
	local chart_dir="${REPO_ROOT}/cloud/helm/${chart}"

	pushd "${chart_dir}" > /dev/null

	helm dependency update --skip-refresh
	helm lint .
	helm template liferay . \
		| kubeconform --strict --summary \
			-schema-location default \
			-schema-location 'https://raw.githubusercontent.com/datreeio/CRDs-catalog/main/{{.Group}}/{{.ResourceKind}}_{{.ResourceAPIVersion}}.json' \
			-skip ClusterProviderConfig,LiferayInfrastructure

	popd > /dev/null
}

function _run_terraform {
	local stacks=()

	if [ "${#}" -eq 0 ]
	then
		local stacks_list
		stacks_list="$(_list_terraform_stacks)"

		if [ -z "${stacks_list}" ]
		then
			_log_error "No terraform stacks found in CI workflow matrix."
			exit 1
		fi

		local stack
		while IFS= read -r stack
		do
			stacks+=("${stack}")
		done <<< "${stacks_list}"
	else
		stacks=("${@}")
	fi

	local stack
	for stack in "${stacks[@]}"
	do
		_log "=== terraform: ${stack} ==="
		_run_terraform_one "${stack}"
	done
}

function _run_terraform_one {
	local stack="${1}"
	local stack_dir="${REPO_ROOT}/cloud/terraform/${stack}"
	local cloud="${stack%%/*}"
	local config="${REPO_ROOT}/cloud/terraform/${cloud}/.tflint.hcl"

	pushd "${stack_dir}" > /dev/null

	terraform init -backend=false -input=false
	terraform validate
	tflint --config="${config}" --init
	tflint --config="${config}"
	checkov --directory . --framework terraform

	popd > /dev/null
}

REPO_ROOT="$(git -C "$(dirname "${BASH_SOURCE[0]}")" rev-parse --show-toplevel)"

main ${1+"$@"}