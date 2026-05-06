resource "google_project_iam_member" "keda_monitoring_viewer" {
	count=var.keda_enabled ? 1 : 0
	member="principal://iam.googleapis.com/projects/${data.google_project.project.number}/locations/global/workloadIdentityPools/${var.project_id}.svc.id.goog/subject/ns/${var.keda_namespace}/sa/keda-operator"
	project=var.project_id
	role="roles/monitoring.viewer"
}