provider "helm" {
	kubernetes={
	}
}
provider "kubernetes" {
}
terraform {
	backend "s3" {}
	required_providers {
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