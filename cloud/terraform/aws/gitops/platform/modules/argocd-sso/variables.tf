variable "argocd_sso_config" {
	type=object({
		dex_config=optional(any)
		enable_sso=optional(bool, false)
		rbac=optional(any)
	})
}
