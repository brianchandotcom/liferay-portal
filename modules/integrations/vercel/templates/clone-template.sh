#!/bin/bash

LIFERAY_REPO="https://github.com/liferay/liferay-portal.git"
LIFERAY_BRANCH="master"

function clone_latest_commit {
	local temp_dir=${1}

	git clone --single-branch --branch=${LIFERAY_BRANCH} --depth 1 ${LIFERAY_REPO} ${temp_dir}
}

function create_temp_dir {
	echo $(mktemp -d)
}

function extract_template {
	local temp_dir=${1}
	local template=${2}
	local destiny=${3}

	local template_origin="${temp_dir}/modules/integrations/vercel/templates/${template}"

	echo -e "extracting ${template_origin} to ${destiny}"

	if [[ ! -d ${destiny} ]]
	then
		mkdir -p ${destiny}
	fi

	mv -v ${template_origin} ${destiny}
}

function main {
	local temp_dir=$(create_temp_dir)
	local template=${1}
	local destiny=${2:-"$(pwd)"}

	clone_latest_commit ${temp_dir}

	extract_template ${temp_dir} ${template} ${destiny}

	make_destiny_a_git_repo "${destiny}/${template}"
}

function make_destiny_a_git_repo {
	local destiny=${1}

	cd ${destiny} && git init && git add . && git commit -m "chore: clone template"
}

main "${@}"