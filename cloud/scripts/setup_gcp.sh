#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

_ROOT_CLOUD_DIR=$(cd "${_SCRIPTS_DIR}/.." && pwd)

function main {
	if [ "${#}" -ne 2 ]
	then
		echo "Usage: ${0} <configuration-json-file> <versions-tfvars-file>" >&2

		exit 1
	fi

	_generate_tfvars "${1}" "${_SCRIPTS_DIR}/global_terraform.tfvars"

	echo "Attempting to login to your Google Cloud account via application default credentials."

	gcloud auth application-default login

	local terraform_args

	terraform_args="$(_get_terraform_apply_args "${1}" "${2}")"

	_set_up_gcp_gke "${terraform_args}"

	_set_up_gcp_gitops "${terraform_args}"
}

function _generate_tfvars {
	local configuration_json_file="${1}"

	if [ ! -f "${configuration_json_file}" ]
	then
		echo "Configuration JSON file ${configuration_json_file} does not exist." >&2

		exit 1
	fi

	if ! jq --exit-status '.variables | objects' "${configuration_json_file}" > /dev/null
	then
		echo "The configuration JSON file must contain a root object named \"variables\"."

		exit 1
	fi

	local tfvars_file="${2}"

	echo "Generating ${tfvars_file} from ${configuration_json_file}."

	local tfvars_content

	tfvars_content=$(
		jq --raw-output '.variables
		| to_entries[]
		| if (.value | type) == "string"
		  then
		  	"\(.key) = \"\(.value)\""
		  elif (.value | type) == "array" or (.value | type) == "object"
		  then
		  	"\(.key) = \(.value | @json)"
		  else
		  	"\(.key) = \(.value)"
		  end' "${configuration_json_file}")

	if [ -z "${tfvars_content}" ]
	then
		echo "The \"variables\" object in the configuration JSON file is empty. You will be prompted for all required variables."

		> "${tfvars_file}"
	else
		echo "${tfvars_content}" > "${tfvars_file}"
	fi

	echo "${tfvars_file} was generated successfully."
}

function _get_terraform_apply_args {
	local auto_approve="false"

	local configuration_json_file="${1}"

	if jq --exit-status '.options.auto_approve' "${configuration_json_file}" > /dev/null
	then
		auto_approve=$(jq --raw-output '.options.auto_approve' "${configuration_json_file}")
	fi

	local versions_tfvars_file="${2}"

	if [ ! -f "${versions_tfvars_file}" ]
	then
		echo "${versions_tfvars_file} does not exist." >&2

		exit 1
	fi

	local versions_tfvars_file_path

	versions_tfvars_file_path=$(realpath "${versions_tfvars_file}")

	local apply_args=(
		"-var-file=${versions_tfvars_file_path}"
		"-var-file=${_SCRIPTS_DIR}/global_terraform.tfvars")

	if [[ "${auto_approve}" == "true" ]]
	then
		apply_args+=("-auto-approve")
	fi

	if jq --exit-status '.options.parallelism | numbers' "${configuration_json_file}" > /dev/null
	then
		local parallelism

		parallelism=$(jq --raw-output '.options.parallelism' "${configuration_json_file}")

		apply_args+=("-parallelism=${parallelism}")
	fi

	echo "${apply_args[@]}"
}

function _popd {
	popd > /dev/null
}

function _pushd {
	pushd "${1}" > /dev/null
}

function _set_up_gcp_gke {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/gcp/gke"

	echo "Setting up the Google GKE cluster."

	_terraform_init_and_apply "." "${1}"

	gcloud auth login

	gcloud \
		container \
		fleet \
		memberships \
		get-credentials \
		"$(terraform output -raw membership_name)" \
		--project "$(terraform output -raw project_id)"

	echo "Google GKE cluster setup complete."

	_popd
}

function _set_up_gcp_gitops {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/gcp/gitops"

	echo "Setting up the Google GCP GitOps infrastructure."

	_terraform_init_and_apply "./platform" "${1}"

	_terraform_init_and_apply "./resources" "${1}"

	echo "Google GCP GitOps infrastructure setup complete."

	_popd
}

function _terraform_init_and_apply {
	_pushd "${1}"

	terraform init -upgrade

	terraform apply ${@:2}

	_popd
}

main "${@}"