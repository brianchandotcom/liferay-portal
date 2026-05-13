provider "aws" {
	default_tags {
		tags={
			DeploymentName=var.deployment_name
		}
	}
	region=var.region
}
provider "kubernetes" {
}
terraform {
	backend "s3" {}
	required_providers {
		aws={
			source="hashicorp/aws"
			version="~> 6.43.0"
		}
		kubernetes={
			source="hashicorp/kubernetes"
			version="~> 3.1.0"
		}
		null={
			source="hashicorp/null"
			version="~> 3.2.4"
		}
		random={
			source="hashicorp/random"
			version="~> 3.8.1"
		}
	}
	required_version=">=1.10.0"
}