#!/usr/bin/env bash

TARGET_REGISTRY=${1?"first argument specifying the AWS helm registry to push the custom chart to is required"}
TARGET_REPOSITORY=${2?"second argument specifying the AWS helm repository to put the custom chart in is required"}
HELM_CHART_VERSION=${3?"third argument specifying the version for the custom helm chart is required"}
IMAGE_REPOSITORY=${4?"fourth argument specifying the AWS image repository from which to get the image for the custom chart is required"}
IMAGE_TAG=${5?"firth argument specifying the tag of the AWS image to use in the custom chart is required"}
LIFERAY_HELM_CHART_VERSION=${6?"sixth argument specifying the version of the official Liferay helm chart dependency is required"}

echo "Publishing custom Helm chart to Registry: ${TARGET_REGISTRY}"
echo "Publishing custom Helm chart into Repository: ${TARGET_REPOSITORY}"
echo "Publishing custom Helm chart with Version: ${HELM_CHART_VERSION}"
echo "Setting custom Helm chart Docker image Repository to: ${IMAGE_REPOSITORY}"
echo "Setting custom Helm chart Docker image Tag to: ${IMAGE_TAG}"
echo "Depending on Liferay Helm Chart Version: ${LIFERAY_HELM_CHART_VERSION}"

TEMP_CHART_DIR="tmp-chart"
rm --force --recursive ${TEMP_CHART_DIR}
mkdir --parents ${TEMP_CHART_DIR}/charts
cp Chart.yaml values.yaml ${TEMP_CHART_DIR}

sed --in-place "s/^version: 0.0.0/version: ${HELM_CHART_VERSION}/" ${TEMP_CHART_DIR}/Chart.yaml
sed --in-place "s/^        version: 0.0.0/        version: ${LIFERAY_HELM_CHART_VERSION}/" ${TEMP_CHART_DIR}/Chart.yaml
sed --in-place "s|^    repository: \&imageRepository liferay/dxp|    repository: \&imageRepository ${TARGET_REGISTRY}/${IMAGE_REPOSITORY}|" ${TEMP_CHART_DIR}/values.yaml
sed --in-place "s/^    tag: \&imageTag latest/    tag: \&imageTag ${IMAGE_TAG}/" ${TEMP_CHART_DIR}/values.yaml

if [ ! -f helm ]
then
	curl --fail --location --output helm.tar.gz --show-error --silent https://get.helm.sh/helm-v3.6.3-linux-amd64.tar.gz
	tar --extract --file=helm.tar.gz --gzip --strip-components=1 linux-amd64/helm
	rm helm.tar.gz
fi

pushd "${TEMP_CHART_DIR}"

# Use latest helm (not the old downloaded one) to get from the dependency
# from the oci based GAR repo

helm dependency update

if [[ " ${@} " =~ " --test " ]]
then
	helm package . --version "${HELM_CHART_VERSION}"

	echo "Exiting because --test argument was provided"

	exit
fi

export HELM_EXPERIMENTAL_OCI=1

# make sure to
# export AWS_PROFILE=<aws_mktplc_profile>

aws sso login
aws ecr get-login-password --region us-east-1 | \
	../helm registry login --password-stdin --username AWS ${TARGET_REGISTRY}

../helm chart save . "${TARGET_REGISTRY}/${TARGET_REPOSITORY}:${HELM_CHART_VERSION}"
../helm chart push "${TARGET_REGISTRY}/${TARGET_REPOSITORY}:${HELM_CHART_VERSION}"

# Run report
aws ecr describe-images --region us-east-1 --registry-id 709825985650 --repository-name liferay/charts

popd

rm --force --recursive helm ${TEMP_CHART_DIR}