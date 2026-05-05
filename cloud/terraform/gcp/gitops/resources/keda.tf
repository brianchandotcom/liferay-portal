resource "google_project_iam_member" "keda_monitoring_viewer" {
	count=var.keda_enabled ? 1 : 0
	member="serviceAccount:${google_service_account.keda[0].email}"
	project=var.project_id
	role="roles/monitoring.viewer"
}
resource "google_service_account" "keda" {
	account_id="${var.deployment_name}-keda-gsa"
	count=var.keda_enabled ? 1 : 0
	project=var.project_id
}
resource "google_service_account_iam_member" "keda_wi_binding" {
	count=var.keda_enabled ? 1 : 0
	member="serviceAccount:${var.project_id}.svc.id.goog[${var.keda_namespace}/keda-operator]"
	role="roles/iam.workloadIdentityUser"
	service_account_id=google_service_account.keda[0].name
}
resource "kubernetes_annotations" "keda_operator_sa" {
	annotations={
		"iam.gke.io/gcp-service-account"=google_service_account.keda[0].email
	}
	api_version="v1"
	count=var.keda_enabled ? 1 : 0
	force=true
	kind="ServiceAccount"
	metadata {
		name="keda-operator"
		namespace=var.keda_namespace
	}
}