variable "argocd_namespace" {
	default="argocd"
	type=string
}
variable "git_repo_liferay_application_base_path" {
	default="applications/liferay/base"
	type=string
}
variable "git_repo_liferay_application_environments_pattern" {
	default="applications/liferay/environments/**/values.yaml"
	type=string
}
variable "git_repo_url" {
	type=string
}
variable "git_token_property" {
	type=string
}
variable "git_username_property" {
	type=string
}
variable "liferay_helm_chart_name" {
	default="liferay-aws"
	type=string
}
variable "liferay_helm_chart_repo" {
	default="oci://us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart"
	type=string
}
variable "liferay_helm_chart_version" {
	type=string
}
variable "remote_secret_key" {
	type=string
}
variable "secret_store_provider_yaml_spec" {
	type=string
}