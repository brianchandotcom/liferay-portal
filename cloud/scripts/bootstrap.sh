#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

function main {
	if [[ "${BASH_SOURCE[0]}" != "${0}" ]]
	then
		return
	fi

	_check_utils curl jq tar

	local config_file

	config_file=$(_get_config_file "${1:-}")

	local provider

	provider=$(_get_provider "${config_file}")

	local version

	version=$(_get_version "${config_file}");

	local extracted_dir

	extracted_dir=$(_download_and_extract_files "${provider}" "${version}")

	"${extracted_dir}/cloud/scripts/setup_${provider}.sh" "${config_file}" "${extracted_dir}/cloud/scripts/versions_${provider}.tfvars"
}

function _check_utils {
	for util in "${@}"
	do
		if (! command -v "${util}" &> /dev/null)
		then
			echo "The utility ${util} is not installed."

			exit 1
		fi
	done
}

function _download_and_extract_files {
	local bucket_name="liferay-cloud-native-bootstrap"

	local prefix="bootstrap/liferay-${1}-bootstrap"

	local json

	json=$( \
		curl \
			--location \
			--silent \
			"https://storage.googleapis.com/storage/v1/b/${bucket_name}/o?prefix=${prefix}/&projection=noAcl")

	if [ ! -n "${json}" ]
	then
		echo "Unable to get metadata from gs://${bucket_name}/${prefix}" >&2

		exit 1
	fi

	local version="${2}"

	local output_path

	if [ "${version}" == "latest" ]
	then
		output_path=$( \
			jq \
				--raw-output \
				".items
				| sort_by(.updated)
				| last
				| .name" <<< "${json}")

	else
		output_path=$( \
			jq \
				--arg sn "bootstrap/liferay-${provider}-bootstrap/liferay-${provider}-bootstrap-${version}.tar.gz" \
				--raw-output \
				'.items[]
				| select(.name == $sn)
				| .name' <<< "${json}")
	fi

	local output_file

	output_file=$(basename "${output_path}")

	if [ "${output_file}" == "null" ] || [ -z "${output_file}" ]
	then
		echo "There are no files in gs://${bucket_name}/${prefix}/ for the version \"${version}\"" >&2

		exit 1
	fi

	curl \
		--location \
		--output "${output_file}" \
		--silent \
		"https://cdn.liferay.cloud/${output_path}"

	local output_dir="${output_file%.tar.gz}"

	mkdir "${output_dir}"

	tar \
		--directory "${output_dir}" \
		--extract \
		--file "${output_file}" \
		--ungzip

	echo "${output_dir}"
}

function _get_config_file {
	local config_file="${1}"

	if [ -z "${config_file}" ]
	then
		config_file="config.json"
	fi

	if [ ! -f "${config_file}" ]
	then
		echo "The configuration file ${config_file} does not exist." >&2

		exit 1
	fi

	echo "${config_file}"
}

function _get_provider {
	local config_file="${1}"

	local provider

	provider=$(jq -r ".options.provider // empty" "${config_file}")

	if [ -z "${provider}" ]
	then
		provider=$(jq -r ".provider // empty" "${config_file}")

		if [ -z "${provider}" ]
		then
			echo "No provider is specified in ${config_file}." >&2

			exit 1
		fi
	fi

	if [ "${provider}" != "aws" ] && [ "${provider}" != "gcp" ]
	then
		echo "Unsupported provider ${provider} was specified in ${config_file}." >&2

		exit 1
	fi

	echo "${provider}"
}

function _get_version {
	local config_file="${1}"

	local version

	version=$(jq -r ".options.version // empty" "${config_file}")

	if [ -z "${version}" ]
	then
		version="latest"
	fi

	echo "${version}"
}

main "${@}"