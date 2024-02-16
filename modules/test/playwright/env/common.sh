#!/bin/bash

set -e -x

function deploy_client_extensions() {
	if [[ -n ${1} ]]
	then
		mkdir -p ${LIFERAY_HOME}/deploy

		cd ${PORTAL_PROJECT_DIR}

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

		cd ${PORTAL_PROJECT_DIR}

		for osgi_module_dir in ${@}
		do
			ant -f build-test-playwright.xml deploy-osgi-module -Dosgi.module.dir=${osgi_module_dir}
		done
	fi
}

function deploy_project_client_extensions() {
	local playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/client-extensions.list ]]
	then
		deploy_client_extensions $(cat ${playwright_project_dir}/env/client-extensions.list)
	fi
}

function deploy_project_env_deploy_folder() {
	mkdir -p ${LIFERAY_HOME}/deploy

	local playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/osgi-modules.list ]]
	then
		cp -r ${playwright_project_dir}/env/deploy/ ${LIFERAY_HOME}/deploy
	fi
}

function deploy_project_osgi_modules() {
	local playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/osgi-modules.list ]]
	then
		deploy_osgi_modules $(cat ${playwright_project_dir}/env/osgi-modules.list)
	fi
}

function get_playwright_project_dir() {
	find ${PLAYWRIGHT_BASE_DIR} -name config.ts -type f -print | xargs grep "name: '${PLAYWRIGHT_PROJECT_NAME}'" | sed -n 's/\(.*\)\/config.ts.*/\1/p'
}

function start_app_server() {
	cd ${PORTAL_PROJECT_DIR}

	ant -f build-test.xml start-app-server

	ant -f build-test.xml wait-for-server-startup
}

function stop_app_server() {
	cd ${PORTAL_PROJECT_DIR}

	ant -f build-test.xml stop-app-server

	ant -f build-test.xml wait-for-server-shutdown -Dapp.server.port.number=8080
}

function update_portal_ext_properties() {
	cd ${PORTAL_PROJECT_DIR}

	if [[ -f ${PLAYWRIGHT_BASE_DIR}/env/portal-ext.properties ]]
	then
		ant -f build-test-playwright.xml update-portal-ext-properties -Dupdated.portal.ext.properties=${PLAYWRIGHT_BASE_DIR}/env/portal-ext.properties
	fi

	local playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/portal-ext.properties ]]
	then
		ant -f build-test-playwright.xml update-portal-ext-properties -Dupdated.portal.ext.properties=${playwright_project_dir}/env/portal-ext.properties
	fi
}


PLAYWRIGHT_ENV_DIR=$(dirname ${BASH_SOURCE[0]})

export PLAYWRIGHT_BASE_DIR=$(get_absolute_dir ${PLAYWRIGHT_ENV_DIR}/../..)
export PORTAL_PROJECT_DIR=$(get_absolute_dir ${PLAYWRIGHT_ENV_DIR}/../../../../..)

if [[ "${LIFERAY_HOME}" == "" ]]
then
	echo "Please set 'LIFERAY_HOME'"

	exit 1
fi

if [[ "${PLAYWRIGHT_PROJECT_NAME}" == "" ]]
then
	echo "Please set 'PLAYWRIGHT_PROJECT_NAME'"

	exit 1
fi