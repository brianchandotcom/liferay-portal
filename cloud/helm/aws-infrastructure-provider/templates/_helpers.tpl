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

{{- define "aws-infrastructure-provider.overlayBucketName" -}}
{{- print `{{- $overlayUidHash := printf "%s-%s-%s" $accountId $deploymentName $projectId | sha256sum | trunc 6 -}}
{{- $overlayBaseName := printf "%.18s-%s" $projectId $overlayUidHash -}}
{{- $overlayBucketName := printf "%s-overlay-%s" $deploymentName $overlayBaseName -}}` -}}
{{- end -}}