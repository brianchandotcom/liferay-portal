#!/bin/sh

set -o errexit
set -o nounset

function main {
	local liferay_infrastructure_json

	liferay_infrastructure_json=$( \
		kubectl \
			get \
			liferayinfrastructure \
			--output json \
			| jq ".items[0]")

	local restore_phase

	restore_phase=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.restorePhase")

	if [ "${restore_phase}" = "promoting" ] || [ "${restore_phase}" = "provisioning" ]
	then
		echo "The LiferayInfrastructure spec.restorePhase is set to ${restore_phase}. A restore is in progress." >&2

		exit 1
	fi

	local backup_service_role_arn

	backup_service_role_arn=$( \
		kubectl \
			get \
			roles.iam.aws.m.upbound.io \
			--output jsonpath="{.items[0].status.atProvider.arn}" \
			--selector "component=backup-service-role")

	echo "${backup_service_role_arn}" > /tmp/backup-service-role-arn.txt

	local backup_vault_name

	backup_vault_name=$( \
		kubectl \
			get \
			vaults.backup.aws.m.upbound.io \
			--output jsonpath="{.items[0].metadata.name}" \
			--selector "component=backup-vault")

	echo "${backup_vault_name}" > /tmp/backup-vault-name.txt

	local data_plane_active

	data_plane_active=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.targetActiveDataPlane // \"blue\"")

	echo "${data_plane_active}" > /tmp/data-plane-active.txt

	local liferay_infrastructure_name

	liferay_infrastructure_name=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".metadata.name")

	echo "${liferay_infrastructure_name}" > /tmp/liferay-infrastructure-name.txt

	local liferay_workload_name

	liferay_workload_name=$( \
		kubectl \
			get \
			statefulset \
			--output jsonpath="{.items[0].metadata.name}" \
			--selector "component=liferay")

	echo "${liferay_workload_name}" > /tmp/liferay-workload-name.txt

	kubectl \
		get \
		buckets.s3.aws.m.upbound.io \
		--output jsonpath="{.items[0].metadata.name}" \
		--selector "dataPlane=${data_plane_active}" \
		> /tmp/s3-bucket-id-active.txt

	local data_plane_inactive

	if [ "${data_plane_active}" = "blue" ]
	then
		data_plane_inactive="green"
	else
		data_plane_inactive="blue"
	fi

	echo "${data_plane_inactive}" > /tmp/data-plane-inactive.txt

	kubectl \
		get \
		buckets.s3.aws.m.upbound.io \
		--output jsonpath="{.items[0].metadata.name}" \
		--selector "dataPlane=${data_plane_inactive}" \
		> /tmp/s3-bucket-id-inactive.txt
}

main