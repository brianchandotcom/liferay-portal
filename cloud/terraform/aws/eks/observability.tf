resource "aws_grafana_workspace" "amg" {
	account_access_type="CURRENT_ACCOUNT"
	authentication_providers=["AWS_SSO"]
	count=var.observability_config.enabled ? 1 : 0
	name="${var.deployment_name}-amg-workspace"
	permission_type="SERVICE_MANAGED"
	role_arn=aws_iam_role.grafana[0].arn
}
resource "aws_grafana_workspace_api_key" "amg_api_key" {
	count=var.observability_config.enabled ? 1 : 0
	key_name="terraform-api-key-${time_rotating.grafana_api_key[0].id}"
	key_role="ADMIN"
	seconds_to_live=3600
	workspace_id=aws_grafana_workspace.amg[0].id
}
resource "aws_iam_role" "alloy" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRoleWithWebIdentity",
					Condition={
						StringEquals={
							"${module.eks.oidc_provider}:sub"="system:serviceaccount:${var.observability_config.alloy_namespace}:${var.deployment_name}-alloy-sa"
						}
					},
					Effect="Allow",
					Principal={
						Federated=module.eks.oidc_provider_arn
					}
				}
			]
			Version="2012-10-17"
		})
	count=var.observability_config.enabled ? 1 : 0
	name="${var.deployment_name}-alloy-irsa"
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
	count=var.observability_config.enabled ? 1 : 0
	name="${var.deployment_name}-grafana-role"
}
resource "aws_iam_role_policy" "alloy_amp_write" {
	count=var.observability_config.enabled ? 1 : 0
	name="${var.deployment_name}-alloy-amp-write-policy"
	policy=jsonencode(
		{
			Statement=[
				{
					Action=[
						"aps:GetLabels",
						"aps:GetMetricMetadata",
						"aps:GetSeries",
						"aps:RemoteWrite"
					],
					Effect="Allow",
					Resource=aws_prometheus_workspace.amp[0].arn
				}
			]
			Version="2012-10-17"
		})
	role=aws_iam_role.alloy[0].id
}
resource "aws_iam_role_policy" "amp_access" {
	count=var.observability_config.enabled ? 1 : 0
	name="${var.deployment_name}-amg-amp-access-policy"
	policy=jsonencode(
		{
			Statement=[
				{
					Action=[
						"aps:DescribeWorkspace",
						"aps:GetLabels",
						"aps:GetMetricMetadata",
						"aps:GetSeries",
						"aps:ListWorkspaces",
						"aps:QueryMetrics"
					],
					Effect="Allow",
					Resource=aws_prometheus_workspace.amp[0].arn
				}
			]
			Version="2012-10-17"
		})
	role=aws_iam_role.grafana[0].id
}
resource "aws_prometheus_workspace" "amp" {
	alias="${var.deployment_name}-amp-workspace"
	count=var.observability_config.enabled ? 1 : 0
}
resource "helm_release" "alloy" {
	chart="alloy"
	count=var.observability_config.enabled ? 1 : 0
	create_namespace=true
	name="alloy"
	namespace=var.observability_config.alloy_namespace
	repository="https://grafana.github.io/helm-charts"
	values=[
		yamlencode(
			{
				alloy={
					configMap={
						content=<<-EOT
							discovery.kubernetes "endpoints" {
							  role="endpoints"
							}
							discovery.kubernetes "endpointslices" {
							  role="endpointslice"
							}
							discovery.kubernetes "ingresses" {
							  role="ingress"
							}
							discovery.kubernetes "nodes" {
							  role="node"
							}
							discovery.kubernetes "pods" {
							  role="pod"
							}
							discovery.kubernetes "services" {
							  role="service"
							}
							logging {
							  format="logfmt"
							  level="info"
							}
							prometheus.remote_write "amp" {
                				endpoint {
                    				url="${aws_prometheus_workspace.amp[0].prometheus_endpoint}api/v1/remote_write"
									sigv4 {
										region="${var.region}"
									}
                				}
            				}
							prometheus.scrape "k8s" {
								targets=array.concat(
									discovery.kubernetes.endpoints.targets,
									discovery.kubernetes.endpointslices.targets,
									discovery.kubernetes.ingresses.targets,
									discovery.kubernetes.nodes.targets,
									discovery.kubernetes.pods.targets,
									discovery.kubernetes.services.targets,
								)
								forward_to=[prometheus.remote_write.amp.receiver]
							}
						EOT
					}
				}
				serviceAccount={
					annotations={
						"eks.amazonaws.com/role-arn"=aws_iam_role.alloy[0].arn
					}
					create=true
					name="${var.deployment_name}-alloy-sa"
				}
			})
	]
}
resource "time_rotating" "grafana_api_key" {
	count=var.observability_config.enabled ? 1 : 0
	rotation_hours=1
}