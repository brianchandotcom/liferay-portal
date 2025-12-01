locals {
	argocd_eso_custom_resources_chart="custom-resources"
	argocd_repo_credentials_secret_name="argocd-repo-credentials"
	secret_store_name="eso-default-store"
}
resource "helm_release" "argocd_eso_custom_resources" {
	chart="${path.module}/${local.argocd_eso_custom_resources_chart}"
	depends_on=[
		helm_release.external_secrets,
		kubernetes_role_binding.eso_secret_writer_binding,
	]
	name="argocd-secrets"
	namespace=var.argocd_namespace
	values=[
		yamlencode(
			{
				argocdSecretName=local.argocd_repo_credentials_secret_name
				gitTokenProperty=var.git_token_property
				gitUsernameProperty=var.git_username_property
				namespace=var.argocd_namespace
				remoteSecretKey=var.remote_secret_key
				secretStoreName=local.secret_store_name
				secretStoreProviderYaml=var.secret_store_provider_yaml_spec
			})
	]
}
resource "helm_release" "external_secrets" {
	chart="external-secrets"
	create_namespace=true
	depends_on=[
		null_resource.cleanup_eso_webhooks,
	]
	name="external-secrets"
	namespace="external-secrets"
	repository="https://charts.external-secrets.io"
	values=[
		yamlencode(
			{
				certController={
					resources={
						limits={
							cpu="20m"
							memory="64Mi"
						}
						requests={
							cpu="10m"
							memory="32Mi"
						}
					}
				}
				installCRDs=false
				resources={
					limits={
						cpu="100m"
						memory="128Mi"
					}
					requests={
						cpu="50m"
						memory="64Mi"
					}
				}
				webhook={
					resources={
						limits={
							cpu="20m"
							memory="64Mi"
						}
						requests={
							cpu="10m"
							memory="32Mi"
						}
					}
				}
			})
	]
	version="1.0.0"
}
resource "helm_release" "external_secrets_crds" {
	chart="external-secrets"
	name="external-secrets-crds"
	namespace="kube-system"
	repository="https://charts.external-secrets.io"
	values=[
		yamlencode(
			{
				certController={
					enabled=false
				}
				installCRDs=true
				installController=false
				webhook={
					enabled=false
				}
			})
	]
	version="1.0.0"
}
resource "kubernetes_role" "eso_secret_writer" {
	metadata {
		name="eso-${local.argocd_repo_credentials_secret_name}-writer"
		namespace=var.argocd_namespace
	}
	rule {
		api_groups=[
			""
		]
		resources=[
			"secrets"
		]
		verbs=[
			"create",
			"delete",
			"get",
			"update",
			"watch",
		] 
	}
}
resource "kubernetes_role_binding" "eso_secret_writer_binding" {
	metadata {
		name="eso-${local.argocd_repo_credentials_secret_name}-binding"
		namespace=var.argocd_namespace
	}
	role_ref {
		api_group="rbac.authorization.k8s.io"
		kind="Role"
		name=kubernetes_role.eso_secret_writer.metadata[0].name
	}
	subject {
		kind="ServiceAccount"
		name="external-secrets"
		namespace="external-secrets"
	}
}
resource "null_resource" "cleanup_eso_webhooks" {
	depends_on=[
		helm_release.external_secrets_crds,
	]
	provisioner "local-exec" {
		command="kubectl delete ValidatingWebhookConfiguration externalsecret-validate secretstore-validate || true"
	}
}