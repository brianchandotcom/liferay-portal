resource "aws_iam_policy" "secret_store_policy" {
	count=local.secret_store_provider_default_enabled ? 1 : 0
	name="${local.cluster_name}-secret-store-policy"
	policy=jsonencode(
		{
			Statement=[
				{
					Action=[
						"secretsmanager:DescribeSecret",
						"secretsmanager:GetSecretValue",
					]
					Effect="Allow"
					Resource=concat(
						[
							"arn:aws:secretsmanager:${var.region}:${local.account_id}:secret:${local.secret_prefixes.certificates}*",
							"arn:aws:secretsmanager:${var.region}:${local.account_id}:secret:${local.secret_prefixes.credentials}*",
							"arn:aws:secretsmanager:${var.region}:${local.account_id}:secret:${local.secret_prefixes.licenses}*",
						],
						[
							for git_repo_auth_config in local.git_repo_auth_configs : "arn:aws:secretsmanager:${var.region}:${local.account_id}:secret:${git_repo_auth_config.credentials_secret_name}*"
						])
				},
			]
			Version="2012-10-17"
		})
}
resource "aws_iam_role" "secret_store_role" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRoleWithWebIdentity"
					Condition={
						StringEquals={
							"${local.oidc_provider}:aud"="sts.amazonaws.com",
							"${local.oidc_provider}:sub"="system:serviceaccount:${var.external_secrets_namespace}:secret-store-sa"
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
	name="${local.cluster_name}-secret-store"
}
resource "aws_iam_role_policy_attachment" "secret_store_policy_attachment" {
	count=local.secret_store_provider_default_enabled ? 1 : 0
	policy_arn=aws_iam_policy.secret_store_policy[0].arn
	role=aws_iam_role.secret_store_role[0].name
}
resource "kubernetes_service_account" "secret_store_sa" {
	automount_service_account_token=false
	count=local.secret_store_provider_default_enabled ? 1 : 0
	metadata {
		annotations={
			"eks.amazonaws.com/role-arn"=aws_iam_role.secret_store_role[0].arn
		}
		labels=local.common_labels
		name="secret-store-sa"
		namespace=var.external_secrets_namespace
	}
}