variable "active_data" {
	default="blue"
	validation {
		condition=contains(["blue", "green"], var.active_data)
		error_message="The active_db_slot must be either 'blue' or 'green'"
	}
}
variable "cluster_name" {
	type=string
}
variable "db_restore_snapshot_identifier" {
	default=null
}
variable "deployment_name" {
	default="liferay-self-hosted"
}
variable "deployment_namespace" {
	default="liferay-system"
}
variable "is_restoring" {
	default=false
}
variable "private_subnet_ids" {
	type=list(string)
}
variable "region" {
	type=string
}