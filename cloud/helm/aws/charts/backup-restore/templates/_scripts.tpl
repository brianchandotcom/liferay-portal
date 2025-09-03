{{- define "liferayAwsBackupRestore.script.checkoutTerraformRepo" -}}
{{- $workingDir := include "liferayAwsBackupRestore.constant.workingDir" . -}}
{{- include "liferayAwsBackupRestore.script.partial.shStart" . }}

main() {
    {{ include "liferayAwsBackupRestore.script.partial.setupGitCredentials" . }}

    git \
    	clone \
    	--branch "{{ .Values.terraformRepo.branch }}" \
    	--depth 1 \
    	--filter blob:none \
    	--no-checkout \
    	"{{ .Values.terraformRepo.httpsUrl }}" \
    	"{{ $workingDir }}"

    cd "{{ $workingDir }}"

    git sparse-checkout set --no-cone "{{ .Values.terraformRepo.sparseCheckoutRelativePath }}"

    git checkout
}

main
{{- end -}}

{{- define "liferayAwsBackupRestore.script.findPeerRecoveryPoints" -}}
{{- $awsBackupVaultName := .Values.awsBackupVaultName -}}
{{- $recoveryPointArn := include "liferayAwsBackupRestore.workflow.parameter" (include "liferayAwsBackupRestore.param.recoveryPointArn" .) -}}
{{- include "liferayAwsBackupRestore.script.partial.shStart" . }}

get_recovery_point_arn_by_type() {
    local resource_type="${1}"
    local recovery_points_json="${2}"

    local filtered_recovery_points_json="$( \
    	echo \
			"${recovery_points_json}" \
			| jq --arg resource_type "${resource_type}" '[.[] | select(.ResourceType == $resource_type)]')"

    local filtered_recovery_points_length="$(echo "${filtered_recovery_points_json}" | jq 'length')"

    if [ "${filtered_recovery_points_length}" -ne 1 ];
    then
        echo "Error: Expected to find 1 recovery point of type '${resource_type}', but found ${filtered_recovery_points_length}." >&2

        exit 1
    fi

    echo "${filtered_recovery_points_json}" | jq --raw-output '.[0].RecoveryPointArn'
}

main() {
	echo "Finding peer recovery points for {{ $recoveryPointArn }}."

    local recovery_point_details=$( \
    	aws \
			backup \
			describe-recovery-point \
			--backup-vault-name "{{ $awsBackupVaultName }}" \
			--recovery-point-arn "{{ $recoveryPointArn }}")

    local creation_date="$(echo "${recovery_point_details}" | jq --raw-output '.CreationDate')"

    if [ -z "${creation_date}" ] || [ "${creation_date}" = "null" ];
    then
        echo "Error: Could not determine CreationDate from the provided recovery point ARN." >&2

        exit 1
    fi

    local creation_date_epoch="$(date --date "${creation_date}" +%s)"

    local by_created_after="$(date --date "@$((creation_date_epoch + 1))" --iso-8601 seconds)"
    local by_created_before="$(date --date "@$((creation_date_epoch - 1))" --iso-8601 seconds)"

    echo "Scanning vault for recovery points between ${by_created_before} and ${by_created_after}."

    local peer_recovery_points=$( \
    	aws \
			backup \
			list-recovery-points-by-backup-vault \
			--backup-vault-name "{{ $awsBackupVaultName }}" \
			--by-created-after "${by_created_before}" \
			--by-created-before "${by_created_after}" \
			| jq --arg creation_date "${creation_date}" '[.RecoveryPoints[] | select(.CreationDate == $creation_date)]')

    local rds_recovery_point_arn="$(get_recovery_point_arn_by_type "RDS" "${peer_recovery_points}")"
    local s3_recovery_point_arn="$(get_recovery_point_arn_by_type "S3" "${peer_recovery_points}")"

    local rds_snapshot_id="$( \
    	echo \
			"${rds_recovery_point_arn}" \
			| awk --field-separator "snapshot:" '{print $2}')"

    if [ -z "${rds_snapshot_id}" ];
    then
        echo "Error: could not parse RDS snapshot id from ${rds_recovery_point_arn}." >&2

        exit 1
    fi

    echo "${rds_snapshot_id}" > {{ include "liferayAwsBackupRestore.constant.outputPathRdsSnapshotId" . }}
    echo "${s3_recovery_point_arn}" > {{ include "liferayAwsBackupRestore.constant.outputPathS3RecoveryPointArn" . }}
}

main
{{- end -}}

{{- define "liferayAwsBackupRestore.script.getDependenciesModuleOutputs" -}}
{{- $terraformOutputIsRestoring := "is_restoring" -}}
{{- $terraformOutputDbRestoreSnapshotIdentifier := "db_restore_snapshot_identifier" -}}
{{- include "liferayAwsBackupRestore.script.partial.shStart" . }}

main() {
	terraform init -input=false

    echo "Validating that infrastructure matches configuration."

    terraform plan -detailed-exitcode -input=false

    echo "Validation successful. Infrastructure state matches configuration."

    echo "Validating that a backup restore is not in progress."

    local db_restore_snapshot_identifier="$( \
    	terraform \
			output \
			-raw \
			{{ $terraformOutputDbRestoreSnapshotIdentifier }} 2>/dev/null || echo "")"

    if [ -n "${db_restore_snapshot_identifier}" ];
	then
        echo "Error: '{{ $terraformOutputDbRestoreSnapshotIdentifier }}' is not empty. A restore may already be in progress." >&2

        exit 1
    fi

    if [ "$(terraform output -raw {{ $terraformOutputIsRestoring }})" = "true" ];
    then
        echo "Error: '{{ $terraformOutputIsRestoring }}' is 'true'. A restore may already be in progress." >&2

        exit 1
    fi

    echo "Validation successful. No backup restore is in progress."

    terraform output -raw data_active > {{ include "liferayAwsBackupRestore.constant.outputPathDataActive" . }}
	terraform output -raw data_inactive > {{ include "liferayAwsBackupRestore.constant.outputPathDataInactive" . }}
	terraform output -raw s3_bucket_id_active > {{ include "liferayAwsBackupRestore.constant.outputPathS3BucketIdActive" . }}
	terraform output -raw s3_bucket_id_inactive > {{ include "liferayAwsBackupRestore.constant.outputPathS3BucketIdInactive" . }}
}

main
{{- end -}}

{{- define "liferayAwsBackupRestore.script.partial.setupGitCredentials" -}}
{{- $gitCredentialsMountPath := include "liferayAwsBackupRestore.constant.gitCredentialsMountPath" . -}}
{{- $gitCredentialsTempPath := include "liferayAwsBackupRestore.constant.gitCredentialsTempPath" . -}}
cp "{{ $gitCredentialsMountPath }}" "{{ $gitCredentialsTempPath }}"

git config --global credential.helper 'store --file {{ $gitCredentialsTempPath }}'
{{- end -}}

{{- define "liferayAwsBackupRestore.script.partial.shStart" -}}
#!/bin/sh
set -eu
{{- end -}}

{{- define "liferayAwsBackupRestore.script.restoreS3Bucket" -}}
{{- $s3RecoveryPointArn := include "liferayAwsBackupRestore.task.input.parameter" (include "liferayAwsBackupRestore.param.s3RecoveryPointArn" .) -}}
{{- $targetBucketName := include "liferayAwsBackupRestore.task.input.parameter" (include "liferayAwsBackupRestore.param.s3BucketId" .) -}}
{{- $waitTimeoutSeconds := .Values.s3RestoreWaitTimeoutSeconds -}}
{{- include "liferayAwsBackupRestore.script.partial.shStart" . }}

main() {
	echo "Starting S3 restore job for target bucket '{{ $targetBucketName }}'."

    local restore_job_id="$( \
    	aws \
			backup \
			start-restore-job \
			--iam-role-arn "{{ .Values.awsBackupServiceAssumedIamRoleArn }}" \
			--metadata "DestinationBucketName={{ $targetBucketName }},NewBucket=false" \
			--recovery-point-arn "{{ $s3RecoveryPointArn }}" \
			--resource-type "S3" \
			| jq --raw-output '.RestoreJobId')"

    echo "Restore job started: ${restore_job_id}."
    echo "Waiting for restore job to complete (timeout: {{ $waitTimeoutSeconds }}s)."

    local timeout=$(( $(date +%s) + {{ $waitTimeoutSeconds }} ))

    while [ "$(date +%s)" -lt ${timeout} ];
    do
    	local restore_job_status_json="$( \
    		aws \
				backup \
				describe-restore-job \
				--restore-job-id "${restore_job_id}")"

    	local restore_job_status="$(echo "${restore_job_status_json}" | jq --raw-output '.Status')"

    	case "${restore_job_status}" in
        	COMPLETED)
          		echo "Restore job completed successfully."

          		exit 0

          		;;
       		FAILED|ABORTED)
        		local restore_job_status_message="$( \
        			echo \
						"${restore_job_status_json}" \
						| jq --raw-output '.StatusMessage')"

        		echo "Error: Restore job '${restore_job_id}' failed with status '${restore_job_status}': ${restore_job_status_message}." >&2

        		exit 1

        		;;
       		*)
          		echo "Current restore job status: ${restore_job_status}."

          		sleep 30

          		;;
        esac
    done

    echo "Error: Timed out waiting for restore job to complete." >&2

    exit 1
}

main
{{- end -}}

{{- define "liferayAwsBackupRestore.script.terraform-apply" -}}
{{- $commitMessage := include "liferayAwsBackupRestore.task.input.parameter" (include "liferayAwsBackupRestore.param.commitMessage" .) -}}
{{- $overrideTfvarsFile := "zz_workflow_override.auto.tfvars" -}}
{{- $tfvarsContent := include "liferayAwsBackupRestore.task.input.parameter" (include "liferayAwsBackupRestore.param.tfvarsContent" .) -}}
{{- include "liferayAwsBackupRestore.script.partial.shStart" . }}

main() {
	{{ include "liferayAwsBackupRestore.script.partial.setupGitCredentials" . }}

    git config --global user.email {{ .Values.git.user.email | quote }}
	git config --global user.name {{ .Values.git.user.name | quote }}

    git pull origin {{ .Values.terraformRepo.branch }}

    echo '{{ $tfvarsContent }}' > "{{ $overrideTfvarsFile }}"

    git add "{{ $overrideTfvarsFile }}"

    if ! git diff --staged --quiet;
    then
        git commit --message "{{ $commitMessage }}"

        git push origin HEAD:{{ .Values.terraformRepo.branch }}
    else
        echo "Working tree is clean. No changes to commit."
    fi

    terraform init -input=false

    terraform apply -auto-approve -input=false
}

main
{{- end -}}