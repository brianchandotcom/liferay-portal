#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

_ROOT_CLOUD_DIR=$(cd "${_SCRIPTS_DIR}/.." && pwd)

function main {
	find "${_ROOT_CLOUD_DIR}" -name "Chart.yaml" -type f | while read -r chart_yaml_file;
	do
		_check_chart_yaml "$(dirname "${chart_yaml_file}")"
	done
}

function _bump_chart_yaml_version {
	local helm_chart_yaml="${1}"

	local current_version

	current_version=$(yq '.version' "${helm_chart_yaml}")

	local new_version

	new_version=$(echo "${current_version}" | awk -F"." -v OFS="." '{$NF += 1; print}')

	yq --inplace ".version = \"${new_version}\"" "${helm_chart_yaml}"

	echo "${new_version}"
}

function _check_chart_yaml {
	local helm_dir="${1}"

	local helm_chart_yaml="${helm_dir}/Chart.yaml"

	local git_blame_sha

	git_blame_sha=$(_git_blame_sha "^version: .*$" "${helm_chart_yaml}")

	local commit_count

	commit_count=$(git rev-list --count "${git_blame_sha}..HEAD" -- "${helm_dir}")

	if [[ "${commit_count}" -gt 0 ]]
	then
		git rev-list --oneline "${git_blame_sha}..HEAD" -- "${helm_dir}"

		echo "The version in ${helm_chart_yaml} is outdated." >&2
		echo "" >&2

		local new_version

		new_version=$(_bump_chart_yaml_version "${helm_chart_yaml}")

		local helm_chart_name

		helm_chart_name=$(grep "^name: " "${helm_chart_yaml}" | awk '{print $2}')

		_update_chart_dependency_version "${helm_chart_name}" "${new_version}" "${helm_chart_yaml}"
	fi
}

function _update_chart_dependency_version {
	local chart_name="${1}"
	local new_version="${2}"
	local current_chart_yaml="${3}"

	find "${_ROOT_CLOUD_DIR}" -name "Chart.yaml" -type f | while read -r chart_yaml_file;
	do
		if [[ "${chart_yaml_file}" == "${current_chart_yaml}" ]]; then
			continue
		fi

		if grep --quiet "name: ${chart_name}" "${chart_yaml_file}" && grep --quiet "repository: file:" "${chart_yaml_file}"
		then
			sed --in-place "/name: ${chart_name}$/,/version: / s/version: .*/version: ${new_version}/" "${chart_yaml_file}"
		fi
	done
}

function _git_blame_line {
	local pattern="${1}"
	local git_path="${2}"

	local blame_line

	blame_line=$(grep --extended-regexp --line-number "${pattern}" "${git_path}" | cut --delimiter=':' --fields=1)

	echo "${blame_line}"
}

function _git_blame_sha {
	local pattern="${1}"
	local git_path="${2}"

	local git_blame_line

	git_blame_line=$(_git_blame_line "${pattern}" "${git_path}")

	local target_sha

	target_sha=$(git blame -L "${git_blame_line}","${git_blame_line}" -- "${git_path}" | cut --delimiter=' ' --fields=1)

	echo "${target_sha}"
}

main "$@"