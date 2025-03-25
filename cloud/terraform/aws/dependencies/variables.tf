data "aws_eks_cluster" "cluster" {
  name = var.cluster_name
}

data "aws_eks_cluster_auth" "cluster" {
  name = var.cluster_name
}

variable "cluster_endpoint" {
  description = "EKS Cluster Endpoint"
  type        = string
}

variable "cluster_name" {
  default     = "CLUSTER_NAME"
  description = "The name of the EKS Cluster"
  type        = string
}

variable "cluster_security_group_id" {
  default     = "SECURITY_GROUP_ID"
  description = "Security group ID"
  type        = string
}

variable "deployment_name" {
  default     = "liferay-self-hosted"
  description = "Deployment name"
  type        = string
}

variable "deployment_namespace" {
  default     = "liferay-system"
  description = "Deployment namespace"
  type        = string
}

variable "node_instance_type" {
  default     = "NODE_INSTANCE_TYPE"
  description = "Node instance type"
  type        = string
}

variable "node_role_arn" {
  default     = "NODE_ROLE_ARN"
  description = "Node Role ARN"
  type        = string
}

variable "node_security_group_id" {
  default     = "NODE_SECURITY_GROUP"
  description = "Node security group ID"
  type        = string
}

variable "oidc_provider" {
  default     = "OIDC_PROVIDER"
  description = "OIDC provider"
  type        = string
}

variable "oidc_provider_arn" {
  default     = "OIDC_PROVIDER_ARN"
  description = "OIDC provider ARN"
  type        = string
}

variable "private_subnet_ids" {
  default     = ["PUBLIC_SUBNET_ID_ONE"]
  description = "Public subnet IDs"
  type        = list(string)
}

variable "public_subnet_ids" {
  default     = ["PUBLIC_SUBNET_ID_ONE"]
  description = "Public subnet IDs"
  type        = list(string)
}

resource "random_password" "opensearch_password" {
  length           = 16
  override_special = "!#$%&*()-_=+[]{}<>:?"
  special          = true
}

resource "random_password" "opensearch_username" {
  length  = 16
  special = false
}

resource "random_password" "postgres_password" {
  length           = 16
  override_special = "!#$%&*()-_=+[]{}<>:?"
  special          = true
}

resource "random_password" "postgres_username" {
  length  = 16
  special = false
}

variable "region" {
  default = "REGION"
  type    = string
}

variable "vpc_cidr" {
  default     = ""
  description = "VPC CIDR block"
  type        = string
}

variable "vpc_id" {
  default     = "VPC_ID"
  description = "VPC ID"
  type        = string
}
