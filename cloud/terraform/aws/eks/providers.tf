provider "aws" {
	default_tags {
		tags={
			DeploymentName=var.deployment_name
		}
	}
	region=var.region
}
provider "grafana" {
	auth=module.observability[0].grafana_workspace_api_key
	url="https://${module.observability[0].grafana_workspace_endpoint}"
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
				"--region",
				var.region,
			]
			command="aws"
		}
		host=module.eks.cluster_endpoint
	}
}
provider "kubernetes" {
	cluster_ca_certificate=base64decode(module.eks.cluster_certificate_authority_data)
	exec {
		api_version="client.authentication.k8s.io/v1beta1"
		args=[
			"eks",
			"get-token",
			"--cluster-name",
			module.eks.cluster_name,
			"--region",
			var.region,
		]
		command="aws"
	}
	host=module.eks.cluster_endpoint
}
terraform {
	required_providers {
		aws={
			source="hashicorp/aws"
			version="~> 6.30.0"
		}
		grafana={
			source="grafana/grafana"
			version="~> 3.15.2"
		}
		helm={
			source="hashicorp/helm"
			version="~> 3.1"
		}
		kubernetes={
			source="hashicorp/kubernetes"
			version="~> 2.36.0"
		}
		random={
			source="hashicorp/random"
			version="~> 3.0"
		}
		time={
			source="hashicorp/time"
			version="0.13.1"
		}
	}
	required_version=">=1.5.0"
}