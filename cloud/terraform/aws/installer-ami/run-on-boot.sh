#!/bin/bash

echo "Executing run-on-boot.sh at $(date) by $(id)"

TOKEN=$(curl --header "X-aws-ec2-metadata-token-ttl-seconds: 21600" --request PUT "http://169.254.169.254/latest/api/token")

AMI_ID=$(curl --header "X-aws-ec2-metadata-token: ${TOKEN}" http://169.254.169.254/latest/meta-data/ami-id)
INSTANCE_ID=$(curl --header "X-aws-ec2-metadata-token: ${TOKEN}" http://169.254.169.254/latest/meta-data/instance-id)
REGION=$(curl --header "X-aws-ec2-metadata-token: ${TOKEN}" http://169.254.169.254/latest/meta-data/placement/region)

echo "AMI_ID=${AMI_ID}"
echo "INSTANCE_ID=${INSTANCE_ID}"
echo "REGION=${REGION}"
echo "CALLER_IDENTITY=$(aws sts get-caller-identity)"

tree -a /opt/liferay

CHART_DIR=/opt/liferay/chart
IMAGE_DIR=/opt/liferay/image
TERRAFORM_DIR=/opt/liferay/terraform

# ERC
pushd "${TERRAFORM_DIR}/ecr"

terraform apply -auto-approve \
	-var="deployment_name=lfr-ami" \
	-var="region=${REGION}"
terraform output > "${TERRAFORM_DIR}/eks/terraform.tfvars"

# DXP Image
DXP_IMAGE_TAG=$(oras repo tags --oci-layout "${IMAGE_DIR}/dxp")
ECR_DXP_REPOSITORY_URL=$(terraform output -json ecr_repositories | jq --raw-output '."liferay/dxp".url')
ECR_REGISTRY_URL=${ECR_DXP_REPOSITORY_URL%%/*}

aws ecr get-login-password --region "${REGION}" | \
	oras login --username AWS --password-stdin "${ECR_REGISTRY_URL}"

oras cp --from-oci-layout --no-tty "${IMAGE_DIR}/dxp:${DXP_IMAGE_TAG}" "${ECR_DXP_REPOSITORY_URL}:${DXP_IMAGE_TAG}"

popd

# EKS
pushd "${TERRAFORM_DIR}/eks"

terraform apply -auto-approve -var node_instance_type=t3.2xlarge
terraform output > "${TERRAFORM_DIR}/dependencies/terraform.tfvars"

aws eks update-kubeconfig \
	--name $(terraform output -raw cluster_name) \
	--region $(terraform output -raw region)

kubectl cluster-info

popd

# OpenSearch/Postgres/S3
pushd "${TERRAFORM_DIR}/dependencies"

terraform apply -auto-approve

# Helm Install
if [ -f /opt/liferay/values.yaml ]
then
	VALUES_FILE_ARG="--values /opt/liferay/values.yaml"
else
	VALUES_FILE_ARG=""
fi

helm upgrade liferay "${CHART_DIR}/liferay-aws" \
	--install \
	--namespace "$(terraform output -raw deployment_namespace)" \
	--set "liferay-default.image.repository=${ECR_DXP_REPOSITORY_URL}" \
	--set "liferay-default.image.tag=${DXP_IMAGE_TAG}" \
	--set "liferay-default.serviceAccount.annotations.eks\.amazonaws\.com/role-arn=$(terraform output -raw liferay_sa_role)" \
	${VALUES_FILE_ARG}

kubectl rollout status statefulset/liferay-default \
	--namespace "$(terraform output -raw deployment_namespace)" \
	--timeout=1200s