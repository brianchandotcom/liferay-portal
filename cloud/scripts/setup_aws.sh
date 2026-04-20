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

	local bucket_name=""
	local region=""
	local deployment_name=""

	local terraform_args

	terraform_args="$(_get_terraform_apply_args "${1}" "${2}")"

	if jq --exit-status '.variables.tfstate_bucket_name' "${1}" &> /dev/null
	then
		bucket_name="$(jq --raw-output '.variables.tfstate_bucket_name' "${1}")"
		region="$(jq --raw-output '.variables.region' "${1}")"
		deployment_name="$(jq --raw-output '.variables.deployment_name' "${1}")"

		_create_tfstate_bucket "${bucket_name}" "${region}"
	fi

	_set_up_aws_service_linked_roles

	_set_up_aws_eks "${terraform_args}" "${bucket_name}" "${region}" "${deployment_name}"

	_set_up_aws_grafana "${terraform_args}" "${bucket_name}" "${region}" "${deployment_name}"

	_set_up_aws_gitops "${terraform_args}" "${bucket_name}" "${region}" "${deployment_name}"

	_port_forward_argo_cd
}

function _configure_s3_bucket {
	local alias_name="alias/tfstate-${bucket_name}"
	local bucket_name="${1}"
	local region="${2}"
	
	local account_id

	account_id="$(aws sts get-caller-identity --output text --query "Account")"

	local kms_key_id

	if ! kms_key_id=$( \
		aws kms describe-key --key-id "${alias_name}" \
			--output text \
			--query 'KeyMetadata.KeyId' \
			--region "${region}"  2>/dev/null)
	then
		_log "Creating KMS key for bucket ${bucket_name}."

		kms_key_id=$( \
			aws kms create-key \
				--description "Terraform State Storage Key" \
				--output text \
				--policy "{
						\"Statement\": [{
							\"Action\": \"kms:*\",
							\"Effect\": \"Allow\",
							\"Principal\": {\"AWS\": \"arn:aws:iam::${account_id}:root\"},
							\"Resource\": \"*\"
						}],
						\"Version\": \"2012-10-17\"
					}" \
				--query 'KeyMetadata.KeyId' \
				--region "${region}")

		aws kms create-alias \
			--alias-name "${alias_name}" \
			--region "${region}" \
			--target-key-id "${kms_key_id}"

		_log "KMS key for bucket ${bucket_name} created successfully."
	else
		_log "KMS key for bucket ${bucket_name} already exists. Skipping creation process."
	fi

	aws s3api put-bucket-encryption \
	--bucket "${bucket_name}" \
	--region "${region}" \
	--server-side-encryption-configuration "{
			\"Rules\": [
				{
					\"ApplyServerSideEncryptionByDefault\": {
							\"KMSMasterKeyID\": \"${kms_key_id}\",
							\"SSEAlgorithm\": \"aws:kms\"
					}
				}
			]
		}"

	aws s3api put-public-access-block \
		--bucket "${bucket_name}" \
		--public-access-block-configuration '{
				"BlockPublicAcls": true,
				"BlockPublicPolicy": true,
				"IgnorePublicAcls": true,
				"RestrictPublicBuckets": true
			}' \
		--region "${region}"

	aws s3api put-object-lock-configuration \
		--bucket "${bucket_name}" \
		--object-lock-configuration '{
				"ObjectLockEnabled": "Enabled",
				"Rule": {
					"DefaultRetention": {
						"Days": 90,
						"Mode": "GOVERNANCE"
					}
				}
			}'\
		--region "${region}"
}

function _create_tfstate_bucket {
	local bucket_name="${1}"
	local region="${2}"

	if ! aws s3api head-bucket --bucket "${bucket_name}" --region "${region}" &> /dev/null
	then
		_log "Creating bucket ${bucket_name}."

		_create_s3_bucket "${bucket_name}" "${region}"

		_log "Bucket ${bucket_name} created successfully."
	else
		_log "Bucket ${bucket_name} already exists. Skipping creation process."
	fi

	_log "Configuring bucket ${bucket_name}."

	_configure_s3_bucket "${bucket_name}" "${region}"

	_log "Bucket ${bucket_name} configured successfully."
}

function _create_s3_bucket {
	local bucket_name="${1}"
	local region="${2}"

	if [ "${region}" == "us-east-1" ]
	then
		aws s3api create-bucket \
			--bucket "${bucket_name}" \
			--object-lock-enabled-for-bucket \
			--region "${region}" 1> /dev/null
	else
		aws s3api create-bucket \
			--bucket "${bucket_name}" \
			--create-bucket-configuration LocationConstraint="${region}" \
			--object-lock-enabled-for-bucket \
			--region "${region}" 1> /dev/null
	fi

	aws s3api put-bucket-versioning \
		--bucket "${bucket_name}" \
		--region "${region}" \
		--versioning-configuration Status=Enabled
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

	tfvars_content=$( \
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

function _log {
	echo "[Tfstate bucket configuration] ${1}"
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
		--namespace "${argocd_namespace}" \
		service/argocd-server \
		8080:443

	_popd
}

function _pushd {
	pushd "${1}" > /dev/null
}

function _set_up_aws_eks {
	local terraform_args="${1}"
	local bucket_name="${2}"
	local region="${3}"
	local deployment_name="${4}"

	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/eks"

	echo "Setting up the AWS EKS cluster."

	_terraform_init_and_apply "." "eks" "${bucket_name}" "${deployment_name}" "${region}" "${terraform_args}"

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
	local terraform_args="${1}"
	local bucket_name="${2}"
	local region="${3}"
	local deployment_name="${4}"

	_pushd "${_ROOT_CLOUD_DIR}/terraform/aws/gitops"

	echo "Setting up GitOps infrastructure."

	_terraform_init_and_apply "./platform" "gitops/platform" "${bucket_name}" "${deployment_name}" "${region}" "${terraform_args}"

	_terraform_init_and_apply "./resources" "gitops/resources" "${bucket_name}" "${deployment_name}" "${region}" "${terraform_args}"

	echo "GitOps infrastructure setup complete."

	_popd
}

function _set_up_aws_grafana {
	local terraform_args="${1}"
	local bucket_name="${2}"
	local region="${3}"
	local deployment_name="${4}"

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
		"grafana" \
		"${bucket_name}" \
		"${deployment_name}" \
		"${region}" \
		"${terraform_args}" \
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

		if ! aws iam get-role --role-name "${role_name}" &> /dev/null
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
	local bucket_name="${3}"
	local deployment_name="${4}"
	local folder_separator="${2}"
	local region="${5}"
	local terraform_args="${6}"

	_pushd "${1}"

	if [ -n "${bucket_name}" ]
	then
	terraform init \
		-backend-config="bucket=${bucket_name}" \
		-backend-config="encrypt=true" \
		-backend-config="key=${deployment_name}/${region}/${folder_separator}/terraform.tfstate" \
		-backend-config="region=${region}" \
		-backend-config="use_lockfile=true" \
		-upgrade
	else
		terraform init -backend=false -upgrade
	fi

	terraform apply ${terraform_args} "${@:7}"

	_popd
}

main "${@}"