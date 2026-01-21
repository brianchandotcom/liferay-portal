locals {
	account_id=data.aws_caller_identity.current.account_id
	cluster_name="${var.deployment_name}-eks"
	common_labels={
		"app.kubernetes.io/component"="gitops-infrastructure"
		"app.kubernetes.io/managed-by"=local.terraform_manager_name
		"app.kubernetes.io/part-of"="liferay-gitops"
		"environment"="internal"
		"liferay.com/project"="liferay-cloud-native"
	}
	ecr_credentials_sync_image="alpine/k8s:1.29.1"
	ecr_credentials_sync_schedule="0 */8 * * *"
	ecr_credentials_sync_script=<<-EOT
		set -euo pipefail

		TOKEN=$(aws ecr get-login-password --region ${local.liferay_helm_chart_config.region})

		if [ -z "$TOKEN" ]; then
			echo "Failed to fetch ECR token"
			exit 1
		fi

		kubectl create secret generic argocd-ecr-credentials \
			--dry-run=client -o yaml \
			--from-literal=enableOCI=true \
			--from-literal=name=liferay-helm-chart \
			--from-literal=password="$TOKEN" \
			--from-literal=project=${local.liferay_appproject_name} \
			--from-literal=type=helm \
			--from-literal=url=${local.liferay_helm_chart_config.source_repo_url_value} \
			--from-literal=username=AWS \
			--namespace ${var.argocd_namespace} | kubectl apply -f -

		kubectl label secret argocd-ecr-credentials argocd.argoproj.io/secret-type=repository \
			--namespace ${var.argocd_namespace} \
			--overwrite
	EOT
	ecr_credentials_sync_serviceaccount_name="ecr-credentials-sync-sa"
	git_repo_auth_configs=merge(
		local.git_repo_infrastructure_separate_from_liferay ? {
			"infrastructure"=merge(
				var.infrastructure_git_repo_config.auth,
				{
					secret_store_name="infrastructure-git-repo-credentials-vault"
					secret_store_provider_hcl=(
						var.infrastructure_git_repo_config.auth.secret_store_provider_hcl == null
							? local.git_repo_secret_store_provider_default
							: var.infrastructure_git_repo_config.auth.secret_store_provider_hcl
					)
					url=local.infrastructure_git_repo_url
				})
		} : {},
		{
			"liferay"=merge(
				var.liferay_git_repo_config.auth,
				{
					secret_store_name="liferay-git-repo-credentials-vault"
					secret_store_provider_hcl=var.liferay_git_repo_config.auth.secret_store_provider_hcl == null ? local.git_repo_secret_store_provider_default : var.liferay_git_repo_config.auth.secret_store_provider_hcl
					url=var.liferay_git_repo_url
				})
		})
	git_repo_infrastructure_separate_from_liferay=local.infrastructure_git_repo_url != var.liferay_git_repo_url
	git_repo_secret_store_provider_default={
		aws={
			auth={
				jwt={
					serviceAccountRef={
						name="argocd-git-repo-auth-sa"
						namespace=var.argocd_namespace
					}
				}
			}
			region=var.region
			service="SecretsManager"
		}
	}
	git_repo_secret_store_provider_default_enabled=(
		var.infrastructure_git_repo_config.auth.secret_store_provider_hcl == null ||
		var.liferay_git_repo_config.auth.secret_store_provider_hcl == null
	)
	infrastructure_appproject_name="liferay-infrastructure"
	infrastructure_git_repo_url=coalesce(var.infrastructure_git_repo_config.url, var.liferay_git_repo_url)
	liferay_appproject_name="liferay-application"
	liferay_helm_chart_config=merge(
		{
			version=var.liferay_helm_chart_version
		},
		var.liferay_helm_chart_name == "liferay-default" ? {
			ecr_credentials_sync_required=false
			name="liferay-default"
			region=var.region
			source_chart_value="liferay-default"
			source_repo_url_value="oci://us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart/liferay-default"
			values_scope_prefix=""
		} : {},
		var.liferay_helm_chart_name == "liferay-aws" ? {
			ecr_credentials_sync_required=false
			name="liferay-aws"
			region=var.region
			source_chart_value="liferay-aws"
			source_repo_url_value="oci://us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart/liferay-aws"
			values_scope_prefix="liferay-default."
		} : {},
		var.liferay_helm_chart_name == "liferay-aws-marketplace" ? {
			ecr_credentials_sync_required=true
			name="liferay-aws-marketplace"
			region="us-east-1"
			source_chart_value="liferay/liferay-aws-marketplace"
			source_repo_url_value="709825985650.dkr.ecr.us-east-1.amazonaws.com"
			values_scope_prefix="liferay-aws.liferay-default."
		} : {},
	)
	liferay_service_account_role_name="${var.deployment_name}-irsa"
	oidc_provider=replace(data.aws_eks_cluster.cluster.identity[0].oidc[0].issuer, "https://", "")
	should_create_opensearch_linked_role=length(data.aws_iam_roles.opensearch_linked_role_lookup.arns) == 0
	terraform_manager_name="liferay-cloud-native-terraform"
}