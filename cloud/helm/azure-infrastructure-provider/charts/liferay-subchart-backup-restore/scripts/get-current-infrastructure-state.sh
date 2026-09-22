#!/bin/sh

set -o errexit
set -o nounset

function main {
	local liferay_infrastructure_json

	liferay_infrastructure_json=$( \
		kubectl get liferayinfrastructure \
			--output json \
			| jq ".items[0]")

	local restore_phase

	restore_phase=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.restorePhase")

	if [ "${restore_phase}" == "promoting" ] || [ "${restore_phase}" == "provisioning" ]
	then
		echo "The LiferayInfrastructure spec.restorePhase is set to ${restore_phase}. A restore is in progress." >&2

		exit 1
	fi

	echo "${liferay_infrastructure_json}" | jq --raw-output ".metadata.name" > /tmp/liferay-infrastructure-name.txt

	local data_plane_active

	data_plane_active=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.targetActiveDataPlane // \"blue\"")

	echo "${data_plane_active}" > /tmp/data-plane-active.txt

	if [ "${data_plane_active}" == "blue" ]
	then
		echo "green" > /tmp/data-plane-inactive.txt
	else
		echo "blue" > /tmp/data-plane-inactive.txt
	fi

	local restore_generation

	restore_generation=$( \
		echo "{{ "{{" }}workflow.uid}}" \
			| jq --raw-input --raw-output ".[0:6]")

	if echo "${liferay_infrastructure_json}" \
		| jq \
			--arg data_plane_key "$(cat /tmp/data-plane-inactive.txt)-${restore_generation}" \
			--exit-status \
			'.spec.retainedDataPlanes // {} | has($data_plane_key)' > /dev/null
	then
		echo "The generation ${restore_generation} is already retained on the $(cat /tmp/data-plane-inactive.txt) data plane. Retry the restore to draw another one." >&2

		exit 1
	fi

	echo "${restore_generation}" > /tmp/restore-generation.txt

	kubectl get backupvaults.dataprotection.azure.m.upbound.io \
		--output jsonpath="{.items[0].metadata.name}" \
		> /tmp/backup-vault-name.txt

	local data_plane_key_active

	data_plane_key_active=$( \
		kubectl get flexibleservers.dbforpostgresql.azure.m.upbound.io \
			--output jsonpath="{.items[0].metadata.labels.dataPlaneKey}" \
			--selector "dataPlane=${data_plane_active}")

	if [ -z "${data_plane_key_active}" ]
	then
		data_plane_key_active=${data_plane_active}
	fi

	kubectl get flexibleservers.dbforpostgresql.azure.m.upbound.io \
		--output json \
		| jq \
			--arg data_plane_active "${data_plane_active}" \
			--arg data_plane_key_active "${data_plane_key_active}" \
			--argjson retained_data_planes "$(echo "${liferay_infrastructure_json}" | jq --compact-output ".spec.retainedDataPlanes // {}")" \
			--compact-output \
			'[.items[] | if .metadata.labels.dataPlane == $data_plane_active then {key: $data_plane_key_active, server: .metadata.name} elif ((.metadata.labels.retainedDataPlane // "") != "") and ((($retained_data_planes[.metadata.labels.retainedDataPlane] // "1970-01-01T00:00:00Z") | fromdateiso8601) > (now + 3600)) then {key: .metadata.labels.retainedDataPlane, server: .metadata.annotations["crossplane.io/external-name"]} else empty end]' \
		> /tmp/database-candidates.txt

	kubectl get flexibleservers.dbforpostgresql.azure.m.upbound.io \
		--output jsonpath="{.items[0].spec.forProvider.resourceGroupName}" \
		--selector "dataPlane=${data_plane_active}" \
		> /tmp/resource-group-name.txt

	kubectl get statefulset \
		--output jsonpath="{.items[0].metadata.name}" \
		--selector "component=liferay" \
		> /tmp/liferay-workload-name.txt

	kubectl get accounts.storage.azure.m.upbound.io \
		--output jsonpath="{.items[0].status.atProvider.id}" \
		--selector "dataPlane=${data_plane_active}" \
		> /tmp/storage-account-id-active.txt
}

main