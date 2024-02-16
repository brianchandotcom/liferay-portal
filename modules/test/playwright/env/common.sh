#!/bin/bash

set -e -x

function deploy_client_extensions() {
	if [[ -n ${1} ]]
	then
		mkdir -p ${LIFERAY_HOME}/deploy

		for client_extension_name in ${@}
		do
			local client_extension_dir=$(find ${PORTAL_PROJECT_DIR}/workspaces -type d -name "${client_extension_name}" | grep -v .releng | grep -v .npmscripts)

			if [[ -f ${client_extension_dir}/build.gradle ]]
			then
				echo "Deploy '${client_extension_dir}'"

				cd ${osgi_module_dir}

				local gradlew=$(get_gradlew)

				${gradlew} deploy
			fi
		done
	fi
}

function deploy_osgi_modules() {
	if [[ -n ${1} ]]
	then
		mkdir -p ${LIFERAY_HOME}/deploy

		for osgi_module_name in ${@}
		do
			local osgi_module_dir=$(find ${PORTAL_PROJECT_DIR}/modules -type d -name "${osgi_module_name}" | grep -v .releng | grep -v .npmscripts)

			if [[ -f ${osgi_module_dir}/build.gradle ]]
			then
				echo "Deploy '${osgi_module_dir}'"

				cd ${osgi_module_dir}

				local gradlew=$(get_gradlew)

				${gradlew} deploy
			fi
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

function get_absolute_dir() {
	echo $(cd -- $(dirname -- $1) &> /dev/null && pwd)
}

function get_gradlew() {
	if [[ -e ./gradlew ]]
	then
		echo "$(pwd)/gradlew"
	elif [[ $(pwd) == / ]]
	then
		echo "unable to find gradlew"

		exit 1
	else
		echo $(cd .. ; get_gradlew)
	fi
}

function get_playwright_project_dir() {
	find ${PLAYWRIGHT_BASE_DIR} -name config.ts -type f -print | xargs grep "name: '${PLAYWRIGHT_PROJECT_NAME}'" | sed -n 's/\(.*\)\/config.ts.*/\1/p'
}

function get_tomcat_dir() {
	find ${LIFERAY_HOME} -type d -name "tomcat*"
}

function start_app_server() {
	cd $(get_tomcat_dir)/bin

	/bin/bash catalina.sh run &

	while ! curl --output /dev/null --silent --head --fail ${LIFERAY_PORTAL_URL}
	do
		sleep 5
	done

	echo "${LIFERAY_PORTAL_URL} is now available"
}

function stop_app_server() {
	cd $(get_tomcat_dir)/bin

	/bin/bash shutdown.sh &

	while curl --output /dev/null --silent --head --fail ${LIFERAY_PORTAL_URL}
	do
		sleep 5
	done

	echo "${LIFERAY_PORTAL_URL} is no longer available"
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

if [[ "${LIFERAY_PORTAL_URL}" == "" ]]
then
	echo "Please set 'LIFERAY_PORTAL_URL'"

	exit 1
fi

if [[ "${PLAYWRIGHT_PROJECT_NAME}" == "" ]]
then
	echo "Please set 'PLAYWRIGHT_PROJECT_NAME'"

	exit 1
fi