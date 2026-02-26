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

	local config_file=""

	config_file=$(_get_config_file "${1:-}")

	local provider=""

	provider=$(_get_provider "${config_file}")

	local extracted_dir=""

	extracted_dir=$(_download_and_extract_files "${provider}")

	trap "rm --force --recursive \"${extracted_dir}\"" EXIT

	"${extracted_dir}/cloud/scripts/setup_${provider}.sh" "${@}"
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

	local json=""

	json=$( \
		curl \
			--location \
			--silent \
			"https://storage.googleapis.com/storage/v1/b/${bucket_name}/o?prefix=${prefix}/&projection=noAcl")

	if [ ! -n "${json}" ]
	then
		echo "Unable to get metadata from gs://${bucket_name}/${prefix}/" >&2

		exit 1
	fi

	local latest_path=""

	latest_path=$( \
		jq \
			--raw-output \
			".items
			| sort_by(.updated)
			| last
			| .name" <<< "${json}")

	if [ "${latest_path}" == "null" ] || [ -z "${latest_path}" ]
	then
		echo "Unable to find any files in gs://${bucket_name}/${prefix}/" >&2

		exit 1
	fi

	local temp_dir=""

	temp_dir=$(mktemp --directory --tmpdir liferay-bootstrap.XXXXXX)

	local output_location=""

	output_location="${temp_dir}/$(basename "${latest_path}")"

	curl \
		--location \
		--output "${output_location}" \
		--silent \
		"https://cdn.liferay.cloud/${latest_path}"

	tar \
		--directory "${temp_dir}" \
		--extract \
		--file "${output_location}" \
		--ungzip

	echo "${temp_dir}"
}

function _get_config_file {
	local config_file="${1}"

	if [ -z "${config_file}" ]
	then
		config_file="config.json"
	fi

	if [ ! -f "${config_file}" ]
	then
		echo "No config file '${config_file}' found." >&2

		exit 1
	fi

	echo "${config_file}"
}

function _get_provider {
	local config_file="${1}"

	local provider=""

	provider=$(jq -r ".provider // empty" "${config_file}")

	if [ -z "${provider}" ]
	then
		echo "'provider' field is missing in ${1}." >&2

		exit 1
	elif [ "${provider}" != "aws" ] && [ "${provider}" != "gcp" ]
	then
		echo "Unsupported provider ${provider}." >&2

		exit 1
	fi

	echo "${provider}"
}

main "${@}"