resource "aws_iam_role" "aws_marketplace_role" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRoleWithWebIdentity"
					Condition={
						StringEquals={
							"${local.oidc_provider}:aud": "sts.amazonaws.com",
							"${local.oidc_provider}:sub": "system:serviceaccount:${local.liferay_namespace_pattern}:${local.aws_marketplace_serviceaccount_name_prefix}*"
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
	name=local.aws_marketplace_role_name
}
resource "aws_iam_role_policy" "aws_marketplace_metering_policy" {
	name="${var.deployment_name}-marketplace-metering"
	policy=jsonencode({
		Statement=[
			{
				Action=["aws-marketplace:RegisterUsage"]
				Effect="Allow"
				Resource="*"
			},
		]
		Version="2012-10-17"
	})
	role=aws_iam_role.aws_marketplace_role.id
}
