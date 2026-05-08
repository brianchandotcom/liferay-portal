variable "argocd_sso_config" {
	type=object({
		custom_values_yaml=optional(string)
		enable_sso=optional(bool, false)
	})
}
