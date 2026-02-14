resource "aws_vpc_security_group_ingress_rule" "node_ingress_envoy_tls" {
	cidr_ipv4="0.0.0.0/0"
	from_port=8443
	ip_protocol="tcp"
	security_group_id=module.eks.node_security_group_id
	to_port=8443
}
resource "aws_vpc_security_group_ingress_rule" "node_ingress_liferay" {
	cidr_ipv4=var.vpc_cidr
	from_port=8080
	ip_protocol="tcp"
	security_group_id=module.eks.node_security_group_id
	to_port=8080
}