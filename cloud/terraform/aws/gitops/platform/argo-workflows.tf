resource "helm_release" "argo_workflows" {
	chart="argo-workflows"
	depends_on=[
		kubernetes_namespace.argocd,
	]
	name="argo-workflows"
	namespace=var.argo_workflows_namespace
	repository="https://argoproj.github.io/argo-helm"
	upgrade_install=true
	version="0.47.3"
}
resource "kubernetes_namespace" "argo_workflows" {
	metadata {
		labels=local.common_labels
		name=var.argo_workflows_namespace
	}
}