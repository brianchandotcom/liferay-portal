variable "argo_workflows_helm_chart_version" {
	type=string
}
variable "argo_workflows_namespace" {
	default="argo-workflows-system"
}
variable "argocd_helm_chart_version" {
	type=string
}
variable "argocd_namespace" {
	default="argocd-system"
}
variable "crossplane_helm_chart_version" {
	type=string
}
variable "crossplane_namespace" {
	default="crossplane-system"
}
variable "external_secrets_helm_chart_version" {
	type=string
}
variable "external_secrets_namespace" {
	default="external-secrets-system"
}