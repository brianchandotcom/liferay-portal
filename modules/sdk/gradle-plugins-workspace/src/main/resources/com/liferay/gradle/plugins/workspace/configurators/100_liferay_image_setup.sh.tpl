#!/bin/bash

function copy_configs {
	CONFIGS_DIR="/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}"

	if [ -n "$(ls -A ${CONFIGS_DIR}/* 2> /dev/null)" ]; then
		echo "[LIFERAY] Copying ${CONFIGS_DIR} config files:"
		echo ""

		tree --noreport "${CONFIGS_DIR}"

		echo ""
		echo "[LIFERAY] ... into ${LIFERAY_HOME}."

		cp -R "${CONFIGS_DIR}"/* ${LIFERAY_HOME}

		echo ""
	fi
}

function copy_and_remove_scripts {
	SCRIPTS_DIR="/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts"

	if [ -n "$(ls -A ${SCRIPTS_DIR}/* 2> /dev/null)" ]; then
		echo "[LIFERAY] Copying $SCRIPTS_DIR script files:"
		echo ""

		tree --noreport "$SCRIPTS_DIR"

		echo ""
		echo "[LIFERAY] ... into ${LIFERAY_MOUNT_DIR}/scripts"

		cp -R "$SCRIPTS_DIR"/* ${LIFERAY_MOUNT_DIR}/scripts

		rm -rf "$SCRIPTS_DIR"

		echo ""
	fi
}

function main {
	copy_and_remove_scripts

	copy_configs
}

main