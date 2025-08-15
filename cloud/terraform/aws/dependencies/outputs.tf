output "cluster_name" {
	value=var.cluster_name
}
output "deployment_namespace" {
	value=var.deployment_namespace
}
output "liferay_sa_role" {
	value=aws_iam_role.liferay.arn
}
output "region" {
	value=var.region
}