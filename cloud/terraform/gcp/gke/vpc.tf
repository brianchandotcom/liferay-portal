module "vpc" {
	source="git::https://github.com/terraform-google-modules/terraform-google-network.git?ref=6d5eaa89ce07578e9d87c6fb3e2b77ba7a925550"

	project_id=var.project_id
	network_name="${var.deployment_name}-vpc"
	routing_mode="GLOBAL"

	subnets =[
		{
			subnet_name="${var.deployment_name}-subnet"
			subnet_ip=var.vpc_cidr
			subnet_region=var.region
			subnet_flow_logs ="true"
			subnet_flow_logs_interval="INTERVAL_10_MIN"
			subnet_flow_logs_sampling=0.5
			subnet_flow_logs_metadata="INCLUDE_ALL_METADATA"
		}
	]

	secondary_ranges ={
		"${var.deployment_name}-subnet"=[
			{
				range_name="${var.deployment_name}-pods"
				ip_cidr_range=var.pod_cidr
			},
			{
				range_name="${var.deployment_name}-services"
				ip_cidr_range=var.service_cidr
			}
		]
	}

	routes =[
		{
			name="${var.deployment_name}-egress-internet"
			description="Route through IGW to access internet"
			destination_range="0.0.0.0/0"
			tags =["egress-inet"]
			next_hop_internet="true"
		}
	]
}

resource "google_compute_router" "router" {
	name="${var.deployment_name}-router"
	region=var.region
	network=module.vpc.network_name
	project=var.project_id
}

resource "google_compute_router_nat" "nat" {
	name="${var.deployment_name}-nat"
	router=google_compute_router.router.name
	region=var.region
	project=var.project_id
	nat_ip_allocate_option="AUTO_ONLY"
	source_subnetwork_ip_ranges_to_nat="ALL_SUBNETWORKS_ALL_IP_RANGES"

	log_config {
		enable=true
		filter="ERRORS_ONLY"
	}
}

resource "google_compute_global_address" "private_ip_alloc" {
	name="${var.deployment_name}-psa-range"
	purpose="VPC_PEERING"
	address_type="INTERNAL"
	prefix_length=16
	network=module.vpc.network_id
	project=var.project_id
}

resource "google_service_networking_connection" "private_vpc_connection" {
	network=module.vpc.network_id
	service="servicenetworking.googleapis.com"
	reserved_peering_ranges =[google_compute_global_address.private_ip_alloc.name]

	depends_on=[module.vpc]
}
