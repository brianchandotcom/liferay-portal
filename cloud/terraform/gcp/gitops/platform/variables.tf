variable "argo_workflows_namespace" {
	default="argo-workflows-system"
	type=string
}
variable "argocd_namespace" {
	default="argocd-system"
	type=string
}
variable "crossplane_namespace" {
	default="crossplane-system"
	type=string
}
variable "deployment_name" {
	type=string
}
variable "external_secrets_namespace" {
	default="external-secrets-system"
	type=string
}
variable "project_id" {
	type=string
}
variable "region" {
	type=string
}