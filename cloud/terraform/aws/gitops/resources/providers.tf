provider "aws" {
	default_tags {
		tags={
			DeploymentName=var.deployment_name
		}
	}
	region=var.region
}
provider "helm" {
	kubernetes={
	}
}
provider "kubernetes" {
}
terraform {
	required_providers {
		aws={
			source="hashicorp/aws"
			version="~> 6.14.1"
		}
		helm={
			source="hashicorp/helm"
			version="~> 3.1"
		}
		kubernetes={
			source="hashicorp/kubernetes"
			version="~> 2.36.0"
		}
	}
	required_version=">=1.5.0"
}