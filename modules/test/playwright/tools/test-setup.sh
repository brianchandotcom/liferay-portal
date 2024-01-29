#!/bin/bash

function copy_portal_ext_properties() {
	for file in \
		"${PROJECT_DIR}/modules/test/playwright/tests/${TEST_PROJECT}/env/portal-ext.properties" \
		"${PROJECT_DIR}/modules/test/playwright/env/portal-ext.properties"
	do
		if [ -f "${file}" ]
		then
			echo "  ===> ${file}"

			cp "${file}" "${BUNDLES_DIR}"

			return
		fi
	done
}

function deploy_client_extensions() {
	local list_file="$1"

	if [ ! -f "${list_file}" ]
	then
		return
	fi

	local cwd=$(pwd)

	while IFS="" read -r module || [ -n "$p" ]
	do
		echo
		echo -n "  ===> ${module}"

		cd "${PROJECT_DIR}/workspaces/liferay-sample-workspace/client-extensions/${module}" >/dev/null 2>&1

		output=$("${PROJECT_DIR}/workspaces/liferay-sample-workspace/gradlew" deploy 2>&1)

		if [ ! $? ]
		then
			echo " ❌"
			echo "${ouptut}"
			echo

			exit 1
		else
			echo " ✅"
		fi
	done <"${list_file}"

	cd "${cwd}"
}

function deploy_env_deploy() {
	local folder="$1"

	if [ ! -d "${folder}" ]
	then
		return
	fi

	mkdir -p "${BUNDLES_DIR}/deploy"

	local cwd=$(pwd)

	cd "${folder}"

	for file in *
	do
		echo "===> ${file}"

		cp "${file}" "${BUNDLES_DIR}/deploy"
	done

	cd "${cwd}"
}

function deploy_osgi_modules() {
	local list_file="$1"

	if [ ! -f "${list_file}" ]
	then
		return
	fi

	local cwd=$(pwd)

	while IFS="" read -r module || [ -n "$module" ]
	do
		echo
		echo -n "  ===> ${module}"

		cd "${PROJECT_DIR}/${module}" >/dev/null 2>&1

		output=$("${PROJECT_DIR}/gradlew" deploy 2>&1)

		if [ ! $? ]
		then
			echo " ❌"
			echo "${ouptut}"
			echo

			exit 1
		else
			echo " ✅"
		fi
	done < "${list_file}"

	cd "${cwd}"
}

function get_abs_path() {
	echo "$( cd -- "$1" >/dev/null 2>&1 ; pwd -P )"
}

PROJECT_DIR="$(get_abs_path "$(dirname "$0")/../../../..")"

BUNDLES_DIR="$(get_abs_path "${PROJECT_DIR}/../bundles")"

TOMCAT_VERSION=$(grep -F 'app.server.tomcat.version=' "${PROJECT_DIR}/app.server.properties" | cut -d '=' -f 2)
TOMCAT_DIR="$(get_abs_path "${BUNDLES_DIR}/tomcat-${TOMCAT_VERSION}")"

TEST_PROJECT="$1"

if [ -z "${TEST_PROJECT}" ]
then
	echo "Please specify the name of the test project to setup."

	exit 1
fi

if [ ! -f "${PROJECT_DIR}/modules/test/playwright/tests/${TEST_PROJECT}/config.ts" ]
then
	echo "Playwright test project '$1' does not exist."

	exit 1
fi

echo ==========================================================================
echo PROJECT_DIR=${PROJECT_DIR}
echo BUNDLES_DIR=${BUNDLES_DIR}
echo TOMCAT_DIR=${TOMCAT_DIR}
echo TEST_PROJECT=${TEST_PROJECT}
echo ==========================================================================
echo

# Backup portal-ext.properties if necessary
if [ -f "${BUNDLES_DIR}/portal-ext.properties" ]
then
	sha1="$(sha1sum "${BUNDLES_DIR}/portal-ext.properties" | cut -d ' ' -f 1)"
	backup_file="${BUNDLES_DIR}/portal-ext.properties.${sha1}"

	echo Making a backup copy of portal-ext.properties
	echo "  ===> backed up to $(basename ${backup_file})"
	echo

	cp "${BUNDLES_DIR}/portal-ext.properties" "${backup_file}"
fi

echo Copying test portal-ext.properties
copy_portal_ext_properties
echo

echo Deploying deploy folder:
deploy_env_deploy "${PROJECT_DIR}/modules/test/playwright/env/deploy"
deploy_env_deploy "${PROJECT_DIR}/modules/test/playwright/${TEST_PROJECT}/env/deploy"
echo

echo Deploying OSGi modules:
deploy_osgi_modules "${PROJECT_DIR}/modules/test/playwright/env/osgi-modules.list"
deploy_osgi_modules "${PROJECT_DIR}/modules/test/playwright/${TEST_PROJECT}/env/osgi-modules.list"
echo

echo Deploying client extensions:
deploy_client_extensions "${PROJECT_DIR}/modules/test/playwright/env/client-extensions.list"
deploy_client_extensions "${PROJECT_DIR}/modules/test/playwright/${TEST_PROJECT}/env/client-extensions.list"
echo

echo ==========================================================================
echo Local Tomcat has been set up as in CI for playwright project ${TEST_PROJECT}
echo
echo Now you can start Tomcat and launch npm test ${TEST_PROJECT} as many times
echo as needed to run your tests.
echo
echo Enjoy!
echo ==========================================================================
echo