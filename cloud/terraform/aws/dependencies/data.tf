data "aws_caller_identity" "current" {
}
data "aws_eks_cluster" "cluster" {
	name=var.cluster_name
}
data "aws_vpc" "current" {
	id=data.aws_eks_cluster.cluster.vpc_config[0].vpc_id
}