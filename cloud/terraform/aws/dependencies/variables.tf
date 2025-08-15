resource "random_password" "opensearch_password" {
	length=16
	min_lower=1
	min_numeric=1
	min_special=1
	min_upper=1
	override_special="!#%&*()-_=+[]{}<>:?"
	special=true
}
resource "random_password" "opensearch_username" {
	length=16
	special=false
}
resource "random_password" "postgres_password" {
	length=16
	override_special="!#%&*()-_=+[]{}<>:?"
	special=true
}
resource "random_password" "postgres_username" {
	length=16
	special=false
}
resource "random_password" "s3_bucket_suffix" {
	length=4
	special=false
	upper=false
}
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