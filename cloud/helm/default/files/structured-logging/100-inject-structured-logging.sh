#!/bin/bash

readonly CONFIGS_DIR="/var/lib/structured-logging/configs"
readonly LOG4J_LAYOUT_TEMPLATE_JSON_SHA256="3bb8b7442ab67b222968add9ef044fbe3cab800a1be9b9b98504bc6923376eb7"
readonly LOG4J_LAYOUT_TEMPLATE_JSON_VERSION="2.17.1"
readonly LOGGING_PROPERTIES="/opt/liferay/structured-logging-runtime/logging.properties"
readonly SHIELDED_CONTAINER_LIB="/opt/liferay/tomcat/webapps/ROOT/WEB-INF/shielded-container-lib"
readonly TOMCAT_CONF="/opt/liferay/tomcat/conf"
readonly WEBAPP_META_INF="/opt/liferay/tomcat/webapps/ROOT/WEB-INF/classes/META-INF"

function main {
	_require_path "${CONFIGS_DIR}/META-INF/cloud-native-layout.json"
	_require_path "${CONFIGS_DIR}/META-INF/portal-log4j-ext.xml"
	_require_path "${SHIELDED_CONTAINER_LIB}"
	_require_path "${TOMCAT_CONF}/logging.properties"
	_require_path "${WEBAPP_META_INF}"

	local work_dir

	work_dir=$(mktemp --directory)

	_log INFO "Writing Tomcat JUL config with org.apache.juli.JsonFormatter to ${LOGGING_PROPERTIES}."

	mkdir --parents "$(dirname "${LOGGING_PROPERTIES}")"

	grep \
		--extended-regexp \
		--invert-match \
		'^[[:space:]]*java\.util\.logging\.ConsoleHandler\.formatter[[:space:]]*=' \
		"${TOMCAT_CONF}/logging.properties" > "${LOGGING_PROPERTIES}"

	echo "java.util.logging.ConsoleHandler.formatter=org.apache.juli.JsonFormatter" >> "${LOGGING_PROPERTIES}"

	_log INFO "Fetching log4j-layout-template-json from Maven Central."

	if _fetch_jar "${work_dir}" "org/apache/logging/log4j" "log4j-layout-template-json" "${LOG4J_LAYOUT_TEMPLATE_JSON_VERSION}" "${LOG4J_LAYOUT_TEMPLATE_JSON_SHA256}"
	then
		_log INFO "Placing log4j2 config under ${WEBAPP_META_INF}."

		cp "${CONFIGS_DIR}/META-INF/cloud-native-layout.json" "${WEBAPP_META_INF}"
		cp "${CONFIGS_DIR}/META-INF/portal-log4j-ext.xml" "${WEBAPP_META_INF}"

		_log INFO "Copying log4j-layout-template-json JAR into ${SHIELDED_CONTAINER_LIB}."

		cp "${work_dir}/log4j-layout-template-json-${LOG4J_LAYOUT_TEMPLATE_JSON_VERSION}.jar" "${SHIELDED_CONTAINER_LIB}"

		_log INFO "Structured logging configuration injected."
	else
		_log WARNING "Skipping log4j2 patch because Maven Central fetch failed."
	fi
}

function _fetch_jar {
	local artifact_id="${3}"
	local group_path="${2}"
	local sha256="${5}"
	local version="${4}"
	local work_dir="${1}"

	local jar_name="${artifact_id}-${version}.jar"
	local jar_path="${work_dir}/${jar_name}"

	if ! curl \
		--connect-timeout 10 \
		--fail \
		--location \
		--max-time 60 \
		--output "${jar_path}" \
		--retry 5 \
		--retry-connrefused \
		--retry-delay 5 \
		--show-error \
		--silent \
		"https://repo1.maven.org/maven2/${group_path}/${artifact_id}/${version}/${jar_name}"
	then
		return 1
	fi

	if ! printf "%s  %s\n" "${sha256}" "${jar_path}" | sha256sum --check > /dev/null 2>&1
	then
		_log ERROR "SHA-256 check failed for ${jar_name}."

		return 1
	fi

	return 0
}

function _log {
	local level="${1}"
	local message="${2}"

	message="${message//\\/\\\\}"
	message="${message//\"/\\\"}"
	message="${message//$'\n'/\\n}"
	message="${message//$'\r'/\\r}"
	message="${message//$'\t'/\\t}"

	printf "{\"message\": \"%s\", \"script\": \"%s\", \"severity\": \"%s\", \"timestamp\": \"%s\"}\n" \
		"${message}" \
		"100-inject-structured-logging.sh" \
		"${level}" \
		"$(date -u +%Y-%m-%dT%H:%M:%SZ)"
}

function _require_path {
	if [ ! -e "${1}" ]
	then
		_log ERROR "Required path \"${1}\" does not exist."

		exit 1
	fi
}

(
	set -o errexit
	set -o nounset
	set -o pipefail

	main "${@}"
) || exit 1

if [ -f "${LOGGING_PROPERTIES}" ]
then
	export LIFERAY_JVM_OPTS="${LIFERAY_JVM_OPTS:+${LIFERAY_JVM_OPTS} }-Djava.util.logging.config.file=${LOGGING_PROPERTIES}"
fi