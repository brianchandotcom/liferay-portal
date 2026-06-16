mock_provider "aws" {}
mock_provider "helm" {}
mock_provider "kubernetes" {}
mock_provider "random" {}
mock_provider "time" {}

override_data {
	target=data.aws_availability_zones.available
	values={
		names=["us-east-1a", "us-east-1b", "us-east-1c"]
	}
}

override_data {
	target=data.aws_caller_identity.current
	values={
		account_id="123456789012"
	}
}

override_module {
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
	target=module.eks
}

override_module {
	outputs={}
	target=module.envoy_proxy_role
}

override_module {
	outputs={
		private_subnets=["subnet-aaa", "subnet-bbb"]
		vpc_id="vpc-0123456789abcdef0"
	}
	target=module.vpc
}

run "should_cover_multiple_repositories_in_the_ecr_policy" {
	assert {
		condition=length(jsondecode(aws_iam_role_policy.this[0].policy).Statement[0].Resource) == 2
		error_message="The ECR pull policy must list every configured repository ARN"
	}

	command=plan

	variables {
		ecr_repositories={
			images={
				arn="arn:aws:ecr:us-east-1:123456789012:repository/liferay-test-images"
				url="123456789012.dkr.ecr.us-east-1.amazonaws.com/liferay-test-images"
			}
			search={
				arn="arn:aws:ecr:us-east-1:123456789012:repository/liferay-test-search"
				url="123456789012.dkr.ecr.us-east-1.amazonaws.com/liferay-test-search"
			}
		}
	}
}

run "should_create_an_ecr_policy_when_repositories_are_configured" {
	assert {
		condition=contains(jsondecode(aws_iam_role_policy.this[0].policy).Statement[0].Resource, "arn:aws:ecr:us-east-1:123456789012:repository/liferay-test-images")
		error_message="The ECR pull policy must scope to the configured repository ARN"
	}

	assert {
		condition=length(aws_iam_role_policy.this) == 1
		error_message="The IRSA ECR pull policy must be attached when ecr_repositories is nonempty"
	}

	command=plan

	variables {
		ecr_repositories={
			images={
				arn="arn:aws:ecr:us-east-1:123456789012:repository/liferay-test-images"
				url="123456789012.dkr.ecr.us-east-1.amazonaws.com/liferay-test-images"
			}
		}
	}
}

run "should_harden_the_kms_key" {
	assert {
		condition=aws_kms_alias.eks_kms_alias.name == "alias/liferay-test-eks_kms"
		error_message="The KMS alias must embed local.cluster_name (\"<deployment_name>-eks\")"
	}

	assert {
		condition=aws_kms_key.eks_secrets.deletion_window_in_days == 7
		error_message="The EKS secrets KMS key must use a 7-day deletion window"
	}

	assert {
		condition=aws_kms_key.eks_secrets.enable_key_rotation == true
		error_message="The EKS secrets KMS key must have rotation enabled"
	}

	assert {
		condition=jsondecode(aws_kms_key.eks_secrets.policy).Statement[1].Principal.Service == "eks.amazonaws.com"
		error_message="The KMS key policy must grant the EKS service access"
	}

	command=plan
}

run "should_name_and_scope_the_irsa_role" {
	assert {
		condition=aws_iam_role.irsa.name == "liferay-test-irsa"
		error_message="The IRSA role name must be derived from deployment_name"
	}

	assert {
		condition=jsondecode(aws_iam_role.irsa.assume_role_policy).Statement[0].Condition.StringEquals["oidc.eks.us-east-1.amazonaws.com/id/EXAMPLE:aud"] == "sts.amazonaws.com"
		error_message="The IRSA trust policy audience must be sts.amazonaws.com"
	}

	assert {
		condition=jsondecode(aws_iam_role.irsa.assume_role_policy).Statement[0].Condition.StringLike["oidc.eks.us-east-1.amazonaws.com/id/EXAMPLE:sub"] == "system:serviceaccount:liferay-*:liferay-default"
		error_message="The IRSA trust policy must scope to the liferay-* namespace pattern and liferay-default service account"
	}

	assert {
		condition=jsondecode(aws_iam_role.irsa.assume_role_policy).Statement[0].Principal.Federated == "arn:aws:iam::123456789012:oidc-provider/oidc.eks.us-east-1.amazonaws.com/id/EXAMPLE"
		error_message="The IRSA trust policy must use the constructed local.oidc_provider_arn"
	}

	command=plan
}

run "should_not_create_an_ecr_policy_by_default" {
	assert {
		condition=length(aws_iam_role_policy.this) == 0
		error_message="No ECR pull policy must be attached when ecr_repositories is empty"
	}

	command=plan
}

variables {
	deployment_name="liferay-test"
	envoy_gateway_helm_chart_version="1.5.2"
	region="us-east-1"
}