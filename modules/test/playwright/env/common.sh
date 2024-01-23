#!/bin/bash

set -e -x

if [[ "${LIFERAY_HOME}" == "" ]]
then
	echo "Please set 'LIFERAY_HOME'"

	exit 1
fi

if [[ "${PLAYWRIGHT_BASE_DIR}" == "" ]]
then
	echo "Please set 'PLAYWRIGHT_BASE_DIR'"

	exit 1
fi

if [[ "${PLAYWRIGHT_PROJECT_DIR}" == "" ]]
then
	echo "Please set 'PLAYWRIGHT_PROJECT_DIR'"

	exit 1
fi

if [[ "${PROJECT_DIR}" == "" ]]
then
	echo "Please set 'PROJECT_DIR'"

	exit 1
fi

function deploy_client_extensions() {
	if [[ -n ${1} ]]
	then
		mkdir -p ${LIFERAY_HOME}/deploy

		cd ${PROJECT_DIR}

		for client_extension_dir in ${@}
		do
			ant -f build-test-playwright.xml deploy-client-extension -Dclient.extension.dir=${client_extension_dir}
		done
	fi
}

function deploy_osgi_modules() {
	if [[ -n ${1} ]]
	then
		mkdir -p ${LIFERAY_HOME}/deploy

		cd ${PROJECT_DIR}

		for osgi_module_dir in ${@}
		do
			ant -f build-test-playwright.xml deploy-osgi-module -Dosgi.module.dir=${osgi_module_dir}
		done
	fi
}

function deploy_project_client_extensions() {
	if [[ -f ${PLAYWRIGHT_PROJECT_DIR}/env/client-extensions.list ]]
	then
		deploy_client_extensions $(cat ${PLAYWRIGHT_PROJECT_DIR}/env/client-extensions.list)
	fi
}

function deploy_project_env_deploy_folder() {
	mkdir -p ${LIFERAY_HOME}/deploy

	if [[ -f ${PLAYWRIGHT_PROJECT_DIR}/env/osgi-modules.list ]]
	then
		cp -r ${PLAYWRIGHT_PROJECT_DIR}/env/deploy/ ${LIFERAY_HOME}/deploy
	fi
}

function deploy_project_osgi_modules() {
	if [[ -f ${PLAYWRIGHT_PROJECT_DIR}/env/osgi-modules.list ]]
	then
		deploy_osgi_modules $(cat ${PLAYWRIGHT_PROJECT_DIR}/env/osgi-modules.list)
	fi
}

function start_app_server() {
	cd ${PROJECT_DIR}

	ant -f build-test.xml start-app-server

	ant -f build-test.xml wait-for-server-startup
}

function stop_app_server() {
	cd ${PROJECT_DIR}

	ant -f build-test.xml stop-app-server

	ant -f build-test.xml wait-for-server-shutdown -Dapp.server.port.number=8080
}

function update_portal_ext_properties() {
	cd ${PROJECT_DIR}

	if [[ -f ${PLAYWRIGHT_BASE_DIR}/env/portal-ext.properties ]]
	then
		ant -f build-test-playwright.xml update-portal-ext-properties -Dupdated.portal.ext.properties=${PLAYWRIGHT_BASE_DIR}/env/portal-ext.properties
	fi

	if [[ -f ${PLAYWRIGHT_PROJECT_DIR}/env/portal-ext.properties ]]
	then
		ant -f build-test-playwright.xml update-portal-ext-properties -Dupdated.portal.ext.properties=${PLAYWRIGHT_PROJECT_DIR}/env/portal-ext.properties
	fi
}