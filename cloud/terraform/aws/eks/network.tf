resource "aws_eip" "main" {
	domain="vpc"
	tags={
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-main-eip"
	}
}
resource "aws_internet_gateway" "main" {
	tags={
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-main-ig"
	}
	vpc_id=aws_vpc.main.id
}
resource "aws_nat_gateway" "main" {
	allocation_id=aws_eip.main.id
	depends_on=[aws_internet_gateway.main]
	subnet_id=aws_subnet.public[0].id
	tags={
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-main-ng"
	}
}
resource "aws_route_table" "private" {
	route {
		cidr_block="0.0.0.0/0"
		nat_gateway_id=aws_nat_gateway.main.id
	}
	tags={
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-private-rt"
	}
	vpc_id=aws_vpc.main.id
}
resource "aws_route_table" "public" {
	route {
		cidr_block="0.0.0.0/0"
		gateway_id=aws_internet_gateway.main.id
	}
	tags={
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-public-rt"
	}
	vpc_id=aws_vpc.main.id
}
resource "aws_route_table_association" "private" {
	count=length(var.private_subnets)
	route_table_id=aws_route_table.private.id
	subnet_id=aws_subnet.private[count.index].id
}
resource "aws_route_table_association" "public" {
	count=length(var.public_subnets)
	route_table_id=aws_route_table.public.id
	subnet_id=aws_subnet.public[count.index].id
}
resource "aws_subnet" "private" {
	availability_zone=local.availabilityZones[count.index]
	cidr_block=var.private_subnets[count.index]
	count=length(var.private_subnets)
	tags={
		"kubernetes.io/role/internal-elb"="1"
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-private-sn-${count.index}"
	}
	vpc_id=aws_vpc.main.id
}
resource "aws_subnet" "public" {
	availability_zone=local.availabilityZones[count.index]
	cidr_block=var.public_subnets[count.index]
	count=length(var.public_subnets)
	map_public_ip_on_launch=true
	tags={
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-public-sn-${count.index}"
		"kubernetes.io/role/elb"="1"
	}
	vpc_id=aws_vpc.main.id
}
resource "aws_vpc" "main" {
	cidr_block=var.vpc_cidr
	enable_dns_hostnames=true
	tags={
		DeploymentName=var.deployment_name
		Name="${var.deployment_name}-vpc"
	}
}