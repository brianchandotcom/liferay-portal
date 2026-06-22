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
run "should_bind_provider_workload_identity_roles" {
	assert {
		condition=endswith(google_project_iam_member.provider_direct_iam["storage"].member, "/provider-gcp-storage")
		error_message="The storage provider binding must target the provider-gcp-storage service account"
	}
	assert {
		condition=google_project_iam_member.provider_direct_iam["storage"].role == "roles/storage.admin"
		error_message="The storage provider KSA must be granted the storage admin role"
	}
	assert {
		condition=length(google_project_iam_member.cloudplatform_roles) == 2
		error_message="The cloudplatform GSA must be granted both required roles"
	}
	assert {
		condition=length(google_project_iam_member.provider_direct_iam) == 4
		error_message="A direct IAM binding must be created for every Crossplane GCP provider"
	}
	command=plan
}
run "should_configure_crossplane_functions_and_runtime_configs" {
	assert {
		condition=kubernetes_manifest.function_auto_ready.manifest.spec.package == "xpkg.upbound.io/upbound/function-auto-ready:v0.6.0"
		error_message="The auto-ready function must pin its expected package version"
	}
	assert {
		condition=kubernetes_manifest.function_auto_ready_runtime_config.manifest.spec.deploymentTemplate.metadata.annotations["sidecar.opentelemetry.io/inject"] == "false"
		error_message="The Function runtime configs must disable OpenTelemetry sidecar injection"
	}
	assert {
		condition=kubernetes_manifest.function_auto_ready_runtime_config.manifest.spec.deploymentTemplate.spec.template.spec.securityContext.runAsNonRoot == true
		error_message="The Function runtime configs must run as nonroot"
	}
	assert {
		condition=kubernetes_manifest.function_go_templating.manifest.spec.package == "xpkg.upbound.io/crossplane-contrib/function-go-templating:v0.11.3"
		error_message="The go-templating function must pin its expected package version"
	}
	command=plan
}
run "should_name_the_cloudplatform_service_account" {
	assert {
		condition=google_service_account.cloudplatform_gsa.account_id == "liferay-test-cp-iam"
		error_message="The cloudplatform service account id must be derived from deployment_name"
	}
	command=plan
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
