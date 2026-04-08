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
							discovery.kubernetes "kube_state_metrics_service" {
								namespaces {
									own_namespace=false
							  	}
								role="service"
								selectors {
									label="app.kubernetes.io/name=kube-state-metrics"
									role="service"
								}
							}
							discovery.kubernetes "liferay_pods" {
								role="pod"
								selectors {
									label="component=liferay"
									role="pod"
								}
							}
							discovery.kubernetes "nodes" {
								role="node"
							}
							discovery.relabel "kube_state_metrics_relabel" {
								rule {
									action="keep"
									regex="http-metrics|metrics|8080"
									source_labels=["__meta_kubernetes_service_port"]
								}
								rule {
									action="keep"
									regex="kube-state-metrics"
									source_labels=["__meta_kubernetes_service_name"]
								}
								rule {
									regex="(.+):\\d+"
									replacement="$1:8080"
									source_labels=["__address__"]
									target_label="__address__"
								}
								rule {
									replacement="/metrics"
									target_label="__metrics_path__"
								}
								rule {
									replacement="http"
									target_label="__scheme__"
								}
								rule {
									replacement="kube-state-metrics"
									target_label="job"
								}
								targets=discovery.kubernetes.kube_state_metrics_service.targets
							}
							discovery.relabel "kubelet_relabel" {
								rule {
									action="replace"
									replacement="https"
									target_label="__scheme__"
								}
								rule {
									action="replace"
									replacement="$1:10250"
									source_labels=["__meta_kubernetes_node_address_InternalIP"]
									target_label="__address__"
								}
								rule {
									action="replace"
									replacement="/metrics/resource"
									target_label="__metrics_path__"
								}
								targets=discovery.kubernetes.nodes.targets
							}
							discovery.relabel "liferay_relabel" {
								targets=discovery.kubernetes.liferay_pods.targets
								rule {
									regex="(.*)"
									replacement="$1:12345"
									source_labels=["__meta_kubernetes_pod_ip"]
									target_label="__address__"
								}
								rule {
									replacement="/metrics"
									target_label="__metrics_path__"
								}
								rule {
									replacement="liferay"
									target_label="job"
								}
							}
							logging {
								format="logfmt"
								level="info"
							}
							prometheus.remote_write "amp" {
								endpoint {
									sigv4 {
										region="${var.region}"
									}
								url="${aws_prometheus_workspace.amp[0].prometheus_endpoint}api/v1/remote_write"
							  }
							}
							prometheus.scrape "kube_state_metrics" {
								forward_to=[prometheus.remote_write.amp.receiver]
								targets=discovery.relabel.kube_state_metrics_relabel.output
							}
							prometheus.scrape "kubelet_resource" {
								bearer_token_file="/var/run/secrets/kubernetes.io/serviceaccount/token"
								forward_to=[prometheus.remote_write.amp.receiver]
								targets=discovery.relabel.kubelet_relabel.output
								tls_config {
									ca_file="/var/run/secrets/kubernetes.io/serviceaccount/ca.crt"
									insecure_skip_verify=true
								}
							}
							prometheus.scrape "liferay" {
								forward_to=[prometheus.remote_write.amp.receiver]
								targets=discovery.relabel.liferay_relabel.output
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
resource "helm_release" "kube_state_metrics" {
	chart="kube-state-metrics"
	count=var.observability_config.enabled ? 1 : 0
	name="kube-state-metrics"
	namespace="kube-system"
	repository="https://prometheus-community.github.io/helm-charts"
	version="7.2.2"
	wait=false
}
resource "time_rotating" "grafana_api_key" {
	count=var.observability_config.enabled ? 1 : 0
	rotation_hours=1
}