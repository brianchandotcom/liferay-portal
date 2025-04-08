locals {
	availabilityZones=["${var.region}a", "${var.region}b"]
}

variable "deployment_name" {
	default="liferay-self-hosted"
	description="Deployment name"
}

variable "node_group_ami_type" {
	default="AL2023_x86_64_STANDARD"
	description="AMI type for EKS node group (must match instance architecture)"
}

variable "node_group_desired_size" {
	default=2
	description="Desired number of worker nodes"
}

variable "node_group_max_size" {
	default=2
	description="Maximum number of worker nodes"
}

variable "node_group_min_size" {
	default=2
	description="Minimum number of worker nodes"
}

variable "node_instance_type" {
	default="t3.xlarge"
	description="Instance type for EKS node group"
}

variable "private_subnets" {
	default=["10.0.1.0/24", "10.0.2.0/24"]
	description="Private subnet CIDRs"
}

variable "public_subnets" {
	default=["10.0.101.0/24", "10.0.102.0/24"]
	description="Public subnet CIDRs"
}

variable "region" {
	default="us-west-2"
	description="AWS region"
}

variable "root_volume_size" {
	default=20
	description="Root volume size in GB"
}

variable "root_volume_type" {
	default="gp3"
	description="Root volume type"
}

variable "vpc_cidr" {
	default="10.0.0.0/16"
	description="VPC CIDR block"
}