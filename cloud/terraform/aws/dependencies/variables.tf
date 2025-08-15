variable "cluster_name" {
	type=string
}
variable "cluster_security_group_id" {
	type=string
}
variable "deployment_name" {
	default="liferay-self-hosted"
}
variable "deployment_namespace" {
	default="liferay-system"
}
variable "private_subnet_ids" {
	type=list(string)
}
variable "region" {
	type=string
}
variable "vpc_cidr" {
	type=string
}
variable "vpc_id" {
	type=string
}