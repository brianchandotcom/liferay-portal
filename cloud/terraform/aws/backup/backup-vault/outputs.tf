output "backup_service_assumed_role_arn" {
	value=aws_iam_role.liferay_backup.arn
}
output "backup_vault_name" {
	value=aws_backup_vault.liferay_backup.name
}