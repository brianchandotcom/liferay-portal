resource "kubernetes_manifest" "liferay_applicationset" {
	depends_on=[
		kubernetes_manifest.external_secret,
	]
	manifest={
		apiVersion="argoproj.io/v1alpha1"
		kind="ApplicationSet"
		metadata={
			name="liferay-dxp-environments"
			namespace=var.argocd_namespace
		}
		spec={
			generators=[
				{
					git={
						files=[
							{
								path=var.git_repo_liferay_application_environments_pattern
							}
						]
						repoURL=var.git_repo_url
						revision="HEAD"
					}
				}
			]
			template={
				metadata={
					name="liferay-dxp-${"{{path.basename}}"}"
				}
				spec={
					project="default"
					sources: [
						{
							chart: var.liferay_helm_chart_name,
							helm={
								valueFiles: [
									"$values/${var.git_repo_liferay_application_base_path}/values.yaml",
									"$values/{{path}}/values.yaml",
								],
							}
							repoURL: "${var.liferay_helm_chart_repo}/${var.liferay_helm_chart_name}",
							targetRevision: var.liferay_helm_chart_version,
						},
						{
							ref="values"
							repoURL=var.git_repo_url,
							targetRevision="HEAD",
						},
					]
					destination={
						namespace="liferay-${"{{path.basename}}"}"
						server="https://kubernetes.default.svc"
					}
					syncPolicy={
						automated={
							prune=true
							selfHeal=true
						}
						syncOptions=[
							"ApplyOutOfSyncOnly=true",
							"CreateNamespace=true",
						]
					}
				}
			}
		}
	}
}