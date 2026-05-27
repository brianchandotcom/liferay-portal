#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

_REPO_ROOT=$(cd "${_SCRIPTS_DIR}/../.." && pwd)
_ROOT_CLOUD_DIR=$(cd "${_SCRIPTS_DIR}/.." && pwd)

function main {
	_check_utils helm kubeconform yq

	_run_helm
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
		"${_REPO_ROOT}/.github/workflows/ci-test-cloud-helm-chart.yaml"
}

function _log {
	printf '[%s] %s\n' "$(date -u +"%Y-%m-%dT%H:%M:%SZ")" "${*}"
}

function _log_error {
	printf '[%s] ERROR: %s\n' "$(date -u +"%Y-%m-%dT%H:%M:%SZ")" "${*}" 1>&2
}

function _run_helm {
	local chart
	local charts_list

	charts_list="$(_list_helm_charts)"

	if [ -z "${charts_list}" ]
	then
		_log_error "No charts found in CI workflow matrix."

		exit 1
	fi

	while IFS= read -r chart
	do
		_log "=== helm: ${chart} ==="
		_run_helm_one "${chart}"
	done <<< "${charts_list}"
}

function _run_helm_one {
	local chart="${1}"
	local chart_dir="${_ROOT_CLOUD_DIR}/helm/${chart}"

	pushd "${chart_dir}" > /dev/null

	helm dependency update --skip-refresh
	helm lint .
	helm template liferay . \
		| kubeconform \
			-schema-location default \
			-schema-location 'https://raw.githubusercontent.com/datreeio/CRDs-catalog/main/{{.Group}}/{{.ResourceKind}}_{{.ResourceAPIVersion}}.json' \
			-skip ClusterProviderConfig,LiferayInfrastructure \
			--strict \
			--summary

	popd > /dev/null
}

main