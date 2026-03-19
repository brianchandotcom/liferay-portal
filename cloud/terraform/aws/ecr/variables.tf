variable "deployment_name" {
	type=string
	validation {
		condition=can(regex("^[a-z][a-z0-9-]{2,23}$", var.deployment_name))
		error_message="The variable \"deployment_name\" must be 3-24 characters, start with a lowercase letter, and contain only lowercase letters, numbers, and hyphens."
	}
}
variable "ecr_repository_names" {
	default=["liferay/dxp"]
	type=list(string)
}
variable "region" {
	type=string
}