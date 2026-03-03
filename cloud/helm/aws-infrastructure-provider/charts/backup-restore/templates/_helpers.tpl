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

{{- define "liferayAWSBackupRestore.infraResourceBaseName" -}}
{{- $projectIdFull := printf "%s-%s" .Values.global.projectId .Values.global.environmentId -}}
{{- $uidHash := printf "%v-%s-%s" .Values.global.aws.accountId .Values.global.deploymentName $projectIdFull | sha256sum | trunc 6 -}}
{{- printf "%.18s-%s" $projectIdFull $uidHash -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.labels" -}}
{{ include "liferayAWSBackupRestore.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ include "liferayAWSBackupRestore.chart" . }}
{{- end -}}

{{- define "liferayAWSBackupRestore.liferayInfrastructureName" -}}
{{- default (printf "%s-%s-infra" .Values.global.projectId .Values.global.environmentId) .Values.liferayInfrastructure.name -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferayAWSBackupRestore.selectorLabels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/name: {{ include "liferayAWSBackupRestore.name" . }}
{{- end -}}