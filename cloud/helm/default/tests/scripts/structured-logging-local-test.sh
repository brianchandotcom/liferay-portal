#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

CHART_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)

readonly CHART_DIR

readonly CONFIGS_DIR="${CHART_DIR}/files/structured-logging"
readonly SETTLE_SECONDS=30
readonly THRESHOLD_PERCENT=50

if [ -z "${1:-}" ]
then
	readonly WAIT_SECONDS=90
else
	readonly WAIT_SECONDS="${1}"
fi

CONTAINER=""

function main {
	_require_cmd docker jq

	CONTAINER="liferay-structured-logging-test-${$}"

	trap _clean_up EXIT

	echo "Starting Liferay (${CONTAINER})."

	docker run \
		--detach \
		--env LIFERAY_TOMCAT_AJP_PORT= \
		--env LIFERAY_TOMCAT_JVM_ROUTE= \
		--name "${CONTAINER}" \
		--volume "${CONFIGS_DIR}/100-inject-structured-logging.sh:/etc/liferay/mount/scripts/100-inject-structured-logging.sh:ro" \
		--volume "${CONFIGS_DIR}/cloud-native-layout.json:/var/lib/structured-logging/configs/META-INF/cloud-native-layout.json:ro" \
		--volume "${CONFIGS_DIR}/portal-log4j-ext.xml:/var/lib/structured-logging/configs/META-INF/portal-log4j-ext.xml:ro" \
		liferay/dxp:latest > /dev/null

	echo "Waiting up to ${WAIT_SECONDS}s for log output."

	local elapsed=0

	while [ "${elapsed}" -lt "${WAIT_SECONDS}" ]
	do
		if docker logs "${CONTAINER}" 2>&1 | grep --quiet "Starting Liferay DXP"
		then
			echo "Liferay started; letting logs settle for ${SETTLE_SECONDS}s."

			sleep "${SETTLE_SECONDS}"

			break
		fi

		sleep 5

		elapsed=$((elapsed + 5))
	done

	local logs

	logs=$(docker logs "${CONTAINER}" 2>&1)

	local jvm_logs

	jvm_logs=$(awk -v skip=1 '
		/Starting Liferay DXP\. To stop/ {
			skip = 0

			next
		}

		skip {
			next
		}

		{
			print
		}
	' <<< "${logs}")

	local non_empty

	non_empty=$(grep --count --invert-match '^$' <<< "${jvm_logs}" || true)

	if [ "${non_empty}" -eq 0 ]
	then
		echo ""
		echo "FAIL: container emitted no JVM log output."
		echo ""
		echo "Last 30 log lines for context:"

		docker logs --tail 30 "${CONTAINER}" 2>&1 || true

		exit 1
	fi

	local json_valid

	json_valid=$(jq --raw-input '
		fromjson?
		| select((.level? or .severity?) and (.time? or .timestamp?))
	' 2> /dev/null <<< "${jvm_logs}" | jq --slurp 'length')

	local percent=$((json_valid * 100 / non_empty))

	echo "JVM-emitted non-empty log lines:              ${non_empty}"
	echo "Lines with valid JSON + severity + timestamp: ${json_valid}"
	echo "Structured logging ratio:                     ${percent}%"

	if [ "${percent}" -lt "${THRESHOLD_PERCENT}" ]
	then
		echo ""
		echo "FAIL: ratio ${percent}% < threshold ${THRESHOLD_PERCENT}%."
		echo ""
		echo "Last 30 log lines for context:"

		docker logs --tail 30 "${CONTAINER}" 2>&1 || true

		exit 1
	fi

	echo "PASS: structured logging is emitting JSON above the ${THRESHOLD_PERCENT}% threshold."
}

function _clean_up {
	if [ -n "${CONTAINER}" ]
	then
		docker rm --force "${CONTAINER}" > /dev/null 2>&1 || true
	fi
}

function _require_cmd {
	local cmd

	for cmd in "${@}"
	do
		if ! command -v "${cmd}" > /dev/null
		then
			echo "ERROR: required command \"${cmd}\" not found in PATH."

			exit 1
		fi
	done
}

main "${@}"