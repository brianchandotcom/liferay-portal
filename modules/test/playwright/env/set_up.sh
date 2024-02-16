#!/bin/bash

set -e -x

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

source ${CURRENT_DIR_NAME}/common.sh

update_portal_ext_properties

start_app_server

deploy_project_osgi_modules

deploy_project_env_deploy_folder

deploy_project_client_extensions