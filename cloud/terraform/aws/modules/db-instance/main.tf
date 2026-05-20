resource "aws_db_instance" "this" {
	allocated_storage=20
	auto_minor_version_upgrade=true
	backup_retention_period=7
	copy_tags_to_snapshot=true
	db_name="lportal"
	db_subnet_group_name=var.db_subnet_group_name
	deletion_protection=true
	enabled_cloudwatch_logs_exports=["postgresql", "upgrade"]
	engine="postgres"
	engine_version="14"
	identifier=var.identifier
	instance_class="db.t3.medium"
	monitoring_interval=60
	monitoring_role_arn=aws_iam_role.rds_monitoring.arn
	multi_az=false
	password=var.password
	performance_insights_enabled=true
	skip_final_snapshot=true
	snapshot_identifier=var.snapshot_identifier
	storage_encrypted=true
	storage_type="gp2"
	tags=merge(
		{
			Backup="true"
			Name=var.identifier
		},
		var.tags)
	username=var.username
	vpc_security_group_ids=var.vpc_security_group_ids
}
resource "aws_iam_role" "rds_monitoring" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRole"
					Effect="Allow"
					Principal={
						Service="monitoring.rds.amazonaws.com"
					}
				}
			]
			Version="2012-10-17"
		})
	force_detach_policies=true
	name_prefix="${substr(var.identifier, 0, 23)}-rds-mon-"
}
resource "aws_iam_role_policy_attachment" "rds_monitoring" {
	policy_arn="arn:${var.arn_partition}:iam::aws:policy/service-role/AmazonRDSEnhancedMonitoringRole"
	role=aws_iam_role.rds_monitoring.name
}