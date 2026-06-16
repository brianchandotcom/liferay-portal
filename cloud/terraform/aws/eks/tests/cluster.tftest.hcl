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

run "should_accept_custom_deployment_namespace" {
	command=plan

	variables {
		deployment_namespace="my-ns"
	}

	assert {
		condition=output.deployment_namespace == "my-ns"
		error_message="A valid custom deployment_namespace must be accepted and exposed."
	}
}

run "should_accept_deployment_name_with_3_characters" {
	command=plan

	variables {
		deployment_name="abc"
	}

	assert {
		condition=output.deployment_name == "abc"
		error_message="A 3-character lowercase deployment_name must be accepted."
	}
}

run "should_derive_cluster_name_from_deployment_name" {
	command=plan

	assert {
		condition=local.cluster_name == "liferay-test-eks"
		error_message="local.cluster_name must be \"<deployment_name>-eks\"."
	}

	assert {
		condition=output.cluster_name == "liferay-test-eks"
		error_message="The cluster_name output must come from module.eks."
	}
}

run "should_expose_inputs_as_outputs" {
	command=plan

	assert {
		condition=output.deployment_name == "liferay-test"
		error_message="The deployment_name output must echo var.deployment_name."
	}

	assert {
		condition=output.deployment_namespace == "liferay-system"
		error_message="deployment_namespace must default to \"liferay-system\"."
	}

	assert {
		condition=output.region == "us-east-1"
		error_message="The region output must echo var.region."
	}

	assert {
		condition=output.arn_partition == "aws"
		error_message="arn_partition must default to \"aws\"."
	}
}

run "should_not_accept_deployment_name_longer_than_24_characters" {
	command=plan

	variables {
		deployment_name="abcdefghijklmnopqrstuvwxy"
	}

	expect_failures=[var.deployment_name]
}

run "should_not_accept_deployment_name_shorter_than_3_characters" {
	command=plan

	variables {
		deployment_name="ab"
	}

	expect_failures=[var.deployment_name]
}

run "should_not_accept_deployment_name_starting_with_a_digit" {
	command=plan

	variables {
		deployment_name="1iferay-test"
	}

	expect_failures=[var.deployment_name]
}

run "should_not_accept_invalid_deployment_namespace" {
	command=plan

	variables {
		deployment_namespace="Bad_Namespace"
	}

	expect_failures=[var.deployment_namespace]
}

run "should_not_accept_uppercase_deployment_name" {
	command=plan

	variables {
		deployment_name="Liferay-Test"
	}

	expect_failures=[var.deployment_name]
}
