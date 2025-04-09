data "aws_eks_cluster" "cluster" {
	name=var.cluster_name
}
data "aws_eks_cluster_auth" "cluster" {
	name=var.cluster_name
}
variable "cluster_endpoint" {
	default="CLUSTER_ENDPOINT"
	description="EKS Cluster Endpoint"
}
variable "cluster_name" {
	default="CLUSTER_NAME"
	description="The name of the EKS Cluster"
}
variable "cluster_security_group_id" {
	default="SECURITY_GROUP_ID"
	description="Security group ID"
}
variable "deployment_name" {
	default="liferay-self-hosted"
	description="Deployment name"
}
variable "deployment_namespace" {
	default="liferay-system"
	description="Deployment namespace"
}
variable "node_instance_type" {
	default="NODE_INSTANCE_TYPE"
	description="Node instance type"
}
variable "node_role_arn" {
	default="NODE_ROLE_ARN"
	description="Node Role ARN"
}
variable "node_security_group_id" {
	default="NODE_SECURITY_GROUP"
	description="Node security group ID"
}
variable "oidc_provider" {
	default="OIDC_PROVIDER"
	description="OIDC provider"
}
variable "oidc_provider_arn" {
	default="OIDC_PROVIDER_ARN"
	description="OIDC provider ARN"
}
variable "private_subnet_ids" {
	default=["PUBLIC_SUBNET_ID_ONE"]
	description="Public subnet IDs"
}
variable "public_subnet_ids" {
	default=["PUBLIC_SUBNET_ID_ONE"]
	description="Public subnet IDs"
}
resource "random_password" "opensearch_password" {
	length=16
	override_special="!#$%&*()-_=+[]{}<>:?"
	special=true
}
resource "random_password" "opensearch_username" {
	length=16
	special=false
}
resource "random_password" "postgres_password" {
	length=16
	override_special="!#$%&*()-_=+[]{}<>:?"
	special=true
}
resource "random_password" "postgres_username" {
	length=16
	special=false
}
variable "region" {
	default="REGION"
}
variable "vpc_cidr" {
	default=""
	description="VPC CIDR block"
}
variable "vpc_id" {
	default="VPC_ID"
	description="VPC ID"
}