provider "grafana" {
	auth=var.grafana_workspace_api_key
	url="https://${var.grafana_workspace_endpoint}"
}
terraform {
	backend "s3" {}
	required_providers {
		grafana={
			source="grafana/grafana"
			version="~> 4.35.0"
		}
	}
	required_version=">=1.10.0"
}