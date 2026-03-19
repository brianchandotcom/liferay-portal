data "google_compute_zones" "available" {
	project=var.project_id
	region=var.region
}
data "google_project" "project" {
	project_id=var.project_id
}