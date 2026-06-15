mock_provider "helm" {}
mock_provider "kubernetes" {}
mock_provider "random" {}

variables {
	argo_workflows_helm_chart_version="1.0.10"
	argocd_helm_chart_version="9.5.16"
	crossplane_helm_chart_version="2.1.3"
	external_secrets_helm_chart_version="1.0.0"
	keda_helm_chart_version="2.19.0"
}

run "should_append_a_second_values_document_when_saml_sso_is_enabled" {
	command=plan

	variables {
		argocd_sso_config={
			enable_saml_sso=true
		}
	}

	assert {
		condition=length(helm_release.argocd.values) == 2
		error_message="Enabling SAML SSO must append a second ArgoCD values document."
	}

	assert {
		condition=can(yamldecode(helm_release.argocd.values[1]).configs.cm["dex.config"])
		error_message="The SAML SSO values document must configure dex.config."
	}

	assert {
		condition=strcontains(yamldecode(helm_release.argocd.values[1]).configs.rbac["policy.default"], "role:liferay-guest")
		error_message="The SAML SSO values document must default unmatched users to the guest role."
	}
}

run "should_configure_the_helm_release_with_defaults" {
	command=plan

	assert {
		condition=helm_release.argocd.version == var.argocd_helm_chart_version
		error_message="The ArgoCD Helm release must use the configured chart version."
	}

	assert {
		condition=length(helm_release.argocd.values) == 1
		error_message="Without SAML SSO the ArgoCD values list must contain exactly one document."
	}

	assert {
		condition=yamldecode(helm_release.argocd.values[0]).configs.cm["admin.enabled"] == true
		error_message="admin.enabled must default to true."
	}
}

run "should_create_namespace_and_secret_with_defaults" {
	command=plan

	assert {
		condition=kubernetes_namespace.argocd.metadata[0].name == "argocd-system"
		error_message="The ArgoCD namespace should default to argocd-system."
	}

	assert {
		condition=kubernetes_namespace.argocd.metadata[0].labels["app.kubernetes.io/managed-by"] == "liferay-cloud-native-terraform"
		error_message="The ArgoCD namespace should carry the Terraform manager label from local.common_labels."
	}

	assert {
		condition=kubernetes_namespace.argocd.metadata[0].labels["environment"] == "internal"
		error_message="The ArgoCD namespace should carry the internal environment label."
	}

	assert {
		condition=kubernetes_secret.argocd_secret.metadata[0].name == "argocd-secret"
		error_message="The ArgoCD server secret must be named argocd-secret."
	}

	assert {
		condition=kubernetes_secret.argocd_secret.metadata[0].labels["app.kubernetes.io/managed-by"] == "Helm"
		error_message="argocd-secret must override the managed-by label to Helm so Helm adopts it."
	}
}

run "should_disable_admin_login_when_configured" {
	command=plan

	variables {
		argocd_sso_config={
			enable_admin_login=false
		}
	}

	assert {
		condition=yamldecode(helm_release.argocd.values[0]).configs.cm["admin.enabled"] == false
		error_message="admin.enabled must follow argocd_sso_config.enable_admin_login."
	}

	assert {
		condition=length(helm_release.argocd.values) == 1
		error_message="Disabling admin login must not enable SAML SSO."
	}
}

run "should_honor_a_custom_argocd_namespace" {
	command=plan

	variables {
		argocd_namespace="gitops"
	}

	assert {
		condition=kubernetes_namespace.argocd.metadata[0].name == "gitops"
		error_message="The ArgoCD namespace must honor a custom argocd_namespace."
	}

	assert {
		condition=helm_release.argocd.namespace == "gitops" && output.argocd_namespace == "gitops"
		error_message="A custom argocd_namespace must flow to the Helm release and the module output."
	}
}
