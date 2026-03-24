data "google_client_config" "default" {}
data "google_project" "project" {
	project_id=var.project_id
}
