variable "deployment_name" {
	type=string
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
