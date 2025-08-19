variable "db_subnet_group_name" {
	type=string
}
variable "identifier" {
	type=string
}
variable "password" {
	sensitive=true
	type=string
}
variable "snapshot_identifier" {
	default=null
}
variable "username" {
	sensitive=true
	type=string
}
variable "vpc_security_group_ids" {
	type=list(string)
}