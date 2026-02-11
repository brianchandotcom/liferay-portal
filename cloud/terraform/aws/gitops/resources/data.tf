data "aws_caller_identity" "current" {
}
data "aws_eks_cluster" "cluster" {
	name=local.cluster_name
	region=var.region
}
data "aws_iam_policy_document" "provider_aws_backup_assume_role_policy_document" {
	statement {
		actions=["sts:AssumeRoleWithWebIdentity"]
		condition {
			test="StringEquals"
			values=["sts.amazonaws.com"]
			variable="${local.oidc_provider}:aud"
		}
		condition {
			test="StringLike"
			values=["system:serviceaccount:${var.crossplane_namespace}:provider-aws-backup*"]
			variable="${local.oidc_provider}:sub"
		}
		effect="Allow"
		principals {
			identifiers=["arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"]
			type="Federated"
		}
	}
}
data "aws_iam_policy_document" "provider_aws_backup_policy_document" {
	statement {
		actions=["backup:*"]
		effect="Allow"
		resources=["*"]
	}
	statement {
		actions=["backup-storage:*"]
		effect="Allow"
		resources=["*"]
	}
	statement {
		actions=[
			"iam:GetRole",
			"iam:PassRole"
		]
		effect="Allow"
		resources=["*"]
	}
	statement {
		actions=["kms:CreateGrant"]
		effect="Allow"
		condition {
			test="ForAnyValue:StringEquals"
			values=["aws:backup:backup-vault"]
			variable="kms:EncryptionContextKeys"
		}
		condition {
			test="Bool"
			values=["true"]
			variable="kms:GrantIsForAWSResource"
		}
		condition {
			test="StringLike"
			values=["backup.*.amazonaws.com"]
			variable="kms:ViaService"
		}
		resources=["*"]
	}
	statement {
		actions=["kms:DescribeKey"]
		effect="Allow"
		resources=["*"]
	}
}
data "aws_iam_policy_document" "provider_aws_ec2_assume_role_policy_document" {
	statement {
		actions=["sts:AssumeRoleWithWebIdentity"]
		condition {
			test="StringEquals"
			values=["sts.amazonaws.com"]
			variable="${local.oidc_provider}:aud"
		}
		condition {
			test="StringLike"
			values=["system:serviceaccount:${var.crossplane_namespace}:provider-aws-ec2*"]
			variable="${local.oidc_provider}:sub"
		}
		effect="Allow"
		principals {
			identifiers=["arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"]
			type="Federated"
		}
	}
}
data "aws_iam_policy_document" "provider_aws_ec2_policy_document" {
	statement {
		actions=[
			"ec2:AuthorizeSecurityGroupIngress",
			"ec2:CreateNetworkInterface",
			"ec2:CreateSecurityGroup",
			"ec2:CreateTags",
			"ec2:DeleteNetworkInterface",
			"ec2:DeleteSecurityGroup",
			"ec2:DescribeAvailabilityZones",
			"ec2:DescribeNetworkInterfaces",
			"ec2:DescribeSecurityGroupRules",
			"ec2:DescribeSecurityGroups",
			"ec2:DescribeSubnets",
			"ec2:DescribeVpcs",
			"ec2:ModifyNetworkInterfaceAttribute",
			"ec2:ModifySecurityGroupRules",
			"ec2:RevokeSecurityGroupIngress",
		]
		effect="Allow"
		resources=["*"]
	}
}
data "aws_iam_policy_document" "provider_aws_iam_assume_role_policy_document" {
	statement {
		actions=["sts:AssumeRoleWithWebIdentity"]
		condition {
			test="StringEquals"
			values=["sts.amazonaws.com"]
			variable="${local.oidc_provider}:aud"
		}
		condition {
			test="StringLike"
			values=["system:serviceaccount:${var.crossplane_namespace}:provider-aws-iam*"]
			variable="${local.oidc_provider}:sub"
		}
		effect="Allow"
		principals {
			identifiers=["arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"]
			type="Federated"
		}
	}
}
data "aws_iam_policy_document" "provider_aws_iam_policy_document" {
	statement {
		actions=[
			"iam:AttachRolePolicy",
			"iam:AttachUserPolicy",
			"iam:CreateAccessKey",
			"iam:CreatePolicy",
			"iam:CreateRole",
			"iam:CreateUser",
			"iam:DeleteAccessKey",
			"iam:DeletePolicy",
			"iam:DeleteRole",
			"iam:DeleteUser",
			"iam:DeleteUserPolicy",
			"iam:DetachRolePolicy",
			"iam:DetachUserPolicy",
			"iam:GetAccessKeyLastUsed",
			"iam:GetPolicy",
			"iam:GetPolicyVersion",
			"iam:GetRole",
			"iam:GetUser",
			"iam:GetUserPolicy",
			"iam:ListAccessKeys",
			"iam:ListAttachedRolePolicies",
			"iam:ListAttachedUserPolicies",
			"iam:ListGroupsForUser",
			"iam:ListPolicyVersions",
			"iam:ListRolePolicies",
			"iam:ListUserPolicies",
			"iam:PutUserPolicy",
			"iam:TagPolicy",
			"iam:TagRole",
			"iam:TagUser",
			"iam:UntagUser",
			"iam:UpdateAccessKey",
			"iam:UpdateUser",
		]
		effect="Allow"
		resources=["*"]
	}
}
data "aws_iam_policy_document" "provider_aws_opensearch_assume_role_policy_document" {
	statement {
		actions=["sts:AssumeRoleWithWebIdentity"]
		condition {
			test="StringEquals"
			values=["sts.amazonaws.com"]
			variable="${local.oidc_provider}:aud"
		}
		condition {
			test="StringLike"
			values=["system:serviceaccount:${var.crossplane_namespace}:provider-aws-opensearch*"]
			variable="${local.oidc_provider}:sub"
		}
		effect="Allow"
		principals {
			identifiers=["arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"]
			type="Federated"
		}
	}
}
data "aws_iam_policy_document" "provider_aws_opensearch_policy_document" {
	statement {
		actions=[
			"es:AddTags",
			"es:CreateDomain",
			"es:DeleteDomain",
			"es:DescribeDomain",
			"es:DescribeDomainConfig",
			"es:DescribeDomainHealth",
			"es:DescribeDomainNodes",
			"es:ESHttpGet",
			"es:ESHttpPut",
			"es:ListDomainNames",
			"es:ListTags",
			"es:RemoveTags",
			"es:UpdateDomainConfig",
		]
		effect="Allow"
		resources=["*"]
	}
}
data "aws_iam_policy_document" "provider_aws_rds_assume_role_policy_document" {
	statement {
		actions=["sts:AssumeRoleWithWebIdentity"]
		condition {
			test="StringEquals"
			values=["sts.amazonaws.com"]
			variable="${local.oidc_provider}:aud"
		}
		condition {
			test="StringLike"
			values=["system:serviceaccount:${var.crossplane_namespace}:provider-aws-rds*"]
			variable="${local.oidc_provider}:sub"
		}
		effect="Allow"
		principals {
			identifiers=["arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"]
			type="Federated"
		}
	}
}
data "aws_iam_policy_document" "provider_aws_rds_policy_document" {
	statement {
		actions=[
			"rds:AddTagsToResource",
			"rds:CreateDBInstance",
			"rds:CreateDBSubnetGroup",
			"rds:DeleteDBInstance",
			"rds:DeleteDBSubnetGroup",
			"rds:DescribeDBInstances",
			"rds:DescribeDBParameters",
			"rds:DescribeDBSubnetGroups",
			"rds:DescribeEngineDefaultParameters",
			"rds:ListTagsForResource",
			"rds:ModifyDBInstance",
			"rds:ModifyDBSubnetGroup",
		]
		effect="Allow"
		resources=["*"]
	}
	statement {
		actions=[
			"ec2:AuthorizeSecurityGroupIngress",
			"ec2:CreateSecurityGroup",
			"ec2:CreateTags",
			"ec2:DescribeSecurityGroups",
		]
		effect="Allow"
		resources=["*"]
	}
}
data "aws_iam_policy_document" "provider_aws_s3_assume_role_policy_document" {
	statement {
		actions=["sts:AssumeRoleWithWebIdentity"]
		condition {
			test="StringEquals"
			values=["sts.amazonaws.com"]
			variable="${local.oidc_provider}:aud"
		}
		condition {
			test="StringLike"
			values=["system:serviceaccount:${var.crossplane_namespace}:provider-aws-s3*"]
			variable="${local.oidc_provider}:sub"
		}
		effect="Allow"
		principals {
			identifiers=["arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"]
			type="Federated"
		}
	}
}
data "aws_iam_policy_document" "provider_aws_s3_policy_document" {
	statement {
		actions=[
			"s3:AbortMultipartUpload",
			"s3:CreateBucket",
			"s3:DeleteBucket",
			"s3:GetAccelerateConfiguration",
			"s3:GetBucketAcl",
			"s3:GetBucketCORS",
			"s3:GetBucketLocation",
			"s3:GetBucketLogging",
			"s3:GetBucketObjectLockConfiguration",
			"s3:GetBucketPolicy",
			"s3:GetBucketPublicAccessBlock",
			"s3:GetBucketRequestPayment",
			"s3:GetBucketTagging",
			"s3:GetBucketVersioning",
			"s3:GetBucketWebsite",
			"s3:GetEncryptionConfiguration",
			"s3:GetLifecycleConfiguration",
			"s3:GetReplicationConfiguration",
			"s3:ListBucket",
			"s3:ListBucketMultipartUploads",
			"s3:PutBucketAcl",
			"s3:PutBucketCORS",
			"s3:PutBucketPolicy",
			"s3:PutBucketPublicAccessBlock",
			"s3:PutBucketTagging",
			"s3:PutBucketVersioning",
			"s3:PutBucketWebsite",
			"s3:PutEncryptionConfiguration",
			"s3:PutLifecycleConfiguration",
		]
		effect="Allow"
		resources=["arn:aws:s3:::*"]
	}
	statement {
		actions=[
			"s3:DeleteObject",
			"s3:GetObject",
			"s3:PutObject",
		]
		effect="Allow"
		resources=["arn:aws:s3:::*/*"]
	}
}
data "aws_iam_roles" "opensearch_linked_role_lookup" {
	name_regex="AWSServiceRoleForAmazonOpenSearchService"
}
data "aws_subnets" "private" {
	filter {
		name="tag:DeploymentName"
		values=[var.deployment_name]
	}
	filter {
		name="tag:kubernetes.io/role/internal-elb"
		values=["1"]
	}
	filter {
		name="vpc-id"
		values=[data.aws_vpc.current.id]
	}
}
data "aws_vpc" "current" {
	id=data.aws_eks_cluster.cluster.vpc_config[0].vpc_id
}