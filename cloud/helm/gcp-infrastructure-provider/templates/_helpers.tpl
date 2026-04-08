{{- define "crossplane-runtime-configs.containerSecurityContext" -}}
securityContext:
    allowPrivilegeEscalation: false
    capabilities:
        drop:
            -   ALL
    privileged: false
    readOnlyRootFilesystem: true
{{- end -}}

{{- define "crossplane-runtime-configs.podSecurityContext" -}}
securityContext:
    fsGroup: 2000
    runAsGroup: 2000
    runAsNonRoot: true
    runAsUser: 2000
    seccompProfile:
        type: RuntimeDefault
{{- end -}}

{{- define "liferay.ksaPrincipal" -}}
principal://iam.googleapis.com/projects/{{ .Values.gcp.projectNumber }}/locations/global/workloadIdentityPools/{{ .Values.gcp.projectId }}.svc.id.goog/subject/ns/{{ .Release.Namespace }}/sa/{{ .Values.global.backupServiceAccountName }}
{{- end -}}