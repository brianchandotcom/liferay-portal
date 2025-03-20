#!/usr/bin/env bash

TARGET_REGISTRY=${1?"first argument specifying the AWS container registry to push the custom image to is required"}
TARGET_REPOSITORY=${2?"second argument specifying the AWS container repository to put the custom image in is required"}
BASE_IMAGE_REGISTRY=${3?"third argument specifying the container registry from which to get the base image is required"}
BASE_IMAGE_REPOSITORY=${4?"fourth argument specifying the container repository from which to get the base image is required"}
BASE_IMAGE_TAG=${5?"fifth argument specifying the container image tag of the base image is required"}
TARGET_IMAGE_TAG=${6:-${BASE_IMAGE_TAG}}

echo "Publishing custom image to Registry: ${TARGET_REGISTRY}"
echo "Publishing custom image into Repository: ${TARGET_REPOSITORY}"
echo "Publishing custom image with Tag: ${TARGET_IMAGE_TAG}"
echo "Using base image Registry: ${BASE_IMAGE_REGISTRY}"
echo "Using base image Repository: ${BASE_IMAGE_REPOSITORY}"
echo "Using base image Tag: ${BASE_IMAGE_TAG}"

docker pull "${BASE_IMAGE_REGISTRY}/${BASE_IMAGE_REPOSITORY}:${BASE_IMAGE_TAG}"
docker tag "${BASE_IMAGE_REGISTRY}/${BASE_IMAGE_REPOSITORY}:${BASE_IMAGE_TAG}" "${TARGET_REGISTRY}/${TARGET_REPOSITORY}:${TARGET_IMAGE_TAG}"

# make sure to
# export AWS_PROFILE=<aws_mktplc_profile>

aws sso login
aws ecr get-login-password --region us-east-1 | docker login --password-stdin --username AWS ${TARGET_REGISTRY}

docker push "${TARGET_REGISTRY}/${TARGET_REPOSITORY}:${TARGET_IMAGE_TAG}"

# Run report
aws ecr describe-images --region us-east-1 --registry-id "${TARGET_REGISTRY:0:12}" --repository-name "${TARGET_REPOSITORY}"