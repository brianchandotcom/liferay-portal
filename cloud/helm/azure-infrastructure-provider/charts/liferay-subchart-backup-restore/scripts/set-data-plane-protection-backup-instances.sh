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

	_BACKUP_VAULT_NAME="{{ "{{" }}inputs.parameters.backup-vault-name}}"

	_RESOURCE_GROUP_NAME="{{ "{{" }}inputs.parameters.resource-group-name}}"

	local storage_account_id_promoted

	storage_account_id_promoted="{{ "{{" }}inputs.parameters.storage-account-id-promoted}}"

	local promoted_instance_name

	promoted_instance_name=""

	local timeout

	timeout=$(($(date +%s) + {{ .Values.azureBackupService.protectionWaitTimeoutSeconds }}))

	while [[ "$(date +%s)" -lt "${timeout}" ]]
	do
		promoted_instance_name=$( \
			az dataprotection backup-instance list \
				--output tsv \
				--query "[?properties.dataSourceInfo.resourceID=='${storage_account_id_promoted}'].name | [0]" \
				--resource-group "${_RESOURCE_GROUP_NAME}" \
				--vault-name "${_BACKUP_VAULT_NAME}")

		if [ -n "${promoted_instance_name}" ]
		then
			break
		fi

		echo "No backup instance protects the promoted data plane yet."

		sleep 15
	done

	if [ -z "${promoted_instance_name}" ]
	then
		echo "No backup instance was created for the promoted data plane." >&2

		exit 1
	fi

	if ! _wait_for_protection_state "${promoted_instance_name}" ProtectionConfigured
	then
		echo "Backups were not configured on the promoted data plane." >&2

		exit 1
	fi

	echo "Backups are configured on the promoted data plane."
}

function _wait_for_protection_state {
	local state

	local deadline

	deadline=$(($(date +%s) + {{ .Values.azureBackupService.protectionWaitTimeoutSeconds }}))

	while [[ "$(date +%s)" -lt "${deadline}" ]]
	do
		state=$( \
			az dataprotection backup-instance show \
				--name "${1}" \
				--output tsv \
				--query properties.currentProtectionState \
				--resource-group "${_RESOURCE_GROUP_NAME}" \
				--vault-name "${_BACKUP_VAULT_NAME}")

		if [ "${state}" == "${2}" ]
		then
			return 0
		fi

		echo "The backup instance ${1} reports the protection state ${state}."

		sleep 15
	done

	return 1
}

main