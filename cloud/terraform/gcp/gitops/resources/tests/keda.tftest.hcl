mock_provider "google" {
	mock_resource "google_service_account" {
		defaults={
			email="crossplane-gsa@liferay-test-project.iam.gserviceaccount.com"
			name="projects/liferay-test-project/serviceAccounts/crossplane-gsa@liferay-test-project.iam.gserviceaccount.com"
		}
	}
}
mock_provider "helm" {}
mock_provider "kubernetes" {}
override_data {
	target=data.google_project.project
	values={
		number="1234567890"
	}
}
run "should_disable_keda_by_default" {
	assert {
		condition=length(google_project_iam_member.keda_monitoring_viewer) == 0
		error_message="No KEDA IAM binding should exist when keda_enabled is false"
	}
	command=plan
}
run "should_grant_monitoring_viewer_when_keda_is_enabled" {
	assert {
		condition=endswith(google_project_iam_member.keda_monitoring_viewer[0].member, "/ns/keda-system/sa/keda-operator")
		error_message="The KEDA binding must target the keda-operator workload identity principal"
	}
	assert {
		condition=google_project_iam_member.keda_monitoring_viewer[0].role == "roles/monitoring.viewer"
		error_message="The KEDA binding must grant roles/monitoring.viewer"
	}
	assert {
		condition=length(google_project_iam_member.keda_monitoring_viewer) == 1
		error_message="Enabling KEDA must grant the monitoring viewer role"
	}
	command=plan
	variables {
		keda_enabled=true
	}
}
variables {
	deployment_name="liferay-test"
	infrastructure_helm_chart_version="0.4.9"
	infrastructure_provider_helm_chart_version="0.3.12"
	liferay_git_repo_url="https://github.com/example/liferay-gitops.git"
	liferay_helm_chart_version="0.4.20"
	observability_helm_chart_version="0.1.0"
	project_id="liferay-test-project"
	region="us-central1"
}