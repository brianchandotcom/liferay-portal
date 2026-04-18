#!/bin/sh

set -o errexit
set -o nounset

function main {
	_log_json "Waiting for Elasticsearch health to be \"green\" or \"yellow\" at \"${ELASTICSEARCH_URL}/_cluster/health\"."

	local authenticated_health_url="http://${ELASTICSEARCH_USERNAME}:${ELASTICSEARCH_PASSWORD}@${ELASTICSEARCH_URL#*//}/_cluster/health"

	until wget -qO- "${authenticated_health_url}" | grep -qE "\"status\":\"(green|yellow)\""
	do
		_log_json "Waiting for Elasticsearch (current status: \"red\" or \"unreachable\")."

		sleep 5
	done

	_log_json "Elasticsearch is ready."
}

function _log_json {
	local escaped_message

	escaped_message=$(echo "${1}" | sed 's/"/\\"/g')

	local script_name

	script_name=$(basename "${0}")

	local severity="${2:-INFO}"

	local timestamp

	timestamp=$(date -u +"%Y-%m-%dT%H:%M:%SZ")

	printf '{"message": "%s", "script": "%s", "severity": "%s", "timestamp": "%s"}\n' "${escaped_message}" "${script_name}" "${severity}" "${timestamp}"
}

main