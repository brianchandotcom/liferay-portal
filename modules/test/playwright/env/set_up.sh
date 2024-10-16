#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

source ${CURRENT_DIR_NAME}/common.sh

function main {
	export PORTAL_URL=http://"$(hostname)":8080

	playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/set_up.sh ]]
	then
		/bin/bash ${playwright_project_dir}/env/set_up.sh
	else
		default_set_up
	fi
}

main "${@}"