provider "google" {
	default_labels={
		deployment_name=var.deployment_name
	}
	project=var.project_id
	region=var.region
}
terraform {
	required_providers {
		google={
			source="hashicorp/google"
			version="~> 6.0"
		}
		time={
			source="hashicorp/time"
			version="~> 0.12"
		}
	}
	required_version=">= 1.5.0"
}
