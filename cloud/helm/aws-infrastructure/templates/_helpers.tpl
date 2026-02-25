{{- define "elasticsearch.name" -}}
{{- .Values.search.elasticsearch.name | default (printf "%s-es" .Release.Name) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "liferay.k8sFriendlyString" -}}
{{- $sanitized := . | lower | replace "/" "-" | replace "_" "-" | trimPrefix "-" | trunc 63 | trimSuffix "-" -}}
{{- if or (empty $sanitized) (not (regexMatch "^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$" $sanitized)) -}}
{{- fail (printf "'%s' could not be sanitized to a valid DNS-1123 string." .) -}}
{{- end -}}
{{- $sanitized -}}
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
{{- end -}}