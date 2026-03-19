data "google_compute_zones" "available" {
	project=var.project_id
	region=var.region
}
data "google_netblock_ip_ranges" "health_checkers" {
	range_type="health-checkers"
}
data "google_netblock_ip_ranges" "legacy_health_checkers" {
	range_type="legacy-health-checkers"
}
data "google_project" "project" {
	project_id=var.project_id
}