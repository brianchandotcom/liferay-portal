{{- define "liferayAwsBackupRestore.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.configmap.artifactRepository.name" -}}
{{- printf "%s-art-repo" (include "liferayAwsBackupRestore.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.gitCredentialsFileName" -}}
.git-credentials
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.gitCredentialsMountPath" -}}
{{- printf "/root/%s" (include "liferayAwsBackupRestore.constant.gitCredentialsFileName" .) -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.gitCredentialsTempPath" -}}
{{- printf "/tmp/%s" (include "liferayAwsBackupRestore.constant.gitCredentialsFileName" .) -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.gitCredentialsVolumeName" -}}
git-credentials
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.outputPathDataActive" -}}
/tmp/data_active.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.outputPathDataInactive" -}}
/tmp/data_inactive.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.outputPathRdsSnapshotId" -}}
/tmp/rds_snapshot_id.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.outputPathS3BucketIdActive" -}}
/tmp/s3_bucket_id_active.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.outputPathS3BucketIdInactive" -}}
/tmp/s3_bucket_id_inactive.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.outputPathS3RecoveryPointArn" -}}
/tmp/s3_recovery_point_arn.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.constant.workingDir" -}}
/src
{{- end -}}

{{- define "liferayAwsBackupRestore.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.labels" -}}
helm.sh/chart: {{ include "liferayAwsBackupRestore.chart" . }}
{{ include "liferayAwsBackupRestore.selectorLabels" . }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{- define "liferayAwsBackupRestore.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.param.commitMessage" -}}
commit-message
{{- end -}}

{{- define "liferayAwsBackupRestore.param.dataActive" -}}
data-active
{{- end -}}

{{- define "liferayAwsBackupRestore.param.dataInactive" -}}
data-inactive
{{- end -}}

{{- define "liferayAwsBackupRestore.param.dbRestoreSnapshotIdentifier" -}}
db-restore-snapshot-identifier
{{- end -}}

{{- define "liferayAwsBackupRestore.param.isRestoring" -}}
is-restoring
{{- end -}}

{{- define "liferayAwsBackupRestore.param.recoveryPointArn" -}}
recovery-point-arn
{{- end -}}

{{- define "liferayAwsBackupRestore.param.rdsSnapshotId" -}}
rds-snapshot-id
{{- end -}}

{{- define "liferayAwsBackupRestore.param.s3BucketId" -}}
s3-bucket-id
{{- end -}}

{{- define "liferayAwsBackupRestore.param.s3BucketIdActive" -}}
s3-bucket-id-active
{{- end -}}

{{- define "liferayAwsBackupRestore.param.s3BucketIdInactive" -}}
s3-bucket-id-inactive
{{- end -}}

{{- define "liferayAwsBackupRestore.param.s3RecoveryPointArn" -}}
s3-recovery-point-arn
{{- end -}}

{{- define "liferayAwsBackupRestore.param.tfvarsContent" -}}
tfvars-content
{{- end -}}

{{- define "liferayAwsBackupRestore.secret.gitCredentials.name" -}}
{{- printf "%s-git-creds" (include "liferayAwsBackupRestore.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.selectorLabels" -}}
app.kubernetes.io/name: {{ include "liferayAwsBackupRestore.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}

{{- define "liferayAwsBackupRestore.task.input.parameter" -}}
{{- "{{" }}inputs.parameters.{{ . }}}}
{{- end -}}

{{- define "liferayAwsBackupRestore.task.output.artifact" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.artifacts.{{ .artifact }}}}
{{- end -}}

{{- define "liferayAwsBackupRestore.task.output.parameter" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.parameters.{{ .parameter }}}}
{{- end -}}

{{- define "liferayAwsBackupRestore.volumeMount.gitCredentials" -}}
{{- if and .Values.git.credentials.username .Values.git.credentials.token -}}
volumeMounts:
    -   mountPath: {{ include "liferayAwsBackupRestore.constant.gitCredentialsMountPath" . }}
        name: {{ include "liferayAwsBackupRestore.constant.gitCredentialsVolumeName" . }}
        subPath: {{ include "liferayAwsBackupRestore.constant.gitCredentialsFileName" . }}
{{- end -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.workflow.parameter" -}}
{{- "{{" }}workflow.parameters.{{ . }}}}
{{- end -}}