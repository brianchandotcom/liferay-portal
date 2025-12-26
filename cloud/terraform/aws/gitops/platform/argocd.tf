resource "helm_release" "argocd" {
	chart="argo-cd"
	create_namespace=false
	depends_on=[
		kubernetes_namespace.argocd,
		kubernetes_secret.argocd_secret,
	]
	name="argocd"
	namespace=var.argocd_namespace
	repository="https://argoproj.github.io/argo-helm"
	upgrade_install=true
	values=[
		yamlencode(
			{
				installCRDs=true
				server={
					resources={
						requests={
							cpu="100m"
							memory="128Mi"
						}
						limits={
							cpu="200m"
							memory="256Mi"
						}
					}
					service={
						type="ClusterIP"
					}
				}
			})
	]
	version="9.1.5"
	wait=true
}
resource "kubernetes_namespace" "argocd" {
	metadata {
		labels=local.common_labels
		name=var.argocd_namespace
	}
}
resource "kubernetes_secret" "argocd_secret" {
	data={
		"server.secretkey"=random_password.argocd_server_secretkey.result
	}
	metadata {
		annotations={
			"meta.helm.sh/release-name"="argocd"
			"meta.helm.sh/release-namespace"=var.argocd_namespace
		}
		labels=merge(
			local.common_labels,
			{
				"app.kubernetes.io/managed-by"="Helm"
			})
		name="argocd-secret"
		namespace=var.argocd_namespace
	}
	type="Opaque"
}
resource "random_password" "argocd_server_secretkey" {
	length=32
	special=false
}