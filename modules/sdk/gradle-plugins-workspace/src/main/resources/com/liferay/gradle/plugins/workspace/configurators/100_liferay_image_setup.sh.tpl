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
	echo "[LIFERAY] Copying /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts files:"
	echo ""

	if [ -d "/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts)" ] && [ "$(ls -A /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts)" ]; then

	tree --noreport /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts

	fi

	echo ""
	echo "[LIFERAY] ... into ${LIFERAY_MOUNT_DIR}/scripts"

	mkdir -p ${LIFERAY_MOUNT_DIR}/scripts

	if [ -d "/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts)" ] && [ "$(ls -A /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts)" ]; then

	cp -r /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts/* ${LIFERAY_MOUNT_DIR}/scripts

	fi

	if [ -d "/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts)" ]; then

	rm -rf /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts

	fi

	echo ""
}

function main {
	copy_and_remove_scripts

	copy_configs
}

main