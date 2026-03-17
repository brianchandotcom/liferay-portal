output "arn_partition" {
	value=var.arn_partition
}
output "cluster_name" {
	value=module.eks.cluster_name
}
output "deployment_name" {
	value=var.deployment_name
}
output "deployment_namespace" {
	value=var.deployment_namespace
}
output "grafana_workspace_endpoint" {
	value=aws_grafana_workspace.amg[0].endpoint
}
output "grafana_workspace_id" {
	value=aws_grafana_workspace.amg[0].id
}
output "liferay_sa_role_arn" {
	value=aws_iam_role.irsa.arn
}
output "liferay_sa_role_name" {
	value=aws_iam_role.irsa.name
}
output "private_subnet_ids" {
	value=module.vpc.private_subnets
}
output "prometheus_workspace_endpoint" {
	value=aws_prometheus_workspace.amp[0].prometheus_endpoint
}
output "prometheus_workspace_id" {
	value=aws_prometheus_workspace.amp[0].id
}
output "region" {
	value=var.region
}