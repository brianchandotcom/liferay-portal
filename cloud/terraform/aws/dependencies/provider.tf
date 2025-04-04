provider "aws" {
    default_tags {
        tags={
            DeploymentName=var.deployment_name
        }
    }
    region=var.region
}

provider "kubernetes" {
    cluster_ca_certificate=base64decode(data.aws_eks_cluster.cluster.certificate_authority[0].data)
    host=data.aws_eks_cluster.cluster.endpoint
    token=data.aws_eks_cluster_auth.cluster.token
}

terraform {
    required_providers {
        aws={
            source="hashicorp/aws"
            version="~> 5.0"
        }
        kubernetes={
            source="hashicorp/kubernetes"
            version="~> 2.36.0"
        }
        random={
            source="hashicorp/random"
            version="~> 3.0"
        }
    }
    required_version=">=1.5.0"
}
