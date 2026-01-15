variable "argocd_namespace" {
	default="argocd"
	type=string
}
variable "crossplane_namespace" {
	default="crossplane-system"
	type=string
}
variable "external_secrets_namespace" {
	default="external-secrets"
	type=string
}