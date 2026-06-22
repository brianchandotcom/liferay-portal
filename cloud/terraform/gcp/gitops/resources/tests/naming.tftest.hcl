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
run "should_compute_naming_locals" {
	assert {
		condition=local.secret_store_name == "liferay-test-secret-store"
		error_message="local.secret_store_name must be derived from deployment_name"
	}
	assert {
		condition=local.vpc_name == "liferay-test-vpc"
		error_message="local.vpc_name must be derived from deployment_name"
	}
	command=plan
}
run "should_not_accept_uppercase_deployment_name" {
	command=plan
	expect_failures=[var.deployment_name]
	variables {
		deployment_name="Liferay-Test"
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