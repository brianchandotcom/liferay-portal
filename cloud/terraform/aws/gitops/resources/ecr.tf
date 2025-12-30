resource "aws_iam_role" "ecr_role" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRoleWithWebIdentity"
					Condition={
						StringEquals={
							"${local.oidc_provider}:aud": "sts.amazonaws.com",
							"${local.oidc_provider}:sub": "system:serviceaccount:${var.argocd_namespace}:${local.ecr_credentials_sync_serviceaccount_name}"
						}
					}
					Effect="Allow"
					Principal={
						Federated="arn:aws:iam::${local.account_id}:oidc-provider/${local.oidc_provider}"
					}
				},
			]
			Version="2012-10-17"
		})
	count=local.liferay_helm_chart_config.ecr_credentials_sync_required ? 1 : 0
	name="${var.cluster_name}-gitops-ecr-credentials-sync"
}
resource "aws_iam_role_policy_attachment" "ecr_policy" {
	count=local.liferay_helm_chart_config.ecr_credentials_sync_required ? 1 : 0
	policy_arn="arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
	role=aws_iam_role.ecr_role[0].name
}
resource "kubernetes_cron_job_v1" "ecr_credentials_sync" {
	count=local.liferay_helm_chart_config.ecr_credentials_sync_required ? 1 : 0
	depends_on=[
		aws_iam_role.ecr_role,
	]
	metadata {
		name="ecr-credentials-sync"
		namespace=var.argocd_namespace
	}
	spec {
		job_template {
			metadata {
			}
			spec {
				template {
					metadata {
					}
					spec {
						container {
							args=[
								local.ecr_credentials_sync_script,
							]
							command=[
								"/bin/sh",
								"-c",
							]
							image=local.ecr_credentials_sync_image
							name="ecr-credentials-sync"
							resources {
								limits={
									cpu="100m"
									memory="128Mi"
								}
								requests={
									cpu="50m"
									memory="64Mi"
								}
							}
						}
						service_account_name=local.ecr_credentials_sync_serviceaccount_name
					}
				}
				ttl_seconds_after_finished=300
			}
		}
		schedule=local.ecr_credentials_sync_schedule
	}
}
resource "kubernetes_job_v1" "ecr_credentials_sync_initial" {
	count=local.liferay_helm_chart_config.ecr_credentials_sync_required ? 1 : 0
	depends_on=[
		aws_iam_role.ecr_role,
	]
	metadata {
		name="ecr-credentials-sync-initial"
		namespace=var.argocd_namespace
	}
	spec {
		template {
			metadata {
			}
			spec {
				container {
					args=[
						local.ecr_credentials_sync_script,
					]
					command=[
						"/bin/sh",
						"-c",
					]
					image=local.ecr_credentials_sync_image
					name="aws-cli"
					resources {
						limits={
							cpu="100m"
							memory="128Mi"
						}
						requests={
							cpu="50m"
							memory="64Mi"
						}
					}
				}
				restart_policy="OnFailure"
				service_account_name=local.ecr_credentials_sync_serviceaccount_name
			}
		}
	}
}
resource "kubernetes_role" "ecr_secret_manager" {
	count=local.liferay_helm_chart_config.ecr_credentials_sync_required ? 1 : 0
	metadata {
		name="ecr-secret-manager"
		namespace=var.argocd_namespace
	}
	rule {
		api_groups=[""]
		resources=["secrets"]
		verbs=[
			"create",
			"get",
			"patch",
			"update",
		]
	}
}
resource "kubernetes_role_binding" "ecr_secret_manager_binding" {
	count=local.liferay_helm_chart_config.ecr_credentials_sync_required ? 1 : 0
	metadata {
		name="ecr-secret-manager-binding"
		namespace=var.argocd_namespace
	}
	role_ref {
		api_group="rbac.authorization.k8s.io"
		kind="Role"
		name=kubernetes_role.ecr_secret_manager[0].metadata[0].name
	}
	subject {
		kind="ServiceAccount"
		name=kubernetes_service_account.ecr_sa[0].metadata[0].name
		namespace=var.argocd_namespace
	}
}
resource "kubernetes_service_account" "ecr_sa" {
	automount_service_account_token=false
	count=local.liferay_helm_chart_config.ecr_credentials_sync_required ? 1 : 0
	metadata {
		annotations={
			"eks.amazonaws.com/role-arn"=aws_iam_role.ecr_role[0].arn
		}
		labels=local.common_labels
		name=local.ecr_credentials_sync_serviceaccount_name
		namespace=var.argocd_namespace
	}
}