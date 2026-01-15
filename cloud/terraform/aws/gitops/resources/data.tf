data "aws_caller_identity" "current" {
}
data "aws_eks_cluster" "cluster" {
	name=local.cluster_name
	region=var.region
}
data "aws_iam_roles" "opensearch_linked_role_lookup" {
	name_regex="AWSServiceRoleForAmazonOpenSearchService"
}
data "aws_subnets" "private" {
	filter {
		name="tag:DeploymentName"
		values=[var.deployment_name]
	}
	filter {
		name="tag:kubernetes.io/role/internal-elb"
		values=["1"]
	}
	filter {
		name="vpc-id"
		values=[data.aws_vpc.current.id]
	}
}
data "aws_vpc" "current" {
	id=data.aws_eks_cluster.cluster.vpc_config[0].vpc_id
}