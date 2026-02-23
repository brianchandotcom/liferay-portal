{{- define "elasticsearch.name" -}}
{{- .Values.search.elasticsearch.name | default (printf "%s-es" .Release.Name) | trunc 63 | trimSuffix "-" -}}
{{- end -}}