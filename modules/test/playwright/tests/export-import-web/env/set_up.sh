#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../env/common.sh

function main {
	local liferay_home=${1}

	export TOMCAT_DIR=$(get_tomcat_dir ${liferay_home})

	default_set_up

}

main "${@}"