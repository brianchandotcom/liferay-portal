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
run "should_create_the_secret_store_with_the_default_provider" {
	assert {
		condition=kubernetes_cluster_role.eso_secret_writer.metadata[0].name == "eso-cluster-secret-writer"
		error_message="The External Secrets writer ClusterRole must be named eso-cluster-secret-writer"
	}
	assert {
		condition=kubernetes_manifest.secret_store.manifest.metadata.name == "liferay-test-secret-store"
		error_message="The ClusterSecretStore name must be derived from deployment_name"
	}
	assert {
		condition=kubernetes_manifest.secret_store.manifest.spec.provider.gcpsm.projectID == "liferay-test-project"
		error_message="The default ClusterSecretStore must use the GCP Secret Manager provider"
	}
	assert {
		condition=length(google_project_iam_member.secrets_accessor_permissions) == 1
		error_message="The default provider must grant the Secret Manager accessor binding"
	}
	command=plan
}
run "should_disable_default_iam_for_a_custom_secret_store_provider" {
	assert {
		condition=kubernetes_manifest.secret_store.manifest.spec.provider.gcpsm.projectID == "liferay-test-project"
		error_message="The ClusterSecretStore must still be created from the custom provider HCL"
	}
	assert {
		condition=length(google_project_iam_member.secrets_accessor_permissions) == 0
		error_message="Supplying a custom secret store provider must disable the default Secret Manager binding"
	}
	command=plan
	variables {
		external_secret_store_provider_hcl={
			gcpsm={
				projectID="liferay-test-project"
			}
		}
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