locals {
	account_id=data.aws_caller_identity.current.account_id
	cluster_name="${var.deployment_name}-eks"
	common_labels={
		"app.kubernetes.io/managed-by"=local.terraform_manager_name
		"environment"="internal"
	}
	default_crossplane_container_security_context={
		allowPrivilegeEscalation=false
		capabilities={
			drop=["ALL"]
		}
		readOnlyRootFilesystem=true
		runAsGroup=65532
		runAsUser=65532
	}
	default_crossplane_pod_security_context={
		runAsNonRoot=true
		seccompProfile={
			type="RuntimeDefault"
		}
	}
	karpenter_pod_annotations={
		"karpenter.sh/do-not-disrupt"="30m"
	}
	oidc_provider=replace(data.aws_eks_cluster.cluster.identity[0].oidc[0].issuer, "https://", "")
	terraform_manager_name="liferay-cloud-native-terraform"
}