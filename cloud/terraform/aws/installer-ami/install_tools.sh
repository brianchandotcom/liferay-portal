#!/bin/bash

set -eux

function download {
	curl \
		--fail-with-body \
		--location \
		--output "${2}" \
		--show-error \
		--silent \
		"${1}"
}

function main {
	sudo yum update --assumeyes

	sudo yum install --assumeyes git jq tree shadow-utils unzip yum-utils

	download "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" "awscliv2.zip"

	unzip awscliv2.zip -x "aws/dist/awscli/examples/*"

	sudo ./aws/install

	rm awscliv2.zip

	local terraform_version

	terraform_version="1.13.1"

	download "https://releases.hashicorp.com/terraform/${terraform_version}/terraform_${terraform_version}_linux_amd64.zip" "terraform.zip"

	unzip terraform.zip

	sudo mv terraform /usr/local/bin/

	rm terraform.zip

	local kubernetes_version

	kubernetes_version="1.23.6"

	download "https://dl.k8s.io/release/v${kubernetes_version}/bin/linux/amd64/kubectl" "kubectl"

	chmod +x kubectl

	sudo mv kubectl /usr/local/bin/

	download "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_Linux_amd64.tar.gz" "eksctl.tar.gz"

	tar --extract --file=eksctl.tar.gz --gzip

	sudo mv eksctl /usr/local/bin

	rm eksctl.tar.gz

	local oras_version

	oras_version="1.3.0"

	download "https://github.com/oras-project/oras/releases/download/v${oras_version}/oras_${oras_version}_linux_amd64.tar.gz" "oras.tar.gz"

	tar --extract --file=oras.tar.gz --gzip

	sudo mv oras /usr/local/bin/

	sudo chmod +x /usr/local/bin/oras

	rm oras.tar.gz

	download "https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3" "get_helm.sh"

	chmod 700 get_helm.sh

	./get_helm.sh

	rm get_helm.sh

	local chart_dir

	chart_dir=/opt/liferay/chart

	local image_dir

	image_dir=/opt/liferay/image

	local terraform_dir

	terraform_dir=/opt/liferay/terraform

	sudo mkdir --parents "${chart_dir}"
	sudo mkdir --parents "${image_dir}"
	sudo chown --recursive 1000:1000 /opt/liferay

	pushd "${terraform_dir}/ecr"

	terraform init -upgrade

	popd

	pushd "${terraform_dir}/eks"

	terraform init -upgrade

	popd

	pushd "${terraform_dir}/dependencies"

	terraform init -upgrade

	popd

	local container_registry

	container_registry="docker.io"

	local dxp_image_tag

	if [ "${DXP_IMAGE_TAG}" != "" ]
	then
		dxp_image_tag="${DXP_IMAGE_TAG}"
	else
		dxp_image_tag=$( \
			oras \
				repo \
				tags \
				docker.io/liferay/dxp \
				| grep $(date +%Y) \
				| grep lts \
				| grep "slim$" \
				| sort --reverse \
				| head -1)
	fi

	local liferay_dxp_repository

	liferay_dxp_repository="liferay/dxp"

	mkdir "${image_dir}/dxp"

	oras \
		cp \
		--no-tty \
		--to-oci-layout \
		"${container_registry}/${liferay_dxp_repository}:${dxp_image_tag}" \
		"${image_dir}/dxp:${dxp_image_tag}"

	local chart_repo

	chart_repo="us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart"

	local chart_name

	chart_name="liferay-aws"

	local oci_endpoint

	oci_endpoint="oci://${chart_repo}/${chart_name}"

	if [ "${DXP_AWS_CHART_VERSION}" != "" ]
	then
		helm \
			pull \
			--untar \
			--untardir "${chart_dir}" \
			--version "${DXP_AWS_CHART_VERSION}" \
			"${oci_endpoint}"
	else
		helm pull --untar --untardir "${chart_dir}" "${oci_endpoint}"
	fi
}

main