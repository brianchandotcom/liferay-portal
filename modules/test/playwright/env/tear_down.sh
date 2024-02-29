#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

source ${CURRENT_DIR_NAME}/common.sh

function main {
	playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/tear_down.sh ]]
	then
		/bin/bash ${playwright_project_dir}/env/tear_down.sh
	else
		default_tear_down
	fi
}

main "${@}"