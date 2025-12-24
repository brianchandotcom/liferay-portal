variable "cluster_name" {
	type=string
}
variable "argocd_namespace" {
	default="argocd"
	type=string
}
variable "external_secrets_namespace" {
	default="external-secrets"
	type=string
}
variable "git_repo_auth_config" {
	default={}
	type=object(
		{
			enable_aws_secretmanager_default_irsa=optional(bool, false)
			method=optional(string, "https")
			secret_store_provider_hcl_spec=optional(any, null)
			ssh_private_key_vault_secret_property=optional(string, "git_ssh_private_key")
			token_vault_secret_property=optional(string, "git_access_token")
			username_vault_secret_property=optional(string, "git_machine_user_id")
			vault_secret_name=optional(string, "argocd/pat/gitops-source-of-truth")
		})
	validation {
		condition=(
			!contains(keys(var.git_repo_auth_config), "method") ||
			contains(["https", "ssh"], var.git_repo_auth_config.method))
		error_message="The 'git_repo_auth_config.method' value must be 'https' or 'ssh'."
	}
	validation {
		condition=(
			!var.git_repo_auth_config.enable_aws_secretmanager_default_irsa ||
			var.git_repo_auth_config.secret_store_provider_hcl_spec == null)
		error_message="Do not set secret_store_provider_hcl_spec when enable_aws_secretmanager_default_irsa is true."
	}
	validation {
		condition=(
			var.git_repo_auth_config.enable_aws_secretmanager_default_irsa ||
			var.git_repo_auth_config.secret_store_provider_hcl_spec != null)
		error_message="Either secret_store_provider_hcl_spec must be defined or enable_aws_secretmanager_default_irsa must be true."
	}
}
variable "git_repo_paths" {
	default={}
	type=object(
		{
			liferay_application_base_path=optional(string, "applications/liferay/base")
			liferay_application_environments_pattern=optional(string, "applications/liferay/environments/**/values.yaml")
		})
}
variable "git_repo_url" {
	type=string
}
variable "liferay_helm_chart_name" {
	default="liferay-aws"
	type=string
	validation {
		condition=contains(
			[
				"liferay-default",
				"liferay-aws",
				"liferay-aws-marketplace",
			],
			var.liferay_helm_chart_name)
		error_message="The 'liferay_helm_chart_name' value must be 'liferay-default', 'liferay-aws', or 'liferay-aws-marketplace'."
	}
}
variable "liferay_helm_chart_version" {
	type=string
}
variable "region" {
	default="us-west-2"
}