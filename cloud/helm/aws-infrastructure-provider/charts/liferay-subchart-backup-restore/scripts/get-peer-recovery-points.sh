#!/bin/sh

set -eu

function get_recovery_point_arn_by_type {
	local recovery_points_json="${2}"
	local resource_type="${1}"

	local filtered_recovery_points_json=$( \
		echo \
			"${recovery_points_json}" \
			| jq --arg resource_type "${resource_type}" "[.[] | select(.ResourceType == \$resource_type)]")

	local filtered_recovery_points_lengths=$(echo "${filtered_recovery_points_json}" | jq "length")

	if [ "${filtered_recovery_points_length}" -ne 1 ]
	then
		echo "A single recovery point of type \"${resource_type}\" was expected, but ${filtered_recovery_points_length} were found." >&2

		return 1
	fi

	echo "${filtered_recovery_points_json}" | jq --raw-output ".[0].RecoveryPointArn"
}

function main {
	local recovery_point_details

	recovery_point_details=$( \
		aws \
			backup \
			describe-recovery-point \
			--backup-vault-name "{{ "{{" }}inputs.parameters.backup-vault-name}}" \
			--recovery-point-arn "{{ "{{" }}workflow.parameters.recovery-point-arn}}")

	local creation_date=$(echo "${recovery_point_details}" | jq --raw-output ".CreationDate")

	if [ -z "${creation_date}" ] || [ "${creation_date}" = "null" ]
	then
		echo "The provided recovery point ARN has no creation date." >&2

		return 1
	fi

	local creation_date_timestamp=$(date --date "${creation_date}" +%s)

	local by_created_after=$(date --date @$((creation_date_timestamp - 1)) --iso-8601=seconds)
	local by_created_before=$(date --date @$((creation_date_timestamp + 1)) --iso-8601=seconds)

	local peer_recovery_points=$( \
		aws \
			backup \
			list-recovery-points-by-backup-vault \
			--backup-vault-name "{{ "{{" }}inputs.parameters.backup-vault-name}}" \
			--by-created-after "${by_created_after}" \
			--by-created-before "${by_created_before}" \
			| jq --arg creation_date "${creation_date}" "[.RecoveryPoints[] | select(.CreationDate == \$creation_date)]")

	local rds_recovery_point_arn=$(get_recovery_point_arn_by_type "RDS" "${peer_recovery_points}")

	local rds_snapshot_id=$( \
		echo \
			"${rds_recovery_point_arn}" \
			| awk --field-separator "snapshot:" "{print \$2}")

	if [ -z "${rds_snapshot_id}" ]
	then
		echo "The RDS snapshot ID could not be parsed from ${rds_recovery_point_arn}." >&2

		exit 1
	fi

	echo "${rds_snapshot_id}" > /tmp/rds-snapshot-id.txt

	local s3_recovery_point_arn=$(get_recovery_point_arn_by_type "S3" "${peer_recovery_points}")

	echo "${s3_recovery_point_arn}" > /tmp/s3-recovery-point-arn.txt
}

main