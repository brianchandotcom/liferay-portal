variable "argocd_namespace" {
	default="argocd"
	type=string
}
variable "git_token_property" {
	type=string
}
variable "git_username_property" {
	type=string
}
variable "remote_secret_key" {
	type=string
}
variable "secret_store_provider_yaml_spec" {
	type=string
}