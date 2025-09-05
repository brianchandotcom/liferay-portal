{{- define "liferayAWSBackupRestore.argo.param.commitMessage" -}}
commit-message
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.dataActive" -}}
data-active
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.dataInactive" -}}
data-inactive
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.recoveryPointArn" -}}
recovery-point-arn
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.rdsSnapshotId" -}}
rds-snapshot-id
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.s3BucketId" -}}
s3-bucket-id
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.s3BucketIdActive" -}}
s3-bucket-id-active
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.s3BucketIdInactive" -}}
s3-bucket-id-inactive
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.s3RecoveryPointArn" -}}
s3-recovery-point-arn
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.param.tfvarsContent" -}}
tfvars-content
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.ref.taskInputParam" -}}
{{- "{{" }}inputs.parameters.{{ . }}}}
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.ref.taskOutputArtifact" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.artifacts.{{ .artifact }}}}
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.ref.taskOutputParam" -}}
{{- "{{" }}tasks.{{ .task }}.outputs.parameters.{{ .parameter }}}}
{{- end -}}

{{- define "liferayAWSBackupRestore.argo.ref.workflowParam" -}}
{{- "{{" }}workflow.parameters.{{ . }}}}
{{- end -}}

{{- define "liferayAWSBackupRestore.artifactRepositoryConfigMapName" -}}
{{- printf "%s-art-repo" (include "liferayAWSBackupRestore.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.fullname" -}}
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

{{- define "liferayAWSBackupRestore.gitCredentials.fileName" -}}
.git-credentials
{{- end -}}

{{- define "liferayAWSBackupRestore.gitCredentials.mountPath" -}}
{{- printf "/mnt/%s" (include "liferayAWSBackupRestore.gitCredentials.fileName" .) -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.gitCredentials.secretName" -}}
{{- printf "%s-git-creds" (include "liferayAWSBackupRestore.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.gitCredentials.tempPath" -}}
{{- printf "/tmp/%s" (include "liferayAWSBackupRestore.gitCredentials.fileName" .) -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.gitCredentials.volumeMount" -}}
{{- if and .Values.git.credentials.token .Values.git.credentials.username -}}
volumeMounts:
    -   mountPath: {{ include "liferayAWSBackupRestore.gitCredentials.mountPath" . }}
        name: {{ include "liferayAWSBackupRestore.gitCredentials.volumeName" . }}
        subPath: {{ include "liferayAWSBackupRestore.gitCredentials.fileName" . }}
{{- end -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.gitCredentials.volumeName" -}}
git-credentials
{{- end -}}

{{- define "liferayAWSBackupRestore.labels" -}}
{{ include "liferayAWSBackupRestore.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ include "liferayAWSBackupRestore.chart" . }}
{{- end -}}

{{- define "liferayAWSBackupRestore.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.path.dataActive" -}}
/tmp/data-active.txt
{{- end -}}

{{- define "liferayAWSBackupRestore.path.dataInactive" -}}
/tmp/data-inactive.txt
{{- end -}}

{{- define "liferayAWSBackupRestore.path.rdsSnapshotId" -}}
/tmp/rds-snapshot-id.txt
{{- end -}}

{{- define "liferayAWSBackupRestore.path.s3BucketIdActive" -}}
/tmp/s3-bucket-id-active.txt
{{- end -}}

{{- define "liferayAWSBackupRestore.path.s3BucketIdInactive" -}}
/tmp/s3-bucket-id-inactive.txt
{{- end -}}

{{- define "liferayAWSBackupRestore.path.s3RecoveryPointArn" -}}
/tmp/s3-recovery-point-arn.txt
{{- end -}}

{{- define "liferayAWSBackupRestore.path.src" -}}
/src
{{- end -}}

{{- define "liferayAWSBackupRestore.selectorLabels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/name: {{ include "liferayAWSBackupRestore.name" . }}
{{- end -}}