#!/bin/sh

set -o errexit
set -o nounset

function main {
	local backup_name="{{ "{{" }}inputs.parameters.backup-name}}"

	local timeout

	timeout=$(($(date +%s) + {{ .Values.liferayBackup.waitTimeoutSeconds }}))

	while [ $(date +%s) -lt ${timeout} ]
	do
		local succeeded_condition

		succeeded_condition=$( \
			kubectl \
				get \
				liferaybackup \
				"${backup_name}" \
				--output jsonpath="{.status.conditions[?(@.type==\"Succeeded\")]}" 2>/dev/null || echo "{}")

		local status

		status=$(echo "${succeeded_condition}" | jq --raw-output ".status")

		if [ "${status}" = "True" ]
		then
			exit 0
		fi

		if [ "${status}" = "False" ]
		then
			echo "LiferayBackup ${backup_name} failed: $(echo "${succeeded_condition}" | jq --raw-output ".reason")" >&2

			exit 1
		fi

		sleep 30
	done

	echo "The system timed out waiting for LiferayBackup ${backup_name} to succeed." >&2

	exit 1
}

main