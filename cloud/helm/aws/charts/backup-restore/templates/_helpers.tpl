{{- define "liferayAwsBackupRestore.argo.param.commitMessage" -}}
commit-message
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.dataActive" -}}
data-active
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.dataInactive" -}}
data-inactive
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.dbRestoreSnapshotIdentifier" -}}
db-restore-snapshot-identifier
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.recoveryPointArn" -}}
recovery-point-arn
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.rdsSnapshotId" -}}
rds-snapshot-id
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.s3BucketId" -}}
s3-bucket-id
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.s3BucketIdActive" -}}
s3-bucket-id-active
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.s3BucketIdInactive" -}}
s3-bucket-id-inactive
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.s3RecoveryPointArn" -}}
s3-recovery-point-arn
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.param.tfvarsContent" -}}
tfvars-content
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.ref.taskInputParam" -}}
{{- "{{" }}inputs.parameters.{{ . }}}}
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.ref.taskOutputArtifact" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.artifacts.{{ .artifact }}}}
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.ref.taskOutputParam" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.parameters.{{ .parameter }}}}
{{- end -}}

{{- define "liferayAwsBackupRestore.argo.ref.workflowParam" -}}
{{- "{{" }}workflow.parameters.{{ . }}}}
{{- end -}}

{{- define "liferayAwsBackupRestore.artifactRepositoryConfigMapName" -}}
{{- printf "%s-art-repo" (include "liferayAwsBackupRestore.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
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

{{- define "liferayAwsBackupRestore.gitCredentials.fileName" -}}
.git-credentials
{{- end -}}

{{- define "liferayAwsBackupRestore.gitCredentials.mountPath" -}}
{{- printf "/mnt/%s" (include "liferayAwsBackupRestore.gitCredentials.fileName" .) -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.gitCredentials.secretName" -}}
{{- printf "%s-git-creds" (include "liferayAwsBackupRestore.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.gitCredentials.tempPath" -}}
{{- printf "/tmp/%s" (include "liferayAwsBackupRestore.gitCredentials.fileName" .) -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.gitCredentials.volumeMount" -}}
{{- if and .Values.git.credentials.token .Values.git.credentials.username -}}
volumeMounts:
    -   mountPath: {{ include "liferayAwsBackupRestore.gitCredentials.mountPath" . }}
        name: {{ include "liferayAwsBackupRestore.gitCredentials.volumeName" . }}
        subPath: {{ include "liferayAwsBackupRestore.gitCredentials.fileName" . }}
{{- end -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.gitCredentials.volumeName" -}}
git-credentials
{{- end -}}

{{- define "liferayAwsBackupRestore.labels" -}}
{{ include "liferayAwsBackupRestore.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ include "liferayAwsBackupRestore.chart" . }}
{{- end -}}

{{- define "liferayAwsBackupRestore.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.path.dataActive" -}}
/tmp/data-active.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.dataInactive" -}}
/tmp/data-inactive.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.rdsSnapshotId" -}}
/tmp/rds-snapshot-id.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.s3BucketIdActive" -}}
/tmp/s3-bucket-id-active.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.s3BucketIdInactive" -}}
/tmp/s3-bucket-id-inactive.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.s3RecoveryPointArn" -}}
/tmp/s3-recovery-point-arn.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.workingDir" -}}
/src
{{- end -}}

{{- define "liferayAwsBackupRestore.selectorLabels" -}}
app.kubernetes.io/name: {{ include "liferayAwsBackupRestore.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}