output "network_id" {
	value=module.vpc.network_id
}

output "network_name" {
	value=module.vpc.network_name
}

output "subnet_id" {
	value=module.vpc.subnets_ids[0]
}

output "subnet_name" {
	value=module.vpc.subnets_names[0]
}
