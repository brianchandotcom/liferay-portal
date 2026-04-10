data "google_client_config" "default" {}
provider "google" {
	default_labels={
		deployment_name=var.deployment_name
	}
	project=var.project_id
	region=var.region
}
provider "google-beta" {
	default_labels={
		deployment_name=var.deployment_name
	}
	project=var.project_id
	region=var.region
}
provider "helm" {
	kubernetes={
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
			version="~> 7.0"
		}
		google-beta={
			source="hashicorp/google-beta"
			version="~> 7.0"
		}
		helm={
			source="hashicorp/helm"
			version="~> 3.1"
		}
		kubernetes={
			source="hashicorp/kubernetes"
			version="~> 2.36.0"
		}
		time={
			source="hashicorp/time"
			version="~> 0.12"
		}
	}
	required_version=">= 1.5.0"
}