variable "arn_partition" {
	default="aws"
}
variable "deployment_name" {
	type=string
	validation {
		condition=can(regex("^[a-z0-9-]*$", var.deployment_name))
		error_message="The deployment_name must contain only lowercase letters, numbers, and hyphens."
	}
}
variable "ecr_repositories" {
	type=map(object({ arn=string, url=string }))
	default={}
}
variable "gateway_namespace" {
	default="envoy-gateway-system"
}
variable "private_subnets" {
	default=null
	type=list(string)
}
variable "public_subnets" {
	default=null
	type=list(string)
}
variable "region" {
	type=string
}
variable "vpc_cidr" {
	default="10.0.0.0/16"
}