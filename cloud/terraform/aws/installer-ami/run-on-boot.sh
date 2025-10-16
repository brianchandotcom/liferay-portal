#!/bin/bash

set -eux

function main {
	echo "Executing run-on-boot.sh at $(date) by $(id)"

	local token

	token=$( \
		curl \
		--header "X-aws-ec2-metadata-token-ttl-seconds: 21600" \
		--request PUT \
		"http://169.254.169.254/latest/api/token")

	local ami_id

	ami_id=$( \
		curl \
			--header "X-aws-ec2-metadata-token: ${token}" \
			http://169.254.169.254/latest/meta-data/ami-id)

	local instance_id

	instance_id=$( \
		curl \
			--header "X-aws-ec2-metadata-token: ${token}" \
			http://169.254.169.254/latest/meta-data/instance-id)

	local region

	region=$( \
		curl \
			--header "X-aws-ec2-metadata-token: ${token}" \
			http://169.254.169.254/latest/meta-data/placement/region)

	echo "The region is ${region}"

	echo "The caller identity is $(aws sts get-caller-identity)"

	tree -a /opt/liferay

	local chart_dir

	chart_dir=/opt/liferay/chart

	local image_dir

	image_dir=/opt/liferay/image

	local terraform_dir

	terraform_dir=/opt/liferay/terraform

	pushd "${terraform_dir}/ecr"

	terraform apply -auto-approve \
		-var="deployment_name=lfr-ami" \
		-var="region=${region}"
	terraform output > "${terraform_dir}/eks/terraform.tfvars"

	local dxp_image_tag

	dxp_image_tag=$(oras repo tags --oci-layout "${image_dir}/dxp")

	local ecr_dxp_repository_url

	ecr_dxp_repository_url=$( \
		terraform \
			output \
			-json ecr_repositories \
			| jq --raw-output '."liferay/dxp".url')

	local ecr_registry_url

	ecr_registry_url=${ecr_dxp_repository_url%%/*}

	aws \
		ecr \
		get-login-password \
		--region "${region}" \
		| oras login --username AWS --password-stdin "${ecr_registry_url}"

	oras \
		cp \
		--from-oci-layout \
		--no-tty \
		"${image_dir}/dxp:${dxp_image_tag}" \
		"${ecr_dxp_repository_url}:${dxp_image_tag}"

	popd

	pushd "${terraform_dir}/eks"

	terraform apply -auto-approve -var node_instance_type=t3.2xlarge
	terraform output > "${terraform_dir}/dependencies/terraform.tfvars"

	aws \
		eks \
		update-kubeconfig \
		--name $(terraform output -raw cluster_name) \
		--region $(terraform output -raw region)

	kubectl cluster-info

	popd

	pushd "${terraform_dir}/dependencies"

	terraform apply -auto-approve

	local values_file_arg

	if [ -f /opt/liferay/values.yaml ]
	then
		values_file_arg="--values /opt/liferay/values.yaml"
	else
		values_file_arg=""
	fi

	local namespace

	namespace=$(terraform output -raw deployment_namespace)

	local liferay_sa_role

	liferay_sa_role=$(terraform output -raw liferay_sa_role)

	helm \
		upgrade \
		liferay \
		"${chart_dir}/liferay-aws" \
		--install \
		--namespace "${namespace}" \
		--set "liferay-default.image.repository=${ecr_dxp_repository_url}" \
		--set "liferay-default.image.tag=${dxp_image_tag}" \
		--set "liferay-default.serviceAccount.annotations.eks\.amazonaws\.com/role-arn=${liferay_sa_role}" \
		${values_file_arg}

	kubectl \
		rollout \
		status \
		statefulset/liferay-default \
		--namespace "${namespace}" \
		--timeout=1200s
}

main