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
		cluster_ca_certificate=base64decode(module.eks.cluster_certificate_authority_data)
		exec={
			api_version="client.authentication.k8s.io/v1beta1"
			args=[
				"eks",
				"get-token",
				"--cluster-name",
				module.eks.cluster_name,
			]
			command="aws"
		}
		host=module.eks.cluster_endpoint
	}
}
terraform {
	required_providers {
		aws={
			source="hashicorp/aws"
			version="~> 6.14.1"
		}
		random={
			source="hashicorp/random"
			version="~> 3.0"
		}
	}
	required_version=">=1.5.0"
}