resource "aws_iam_role_policy" "aws_marketplace_metering_policy" {
	count=local.aws_marketplace_enabled ? 1 : 0
	name="${var.deployment_name}-marketplace-metering"
	policy=jsonencode(
		{
			Statement=[
				{
					Action=[
						"aws-marketplace:BatchMeterUsage",
						"aws-marketplace:RegisterUsage",
					]
					Effect="Allow"
					Resource="*"
				},
			]
			Version="2012-10-17"
		})
	role=local.liferay_service_account_role_name
}
