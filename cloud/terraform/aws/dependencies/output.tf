output "cluster_name" {
	description="The name of the EKS cluster"
	value=var.cluster_name
}
output "deployment_namespace" {
	value=var.deployment_namespace
}
output "liferay_sa_role" {
	description="The ARN of the IAM role for the Liferay service account"
	value=aws_iam_role.liferay.arn
}
output "region" {
	description="The AWS region"
	value=var.region
}