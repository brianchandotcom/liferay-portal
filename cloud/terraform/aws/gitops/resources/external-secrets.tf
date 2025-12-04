locals {
	argocd_repo_credentials_secret_name="argocd-repo-credentials"
	secret_store_name="eso-default-store"
}
resource "kubernetes_manifest" "external_secret" {
	depends_on=[
		kubernetes_manifest.secret_store,
	]
	field_manager {
		force_conflicts=true
		name="terraform-external-secrets-manager"
	}
	manifest={
		apiVersion="external-secrets.io/v1"
		kind="ExternalSecret"
		metadata={
			name=local.argocd_repo_credentials_secret_name
			namespace=var.argocd_namespace
		}
		spec={
			data=flatten(
				[
					var.git_repo_auth_method == "https" ? [
						{
							remoteRef={
								key=var.remote_secret_key
								property=var.git_username_property
							}
							secretKey="username"
						},
						{
							remoteRef={
								key=var.remote_secret_key
								property=var.git_token_property
							}
							secretKey="password"
						},
					] : [],
					var.git_repo_auth_method == "ssh" ? [
						{
							remoteRef={
								key=var.remote_secret_key
								property=var.git_ssh_private_key_property
							}
							secretKey="ssh_private_key"
						},
					] : [],
				])
			refreshInterval="1h"
			secretStoreRef={
				kind="SecretStore"
				name=local.secret_store_name
			}
			target={
				creationPolicy="Owner"
				name=local.argocd_repo_credentials_secret_name
				template={
					data={
						"password"=var.git_repo_auth_method == "https" ? "{{ .password }}" : null
						"sshPrivateKey"=var.git_repo_auth_method == "ssh" ? "{{ .ssh_private_key }}" : null
						"type"="git"
						"url"=var.git_repo_url
						"username"=var.git_repo_auth_method == "https" ? "{{ .username }}" : null
					}
					metadata = {
						labels = {
							"argocd.argoproj.io/secret-type"="repository"
						}
					}
					type="Opaque"
				}
			}
		}
	}
}
resource "kubernetes_manifest" "secret_store" {
	manifest={
		apiVersion="external-secrets.io/v1"
		kind="SecretStore"
		metadata={
			name=local.secret_store_name
			namespace=var.argocd_namespace
		}
		spec={
			provider=yamldecode(var.secret_store_provider_yaml_spec)
		}
	}
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