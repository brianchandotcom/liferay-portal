data "google_client_config" "default" {}
provider "google" {
	default_labels={
		deployment_name=var.deployment_name
	}
	project=var.project_id
	region=var.region
}
provider "helm" {
	kubernetes {
		host="https://connectgateway.googleapis.com/v1/projects/${local.project_number}/locations/global/gkeMemberships/${var.deployment_name}-membership"
		token=data.google_client_config.default.access_token
	}
}
provider "kubernetes" {
	host="https://connectgateway.googleapis.com/v1/projects/${local.project_number}/locations/global/gkeMemberships/${var.deployment_name}-membership"
	token=data.google_client_config.default.access_token
}
terraform {
	required_providers {
		google={
			source="hashicorp/google"
			version="~> 6.0"
		}
		helm={
			source="hashicorp/helm"
			version="~> 2.17"
		}
		kubernetes={
			source="hashicorp/kubernetes"
			version="~> 2.35"
		}
		time={
			source="hashicorp/time"
			version="~> 0.12"
		}
	}
	required_version=">= 1.5.0"
}
