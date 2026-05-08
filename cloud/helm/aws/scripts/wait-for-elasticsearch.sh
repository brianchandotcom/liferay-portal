#!/bin/sh

set -o errexit
set -o nounset

main() {
	_log_json "Waiting for Elasticsearch health to be \"green\" or \"yellow\" at \"${ELASTICSEARCH_URL}/_cluster/health\"."

	_protocol="http"

	case "${ELASTICSEARCH_URL}" in
		https://*) _protocol="https" ;;
	esac

	_authenticated_health_url="${_protocol}://${ELASTICSEARCH_USERNAME}:${ELASTICSEARCH_PASSWORD}@${ELASTICSEARCH_URL#*//}/_cluster/health"

	_wget_args=""

	if [ "${_protocol}" = "https" ] && [ "${ELASTICSEARCH_TLS_INSECURE:-false}" = "true" ]
	then
		_wget_args="--no-check-certificate"
	fi

	until wget ${_wget_args} -qO- "${_authenticated_health_url}" | grep -qE "\"status\":\"(green|yellow)\""
	do
		_log_json "Waiting for Elasticsearch (current status: \"red\" or \"unreachable\")."

		sleep 5
	done

	_log_json "Elasticsearch is ready."
}

_log_json() {
	_escaped_message=$(echo "${1}" | sed 's/"/\\"/g')

	_script_name=$(basename "${0}")

	_severity="${2:-INFO}"

	_timestamp=$(date -u +"%Y-%m-%dT%H:%M:%SZ")

	printf '{"message": "%s", "script": "%s", "severity": "%s", "timestamp": "%s"}\n' "${_escaped_message}" "${_script_name}" "${_severity}" "${_timestamp}"
}

main