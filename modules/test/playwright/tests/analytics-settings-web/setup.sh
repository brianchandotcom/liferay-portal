#!/bin/bash

set -e -x

source ${PLAYWRIGHT_BASE_DIR}/env/common.sh

update_portal_ext_properties

start_app_server

start_ac