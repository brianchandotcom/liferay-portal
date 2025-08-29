{{- define "liferayBackupRestoreWorkflow.script.checkoutTerraformRepo" -}}
{{- $workingDir := include "liferayBackupRestoreWorkflow.constant.workingDir" . -}}
{{- include "liferayBackupRestoreWorkflow.script.partial.shStart" . }}

{{ include "liferayBackupRestoreWorkflow.script.partial.setupGitCredentials" . }}

git clone \
    --branch "{{ .Values.terraformRepo.branch }}" \
    --depth 1 \
    --filter=blob:none \
    --no-checkout \
    "{{ .Values.terraformRepo.httpsUrl }}" \
    "{{ $workingDir }}"

cd "{{ $workingDir }}"

git sparse-checkout set --no-cone "{{ .Values.terraformRepo.sparseCheckoutRelativePath }}"

git checkout
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.script.findPeerRecoveryPoints" -}}
{{- $awsBackupVaultName := .Values.awsBackupVaultName -}}
{{- $recoveryPointArn := include "liferayBackupRestoreWorkflow.workflow.parameter" (include "liferayBackupRestoreWorkflow.param.recoveryPointArn" .) -}}
{{- include "liferayBackupRestoreWorkflow.script.partial.shStart" . }}

get_recovery_point_arn_by_type() {
    local resource_type="$1"
    local recovery_points_json="$2"
    local filtered_recovery_points
    local recovery_points_length
    local arn

    filtered_recovery_points=$(echo "$recovery_points_json" \
        | jq --arg type "$resource_type" '[.[] | select(.ResourceType == $type)]')

    recovery_points_length=$(echo "$filtered_recovery_points" | jq 'length')

    if [ "$recovery_points_length" -ne 1 ]; then
        echo "Error: Expected to find 1 recovery point of type '$resource_type', but found $recovery_points_length" >&2

        exit 1
    fi

    arn=$(echo "$filtered_recovery_points" | jq -r '.[0].RecoveryPointArn')

    echo "$arn"
}

echo "Finding peer recovery points for {{ $recoveryPointArn }}"

RECOVERY_POINT_DETAILS=$(aws backup describe-recovery-point \
    --backup-vault-name "{{ $awsBackupVaultName }}" \
    --recovery-point-arn "{{ $recoveryPointArn }}")

CREATION_DATE=$(echo "$RECOVERY_POINT_DETAILS" | jq -r '.CreationDate')

if [ -z "$CREATION_DATE" ] || [ "$CREATION_DATE" = "null" ]; then
    echo "Error: Could not determine CreationDate from the provided recovery point ARN" >&2

    exit 1
fi

CREATION_DATE_EPOCH=$(date -d "$CREATION_DATE" +%s)

BY_CREATED_AFTER=$(date -d "@$((CREATION_DATE_EPOCH + 1))" --iso-8601=seconds)
BY_CREATED_BEFORE=$(date -d "@$((CREATION_DATE_EPOCH - 1))" --iso-8601=seconds)

echo "Scanning vault for recovery points between $BY_CREATED_BEFORE and $BY_CREATED_AFTER"

PEER_RECOVERY_POINTS=$(aws backup list-recovery-points-by-backup-vault \
    --backup-vault-name "{{ $awsBackupVaultName }}" \
    --by-created-after "$BY_CREATED_BEFORE" \
    --by-created-before "$BY_CREATED_AFTER" \
    | jq --arg date "$CREATION_DATE" '[.RecoveryPoints[] | select(.CreationDate == $date)]')

RDS_RECOVERY_POINT_ARN=$(get_recovery_point_arn_by_type "RDS" "$PEER_RECOVERY_POINTS")
S3_RECOVERY_POINT_ARN=$(get_recovery_point_arn_by_type "S3" "$PEER_RECOVERY_POINTS")

RDS_SNAPSHOT_ID=$(echo "$RDS_RECOVERY_POINT_ARN" | awk -F'snapshot:' '{print $2}')

if [ -z "$RDS_SNAPSHOT_ID" ]; then
    echo "Error: could not parse RDS snapshot id from $RDS_RECOVERY_POINT_ARN" >&2

    exit 1
fi

echo "$RDS_SNAPSHOT_ID" > {{ include "liferayBackupRestoreWorkflow.constant.outputPathRdsSnapshotId" . }}
echo "$S3_RECOVERY_POINT_ARN" > {{ include "liferayBackupRestoreWorkflow.constant.outputPathS3RecoveryPointArn" . }}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.script.getDependenciesModuleOutputs" -}}
{{- $terraformOutputIsRestoring := "is_restoring" -}}
{{- $terraformOutputDbRestoreSnapshotIdentifier := "db_restore_snapshot_identifier" -}}
{{- include "liferayBackupRestoreWorkflow.script.partial.shStart" . }}

terraform init -input=false

echo "Validating that infrastructure matches configuration"

terraform plan -detailed-exitcode -input=false

echo "Validation successful. Infrastructure state matches configuration"

echo "Validating that a backup restore is not in progress"

DB_RESTORE_SNAPSHOT_IDENTIFIER=$(terraform output -raw {{ $terraformOutputDbRestoreSnapshotIdentifier }} 2>/dev/null || echo "")

if [ -n "$DB_RESTORE_SNAPSHOT_IDENTIFIER" ]; then
    echo "Error: '{{ $terraformOutputDbRestoreSnapshotIdentifier }}' is not empty. A restore may already be in progress" >&2

    exit 1
fi

IS_RESTORING=$(terraform output -raw {{ $terraformOutputIsRestoring }})

if [ "$IS_RESTORING" = "true" ]; then
    echo "Error: '{{ $terraformOutputIsRestoring }}' is 'true'. A restore may already be in progress" >&2

    exit 1
fi

echo "Validation successful. No backup restore is in progress"

terraform output -raw data_active > {{ include "liferayBackupRestoreWorkflow.constant.outputPathDataActive" . }}
terraform output -raw data_inactive > {{ include "liferayBackupRestoreWorkflow.constant.outputPathDataInactive" . }}
terraform output -raw s3_bucket_id_active > {{ include "liferayBackupRestoreWorkflow.constant.outputPathS3BucketIdActive" . }}
terraform output -raw s3_bucket_id_inactive > {{ include "liferayBackupRestoreWorkflow.constant.outputPathS3BucketIdInactive" . }}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.script.partial.setupGitCredentials" -}}
{{- $gitCredentialsMountPath := include "liferayBackupRestoreWorkflow.constant.gitCredentialsMountPath" . -}}
{{- $gitCredentialsTempPath := include "liferayBackupRestoreWorkflow.constant.gitCredentialsTempPath" . -}}
cp "{{ $gitCredentialsMountPath }}" "{{ $gitCredentialsTempPath }}"

git config --global credential.helper 'store --file {{ $gitCredentialsTempPath }}'
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.script.partial.shStart" -}}
#!/bin/sh
set -eu
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.script.restoreS3Bucket" -}}
{{- $s3RecoveryPointArn := include "liferayBackupRestoreWorkflow.task.input.parameter" (include "liferayBackupRestoreWorkflow.param.s3RecoveryPointArn" .) -}}
{{- $targetBucketName := include "liferayBackupRestoreWorkflow.task.input.parameter" (include "liferayBackupRestoreWorkflow.param.s3BucketId" .) -}}
{{- $waitTimeoutSeconds := .Values.s3RestoreWaitTimeoutSeconds -}}
{{- include "liferayBackupRestoreWorkflow.script.partial.shStart" . }}

echo "Starting S3 restore job for target bucket '{{ $targetBucketName }}'"

RESTORE_JOB_ID=$(aws backup start-restore-job \
    --iam-role-arn "{{ .Values.awsBackupServiceAssumedIamRoleArn }}" \
    --metadata "DestinationBucketName={{ $targetBucketName }},NewBucket=false" \
    --recovery-point-arn "{{ $s3RecoveryPointArn }}" \
    --resource-type "S3" \
    | jq -r '.RestoreJobId')

echo "Restore job started: $RESTORE_JOB_ID"
echo "Waiting for restore job to complete (timeout: {{ $waitTimeoutSeconds }}s)"

END_TIME=$(( $(date +%s) + {{ $waitTimeoutSeconds }} ))

while [ $(date +%s) -lt $END_TIME ]; do
  RESTORE_JOB_STATUS_JSON=$(aws backup describe-restore-job --restore-job-id "$RESTORE_JOB_ID")
  
  RESTORE_JOB_STATUS=$(echo "$RESTORE_JOB_STATUS_JSON" | jq -r '.Status')

  case "$RESTORE_JOB_STATUS" in
    COMPLETED)
      echo "Restore job completed successfully"
      
      exit 0
      ;;
    FAILED|ABORTED)
      RESTORE_JOB_STATUS_MESSAGE=$(echo "$RESTORE_JOB_STATUS_JSON" | jq -r '.StatusMessage')
      
      echo "Error: Restore job '$RESTORE_JOB_ID' failed with status '$RESTORE_JOB_STATUS': $RESTORE_JOB_STATUS_MESSAGE" >&2
      
      exit 1
      ;;
    *)
      echo "Current restore job status: $RESTORE_JOB_STATUS"

      sleep 30
      ;;
  esac
done

echo "Error: Timed out waiting for restore job to complete" >&2

exit 1
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.script.terraform-apply" -}}
{{- $commitMessage := include "liferayBackupRestoreWorkflow.task.input.parameter" (include "liferayBackupRestoreWorkflow.param.commitMessage" .) -}}
{{- $tfvarsContent := include "liferayBackupRestoreWorkflow.task.input.parameter" (include "liferayBackupRestoreWorkflow.param.tfvarsContent" .) -}}
{{- include "liferayBackupRestoreWorkflow.script.partial.shStart" . }}

{{ include "liferayBackupRestoreWorkflow.script.partial.setupGitCredentials" . }}

git config --global user.email {{ .Values.git.user.email | quote }}
git config --global user.name {{ .Values.git.user.name | quote }}

git pull origin {{ .Values.terraformRepo.branch }}

OVERRIDE_TFVARS_FILE="zz_workflow_override.auto.tfvars"

echo '{{ $tfvarsContent }}' > "$OVERRIDE_TFVARS_FILE"

git add "$OVERRIDE_TFVARS_FILE"

if ! git diff --staged --quiet; then
    git commit -m "{{ $commitMessage }}"

    git push origin HEAD:{{ .Values.terraformRepo.branch }}
else
    echo "Working tree is clean. No changes to commit"
fi

terraform init -input=false

terraform apply -auto-approve -input=false
{{- end -}}