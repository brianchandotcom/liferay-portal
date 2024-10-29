#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../env/common.sh

function main {
	combine_properties_files \
		$(get_tomcat_portal_ext_properties_file) \
		\
		$(get_portal_project_dir)/workspaces/liferay-sample-workspace/configs/local/portal-ext.properties

	default_set_up
}

main "${@}"