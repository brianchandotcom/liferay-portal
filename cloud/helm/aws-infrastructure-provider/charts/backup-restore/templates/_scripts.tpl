{{- define "liferayAWSBackupRestore.script.getCurrentInfrastructureState" -}}
#!/bin/sh

set -eu

function main {
	local liferay_infrastructure_json

	liferay_infrastructure_json=$( \
		kubectl \
			get \
			liferayinfrastructure \
			"{{ include "liferayAWSBackupRestore.liferayInfrastructureName" . }}" \
			--output json)

	local restore_phase=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.restorePhase")

	if [ ${restore_phase} = "promoting" ] || [ ${restore_phase} = "provisioning" ]
	then
		echo "LiferayInfrastructure spec.restorePhase is set to ${restore_phase}. A restore is in progress." >&2

		exit 1
	fi

	local data_active=$(echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.targetActiveDataPlane // \"blue\"")

	echo "${data_active}" > /tmp/data-active.txt

	kubectl \
		get \
		buckets.s3.aws.m.upbound.io \
		--output jsonpath="{.items[0].metadata.name}" \
		--selector "dataPlane=${data_active}" \
		> /tmp/s3-bucket-id-active.txt

	local data_inactive

	if [ "${data_active}" = "blue" ]
	then
		data_inactive="green"
	else
		data_inactive="blue"
	fi

	echo "${data_inactive}" > /tmp/data-inactive.txt

	kubectl \
		get \
		buckets.s3.aws.m.upbound.io \
		--output jsonpath="{.items[0].metadata.name}" \
		--selector "dataPlane=${data_inactive}" \
		> /tmp/s3-bucket-id-inactive.txt
}

main
{{- end -}}

{{- define "liferayAWSBackupRestore.script.getPeerRecoveryPoints" -}}
#!/bin/sh

set -eu

function get_recovery_point_arn_by_type {
	local recovery_points_json="${2}"
	local resource_type="${1}"

	local filtered_recovery_points_json

	filtered_recovery_points_json=$( \
		echo \
			"${recovery_points_json}" \
			| jq --arg resource_type "${resource_type}" "[.[] | select(.ResourceType == \$resource_type)]")

	local filtered_recovery_points_length

	filtered_recovery_points_length=$(echo "${filtered_recovery_points_json}" | jq "length")

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
			--backup-vault-name "{{ printf "%s-backup-vault" (include "liferayAWSBackupRestore.infraResourceBaseName" .) }}" \
			--recovery-point-arn "{{ "{{" }}workflow.parameters.recovery-point-arn}}")

	local creation_date

	creation_date=$(echo "${recovery_point_details}" | jq --raw-output ".CreationDate")

	if [ -z "${creation_date}" ] || [ "${creation_date}" = "null" ]
	then
		echo "The provided recovery point ARN has no creation date." >&2

		return 1
	fi

	local creation_date_timestamp

	creation_date_timestamp=$(date --date "${creation_date}" +%s)

	local by_created_after

	by_created_after=$(date --date @$((creation_date_timestamp - 1)) --iso-8601=seconds)

	local by_created_before

	by_created_before=$(date --date @$((creation_date_timestamp + 1)) --iso-8601=seconds)

	local peer_recovery_points

	peer_recovery_points=$( \
		aws \
			backup \
			list-recovery-points-by-backup-vault \
			--backup-vault-name "{{ printf "%s-backup-vault" (include "liferayAWSBackupRestore.infraResourceBaseName" .) }}" \
			--by-created-after "${by_created_after}" \
			--by-created-before "${by_created_before}" \
			| jq --arg creation_date "${creation_date}" "[.RecoveryPoints[] | select(.CreationDate == \$creation_date)]")

	local rds_recovery_point_arn

	rds_recovery_point_arn=$(get_recovery_point_arn_by_type "RDS" "${peer_recovery_points}")

	local rds_snapshot_id

	rds_snapshot_id=$( \
		echo \
			"${rds_recovery_point_arn}" \
			| awk --field-separator "snapshot:" "{print \$2}")

	if [ -z "${rds_snapshot_id}" ]
	then
		echo "The RDS snapshot ID could not be parsed from ${rds_recovery_point_arn}." >&2

		exit 1
	fi

	echo "${rds_snapshot_id}" > /tmp/rds-snapshot-id.txt

	local s3_recovery_point_arn

	s3_recovery_point_arn=$(get_recovery_point_arn_by_type "S3" "${peer_recovery_points}")

	echo "${s3_recovery_point_arn}" > /tmp/s3-recovery-point-arn.txt
}

main
{{- end -}}

{{- define "liferayAWSBackupRestore.script.restoreS3Bucket" -}}
#!/bin/sh

set -eu

function main {
	local restore_job_id

	restore_job_id=$( \
		aws \
			backup \
			start-restore-job \
			--iam-role-arn "{{ printf "arn:aws:iam::%v:role/%s-backup-service-role" .Values.global.aws.accountId (include "liferayAWSBackupRestore.infraResourceBaseName" .) }}" \
			--metadata "DestinationBucketName={{ "{{" }}inputs.parameters.s3-bucket-id}},NewBucket=false" \
			--recovery-point-arn "{{ "{{" }}inputs.parameters.s3-recovery-point-arn}}" \
			--resource-type "S3" \
			| jq --raw-output ".RestoreJobId")

	local timeout

	timeout=$(( $(date +%s) + {{ .Values.awsBackupService.restoreWaitTimeoutSeconds }} ))

	while [ $(date +%s) -lt ${timeout} ]
	do
		local restore_job_status_json

		restore_job_status_json=$( \
			aws \
				backup \
				describe-restore-job \
				--restore-job-id "${restore_job_id}")

		local restore_job_status

		restore_job_status=$(echo "${restore_job_status_json}" | jq --raw-output ".Status")

		if [ "${restore_job_status}" = "ABORTED" ] || [ "${restore_job_status}" = "FAILED" ]
		then
			local restore_job_status_message

			restore_job_status_message=$( \
				echo \
					"${restore_job_status_json}" \
					| jq --raw-output ".StatusMessage")

			echo "The restore job \"${restore_job_id}\" failed with status \"${restore_job_status}\": ${restore_job_status_message}." >&2

			exit 1
		elif [ "${restore_job_status}" = "COMPLETED" ]
		then
			exit 0
		else
			echo "The current restore job status is \"${restore_job_status}\"."

			sleep 30
		fi
	done

	echo "The restore timed out." >&2

	exit 1
}

main
{{- end -}}

{{- define "liferayAWSBackupRestore.script.waitInfrastructureReady" -}}
#!/bin/sh

set -eu

function main {
	local expected_generation="{{ "{{" }}inputs.parameters.expected-generation}}"

	local timeout

	timeout=$(( $(date +%s) + {{ .Values.liferayInfrastructure.waitTimeoutSeconds }} ))

	while [ $(date +%s) -lt ${timeout} ]
	do
		local ready_condition=$( \
			kubectl \
				get \
				liferayinfrastructure \
				"{{ include "liferayAWSBackupRestore.liferayInfrastructureName" . }}" \
				--output jsonpath="{.status.conditions[?(@.type==\"Ready\")]}" 2>/dev/null || echo "{}")

		local observed_generation=$(echo "${ready_condition}" | jq --raw-output ".observedGeneration // 0")
		local status=$(echo "${ready_condition}" | jq --raw-output ".status // \"False\"")

		if [ "${observed_generation}" -ge "${expected_generation}" ] && [ "${status}" = "True" ]
		then
			exit 0
		fi

		sleep 30
	done

	echo "The system timed out waiting for the Liferay Infrastructure to be ready." >&2

	exit 1
}

main
{{- end -}}