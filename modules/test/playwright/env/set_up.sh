#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

source ${CURRENT_DIR_NAME}/common.sh

playwright_project_dir=$(get_playwright_project_dir)

if [[ -f ${playwright_project_dir}/env/set_up.sh ]]
then
	/bin/bash ${playwright_project_dir}/env/set_up.sh
else
	update_portal_ext_properties

	start_app_server

	deploy_parent_project_osgi_modules

	deploy_project_osgi_modules

	deploy_parent_project_deploy_folder

	deploy_project_deploy_folder

	deploy_parent_project_client_extensions

	deploy_project_client_extensions
fi