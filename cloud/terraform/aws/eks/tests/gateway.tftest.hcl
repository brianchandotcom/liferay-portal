mock_provider "aws" {}
mock_provider "helm" {}
mock_provider "kubernetes" {}
mock_provider "random" {}
mock_provider "time" {}

override_module {
	target=module.eks
	outputs={
		cluster_addons={}
		cluster_certificate_authority_data="dGVzdA=="
		cluster_endpoint="https://example.eks.amazonaws.com"
		cluster_id="liferay-test-eks"
		cluster_name="liferay-test-eks"
		cluster_primary_security_group_id="sg-0123456789abcdef0"
		cluster_version="1.31"
		oidc_provider="oidc.eks.us-east-1.amazonaws.com/id/EXAMPLE"
		oidc_provider_arn="arn:aws:iam::123456789012:oidc-provider/oidc.eks.us-east-1.amazonaws.com/id/EXAMPLE"
	}
}

override_module {
	target=module.vpc
	outputs={
		private_subnets=["subnet-aaa", "subnet-bbb"]
		vpc_id="vpc-0123456789abcdef0"
	}
}

override_module {
	target=module.envoy_proxy_role
	outputs={}
}

override_data {
	target=data.aws_caller_identity.current
	values={ account_id="123456789012" }
}

override_data {
	target=data.aws_availability_zones.available
	values={ names=["us-east-1a", "us-east-1b", "us-east-1c"] }
}

variables {
	deployment_name="liferay-test"
	envoy_gateway_helm_chart_version="1.5.2"
	region="us-east-1"
}

run "should_configure_the_envoy_gateway_helm_release" {
	command=plan

	assert {
		condition=helm_release.envoy_gateway.version == "v1.5.2"
		error_message="The Envoy Gateway chart version must be \"v${var.envoy_gateway_helm_chart_version}\"."
	}

	assert {
		condition=helm_release.envoy_gateway.chart == "gateway-helm"
		error_message="The Envoy Gateway release must use the gateway-helm chart."
	}

	assert {
		condition=helm_release.envoy_gateway.namespace == "envoy-gateway-system"
		error_message="The Envoy Gateway release must default to the envoy-gateway-system namespace."
	}
}

run "should_create_the_envoy_proxy_pod_disruption_budget" {
	command=plan

	assert {
		condition=kubernetes_pod_disruption_budget_v1.envoy_proxy_pdb.spec[0].max_unavailable == "1"
		error_message="The Envoy proxy PDB must allow at most 1 unavailable pod."
	}

	assert {
		condition=kubernetes_pod_disruption_budget_v1.envoy_proxy_pdb.spec[0].selector[0].match_labels["app.kubernetes.io/name"] == "envoy"
		error_message="The Envoy proxy PDB must select Envoy proxy pods."
	}
}

run "should_honor_a_custom_gateway_namespace" {
	command=plan

	variables {
		gateway_namespace="custom-gw"
	}

	assert {
		condition=helm_release.envoy_gateway.namespace == "custom-gw"
		error_message="The Envoy Gateway release must honor a custom gateway_namespace."
	}

	assert {
		condition=kubernetes_pod_disruption_budget_v1.envoy_proxy_pdb.metadata[0].namespace == "custom-gw"
		error_message="The Envoy proxy PDB must be created in the configured gateway_namespace."
	}
}

run "should_set_the_envoy_gateway_helm_values" {
	command=plan

	assert {
		condition=yamldecode(helm_release.envoy_gateway.values[0]).deployment.replicas == 2
		error_message="The Envoy Gateway deployment must run 2 replicas."
	}

	assert {
		condition=yamldecode(helm_release.envoy_gateway.values[0]).podDisruptionBudget.maxUnavailable == 1
		error_message="The Envoy Gateway pod disruption budget must allow at most 1 unavailable pod."
	}

	assert {
		condition=yamldecode(helm_release.envoy_gateway.values[0]).config.envoyGateway.extensionApis.enableBackend == false
		error_message="The Envoy Gateway backend extension API must be disabled."
	}
}
