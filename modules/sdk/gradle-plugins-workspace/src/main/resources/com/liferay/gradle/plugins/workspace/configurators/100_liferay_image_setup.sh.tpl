#!/bin/bash

function copy_configs {
	echo "[LIFERAY] Copying /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT} files:"
	echo ""

	tree --noreport /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}

	echo ""
	echo "[LIFERAY] ... into ${LIFERAY_HOME}."

	cp -r /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/* ${LIFERAY_HOME}

	echo ""
}

function copy_and_remove_scripts {
	workspace_scripts=false

	WORKSPACE_SCRIPTS_DIR="/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts"

	if [ -d "$WORKSPACE_SCRIPTS_DIR" ] && [ "$(ls -A $WORKSPACE_SCRIPTS_DIR)" ]; then
		workspace_scripts=true

		echo "[LIFERAY] Copying $WORKSPACE_SCRIPTS_DIR files:"
		echo ""

		tree --noreport "$WORKSPACE_SCRIPTS_DIR"
	fi

	mkdir -p ${LIFERAY_MOUNT_DIR}/scripts

	if [ "$workspace_scripts" == true ]; then
		echo ""
		echo "[LIFERAY] ... into ${LIFERAY_MOUNT_DIR}/scripts"

		cp -r "$WORKSPACE_SCRIPTS_DIR"/* ${LIFERAY_MOUNT_DIR}/scripts

		rm -rf "$WORKSPACE_SCRIPTS_DIR"
	fi

	echo ""
}

function main {
	copy_and_remove_scripts

	copy_configs
}

main