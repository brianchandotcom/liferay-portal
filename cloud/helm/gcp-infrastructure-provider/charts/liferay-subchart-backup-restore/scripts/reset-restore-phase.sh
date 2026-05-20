#!/bin/sh

set -o errexit
set -o nounset

function main {
	local liferay_infrastructure_name

	liferay_infrastructure_name=$( \
		kubectl \
			get \
			liferayinfrastructure \
			--output jsonpath="{.items[0].metadata.name}" 2>/dev/null || echo "")

	if [ -z "${liferay_infrastructure_name}" ]
	then
		echo "No LiferayInfrastructure found in the workflow namespace. Skipping restore phase reset."

		exit 0
	fi

	kubectl \
		patch \
		liferayinfrastructure \
		"${liferay_infrastructure_name}" \
		--patch '{"spec":{"restorePhase":"none"}}' \
		--type merge
}

main