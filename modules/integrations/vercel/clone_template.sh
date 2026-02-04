#!/bin/bash

function main {
	TEMPLATE="${1}"

	DESTINATION=$(pwd)

	if [ -n "${2}" ]
	then
		DESTINATION="${2}"
	fi

	TEMP_DIR=$(mktemp -d)

	_clone_latest_commit

	_extract_template

	_make_destination_a_git_repo
}

function _clone_latest_commit {
	git clone \
		--branch=master \
		--depth 1 \
		--single-branch \
		https://github.com/liferay/liferay-portal.git "${TEMP_DIR}"
}

function _extract_template {
	local template_origin="${TEMP_DIR}/modules/integrations/vercel/${TEMPLATE}"

	echo "Moving ${template_origin} to ${DESTINATION}"

	if [ ! -d "${DESTINATION}" ]
	then
		mkdir -p "${DESTINATION}"
	fi

	mv -v "${template_origin}" "${DESTINATION}"
}

function _make_destination_a_git_repo {
	cd "${DESTINATION}/${TEMPLATE}" && git init && git add . && git commit --message "chore: clone TEMPLATE"
}

main "${@}"