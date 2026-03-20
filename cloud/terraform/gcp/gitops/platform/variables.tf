variable "argo_workflows_namespace" {
	default="argo-workflows-system"
}
variable "argocd_namespace" {
	default="argocd-system"
}
variable "crossplane_namespace" {
	default="crossplane-system"
}
variable "deployment_name" {
	type=string
}
variable "external_secrets_namespace" {
	default="external-secrets-system"
}
variable "project_id" {
	type=string
}
variable "region" {
	type=string
}