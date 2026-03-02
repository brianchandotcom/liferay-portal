variable "argocd_domain_config" {
	default={}
	type=object({
		hostname=optional(string, null)
		tls_external_secret_name=optional(string, null)
	})
}
variable "argocd_namespace" {
	default="argocd-system"
}
variable "crossplane_namespace" {
	default="crossplane-system"
}
variable "deployment_name" {
	validation {
		condition=can(regex("^[a-z0-9-]*$", var.deployment_name))
		error_message="The deployment_name must contain only lowercase letters, numbers, and hyphens."
	}
}
variable "external_secret_store_provider_hcl" {
	default=null
	type=any
}
variable "external_secrets_namespace" {
	default="external-secrets-system"
}
variable "gateway_class_name" {
	default="liferay-gateway-class"
}
variable "gateway_namespace" {
	default="envoy-gateway-system"
}
variable "infrastructure_git_repo_config" {
	default={
		auth={}
		source_paths={}
		target={}
	}
	type=object(
		{
			auth=object({
				credentials_secret_name=optional(string, "liferay/credentials/gitops")
				internal_secret_name=optional(string, "gitops-credentials")
				method=optional(string, "https")
				ssh_private_key_property=optional(string, "git_ssh_private_key")
				token_property=optional(string, "git_access_token")
				username_property=optional(string, "git_machine_user_id")
			})
			revision=optional(string, "HEAD")
			source_paths=object({
				base=optional(string, "liferay/projects/{{path[2]}}/base")
				environments=optional(string, "liferay/projects/*/environments/*")
				infrastructure_provider_values_filename=optional(string, "infrastructure-provider.yaml")
				system=optional(string, "liferay/system")
				values_filename=optional(string, "infrastructure.yaml")
			})
			target=object({
				name=optional(string, "{{path[2]}}-{{path[4]}}-infra")
				namespaceSuffix=optional(string, "{{path[2]}}-{{path[4]}}")
				slugEnvironmentId=optional(string, "{{path[4]}}")
				slugProjectId=optional(string, "{{path[2]}}")
			})
			url=optional(string, null)
		})
	validation {
		condition=(
			!contains(keys(var.infrastructure_git_repo_config.auth), "method") ||
			contains(["https", "ssh"], var.infrastructure_git_repo_config.auth.method))
		error_message="The 'infrastructure_git_repo_auth_config.method' value must be 'https' or 'ssh'."
	}
}
variable "infrastructure_helm_chart_config" {
	default={}
	type=object(
		{
			chart_name=optional(string, "liferay-aws-infrastructure")
			chart_url=optional(string, "oci://us-central1-docker.pkg.dev/external-assets-prd/liferay-helm-chart/liferay-aws-infrastructure")
			path=optional(string, null)
			version=optional(string, "0.3.0")
		})
}
variable "infrastructure_provider_helm_chart_config" {
	default={}
	type=object(
		{
			chart_name=optional(string, "liferay-aws-infrastructure-provider")
			chart_url=optional(string, "oci://us-central1-docker.pkg.dev/external-assets-prd/liferay-helm-chart/liferay-aws-infrastructure-provider")
			path=optional(string, null)
			version=optional(string, "0.2.0")
		})
}
variable "liferay_git_repo_config" {
	default={
		auth={}
		source_paths={}
		target={}
	}
	type=object(
		{
			auth=object({
				credentials_secret_name=optional(string, "liferay/credentials/gitops")
				internal_secret_name=optional(string, "gitops-credentials")
				method=optional(string, "https")
				ssh_private_key_property=optional(string, "git_ssh_private_key")
				token_property=optional(string, "git_access_token")
				username_property=optional(string, "git_machine_user_id")
			})
			revision=optional(string, "HEAD")
			source_paths=object({
				base=optional(string, "liferay/projects/{{path[2]}}/base")
				environments=optional(string, "liferay/projects/*/environments/*")
				values_filename=optional(string, "liferay.yaml")
			})
			target=object({
				name=optional(string, "{{path[2]}}-{{path[4]}}-app")
				namespaceSuffix=optional(string, "{{path[2]}}-{{path[4]}}")
				slugEnvironmentId=optional(string, "{{path[4]}}")
				slugProjectId=optional(string, "{{path[2]}}")
			})
		})
	validation {
		condition=(
			!contains(keys(var.liferay_git_repo_config.auth), "method") ||
			contains(["https", "ssh"], var.liferay_git_repo_config.auth.method))
		error_message="The 'liferay_git_repo_auth_config.method' value must be 'https' or 'ssh'."
	}
}
variable "liferay_git_repo_url" {
	type=string
}
variable "liferay_helm_chart_config" {
	default={}
	type=object({
		chart_url=optional(string, null)
		path=optional(string, null)
	})
}
variable "liferay_helm_chart_name" {
	default="liferay-aws"
	type=string
	validation {
		condition=contains(
			[
				"liferay-default",
				"liferay-aws",
				"liferay-aws-marketplace",
			],
			var.liferay_helm_chart_name)
		error_message="The 'liferay_helm_chart_name' value must be 'liferay-default', 'liferay-aws', or 'liferay-aws-marketplace'."
	}
}
variable "liferay_helm_chart_version" {
	type=string
}
variable "region" {
	type=string
}