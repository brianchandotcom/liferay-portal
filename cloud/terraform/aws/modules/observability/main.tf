resource "aws_grafana_workspace" "amg" {
	account_access_type="CURRENT_ACCOUNT"
	authentication_providers=["AWS_SSO"]
	name="${var.deployment_name}-amg-workspace"
	permission_type="SERVICE_MANAGED"
	role_arn=aws_iam_role.grafana.arn
}
resource "aws_iam_role" "grafana" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRole",
					Effect="Allow",
					Principal={
						Service="grafana.amazonaws.com"
					}
				}
			]
			Version="2012-10-17"
		})
	name="grafana-role"
}
resource "aws_prometheus_workspace" "amp" {
	alias="${var.deployment_name}-amp-workspace"
}