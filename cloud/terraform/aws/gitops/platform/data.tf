data "aws_caller_identity" "current" {
}
data "aws_eks_cluster" "cluster" {
	name=local.cluster_name
	region=var.region
}
