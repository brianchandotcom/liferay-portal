#!/usr/bin/env bash

set -euo pipefail

function check_utils {
	for util in "${@}"
	do
		if (! command -v "${util}" &>/dev/null)
		then
			echo "The utility ${util} is not installed."

			exit 1
		fi
	done
}

function download_and_extract_files {
	local bucket_name="liferay-cloud-native-bootstrap"

	local provider="${1}"

	local prefix="bootstrap/liferay-${provider}-bootstrap"

	local json
	json=$( \
		curl \
			--location \
			--silent \
			"https://storage.googleapis.com/storage/v1/b/${bucket_name}/o?prefix=${prefix}/&projection=noAcl")

	if [ ! -n "${json}" ]
	then
		echo "Error: Could not get metadata from gs://${bucket_name}/${prefix}/" >&2

		exit 1
	fi

	local latest_path
	latest_path=$(jq --raw-output '.items | sort_by(.updated) | last | .name' <<< "${json}")

	if [ "${latest_path}" == "null" ] || [ -z "${latest_path}" ]
	then
		echo "Error: Could not find any files in gs://${bucket_name}/${prefix}/" >&2

		exit 1
	fi

	local temp_dir=
	temp_dir=$(mktemp --directory -t liferay-bootstrap.XXXXXX)

	local output_location
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

function get_config_file {
	local config_file="${1}"

	if [ -z "${config_file}" ]
	then
		config_file="config.json"
	fi

	if [ ! -f "${config_file}" ]
	then
		echo "Error: Config file '${config_file}' not found." >&2

		exit 1
	fi

	echo "${config_file}"
}

function get_provider {
	local config_file="${1}"

	local provider
	provider=$(jq -r ".provider // empty" "${config_file}")

	case "${provider}" in
		"aws" | "gcp")
			;;
		"")
			echo "Error: 'provider' field is missing in ${1}." >&2

			exit 1
			;;
		*)
			echo "Error: Unsupported provider ${provider}." >&2

			exit 1
			;;
	esac

	echo "${provider}"
}

function main {
	if [[ "${BASH_SOURCE[0]}" != "${0}" ]]
	then
		return
	fi

	check_utils curl jq tar

	local config_file
	config_file=$(get_config_file "${1:-}")

	local provider
	provider=$(get_provider "${config_file}")

	local extracted_dir
	extracted_dir=$(download_and_extract_files "${provider}")

	trap 'rm --force --recursive "${extracted_dir}"' EXIT

	"${extracted_dir}/cloud/scripts/setup_${provider}" "${@}"
}

main "${@}"