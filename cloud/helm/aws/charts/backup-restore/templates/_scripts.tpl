{{- define "liferayAWSBackupRestore.script.getDependenciesModuleOutputs" -}}
#!/bin/sh

set -eu

function main {
	terraform init -input=false

    terraform plan -detailed-exitcode -input=false

    local db_restore_snapshot_identifier

    db_restore_snapshot_identifier=$( \
    	terraform \
			output \
			-raw \
			db_restore_snapshot_identifier 2>/dev/null || echo "")

    if [ -n "${db_restore_snapshot_identifier}" ]
	then
        echo "Terraform output \"db_restore_snapshot_identifier\" is not empty. A restore may already be in progress." >&2

        exit 1
    fi

    if [ $(terraform output -raw is_restoring) = "true" ];
    then
        echo "Terraform output \"is_restoring\" is set to \"true\". A restore may already be in progress." >&2

        exit 1
    fi

    terraform output -raw data_active > /tmp/data-active.txt
    terraform output -raw data_inactive > /tmp/data-inactive.txt
    terraform output -raw s3_bucket_id_active > /tmp/s3-bucket-id-active.txt
    terraform output -raw s3_bucket_id_inactive > /tmp/s3-bucket-id-inactive.txt
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
			--iam-role-arn "{{ .Values.awsBackupService.assumedIamRoleArn }}" \
			--metadata "DestinationBucketName={{ "{{" }}inputs.parameters.s3-bucket-id}},NewBucket=false" \
			--recovery-point-arn "{{ "{{" }}inputs.parameters.recovery-point-arn}}" \
			--resource-type "S3" \
			| jq --raw-output '.RestoreJobId')

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

    	restore_job_status=$(echo "${restore_job_status_json}" | jq --raw-output '.Status')

        if [ "${restore_job_status}" = "ABORTED" ] || [ "${restore_job_status}" = "FAILED" ]
        then
            local restore_job_status_message

            restore_job_status_message=$( \
                echo \
                    "${restore_job_status_json}" \
                    | jq --raw-output '.StatusMessage')

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