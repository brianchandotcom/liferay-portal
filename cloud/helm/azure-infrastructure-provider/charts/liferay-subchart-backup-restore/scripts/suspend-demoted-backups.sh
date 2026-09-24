#!/bin/sh

set -o errexit
set -o nounset

function main {
	az extension add \
		--name dataprotection \
		--version {{ .Values.images.azureCli.dataprotectionExtensionVersion }} \
		--yes > /dev/null

	az login \
		--federated-token "$(cat "${AZURE_FEDERATED_TOKEN_FILE}")" \
		--service-principal \
		--tenant "${AZURE_TENANT_ID}" \
		--username "${AZURE_CLIENT_ID}" > /dev/null

	local backup_instance_name

	backup_instance_name="{{ "{{" }}inputs.parameters.backup-instance-name}}"

	if [ -z "${backup_instance_name}" ]
	then
		echo "No backup instance protected the demoted data plane, so no backups were suspended."

		exit 0
	fi

	local backup_vault_name

	backup_vault_name="{{ "{{" }}inputs.parameters.backup-vault-name}}"

	local resource_group_name

	resource_group_name="{{ "{{" }}inputs.parameters.resource-group-name}}"

	local state

	state=$( \
		az dataprotection backup-instance show \
			--name "${backup_instance_name}" \
			--output tsv \
			--query properties.currentProtectionState \
			--resource-group "${resource_group_name}" \
			--vault-name "${backup_vault_name}")

	if [ "${state}" != "BackupsSuspended" ]
	then
		az dataprotection backup-instance suspend-backup \
			--name "${backup_instance_name}" \
			--no-wait \
			--output none \
			--resource-group "${resource_group_name}" \
			--vault-name "${backup_vault_name}"
	fi

	local timeout

	timeout=$(($(date +%s) + {{ .Values.azureBackupService.protectionWaitTimeoutSeconds }}))

	while [[ "$(date +%s)" -lt "${timeout}" ]]
	do
		state=$( \
			az dataprotection backup-instance show \
				--name "${backup_instance_name}" \
				--output tsv \
				--query properties.currentProtectionState \
				--resource-group "${resource_group_name}" \
				--vault-name "${backup_vault_name}")

		if [ "${state}" == "BackupsSuspended" ]
		then
			echo "Backups were suspended on the demoted backup instance ${backup_instance_name}, which keeps its recovery points."

			exit 0
		fi

		echo "The backup instance ${backup_instance_name} reports the protection state ${state}."

		sleep 15
	done

	echo "Backups were not suspended on the demoted backup instance ${backup_instance_name} before the timeout." >&2

	exit 1
}

main