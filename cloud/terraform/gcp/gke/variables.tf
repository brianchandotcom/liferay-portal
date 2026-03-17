variable "deployment_name" {
	type=string
	validation {
		condition=can(regex("^[a-z][a-z0-9-]{2,23}$", var.deployment_name))
		error_message="The variable \"deployment_name\" must be 3-24 characters, start with a lowercase letter, and contain only lowercase letters, numbers, and hyphens."
	}
}
variable "pod_cidr" {
	default="10.1.0.0/16"
	type=string
}
variable "project_id" {
	type=string
}
variable "region" {
	type=string
}
variable "service_cidr" {
	default="10.2.0.0/16"
	type=string
}
variable "vpc_cidr" {
	default="10.0.0.0/16"
	type=string
}
