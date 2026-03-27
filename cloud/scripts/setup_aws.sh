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

	echo "Attempting to login to your AWS account via SSO."

	aws sso login

	local terraform_args

	terraform_args="$(_get_terraform_apply_args "${1}" "${2}")"

	_set_up_aws_service_linked_roles

	_set_up_aws_eks "${terraform_args}"

	_set_up_aws_grafana "${terraform_args}"

	_set_up_aws_gitops "${terraform_args}"

	_port_forward_argo_cd
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
		echo "The configuration JSON file must contain a root object named \"variables\"." >&2

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

	local apply_args=(
		"-var-file=${versions_tfvars_file}"
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

function _port_forward_argo_cd {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/gitops/platform"

	local argocd_namespace

	argocd_namespace=$(terraform output -raw argocd_namespace)

	local argocd_password

	argocd_password=$( \
		kubectl \
			get \
			secret \
			argocd-initial-admin-secret \
			--namespace ${argocd_namespace} \
			--output jsonpath="{.data.password}" \
		| base64 --decode)

	echo "Port-forwarding the ArgoCD service at http://localhost:8080."
	echo ""
	echo "Login with username and password \"${argocd_password}\" to continue monitoring setup."
	echo ""
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

function _set_up_aws_eks {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/eks"

	echo "Setting up the AWS EKS cluster."

	_terraform_init_and_apply "." "${1}"

	export KUBE_CONFIG_PATH="${HOME}/.kube/config"

	aws \
		eks \
		update-kubeconfig \
		--name "$(terraform output -raw cluster_name)" \
		--region "$(terraform output -raw region)"

	echo "AWS EKS cluster setup complete."

	_popd
}

function _set_up_aws_gitops {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/gitops"

	echo "Setting up GitOps infrastructure."

	_terraform_init_and_apply "./platform" "${1}"

	_terraform_init_and_apply "./resources" "${1}"

	echo "GitOps infrastructure setup complete."

	_popd
}

function _set_up_aws_grafana {
	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/eks"

	local grafana_enabled

	grafana_enabled=$(terraform output -raw "grafana_enabled")

	if [[ "${grafana_enabled}" != "true" ]]
	then
		echo "Observability disabled. Skipping Grafana setup."

		return
	fi

	echo "Setting up Amazon Managed Grafana."

	TF_VAR_grafana_workspace_api_key=$(terraform output -raw "grafana_workspace_api_key")

	export TF_VAR_grafana_workspace_api_key

	_terraform_init_and_apply \
		"../grafana" \
		${1} \
		"-var=grafana_workspace_endpoint=$(terraform output -raw "grafana_workspace_endpoint")" \
		"-var=grafana_workspace_role_arn=$(terraform output -raw "grafana_workspace_role_arn")" \
		"-var=prometheus_workspace_endpoint=$(terraform output -raw "prometheus_workspace_endpoint")"

	echo "Amazon Managed Grafana setup complete."

	_popd
}

function _set_up_aws_service_linked_roles {
	local service_linked_roles=(
		"opensearchservice.amazonaws.com:AWSServiceRoleForAmazonOpenSearchService"
		"rds.amazonaws.com:AWSServiceRoleForRDS"
	)

	echo "Setting up AWS service-linked roles."

	for service_linked_role in "${service_linked_roles[@]}"
	do
		local role_name="${service_linked_role##*:}"
		local service_name="${service_linked_role%%:*}"

		if ! aws iam get-role --role-name "${role_name}" >/dev/null 2>&1
		then
			echo "Setting up AWS service-linked role for ${service_name}."

			aws iam create-service-linked-role --aws-service-name "${service_name}" --no-cli-pager

			echo "AWS service-linked role for ${service_name} setup complete."
		else
			echo "AWS service-linked role for ${service_name} already exists."
		fi
	done

	echo "AWS service-linked roles setup complete."
}

function _terraform_init_and_apply {
	_pushd "${1}"

	terraform init -upgrade

	terraform apply ${@:2}

	_popd
}

main "${@}"