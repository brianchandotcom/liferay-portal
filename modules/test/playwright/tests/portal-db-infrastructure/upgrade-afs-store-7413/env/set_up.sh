#!/bin/bash

CURRENT_DIR_NAME=$(dirname "${BASH_SOURCE[0]}")

echo "CURRENT_DIR_NAME=${CURRENT_DIR_NAME}"

source "${CURRENT_DIR_NAME}/../../../../env/common.sh"

DATA_ARCHIVE_TYPE="data-archive-afs-store"
PORTAL_VERSION="7.4.13"

function main {
	set -ex

	update_portal_ext_properties

	deploy_project_osgi_configs

	upgrade_legacy_database_set_up "${DATA_ARCHIVE_TYPE}" "${PORTAL_VERSION}"

	assert_advanced_file_system_store_root_dir
}

main "${@}"