#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
_ROOT_CLOUD_DIR=$(cd "${_SCRIPTS_DIR}/.." && pwd)

function main {
	if [ "${#}" -ne 1 ]
	then
		echo "Usage: ${0} <path_to_config_json_file>"

		exit 1
	fi

	_generate_tfvars "${1}" "${_SCRIPTS_DIR}/global_terraform.tfvars"

	echo "Attempting to login to your AWS account via SSO..."

	aws sso login

	_setup_aws_eks

	_setup_aws_gitops

	_port_forward_argocd
}

function _generate_tfvars {
	local json_file="${1}"

	if [ ! -f "${json_file}" ]
	then
		echo "No JSON file ${json_file} found."

		exit 1
	fi

	if ! jq --exit-status '.variables | objects' "${json_file}" > /dev/null
	then
		echo "The JSON file must contain a root object named 'variables'."

		exit 1
	fi

	local tfvars_file="${2}"

	echo "Generating ${tfvars_file} from ${json_file}..."

	local tfvars_content=$(
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
		  end' "${json_file}")

	if [ -z "${tfvars_content}" ]
	then
		echo "The 'variables' object in the JSON file is empty. You will be prompted for all required variables."

		> "${tfvars_file}"
	else
		echo "${tfvars_content}" > "${tfvars_file}"
	fi

	echo "${tfvars_file} generated successfully."
}

function _popd {
	popd > /dev/null
}

function _port_forward_argocd {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/gitops/platform"

	local argocd_namespace=$(terraform output -raw argocd_namespace)

	local argocd_password=$( \
		kubectl \
			get \
			secret \
			argocd-initial-admin-secret \
			--namespace ${argocd_namespace} \
			--output jsonpath="{.data.password}" \
		| base64 --decode)

	echo "Port-forwarding the ArgoCD service at http://localhost:8080...."
	echo "Login with Username: admin and Password: ${argocd_password} to continue monitoring setup."
	echo "Use CTRL+C to exit when finished."

	kubectl \
		port-forward \
		--namespace ${argocd_namespace} \
		service/argocd-server \
		8080:443

	_popd
}

function _pushd {
	pushd "${1}" > /dev/null
}

function _setup_aws_eks {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/eks"

	echo "Setting up the AWS EKS cluster..."

	_terraform_init_and_apply "."

	aws \
		eks \
		update-kubeconfig \
		--name "$(terraform output -raw cluster_name)" \
		--region "$(terraform output -raw region)"

	echo "AWS EKS cluster setup complete."

	_popd
}

function _setup_aws_gitops {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/gitops"

	echo "Setting up GitOps Infrastructure..."

	_terraform_init_and_apply "./platform"

	_terraform_init_and_apply "./resources"

	echo "GitOps Infrastructure setup complete."

	_popd
}

function _terraform_init_and_apply {
	_pushd "${1}"

	terraform init

	terraform apply "-var-file=${_SCRIPTS_DIR}/global_terraform.tfvars"

	_popd
}

main "${@}"