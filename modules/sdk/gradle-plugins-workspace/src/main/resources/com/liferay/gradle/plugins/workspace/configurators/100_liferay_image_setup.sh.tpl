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

	tree --noreport /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts

	echo ""
	echo "[LIFERAY] ... into /usr/local/liferay/scripts/pre-startup"

	mkdir /usr/local/liferay/scripts/pre-startup

	cp -r /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts/* /usr/local/liferay/scripts/pre-startup

	rm -rf /home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts

	echo ""
}

function main {
	copy_and_remove_scripts

	copy_configs
}

main