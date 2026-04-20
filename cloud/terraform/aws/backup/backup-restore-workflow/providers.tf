provider "aws" {
	region=var.region
}
terraform {
	backend "s3" {}
	required_providers {
		aws={
			source="hashicorp/aws"
			version="~> 5.0"
		}
	}
	required_version=">=1.5.0"
}