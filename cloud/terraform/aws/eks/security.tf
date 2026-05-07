resource "aws_vpc_security_group_ingress_rule" "envoy_ingress_managed" {
	cidr_ipv4=var.vpc_cidr
	for_each=toset(["10080", "10443"])
	from_port=each.value
	ip_protocol="tcp"
	security_group_id=module.eks.cluster_primary_security_group_id
	to_port=each.value
}