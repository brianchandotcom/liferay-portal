locals {
	common_labels={
		"app.kubernetes.io/component"="gitops-infrastructure"
		"app.kubernetes.io/managed-by"=local.terraform_manager_name
		"app.kubernetes.io/part-of"="liferay-gitops"
		"environment"="internal"
		"liferay.com/project"="liferay-cloud-native"
	}
	git_repo_auth_configs=merge(
		local.git_repo_infrastructure_separate_from_liferay ? {
			"infrastructure"=merge(
				var.infrastructure_git_repo_config.auth,
				{
					url=local.infrastructure_git_repo_url
			})
		} : {},
		{
			"liferay"=merge(
				var.liferay_git_repo_config.auth,
				{
					url=var.liferay_git_repo_url
			})
		}
	)
	git_repo_infrastructure_separate_from_liferay=local.infrastructure_git_repo_url != var.liferay_git_repo_url
	infrastructure_git_repo_url=coalesce(var.infrastructure_git_repo_config.url, var.liferay_git_repo_url)
	secret_prefixes={
		certificates="liferay/certificates/"
		licenses="liferay/licenses/"
	}
	secret_store_name="${var.deployment_name}-secret-store"
	secret_store_provider_default={
		gcpsm={
			projectID=var.project_id
		}
	}
	secret_store_provider_default_enabled=var.external_secret_store_provider_hcl == null
	secret_store_provider_hcl=local.secret_store_provider_default_enabled ? local.secret_store_provider_default : var.external_secret_store_provider_hcl
	terraform_manager_name="liferay-cloud-native-terraform"
}
