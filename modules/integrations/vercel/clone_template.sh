#!/bin/bash

function main {
	local temp_dir=$(mktemp -d)

	git clone \
		--branch=master \
		--depth 1 \
		--single-branch \
		https://github.com/liferay/liferay-portal.git "${temp_dir}"

	local template_dir="${temp_dir}/modules/integrations/vercel/${1}"

	local destination_dir=$(pwd)

	if [ -n "${2}" ]
	then
		destination_dir="${2}"
	fi

	mkdir --parents "${destination_dir}"

	mv "${template_dir}" "${destination_dir}"

	cd "${destination_dir}/${1}" && git init && git add . && git commit --message "chore: clone ${1}"
}

main "${@}"