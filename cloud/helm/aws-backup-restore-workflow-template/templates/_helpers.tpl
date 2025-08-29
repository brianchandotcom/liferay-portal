{{- define "liferayBackupRestoreWorkflow.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.configmap.artifactRepositories.name" -}}
{{- printf "%s-art-repo" (include "liferayBackupRestoreWorkflow.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.gitCredentialsFileName" -}}
.git-credentials
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.gitCredentialsMountPath" -}}
{{- printf "/root/%s" (include "liferayBackupRestoreWorkflow.constant.gitCredentialsFileName" .) -}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.gitCredentialsTempPath" -}}
{{- printf "/tmp/%s" (include "liferayBackupRestoreWorkflow.constant.gitCredentialsFileName" .) -}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.gitCredentialsVolumeName" -}}
git-credentials
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.outputPathDataActive" -}}
/tmp/data_active.txt
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.outputPathDataInactive" -}}
/tmp/data_inactive.txt
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.outputPathRdsSnapshotId" -}}
/tmp/rds_snapshot_id.txt
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.outputPathS3BucketIdActive" -}}
/tmp/s3_bucket_id_active.txt
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.outputPathS3BucketIdInactive" -}}
/tmp/s3_bucket_id_inactive.txt
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.outputPathS3RecoveryPointArn" -}}
/tmp/s3_recovery_point_arn.txt
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.constant.workingDir" -}}
/src
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.fullname" -}}
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

{{- define "liferayBackupRestoreWorkflow.labels" -}}
helm.sh/chart: {{ include "liferayBackupRestoreWorkflow.chart" . }}
{{ include "liferayBackupRestoreWorkflow.selectorLabels" . }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.commitMessage" -}}
commit-message
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.dataActive" -}}
data-active
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.dataInactive" -}}
data-inactive
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.dbRestoreSnapshotIdentifier" -}}
db-restore-snapshot-identifier
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.isRestoring" -}}
is-restoring
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.recoveryPointArn" -}}
recovery-point-arn
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.rdsSnapshotId" -}}
rds-snapshot-id
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.s3BucketId" -}}
s3-bucket-id
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.s3BucketIdActive" -}}
s3-bucket-id-active
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.s3BucketIdInactive" -}}
s3-bucket-id-inactive
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.s3RecoveryPointArn" -}}
s3-recovery-point-arn
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.param.tfvarsContent" -}}
tfvars-content
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.selectorLabels" -}}
app.kubernetes.io/name: {{ include "liferayBackupRestoreWorkflow.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.task.input.parameter" -}}
{{- "{{" }}inputs.parameters.{{ . }}}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.task.output.artifact" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.artifacts.{{ .artifact }}}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.task.output.parameter" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.parameters.{{ .parameter }}}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.volumeMount.gitCredentials" -}}
{{- if and .Values.git.credentials.username .Values.git.credentials.token -}}
volumeMounts:
    -   mountPath: {{ include "liferayBackupRestoreWorkflow.constant.gitCredentialsMountPath" . }}
        name: {{ include "liferayBackupRestoreWorkflow.constant.gitCredentialsVolumeName" . }}
        subPath: {{ include "liferayBackupRestoreWorkflow.constant.gitCredentialsFileName" . }}
{{- end -}}
{{- end -}}

{{- define "liferayBackupRestoreWorkflow.workflow.parameter" -}}
{{- "{{" }}workflow.parameters.{{ . }}}}
{{- end -}}