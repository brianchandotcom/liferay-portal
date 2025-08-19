resource "random_password" "s3_bucket_suffix" {
	length=4
	special=false
	upper=false
}
module "s3_bucket" {
	block_public_acls=true
	block_public_policy=true
	bucket="${var.deployment_name}-s3-bucket-${random_password.s3_bucket_suffix}"
	force_destroy=true
	ignore_public_acls=true
	restrict_public_buckets=true
	server_side_encryption_configuration={
		rule={
			apply_server_side_encryption_by_default={
				sse_algorithm="aws:kms"
			}
			bucket_key_enabled=true
		}
	}
	source="terraform-aws-modules/s3-bucket/aws"
	tags={
		Backup="true"
	}
	version="~> 4.1.1"
}