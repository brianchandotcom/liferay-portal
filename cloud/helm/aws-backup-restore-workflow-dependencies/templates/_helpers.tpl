{{- define "liferayBackupRestoreWorkflow.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
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

{{- define "liferayBackupRestoreWorkflow.selectorLabels" -}}
app.kubernetes.io/name: {{ include "liferayBackupRestoreWorkflow.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}