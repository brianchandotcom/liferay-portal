#!/bin/sh

set -o errexit
set -o nounset

function main {
	local liferay_infrastructure_name

	liferay_infrastructure_name=$( \
		kubectl \
			get \
			liferayinfrastructures.aws.liferay.com \
			--output jsonpath="{.items[0].metadata.name}" 2>/dev/null || echo "")

	if [ -z "${liferay_infrastructure_name}" ]
	then
		echo "No LiferayInfrastructure found in the workflow namespace. Skipping restore phase reset."

		exit 0
	fi

	kubectl \
		patch \
		liferayinfrastructures.aws.liferay.com \
		"${liferay_infrastructure_name}" \
		--field-manager=liferay-backup-restore \
		--patch '{"spec":{"restorePhase":"none"}}' \
		--type merge
}

main