resource "kubernetes_manifest" "git_repo_credentials_external_secret" {
	depends_on=[
		kubernetes_manifest.git_repo_credentials_secret_store,
	]
	field_manager {
		force_conflicts=true
		name=local.terraform_manager_name
	}
	for_each=local.git_repo_auth_configs
	manifest={
		apiVersion="external-secrets.io/v1"
		kind="ExternalSecret"
		metadata={
			labels=local.common_labels
			name=each.value.internal_secret_name
			namespace=var.argocd_namespace
		}
		spec={
			data=flatten(
				[
					each.value.method == "https" ? [
						{
							remoteRef={
								key=each.value.vault_secret_name
								property=each.value.username_vault_secret_property
							}
							secretKey="username"
						},
						{
							remoteRef={
								key=each.value.vault_secret_name
								property=each.value.token_vault_secret_property
							}
							secretKey="password"
						},
					] : [],
					each.value.method == "ssh" ? [
						{
							remoteRef={
								key=each.value.vault_secret_name
								property=each.value.ssh_private_key_vault_secret_property
							}
							secretKey="ssh_private_key"
						},
					] : [],
				])
			refreshInterval="1h0m0s"
			secretStoreRef={
				kind="SecretStore"
				name=each.value.secret_store_name
			}
			target={
				creationPolicy="Owner"
				name=each.value.internal_secret_name
				template={
					data=merge(
						{
							name="git-repo-${each.key}"
							type="git"
							url=each.value.url
						},
						each.value.method == "https" ? {
							password="{{ .password }}"
							username="{{ .username }}"
						} : {},
						each.value.method == "ssh" ? {
							sshPrivateKey="{{ .ssh_private_key }}"
						} : {})
					metadata={
						labels=merge(
							local.common_labels,
							{
								"app.kubernetes.io/name"=each.value.internal_secret_name
								"argocd.argoproj.io/secret-type"="repository"
							})
					}
					type="Opaque"
				}
			}
		}
	}
}
resource "kubernetes_manifest" "git_repo_credentials_secret_store" {
	field_manager {
		force_conflicts=true
		name=local.terraform_manager_name
	}
	for_each=local.git_repo_auth_configs
	manifest={
		apiVersion="external-secrets.io/v1"
		kind="SecretStore"
		metadata={
			labels=merge(
				local.common_labels,
				{
					"app.kubernetes.io/name"=each.value.secret_store_name
				})
			name=each.value.secret_store_name
			namespace=var.argocd_namespace
		}
		spec={
			provider=yamldecode(
				jsonencode(each.value.secret_store_provider_hcl))
		}
	}
}
resource "kubernetes_role" "eso_secret_writer" {
	metadata {
		labels=merge(
			local.common_labels,
			{
				"app.kubernetes.io/name"="eso-secret-writer"
			})
		name="eso-argocd-git-repo-auth-writer"
		namespace=var.argocd_namespace
	}
	rule {
		api_groups=[""]
		resources=["secrets"]
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
		labels=merge(
			local.common_labels,
			{
				"app.kubernetes.io/name"="eso-secret-writer-binding"
			})
		name="eso-argocd-git-repo-auth-binding"
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
		namespace=var.external_secrets_namespace
	}
}