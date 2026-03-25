locals {
	cloudplatform_roles=[
		"roles/iam.serviceAccountAdmin",
		"roles/resourcemanager.projectIamAdmin",
	]
	common_labels={
		"app.kubernetes.io/component"="gitops-infrastructure"
		"app.kubernetes.io/managed-by"=local.terraform_manager_name
		"app.kubernetes.io/part-of"="liferay-gitops"
		"environment"="internal"
		"liferay.com/project"="liferay-cloud-native"
	}
	default_crossplane_container_security_context={
		allowPrivilegeEscalation=false
		capabilities={
			drop=["ALL"]
		}
		privileged=false
		readOnlyRootFilesystem=true
	}
	default_crossplane_pod_security_context={
		fsGroup=2000
		runAsGroup=2000
		runAsNonRoot=true
		runAsUser=2000
		seccompProfile={
			type="RuntimeDefault"
		}
	}
	direct_provider_ksas={
		compute="roles/compute.admin"
		sql="roles/cloudsql.admin"
		storage="roles/storage.admin"
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
	ksa_principal_base="principal://iam.googleapis.com/projects/${data.google_project.project.number}/locations/global/workloadIdentityPools/${var.project_id}.svc.id.goog/subject/ns/${var.crossplane_namespace}/sa"
	secret_prefixes={
		certificates="liferay-certificates-"
		licenses="liferay-licenses-"
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
