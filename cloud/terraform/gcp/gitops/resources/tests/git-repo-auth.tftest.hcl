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
run "should_create_a_single_credentials_secret_for_a_shared_repo" {
	assert {
		condition=length(kubernetes_manifest.git_repo_credentials_external_secret) == 1 && contains(keys(kubernetes_manifest.git_repo_credentials_external_secret), "liferay")
		error_message="A shared infrastructure/Liferay repo URL must link to a single git credentials ExternalSecret"
	}
	command=plan
}
run "should_create_two_credentials_secrets_for_separate_repos" {
	assert {
		condition=length(kubernetes_manifest.git_repo_credentials_external_secret) == 2
		error_message="A distinct infrastructure repo URL must link infrastructure and Liferay credentials secrets"
	}
	command=plan
	variables {
		infrastructure_git_repo_config={
			auth={}
			source_paths={}
			target={}
			url="https://github.com/example/liferay-infrastructure.git"
		}
	}
}
run "should_map_the_private_key_for_ssh_auth" {
	assert {
		condition=length([
			for d in kubernetes_manifest.git_repo_credentials_external_secret["liferay"].manifest.spec.data : d.secretKey
		]) == 1 && kubernetes_manifest.git_repo_credentials_external_secret["liferay"].manifest.spec.data[0].secretKey == "ssh_private_key"
		error_message="The SSH auth must map a single ssh_private_key secret key"
	}
	command=plan
	variables {
		liferay_git_repo_config={
			auth={
				method="ssh"
			}
			source_paths={}
			target={}
		}
	}
}
run "should_map_three_keys_for_github_app_auth" {
	assert {
		condition=contains([
			for d in kubernetes_manifest.git_repo_credentials_external_secret["liferay"].manifest.spec.data : d.secretKey
		], "github_app_id") && contains([
			for d in kubernetes_manifest.git_repo_credentials_external_secret["liferay"].manifest.spec.data : d.secretKey
		], "github_app_private_key")
		error_message="The github_app auth must map the app id and private key secret keys"
	}
	assert {
		condition=length([
			for d in kubernetes_manifest.git_repo_credentials_external_secret["liferay"].manifest.spec.data : d.secretKey
		]) == 3
		error_message="The github_app auth must map three secret keys"
	}
	command=plan
	variables {
		liferay_git_repo_config={
			auth={
				method="github_app"
			}
			source_paths={}
			target={}
		}
	}
}
run "should_map_username_and_password_for_https_auth" {
	assert {
		condition=length([
			for d in kubernetes_manifest.git_repo_credentials_external_secret["liferay"].manifest.spec.data : d.secretKey
		]) == 2 && contains([
			for d in kubernetes_manifest.git_repo_credentials_external_secret["liferay"].manifest.spec.data : d.secretKey
		], "username")
		error_message="The HTTPS auth must map both username and password secret keys"
	}
	command=plan
}
run "should_not_accept_an_invalid_git_auth_method" {
	command=plan
	expect_failures=[var.liferay_git_repo_config]
	variables {
		liferay_git_repo_config={
			auth={
				method="token"
			}
			source_paths={}
			target={}
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
