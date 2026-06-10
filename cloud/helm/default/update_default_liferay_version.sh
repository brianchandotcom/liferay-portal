#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

function main {
	local latest_lts_dxp_version

	latest_lts_dxp_version=($(\
		blade init --list | \
		grep --extended-regexp 'dxp-.*-lts' | \
		sed 's/^dxp-//'))[0]

	check_if_image_exists "${latest_lts_dxp_version}"

	sed \
		--in-place \
		--regexp-extended \
		"s/^ {4}tag:.*/    tag: ${latest_lts_dxp_version}/" ./values.yaml
}

function check_if_image_exists {
	local tag="${1}"

	local http_code

	http_code=$(curl \
		--max-time 30 \
		--output /dev/null \
		--silent \
		--write-out '%{http_code}' \
		"https://hub.docker.com/v2/repositories/liferay/dxp/tags/${tag}")

	if [ "${http_code}" != "200" ]
	then
		echo "Image liferay/dxp:${tag} does not exist on Docker Hub."

		exit 1
	fi
}

main "${@}"