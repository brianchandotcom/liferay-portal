resource "aws_iam_policy" "argocd_git_repo_auth_policy" {
	count=local.secret_store_provider_default_enabled ? 1 : 0
	name="${var.cluster_name}-argocd-git-repo-auth-policy"
	policy=jsonencode(
		{
			Statement=[
				{
					Action=[
						"secretsmanager:DescribeSecret",
						"secretsmanager:GetSecretValue",
					]
					Effect="Allow"
					Resource="arn:aws:secretsmanager:${var.region}:${local.account_id}:secret:${var.git_repo_auth_config.vault_secret_name}*"
				},
			]
			Version="2012-10-17"
		})
}
resource "aws_iam_role" "argocd_git_repo_auth_role" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRoleWithWebIdentity"
					Condition={
						StringEquals={
							"${local.oidc_provider}:aud"="sts.amazonaws.com",
							"${local.oidc_provider}:sub"="system:serviceaccount:${var.argocd_namespace}:argocd-git-repo-auth-sa"
						}
					}
					Effect="Allow"
					Principal={
						Federated="arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"
					}
				},
			]
			Version="2012-10-17"
		})
	count=local.secret_store_provider_default_enabled ? 1 : 0
	name="${var.cluster_name}-argocd-git-repo-auth"
}
resource "aws_iam_role_policy_attachment" "argocd_git_repo_auth_policy_attachment" {
	count=local.secret_store_provider_default_enabled ? 1 : 0
	policy_arn=aws_iam_policy.argocd_git_repo_auth_policy[0].arn
	role=aws_iam_role.argocd_git_repo_auth_role[0].name
}
resource "kubernetes_service_account" "argocd_git_repo_auth_sa" {
	automount_service_account_token=false
	count=local.secret_store_provider_default_enabled ? 1 : 0
	metadata {
		annotations={
			"eks.amazonaws.com/role-arn"=aws_iam_role.argocd_git_repo_auth_role[0].arn
		}
		labels=local.common_labels
		name="argocd-git-repo-auth-sa"
		namespace=var.argocd_namespace
	}
}