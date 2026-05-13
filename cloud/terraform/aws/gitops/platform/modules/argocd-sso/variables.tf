variable "argocd_sso_config" {
	type=object({
		enable_sso=optional(bool, false)
	})
}
