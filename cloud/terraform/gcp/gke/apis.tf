resource "google_project_service" "apis" {
	disable_on_destroy=false
	for_each=toset([
		"artifactregistry.googleapis.com",
		"cloudbuild.googleapis.com",
		"cloudresourcemanager.googleapis.com",
		"compute.googleapis.com",
		"config.googleapis.com",
		"container.googleapis.com",
		"iam.googleapis.com",
		"iamcredentials.googleapis.com",
		"secretmanager.googleapis.com",
		"servicecontrol.googleapis.com",
		"servicemanagement.googleapis.com",
		"servicenetworking.googleapis.com",
		"sqladmin.googleapis.com",
		"storage-api.googleapis.com",
		"sts.googleapis.com",
	])
	project=var.project_id
	service=each.key
}
resource "time_sleep" "wait_for_apis" {
	create_duration="60s"
	depends_on=[google_project_service.apis]
}
