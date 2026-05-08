output "auth_sso_values" {
	value=compact([
		yamlencode({
			configs={
				cm={
					"dex.config"=yamlencode({
						connectors=[{
							config={
								caData="$customer-idp-saml:caData"
								emailAttr="email"
								entityIssuer="$customer-idp-saml:entityIssuer"
								groupsAttr="groups"
								redirectURI="$customer-idp-saml:redirectURI"
								ssoURL="$customer-idp-saml:ssoURL"
								usernameAttr="name"
							}
							id="customer-idp"
							name="SAML"
							type="saml"
						}]
					})
				}
				rbac={
					"policy.csv"=join("\n", [
						"g, customer-idp:liferay-argocd-role-admin, role:liferay-admin",
						"g, customer-idp:liferay-argocd-role-guest, role:liferay-guest",
						"p, role:liferay-admin, accounts, *, *, allow",
						"p, role:liferay-admin, applications, *, */*, allow",
						"p, role:liferay-admin, clusters, *, *, allow",
						"p, role:liferay-admin, projects, *, *, allow",
						"p, role:liferay-admin, repositories, *, *, allow",
						"p, role:liferay-guest, applications, get, */*, allow",
						"p, role:liferay-guest, clusters, get, *, allow",
						"p, role:liferay-guest, projects, get, *, allow",
						"p, role:liferay-guest, repositories, get, *, allow",
					])
					"policy.default"="role:liferay-guest"
				}
			}
		}),
		var.argocd_sso_config.dex_config != null ? yamlencode({
			configs={
				cm={
					"dex.config"=yamlencode(var.argocd_sso_config.dex_config)
				}
			}
		}) : null,
		var.argocd_sso_config.rbac != null ? yamlencode({
			configs={
				rbac={
					"policy.csv"=base64decode(var.argocd_sso_config.rbac["policy.csv"])
					"policy.default"=var.argocd_sso_config.rbac["policy.default"]
					scopes=var.argocd_sso_config.rbac.scopes
				}
			}
		}) : null,
	])
}
