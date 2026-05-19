#!/bin/sh

set -o errexit
set -o nounset

function main {
	local database_snapshot_uuid="{{ "{{" }}inputs.parameters.database-snapshot-uuid}}"
	local restore_instance="{{ "{{" }}inputs.parameters.restore-instance}}"

	gcloud \
		sql \
		backups \
		restore \
		"${database_snapshot_uuid}" \
		--project={{ .Values.global.gcp.projectId }} \
		--quiet \
		--restore-instance "${restore_instance}"

	gcloud \
		sql \
		users \
		set-password \
		postgres \
		--instance "${restore_instance}" \
		--password "${PGPASSWORD}" \
		--project={{ .Values.global.gcp.projectId }} \
		--quiet
}

main