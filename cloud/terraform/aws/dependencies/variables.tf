data "aws_eks_cluster" "cluster" {
	name=var.cluster_name
}
data "aws_eks_cluster_auth" "cluster" {
	name=var.cluster_name
}
variable "cluster_endpoint" {
	default="CLUSTER_ENDPOINT"
}
variable "cluster_name" {
	default="CLUSTER_NAME"
}
variable "cluster_security_group_id" {
	default="SECURITY_GROUP_ID"
}
variable "deployment_name" {
	default="liferay-self-hosted"
}
variable "deployment_namespace" {
	default="liferay-system"
}
variable "node_instance_type" {
	default="NODE_INSTANCE_TYPE"
}
variable "node_role_arn" {
	default="NODE_ROLE_ARN"
}
variable "node_security_group_id" {
	default="NODE_SECURITY_GROUP"
}
variable "oidc_provider" {
	default="OIDC_PROVIDER"
}
variable "oidc_provider_arn" {
	default="OIDC_PROVIDER_ARN"
}
variable "private_subnet_ids" {
	default=["PUBLIC_SUBNET_ID_ONE"]
}
variable "public_subnet_ids" {
	default=["PUBLIC_SUBNET_ID_ONE"]
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
}
variable "vpc_id" {
	default="VPC_ID"
}