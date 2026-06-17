variable "ack_namespace" {
	default="ack-system"
	type=string
}
variable "ack_prometheusservice_helm_chart_version" {
	type=string
}
variable "argo_workflows_helm_chart_version" {
	type=string
}
variable "argo_workflows_namespace" {
	default="argo-workflows-system"
	type=string
}
variable "argocd_helm_chart_version" {
	type=string
}
variable "argocd_namespace" {
	default="argocd-system"
	type=string
}
variable "argocd_sso_config" {
	default={}
	type=object({
		enable_admin_login=optional(bool, true)
		enable_saml_sso=optional(bool, false)
	})
}
variable "crossplane_helm_chart_version" {
	type=string
}
variable "crossplane_namespace" {
	default="crossplane-system"
	type=string
}
variable "deployment_name" {
	type=string
	validation {
		condition=can(regex("^[a-z][a-z0-9-]{2,23}$", var.deployment_name))
		error_message="The variable \"deployment_name\" must be 3-24 characters, start with a lowercase letter, and contain only lowercase letters, numbers, and hyphens."
	}
}
variable "external_secrets_helm_chart_version" {
	type=string
}
variable "external_secrets_namespace" {
	default="external-secrets-system"
	type=string
}
variable "keda_enabled" {
	default=false
	type=bool
}
variable "keda_helm_chart_version" {
	type=string
}
variable "keda_namespace" {
	default="keda-system"
	type=string
}
variable "observability_config" {
	default={}
	type=object(
		{
			enabled=optional(bool, false)
			namespace=optional(string, "observability")
		}
	)
}
variable "region" {
	type=string
}