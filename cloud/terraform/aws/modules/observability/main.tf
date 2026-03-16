data "aws_region" "current" {}

resource "aws_grafana_workspace" "amg" {
	account_access_type="CURRENT_ACCOUNT"
	authentication_providers=["AWS_SSO"]
	name="${var.deployment_name}-amg-workspace"
	permission_type="SERVICE_MANAGED"
	role_arn=aws_iam_role.grafana.arn
}

resource "aws_grafana_workspace_api_key" "amg_api_key" {
	key_name="terraform-api-key"
	key_role="ADMIN"
	seconds_to_live=3600
	workspace_id=aws_grafana_workspace.amg.id
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
	name="${var.deployment_name}-grafana-role"
}

resource "aws_iam_role_policy" "amp_access" {
	name="${var.deployment_name}-amg-amp-access-policy"
	policy=jsonencode(
		{
			Statement=[
				{
					Effect="Allow",
					Action=[
						"aps:ListWorkspaces",
						"aps:DescribeWorkspace",
						"aps:QueryMetrics",
						"aps:GetLabels",
						"aps:GetSeries",
						"aps:GetMetricMetadata"
					],
					Resource="*"
				}
			]
			Version="2012-10-17"
		})
	role=aws_iam_role.grafana.id
}

resource "aws_prometheus_workspace" "amp" {
	alias="${var.deployment_name}-amp-workspace"
}

resource "grafana_data_source" "amp" {
	is_default=true
	json_data_encoded=jsonencode(
		{
			sigv4_assume_role_arn=aws_iam_role.grafana.arn
			sigv4_auth=true
			sigv4_auth_type="workspace-iam-role"
			sigv4_region=data.aws_region.current.name
		})
	name="amp"
	type="prometheus"
	url=aws_prometheus_workspace.amp.prometheus_endpoint
}

terraform {
	required_providers {
		grafana={
			source="grafana/grafana"
			version="~> 3.15.2"
		}
	}
}