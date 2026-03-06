#!/bin/sh

set -eu

function main {
	local restore_job_id=$( \
		aws \
			backup \
			start-restore-job \
			--iam-role-arn "{{ "{{" }}inputs.parameters.backup-service-role-arn}}" \
			--metadata "DestinationBucketName={{ "{{" }}inputs.parameters.s3-bucket-id}},NewBucket=false" \
			--recovery-point-arn "{{ "{{" }}inputs.parameters.s3-recovery-point-arn}}" \
			--resource-type "S3" \
			| jq --raw-output ".RestoreJobId")

	local timeout=$(($(date +%s) + {{ .Values.awsBackupService.restoreWaitTimeoutSeconds }}))

	while [ $(date +%s) -lt ${timeout} ]
	do
		local restore_job_status_json=$( \
			aws \
				backup \
				describe-restore-job \
				--restore-job-id "${restore_job_id}")

		local restore_job_status=$(echo "${restore_job_status_json}" | jq --raw-output ".Status")

		if [ "${restore_job_status}" = "ABORTED" ] || [ "${restore_job_status}" = "FAILED" ]
		then
			local restore_job_status_message=$( \
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