#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

_ROOT_CLOUD_DIR=$(cd "${_SCRIPTS_DIR}/.." && pwd)
_VERSIONS_AWS_TFVARS_FILE="${_SCRIPTS_DIR}/versions_aws.tfvars"
_VERSIONS_GCP_TFVARS_FILE="${_SCRIPTS_DIR}/versions_gcp.tfvars"
_VERSIONS_JSON_FILE="${_SCRIPTS_DIR}/versions.json"

function main {
	find "${_ROOT_CLOUD_DIR}" -name "Chart.yaml" -type f | while read -r chart_yaml_file;
	do
		_check_chart_yaml "$(dirname "${chart_yaml_file}")"
	done

	_update_versions_tfvars "${_ROOT_CLOUD_DIR}/terraform/aws" "${_VERSIONS_AWS_TFVARS_FILE}"

	_update_versions_tfvars "${_ROOT_CLOUD_DIR}/terraform/gcp" "${_VERSIONS_GCP_TFVARS_FILE}"

	local aws_bootstrap_sources=(
		"${_ROOT_CLOUD_DIR}/scripts/setup_aws.sh"
		"${_ROOT_CLOUD_DIR}/scripts/versions_aws.tfvars"
		"${_ROOT_CLOUD_DIR}/terraform/aws/eks"
		"${_ROOT_CLOUD_DIR}/terraform/aws/gitops/platform"
		"${_ROOT_CLOUD_DIR}/terraform/aws/gitops/resources"
		"${_ROOT_CLOUD_DIR}/terraform/aws/grafana"
	)

	_check_bootstrap "aws" "${aws_bootstrap_sources[@]}"

	local gcp_bootstrap_sources=(
		"${_ROOT_CLOUD_DIR}/scripts/setup_gcp.sh"
		"${_ROOT_CLOUD_DIR}/scripts/versions_gcp.tfvars"
		"${_ROOT_CLOUD_DIR}/terraform/gcp/gke"
		"${_ROOT_CLOUD_DIR}/terraform/gcp/gitops/platform"
		"${_ROOT_CLOUD_DIR}/terraform/gcp/gitops/resources"
	)

	_check_bootstrap "gcp" "${gcp_bootstrap_sources[@]}"
}

function _bump_bootstrap_version {
	local bootstrap_name="${1}"

	local current_version

	current_version=$(jq --raw-output '."liferay-'"${bootstrap_name}"'-bootstrap"' "${_VERSIONS_JSON_FILE}")

	local new_version

	new_version=$(echo "${current_version}" | awk -F"." -v OFS="." '{$NF += 1; print}')

	local git_blame_line

	git_blame_line=$(_git_blame_line '"liferay-'"${bootstrap_name}"'-bootstrap": "[0-9]+\.[0-9]+\.[0-9]+"' "${_VERSIONS_JSON_FILE}")

	sed \
		--in-place \
		--regexp-extended \
		"s/\"version\": \".*\"/\"version\": \"${new_version}\"/" \
		"${_ROOT_CLOUD_DIR}/scripts/config.json.example_${bootstrap_name}"

	sed \
		--in-place \
		--regexp-extended \
		"${git_blame_line}s/\"liferay-${bootstrap_name}-bootstrap\": \"[0-9]+\.[0-9]+\.[0-9]+\"/\"liferay-${1}-bootstrap\": \"${new_version}\"/" \
		"${_VERSIONS_JSON_FILE}"
}

function _bump_chart_yaml_version {
	local helm_chart_yaml="${1}"

	local git_blame_line

	git_blame_line=$(_git_blame_line "^version: .*$" "${helm_chart_yaml}")

	local current_version

	current_version=$(sed --quiet "${git_blame_line}p" "${helm_chart_yaml}" | awk '{print $2}')

	local new_version

	new_version=$(echo "${current_version}" | awk -F"." -v OFS="." '{$NF += 1; print}')

	sed --in-place "${git_blame_line}s/version: .*/version: ${new_version}/" "${helm_chart_yaml}"

	echo "${new_version}"
}

function _check_bootstrap {
	local bootstrap_name="${1}"

	shift

	local git_blame_sha

	git_blame_sha=$(_git_blame_sha '"liferay-'"${bootstrap_name}"'-bootstrap": ".*"' "${_VERSIONS_JSON_FILE}")

	local bootstrap_sources

	mapfile -d '' bootstrap_sources < <(printf '%s\0' "$@")

	for source in "${bootstrap_sources[@]}"
	do
		local clean_source="${source%$'\0'}"

		local commit_count

		commit_count=$(git rev-list --count "${git_blame_sha}..HEAD" -- "${clean_source}")

		if [[ "${commit_count}" -gt 0 ]]; then
			git rev-list --oneline "${git_blame_sha}..HEAD" -- "${clean_source}"

			echo "The version in ${_VERSIONS_JSON_FILE} is outdated. Updating liferay-${bootstrap_name}-bootstrap version." >&2
			echo "" >&2

			_bump_bootstrap_version "${bootstrap_name}"

			return
		fi
	done
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

		echo "Updating ${helm_chart_yaml} to new version ${new_version}." >&2

		_update_default_chart_version "${helm_chart_yaml}" "${new_version}"
	fi
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

function _update_default_chart_version {
	local helm_chart_yaml="${1}"
	local new_version="${2}"

	local helm_chart_name

	helm_chart_name=$(basename "$(dirname "${helm_chart_yaml}")")

	local file_to_update=""

	case "${helm_chart_name}" in
		"aws" | "aws-infrastructure" | "aws-infrastructure-provider")
			file_to_update="${_ROOT_CLOUD_DIR}/terraform/aws/gitops/resources/terraform.tfvars"
			;;
		"gcp" | "gcp-infrastructure" | "gcp-infrastructure-provider")
			file_to_update="${_ROOT_CLOUD_DIR}/terraform/gcp/gitops/resources/terraform.tfvars"
			;;
	esac

	local var_to_update=""

	case "${helm_chart_name}" in
		"aws" | "gcp")
			var_to_update="liferay_helm_chart_version"
			;;
		"aws-infrastructure" | "gcp-infrastructure")
			var_to_update="infrastructure_helm_chart_version"
			;;
		"aws-infrastructure-provider" | "gcp-infrastructure-provider")
			var_to_update="infrastructure_provider_helm_chart_version"
			;;
	esac

	if [ -n "${var_to_update}" ]
	then
		sed --in-place "s/\(${var_to_update} *= *\)\".*\"/\1\"${new_version}\"/" "${file_to_update}"
	fi
}

function _update_versions_tfvars {
	local terraform_dir="${1}"

	local versions_tfvars_file="${2}"

	rm -f "${versions_tfvars_file}"

	local terraform_tfvars_files

	terraform_tfvars_files=$(find "${terraform_dir}" -name "terraform.tfvars")

	echo "${terraform_tfvars_files}" | while read -r tfvars_file
	do
		cat "${tfvars_file}" >> "${versions_tfvars_file}"

		echo "" >> "${versions_tfvars_file}"
	done

	grep . "${versions_tfvars_file}" | sort -o "${versions_tfvars_file}"
}

main "$@"