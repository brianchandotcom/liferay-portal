resource "kubernetes_manifest" "git_repo_credentials_external_secret" {
	depends_on=[
		kubernetes_manifest.secret_store,
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
								key=each.value.credentials_secret_name
								property=each.value.username_property
							}
							secretKey="username"
						},
						{
							remoteRef={
								key=each.value.credentials_secret_name
								property=each.value.token_property
							}
							secretKey="password"
						},
					] : [],
					each.value.method == "ssh" ? [
						{
							remoteRef={
								key=each.value.credentials_secret_name
								property=each.value.ssh_private_key_property
							}
							secretKey="ssh_private_key"
						},
					] : [],
				])
			refreshInterval="1h0m0s"
			secretStoreRef={
				kind="ClusterSecretStore"
				name=local.secret_store_name
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