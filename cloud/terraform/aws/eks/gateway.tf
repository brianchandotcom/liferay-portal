module "envoy_proxy_role" {
	attach_aws_gateway_controller_policy=true
	name="${var.deployment_name}-envoy-proxy"
	oidc_providers={
		main={
			namespace_service_accounts=[
				"${local.liferay_namespace_pattern}:envoy-*",
				"${var.gateway_namespace}:envoy-*",
			]
			provider_arn=local.oidc_provider_arn
		}
	}
	policy_name="${var.deployment_name}-AWS-Gateway-Controller"
	source="terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts"
	use_name_prefix=false
	version="6.4.0"
}
resource "helm_release" "envoy_gateway" {
	chart="gateway-helm"
	create_namespace=true
	depends_on=[module.eks]
	name="envoy-gateway"
	namespace=var.gateway_namespace
	repository="oci://docker.io/envoyproxy"
	values=[
		yamlencode(
			{
				config={
					envoyGateway={
						extensionApis={
							enableBackend=false
						}
					}
				}
				deployment={
					replicas=2
				}
				podDisruptionBudget={
					minAvailable=1
				}
			}),
	]
	version="v${var.envoy_gateway_helm_chart_version}"
}
resource "kubernetes_pod_disruption_budget_v1" "envoy_proxy_pdb" {
	metadata {
		name="envoy-proxy-pdb"
		namespace=var.gateway_namespace
	}
	spec {
		min_available="1"
		selector {
			match_labels={
				"app.kubernetes.io/component"="proxy"
				"app.kubernetes.io/name"="envoy"
			}
		}
	}
}