#!/bin/bash

function copy_configs {
	CONFIGS_DIR="/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}"

	if [ -n "$(ls -A ${CONFIGS_DIR}/* 2> /dev/null)" ]; then
		echo "[LIFERAY] Copying ${CONFIGS_DIR} files:"
		echo ""

		tree --noreport "${CONFIGS_DIR}"

		echo ""
		echo "[LIFERAY] ... into ${LIFERAY_HOME}."

		cp -r "${CONFIGS_DIR}"/* ${LIFERAY_HOME}

		echo ""
	fi
}

function copy_and_remove_scripts {
	WORKSPACE_SCRIPTS_DIR="/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts"

	if [ -n "$(ls -A ${WORKSPACE_SCRIPTS_DIR}/* 2> /dev/null)" ]; then
		echo "[LIFERAY] Copying $WORKSPACE_SCRIPTS_DIR files:"
		echo ""

		tree --noreport "$WORKSPACE_SCRIPTS_DIR"

		echo ""
		echo "[LIFERAY] ... into ${LIFERAY_MOUNT_DIR}/scripts"

		cp -r "$WORKSPACE_SCRIPTS_DIR"/* ${LIFERAY_MOUNT_DIR}/scripts

		rm -rf "$WORKSPACE_SCRIPTS_DIR"

		echo ""
	fi
}

function main {
	copy_and_remove_scripts

	copy_configs
}

main