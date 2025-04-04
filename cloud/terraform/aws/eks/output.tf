output "cluster_endpoint" {
    value=module.eks.cluster_endpoint
}

output "cluster_name" {
    value=module.eks.cluster_name
}

output "cluster_security_group_id" {
    description="EKS cluster security group ID"
    value=aws_security_group.cluster.id
}

output "deployment_name" {
    value=var.deployment_name
}

output "node_instance_type" {
    value=var.node_instance_type
}

output "node_role_arn" {
    value=module.eks.eks_managed_node_groups["liferay_dxp"].node_group_arn
}

output "node_security_group_id" {
    description="Worker node security group ID"
    value=aws_security_group.nodes.id
}

output "oidc_provider" {
    value=module.eks.oidc_provider
}

output "oidc_provider_arn" {
    value=module.eks.oidc_provider_arn
}

output "private_subnet_ids" {
    description="IDs of private subnets"
    value=aws_subnet.private[*].id
}

output "public_subnet_ids" {
    description="IDs of public subnets"
    value=aws_subnet.public[*].id
}

output "region" {
    value=var.region
}

output "vpc_cidr" {
    value=aws_vpc.main.cidr_block
}

output "vpc_id" {
    description="ID of the EKS VPC"
    value=aws_vpc.main.id
}
