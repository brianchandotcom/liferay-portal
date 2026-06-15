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

run "should_disable_keda_by_default" {
	command=plan

	assert {
		condition=length(helm_release.keda) == 0
		error_message="KEDA must not be installed when keda_enabled is false."
	}
}

run "should_honor_a_custom_keda_namespace" {
	command=plan

	variables {
		keda_enabled=true
		keda_namespace="autoscaling"
	}

	assert {
		condition=helm_release.keda[0].namespace == "autoscaling"
		error_message="A custom keda_namespace must flow to the Helm release."
	}
}

run "should_install_keda_when_enabled" {
	command=plan

	variables {
		keda_enabled=true
	}

	assert {
		condition=length(helm_release.keda) == 1
		error_message="KEDA must be installed when keda_enabled is true."
	}

	assert {
		condition=helm_release.keda[0].version == var.keda_helm_chart_version
		error_message="The KEDA Helm release must use the configured chart version."
	}

	assert {
		condition=helm_release.keda[0].namespace == "keda-system" && helm_release.keda[0].create_namespace == true
		error_message="The KEDA Helm release must default to the keda-system namespace and create it."
	}
}
