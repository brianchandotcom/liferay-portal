provider "aws" {
	default_tags {
		tags={
			DeploymentName=var.deployment_name
		}
	}
	region=var.region
}
terraform {
	backend "s3" {}
	required_providers {
		aws={
			source="hashicorp/aws"
			version="~> 6.14.1"
		}
	}
	required_version=">=1.5.0"
}