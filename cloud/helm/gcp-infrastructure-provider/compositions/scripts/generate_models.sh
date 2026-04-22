#!/bin/bash
# Refreshes compositions/models/ from the provider CRDs declared in
# templates/providers.yaml. Run manually when the provider set or versions
# change; the output is checked in so routine builds and tests do not need
# docker.
set -e

function main {
    local compositions_directory="/home/allenz/liferay/liferay-portal/cloud/helm/gcp-infrastructure-provider/compositions"
    local models_directory="${compositions_directory}/models"
    local temporary_directory
    temporary_directory=$(mktemp -d)
    trap 'rm -rf "${temporary_directory}"' EXIT

    echo ""
    echo "Extracting provider CRDs."

    local provider_image_urls
    provider_image_urls=$(yq -N -r '.spec.package | select(. != null)' "/home/allenz/liferay/liferay-portal/cloud/helm/gcp-infrastructure-provider/templates/providers.yaml")

    for provider_image_url in ${provider_image_urls}
    do
        echo "  ${provider_image_url}"

        docker pull -q "${provider_image_url}"

        local container_id
        container_id=$(docker create "${provider_image_url}")

        docker cp "${container_id}:/package.yaml" "${temporary_directory}/package.yaml"
        docker rm -v "${container_id}" > /dev/null

        # Keep only the namespaced (*.m.*) CRDs that v2 crossplane uses. The
        # kubernetes provider group is already namespaced
        # (kubernetes.m.crossplane.io).
        if [[ "${provider_image_url}" == *"provider-kubernetes"* ]]
        then
            yq -N 'select(.kind == "CustomResourceDefinition")' "${temporary_directory}/package.yaml" >> "${temporary_directory}/all_crds.yaml"
        else
            yq -N 'select(.kind == "CustomResourceDefinition" and (.spec.group | contains(".m.")))' "${temporary_directory}/package.yaml" >> "${temporary_directory}/all_crds.yaml"
        fi

        echo "---" >> "${temporary_directory}/all_crds.yaml"
    done

    echo ""
    echo "Generating KCL models."

    mkdir -p "${temporary_directory}/src" "${temporary_directory}/split"
    yq -N -s "\"${temporary_directory}/split/\" + .metadata.name" "${temporary_directory}/all_crds.yaml"

    for crd_file in "${temporary_directory}/split/"*
    do
        [ -e "${crd_file}" ] || continue

        local crd_name
        crd_name=$(basename "${crd_file}")

        mkdir -p "${temporary_directory}/src/${crd_name}"

        kcl-openapi generate model --spec "${crd_file}" --crd --target "${temporary_directory}/src/${crd_name}" --model-package "models"
    done

    echo ""
    echo "Copying into \"${models_directory}\"."

    rm -rf "${models_directory}"
    mkdir -p "${models_directory}"

    # Each kcl-openapi invocation produces its own models tree; merge them all.
    for crd_src_directory in "${temporary_directory}/src"/*/models
    do
        [ -d "${crd_src_directory}" ] || continue

        cp -rn "${crd_src_directory}"/. "${models_directory}/" || cp -r "${crd_src_directory}"/* "${models_directory}/"
    done

    echo ""
    echo "Done. Models written to \"${models_directory}\"."
}

main
