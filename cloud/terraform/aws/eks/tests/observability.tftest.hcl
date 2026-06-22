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
	outputs={}
	target=module.alloy_role
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
	outputs={}
	target=module.grafana_role
}
override_module {
	outputs={}
	target=module.rds_exporter_role
}
override_module {
	outputs={
		private_subnets=["subnet-aaa", "subnet-bbb"]
		vpc_id="vpc-0123456789abcdef0"
	}
	target=module.vpc
}
run "should_create_observability_resources_when_enabled" {
	assert {
		condition=length(aws_iam_policy.alloy_cloudwatch_rds) == 1 && length(aws_iam_policy.rds_exporter) == 1
		error_message="The Alloy CloudWatch/RDS and RDS exporter IAM policies must be created when observability is enabled"
	}
	assert {
		condition=length(aws_prometheus_workspace.amp) == 1
		error_message="A managed Prometheus workspace must be created when observability is enabled"
	}
	command=plan
	variables {
		observability_config={
			enabled=true
		}
	}
}
run "should_disable_observability_by_default" {
	assert {
		condition=length(aws_iam_policy.alloy_cloudwatch_rds) == 0 && length(aws_iam_policy.rds_exporter) == 0
		error_message="No observability IAM policies must be created when observability is disabled"
	}
	assert {
		condition=length(aws_prometheus_workspace.amp) == 0
		error_message="No managed Prometheus workspace must be created when observability is disabled"
	}
	command=plan
}
run "should_name_observability_resources_when_enabled" {
	assert {
		condition=aws_iam_policy.alloy_cloudwatch_rds[0].name == "liferay-test-alloy-cloudwatch-rds"
		error_message="The Alloy CloudWatch/RDS policy name must embed deployment_name"
	}
	assert {
		condition=aws_iam_policy.rds_exporter[0].name == "liferay-test-rds-exporter"
		error_message="The RDS exporter policy name must embed deployment_name"
	}
	assert {
		condition=aws_prometheus_workspace.amp[0].alias == "liferay-test-amp-workspace"
		error_message="The Prometheus workspace alias must embed deployment_name"
	}
	command=plan
	variables {
		observability_config={
			enabled=true
		}
	}
}
run "should_scope_the_observability_policies" {
	assert {
		condition=contains([for statement in jsondecode(aws_iam_policy.alloy_cloudwatch_rds[0].policy).Statement : statement.Sid], "RDSDescribe")
		error_message="The Alloy CloudWatch/RDS policy must allow describing RDS instances"
	}
	assert {
		condition=contains([for statement in jsondecode(aws_iam_policy.rds_exporter[0].policy).Statement : statement.Sid], "CloudWatchMetricsRead")
		error_message="The RDS exporter policy must allow reading CloudWatch metrics"
	}
	command=plan
	variables {
		observability_config={
			enabled=true
		}
	}
}
variables {
	deployment_name="liferay-test"
	envoy_gateway_helm_chart_version="1.5.2"
	region="us-east-1"
}