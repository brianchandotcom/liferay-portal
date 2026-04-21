resource "helm_release" "envoy_gateway" {
	chart="gateway-helm"
	create_namespace=true
	depends_on=[
		null_resource.wait_for_connect_gateway,
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
					maxUnavailable=1
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
		max_unavailable="1"
		selector {
			match_labels={
				"app.kubernetes.io/component"="proxy"
				"app.kubernetes.io/name"="envoy"
			}
		}
	}
}
resource "null_resource" "wait_for_connect_gateway" {
	depends_on=[
		google_container_cluster.primary,
		google_gke_hub_membership.membership,
	]
	provisioner "local-exec" {
		command="${path.module}/scripts/wait-for-connect-gateway.sh"
		environment={
			GATEWAY_TOKEN="Bearer ${data.google_client_config.default.access_token}"
			GATEWAY_URL="https://connectgateway.googleapis.com/v1/projects/${data.google_project.project.number}/locations/global/gkeMemberships/${var.deployment_name}-membership/api/v1/namespaces"
		}
	}
	triggers={
		membership_id=google_gke_hub_membership.membership.id
	}
}
