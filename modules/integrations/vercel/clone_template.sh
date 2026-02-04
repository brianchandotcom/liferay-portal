#!/bin/bash

function main {
	DESTINATION=$(pwd)

	if [ -n "${2}" ]
	then
		DESTINATION="${2}"
	fi

	TEMP_DIR=$(mktemp -d)

	git clone \
		--branch=master \
		--depth 1 \
		--single-branch \
		https://github.com/liferay/liferay-portal.git "${TEMP_DIR}"

	local template_origin="${TEMP_DIR}/modules/integrations/vercel/${1}"

	echo "Moving ${template_origin} to ${DESTINATION}"

	if [ ! -d "${DESTINATION}" ]
	then
		mkdir -p "${DESTINATION}"
	fi

	mv -v "${template_origin}" "${DESTINATION}"

	cd "${DESTINATION}/${1}" && git init && git add . && git commit --message "chore: clone ${1}"
}

main "${@}"