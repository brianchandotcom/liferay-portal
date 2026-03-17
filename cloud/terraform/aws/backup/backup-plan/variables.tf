variable "aws_backup_service_assumed_iam_role_arn" {
	type=string
}
variable "backup_plan_name" {
	default="liferay-backup"
}
variable "backup_rules" {
	default=[
		{
			retention_days=30
			rule_name="daily-backups"
			schedule="cron(0 5 * * ? *)"
			start_window_minutes=60
		}
	]
	type=list(
		object(
			{
				retention_days=number
				rule_name=string
				schedule=string
				start_window_minutes=number
			}
		)
	)
}
variable "backup_selection_name" {
	default="by-tags"
}
variable "backup_vault_name" {
	default="liferay-backup"
}
variable "deployment_name" {
	type=string
	validation {
		condition=can(regex("^[a-z][a-z0-9-]{2,23}$", var.deployment_name))
		error_message="The variable \"deployment_name\" must be 3-24 characters, start with a lowercase letter, and contain only lowercase letters, numbers, and hyphens."
	}
}
variable "region" {
	type=string
}