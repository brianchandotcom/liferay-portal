variable "arn_partition" {
	default="aws"
	type=string
}
variable "aws_backup_service_assumed_iam_role_name" {
	default="liferay-aws-backup-service-role"
	type=string
}
variable "backup_vault_name" {
	default="liferay-backup"
	type=string
}
variable "region" {
	type=string
}