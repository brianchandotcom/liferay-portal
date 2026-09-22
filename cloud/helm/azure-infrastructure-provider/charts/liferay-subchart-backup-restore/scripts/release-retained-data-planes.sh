#!/bin/sh

set -o errexit
set -o nounset

function main {
	local liferay_infrastructure_json

	liferay_infrastructure_json=$( \
		kubectl get liferayinfrastructure \
			--output json \
			| jq ".items[0]")

	if [ "${liferay_infrastructure_json}" == "null" ]
	then
		echo "No LiferayInfrastructure was found in the workflow namespace, so there is nothing to release."

		exit 0
	fi

	local restore_phase

	restore_phase=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.restorePhase // \"none\"")

	if [ "${restore_phase}" != "none" ]
	then
		echo "The LiferayInfrastructure spec.restorePhase is set to ${restore_phase}. A restore is in progress, so no retained resource was released."

		exit 0
	fi

	local expired_data_planes

	expired_data_planes=$( \
		echo "${liferay_infrastructure_json}" \
			| jq --compact-output "(.spec.retainedDataPlanes // {}) | with_entries(select(.value | fromdateiso8601 < now)) | with_entries(.value = null)")

	if [ "${expired_data_planes}" == "{}" ]
	then
		echo "No retained data plane has passed its deadline."

		exit 0
	fi

	kubectl patch liferayinfrastructure \
		"$(echo "${liferay_infrastructure_json}" | jq --raw-output ".metadata.name")" \
		--field-manager=liferay-backup-restore \
		--patch "{\"spec\":{\"retainedDataPlanes\":${expired_data_planes}}}" \
		--type merge

	echo "The retained data planes $(echo "${expired_data_planes}" | jq --raw-output "keys | join(\", \")") were released."
}

main