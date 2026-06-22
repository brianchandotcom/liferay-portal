mock_provider "helm" {}
mock_provider "kubernetes" {}
mock_provider "random" {}
run "should_configure_argo_workflows_with_defaults" {
	assert {
		condition=helm_release.argo_workflows.version == var.argo_workflows_helm_chart_version
		error_message="The Argo Workflows Helm release must use the configured chart version"
	}
	assert {
		condition=helm_release.argo_workflows.namespace == "argo-workflows-system"
		error_message="The Argo Workflows Helm release must default to the argo-workflows-system namespace"
	}
	assert {
		condition=helm_release.argo_workflows.upgrade_install == true
		error_message="The Argo Workflows Helm release must use upgrade_install"
	}
	assert {
		condition=kubernetes_namespace.argo_workflows.metadata[0].name == "argo-workflows-system"
		error_message="The Argo Workflows namespace should default to argo-workflows-system"
	}
	assert {
		condition=kubernetes_namespace.argo_workflows.metadata[0].labels["app.kubernetes.io/managed-by"] == "liferay-cloud-native-terraform"
		error_message="The Argo Workflows namespace should carry the Terraform manager label from local.common_labels"
	}
	command=plan
}
run "should_honor_a_custom_argo_workflows_namespace" {
	assert {
		condition=helm_release.argo_workflows.namespace == "workflows" && kubernetes_namespace.argo_workflows.metadata[0].name == "workflows"
		error_message="A custom argo_workflows_namespace must flow to both the Helm release and the namespace"
	}
	command=plan
	variables {
		argo_workflows_namespace="workflows"
	}
}
variables {
	argo_workflows_helm_chart_version="1.0.10"
	argocd_helm_chart_version="9.5.16"
	crossplane_helm_chart_version="2.1.3"
	external_secrets_helm_chart_version="1.0.0"
	keda_helm_chart_version="2.19.0"
}