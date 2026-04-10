resource "helm_release" "envoy_gateway" {
	chart="gateway-helm"
	create_namespace=true
	depends_on=[
		time_sleep.wait_for_gateway,
	]
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
	version="v1.6.3"
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
resource "time_sleep" "wait_for_gateway" {
	create_duration="60s"
	depends_on=[
		google_container_cluster.primary,
		google_gke_hub_membership.membership,
	]
}
