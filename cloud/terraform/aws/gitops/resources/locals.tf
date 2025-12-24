locals {
	account_id=data.aws_caller_identity.current.account_id
	argocd_ecr_credentials_secret_name="argocd-ecr-credentials"
	argocd_git_credentials_secret_name="argocd-git-credentials"
	common_labels={
		"app.kubernetes.io/managed-by"="terraform"
		"app.kubernetes.io/part-of"="liferay-gitops"
		"environment"="internal"
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
			--from-literal=url=${local.liferay_helm_chart_config.source_repourl_value} \
			--from-literal=username=AWS \
			--namespace ${var.argocd_namespace} | kubectl apply -f -

		kubectl label secret argocd-ecr-credentials argocd.argoproj.io/secret-type=repository \
			--namespace ${var.argocd_namespace} \
			--overwrite
	EOT
	ecr_credentials_sync_serviceaccount_name="ecr-credentials-sync-sa"
	liferay_appproject_name="liferay-cloud-native"
	liferay_helm_chart_config=merge(
		{
			version=var.liferay_helm_chart_version
		},
		var.liferay_helm_chart_name == "liferay-default" ? {
			ecr_credentials_sync_required=false
			name="liferay-default"
			region=var.region
			source_chart_value="liferay-default"
			source_repourl_value="oci://us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart/liferay-default"
		} : {},
		var.liferay_helm_chart_name == "liferay-aws" ? {
			ecr_credentials_sync_required=false
			name="liferay-aws"
			region=var.region
			source_chart_value="liferay-aws"
			source_repourl_value="oci://us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart/liferay-aws"
		} : {},
		var.liferay_helm_chart_name == "liferay-aws-marketplace" ? {
			ecr_credentials_sync_required=true
			name="liferay-aws-marketplace"
			region="us-east-1"
			source_chart_value="liferay/liferay-aws-marketplace"
			source_repourl_value="709825985650.dkr.ecr.us-east-1.amazonaws.com"
		} : {},
	)
	oidc_provider=replace(data.aws_eks_cluster.target.identity[0].oidc[0].issuer, "https://", "")
	secret_store_name="argocd-git-credentials-vault"
	secret_store_provider=var.git_repo_auth_config.enable_aws_secretmanager_default_irsa ? {
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
	} : (var.git_repo_auth_config.secret_store_provider_hcl_spec != null ? var.git_repo_auth_config.secret_store_provider_hcl_spec : {})
}