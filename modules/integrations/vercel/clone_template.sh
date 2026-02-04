#!/bin/bash

function main {
	local destination=$(pwd)

	if [ -n "${2}" ]
	then
		destination="${2}"
	fi

	local temp_dir=$(mktemp -d)

	git clone \
		--branch=master \
		--depth 1 \
		--single-branch \
		https://github.com/liferay/liferay-portal.git "${temp_dir}"

	local template_origin="${temp_dir}/modules/integrations/vercel/${1}"

	echo "Moving ${template_origin} to ${destination}"

	mkdir -p "${destination}"

	mv -v "${template_origin}" "${destination}"

	cd "${destination}/${1}" && git init && git add . && git commit --message "chore: clone ${1}"
}

main "${@}"