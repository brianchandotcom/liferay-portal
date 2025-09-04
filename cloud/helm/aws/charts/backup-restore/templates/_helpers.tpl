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

{{- define "liferayAwsBackupRestore.artifactRepository.configMapName" -}}
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

{{- define "liferayAwsBackupRestore.git.credentials.fileName" -}}
.git-credentials
{{- end -}}

{{- define "liferayAwsBackupRestore.git.credentials.mountPath" -}}
{{- printf "/mnt/%s" (include "liferayAwsBackupRestore.git.credentials.fileName" .) -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.git.credentials.secretName" -}}
{{- printf "%s-git-creds" (include "liferayAwsBackupRestore.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.git.credentials.tempPath" -}}
{{- printf "/tmp/%s" (include "liferayAwsBackupRestore.git.credentials.fileName" .) -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.git.credentials.volumeMount" -}}
{{- if and .Values.git.credentials.token .Values.git.credentials.username -}}
volumeMounts:
    -   mountPath: {{ include "liferayAwsBackupRestore.git.credentials.mountPath" . }}
        name: {{ include "liferayAwsBackupRestore.git.credentials.volumeName" . }}
        subPath: {{ include "liferayAwsBackupRestore.git.credentials.fileName" . }}
{{- end -}}
{{- end -}}

{{- define "liferayAwsBackupRestore.git.credentials.volumeName" -}}
git-credentials
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

{{- define "liferayAwsBackupRestore.path.output.dataActive" -}}
/tmp/data-active.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.output.dataInactive" -}}
/tmp/data-inactive.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.output.rdsSnapshotId" -}}
/tmp/rds-snapshot-id.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.output.s3BucketIdActive" -}}
/tmp/s3-bucket-id-active.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.output.s3BucketIdInactive" -}}
/tmp/s3-bucket-id-inactive.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.output.s3RecoveryPointArn" -}}
/tmp/s3-recovery-point-arn.txt
{{- end -}}

{{- define "liferayAwsBackupRestore.path.workingDir" -}}
/src
{{- end -}}

{{- define "liferayAwsBackupRestore.selectorLabels" -}}
app.kubernetes.io/name: {{ include "liferayAwsBackupRestore.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}