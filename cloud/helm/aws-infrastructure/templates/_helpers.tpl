{{- define "elasticsearch.name" -}}
{{- .Values.search.elasticsearch.name | default (printf "%s-es" .Release.Name) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferay.k8sFriendlyString" -}}
{{- . | lower | replace "/" "-" | replace "_" "-" | trimPrefix "-" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferay.getFullCertificateSecretName" -}}
{{- $trimmed := (. | trimPrefix "/") -}}
{{- if hasPrefix "liferay/certificates/" $trimmed }}
{{- $trimmed -}}
{{- else }}
{{- printf "liferay/certificates/%s" $trimmed -}}
{{- end }}
{{- end -}}

{{- define "liferay.getFullLicenseSecretName" -}}
{{- $trimmed := (. | trimPrefix "/") -}}
{{- if hasPrefix "liferay/licenses/" $trimmed -}}
{{- $trimmed -}}
{{- else }}
{{- printf "liferay/licenses/%s" $trimmed -}}
{{- end }}