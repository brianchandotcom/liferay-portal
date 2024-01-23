#!/bin/bash

set -e -x

source ${PLAYWRIGHT_BASE_DIR}/env/common.sh

update_portal_ext_properties

start_app_server

deploy_project_osgi_modules

deploy_project_env_deploy_folder

deploy_project_client_extensions