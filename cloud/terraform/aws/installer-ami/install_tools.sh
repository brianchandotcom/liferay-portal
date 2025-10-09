#!/bin/bash -eux

# Install dependencies
sudo yum update --assumeyes
sudo yum install --assumeyes git jq tree shadow-utils unzip yum-utils

# Install AWS CLI
curl --fail-with-body --location --output "awscliv2.zip" --show-error --silent \
	"https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip"
unzip awscliv2.zip -x "aws/dist/awscli/examples/*"
sudo ./aws/install
rm awscliv2.zip

# Install Terraform
TERRAFORM_VERSION="1.13.1"
curl --fail-with-body --location --output "terraform.zip" --show-error --silent \
	"https://releases.hashicorp.com/terraform/${TERRAFORM_VERSION}/terraform_${TERRAFORM_VERSION}_linux_amd64.zip"
unzip terraform.zip
sudo mv terraform /usr/local/bin/
rm terraform.zip

# Install Kubernetes Client
KUBERNETES_VERSION="1.23.6"
curl --fail-with-body --location --output "kubectl" --show-error --silent \
	"https://dl.k8s.io/release/v${KUBERNETES_VERSION}/bin/linux/amd64/kubectl"
chmod +x kubectl
sudo mv kubectl /usr/local/bin/

# Install eksctl
curl --fail-with-body --location --output "eksctl.tar.gz" --show-error --silent \
	"https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_Linux_amd64.tar.gz"
tar --extract --file=eksctl.tar.gz --gzip
sudo mv eksctl /usr/local/bin
rm eksctl.tar.gz

# Install ORAS Client
ORAS_VERSION="1.3.0"
curl --fail-with-body --location --output "oras.tar.gz" --show-error --silent \
	"https://github.com/oras-project/oras/releases/download/v${ORAS_VERSION}/oras_${ORAS_VERSION}_linux_amd64.tar.gz"
tar --extract --file=oras.tar.gz --gzip
sudo mv oras /usr/local/bin/
sudo chmod +x /usr/local/bin/oras
rm oras.tar.gz

# Install Helm
curl --fail-with-body --location --output "get_helm.sh" --show-error --silent \
	https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
rm get_helm.sh

# Setup Liferay Cloud Native
CHART_DIR=/opt/liferay/chart
IMAGE_DIR=/opt/liferay/image
TERRAFORM_DIR=/opt/liferay/terraform

sudo mkdir --parents "${CHART_DIR}"
sudo mkdir --parents "${IMAGE_DIR}"
sudo chown --recursive 1000:1000 /opt/liferay

# ERC
pushd "${TERRAFORM_DIR}/ecr"
terraform init -upgrade
popd

# EKS
pushd "${TERRAFORM_DIR}/eks"
terraform init -upgrade
popd

# OpenSearch/RDS/S3/
pushd "${TERRAFORM_DIR}/dependencies"
terraform init -upgrade
popd

# Download the Liferay DXP image
CONTAINER_REGISTRY="docker.io"
DXP_IMAGE_TAG="${DXP_IMAGE_TAG:-$(oras repo tags docker.io/liferay/dxp | grep $(date +%Y) | grep lts | grep slim$ | sort --reverse | head -1)}"
LIFERAY_DXP_REPOSITORY="liferay/dxp"

mkdir "${IMAGE_DIR}/dxp"
oras cp --no-tty --to-oci-layout "${CONTAINER_REGISTRY}/${LIFERAY_DXP_REPOSITORY}:${DXP_IMAGE_TAG}" "${IMAGE_DIR}/dxp:${DXP_IMAGE_TAG}"

# Download the Liferay Cloud Native AWS Chart
CHART_REPO="us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart"
CHART_NAME="liferay-aws"
DXP_AWS_CHART_VERSION="${DXP_AWS_CHART_VERSION:+:${DXP_AWS_CHART_VERSION}}"
OCI_ENDPOINT="oci://${CHART_REPO}/${CHART_NAME}${DXP_AWS_CHART_VERSION}"

# Use the latest chart by default
helm pull --untar --untardir "${CHART_DIR}" "${OCI_ENDPOINT}"

tree -a /opt/liferay