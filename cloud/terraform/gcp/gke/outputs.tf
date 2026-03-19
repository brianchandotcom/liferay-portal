output "cluster_name" {
	value=google_container_cluster.primary.name
}
output "deployment_name" {
	value=var.deployment_name
}
output "deployment_namespace" {
	value=var.deployment_namespace
}
output "gateway_namespace" {
	value=var.gateway_namespace
}
output "membership_name" {
	value=google_gke_hub_membership.membership.name
}
output "network_id" {
	value=google_compute_network.vpc.id
}
output "network_name" {
	value=google_compute_network.vpc.name
}
output "subnet_id" {
	value=google_compute_subnetwork.subnet.id
}
output "subnet_name" {
	value=google_compute_subnetwork.subnet.name
}
