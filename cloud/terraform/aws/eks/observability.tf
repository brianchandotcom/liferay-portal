locals {
	aws_recording_rules=[
		for k, v in local.observability_rules :
		{
			expr=v.expr
			record=v.record
		}
		if try(v.cloudProvider, "aws") == "aws"
	]
	observability_interval=try(local.observability_values.alerting.interval, "1m")
	observability_rules=try(local.observability_values.alerting.recordingRules, {})
	observability_values=yamldecode(file("${path.module}/../../../helm/observability/values.yaml"))
}
module "alloy_role" {
	amazon_managed_service_prometheus_workspace_arns=aws_prometheus_workspace.amp[*].arn
	attach_amazon_managed_service_prometheus_policy=true
	create=var.observability_config.enabled
	name="${var.deployment_name}-alloy"
	oidc_providers={
		main={
			namespace_service_accounts=[
				"${var.observability_config.namespace}:liferay-alloy",
			]
			provider_arn=local.oidc_provider_arn
		}
	}
	policies={
		cloudwatch_rds=try(aws_iam_policy.alloy_cloudwatch_rds[0].arn, "")
	}
	policy_name="${var.deployment_name}-alloy"
	source="git::https://github.com/terraform-aws-modules/terraform-aws-iam.git//modules/iam-role-for-service-accounts?ref=277e8947b1267290988e47882d8dc116850929be"
	use_name_prefix=false
}
module "grafana_role" {
	create=var.observability_config.enabled
	name="${var.deployment_name}-grafana"
	oidc_providers={
		main={
			namespace_service_accounts=[
				"${var.observability_config.namespace}:grafana",
			]
			provider_arn=local.oidc_provider_arn
		}
	}
	policies={
		prometheus_query="arn:aws:iam::aws:policy/AmazonPrometheusQueryAccess"
	}
	source="git::https://github.com/terraform-aws-modules/terraform-aws-iam.git//modules/iam-role-for-service-accounts?ref=277e8947b1267290988e47882d8dc116850929be"
	use_name_prefix=false
}
module "rds_exporter_role" {
	create=var.observability_config.enabled
	name="${var.deployment_name}-rds-exporter"
	oidc_providers={
		main={
			namespace_service_accounts=[
				"${var.observability_config.namespace}:liferay-rds-exporter",
			]
			provider_arn=local.oidc_provider_arn
		}
	}
	policies={
		rds_describe=try(aws_iam_policy.rds_exporter[0].arn, "")
	}
	policy_name="${var.deployment_name}-rds-exporter"
	source="git::https://github.com/terraform-aws-modules/terraform-aws-iam.git//modules/iam-role-for-service-accounts?ref=277e8947b1267290988e47882d8dc116850929be"
	use_name_prefix=false
}
resource "aws_iam_policy" "alloy_cloudwatch_rds" {
	count=var.observability_config.enabled ? 1 : 0
	description="Allows the Alloy IRSA role to read CloudWatch RDS metrics and discover RDS instances by tag."
	name="${var.deployment_name}-alloy-cloudwatch-rds"
	policy=jsonencode({
		Statement=[
			{
				Action=[
					"cloudwatch:GetMetricData",
					"cloudwatch:GetMetricStatistics",
					"cloudwatch:ListMetrics",
				]
				Effect="Allow"
				Resource="*"
				Sid="CloudWatchMetricsRead"
			},
			{
				Action=["tag:GetResources"]
				Effect="Allow"
				Resource="*"
				Sid="TagDiscovery"
			},
			{
				Action=[
					"rds:DescribeDBInstances",
					"rds:ListTagsForResource",
				]
				Effect="Allow"
				Resource="*"
				Sid="RDSDescribe"
			},
		]
		Version="2012-10-17"
	})
}
resource "aws_iam_policy" "rds_exporter" {
	count=var.observability_config.enabled ? 1 : 0
	description="Allows the prometheus-rds-exporter IRSA role to describe RDS instances/clusters and read CloudWatch metrics."
	name="${var.deployment_name}-rds-exporter"
	policy=jsonencode({
		Statement=[
			{
				Action=[
					"rds:DescribeDBInstances",
					"rds:DescribeDBClusters",
					"rds:ListTagsForResource",
				]
				Effect="Allow"
				Resource="*"
				Sid="RDSDescribe"
			},
			{
				Action=[
					"cloudwatch:GetMetricData",
					"cloudwatch:GetMetricStatistics",
				]
				Effect="Allow"
				Resource="*"
				Sid="CloudWatchMetricsRead"
			},
			{
				Action=[
					"ec2:DescribeAccountAttributes",
					"ec2:DescribeInstanceTypes",
				]
				Effect="Allow"
				Resource="*"
				Sid="EC2QuotasRead"
			},
		]
		Version="2012-10-17"
	})
}
resource "aws_prometheus_rule_group_namespace" "liferay" {
	count=var.observability_config.enabled && length(local.aws_recording_rules) > 0 ? 1 : 0
	data=yamlencode({
		groups=[
			{
				interval=local.observability_interval
				name="liferay-recording-rules"
				rules=local.aws_recording_rules
			}
		]
	})
	name="liferay-recording-rules"
	workspace_id=aws_prometheus_workspace.amp[0].id
}
resource "aws_prometheus_workspace" "amp" {
	alias="${var.deployment_name}-amp-workspace"
	count=var.observability_config.enabled ? 1 : 0
}