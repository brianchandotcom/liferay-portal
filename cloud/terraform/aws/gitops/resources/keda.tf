resource "aws_iam_role" "keda" {
	assume_role_policy=jsonencode(
		{
			Statement=[
				{
					Action="sts:AssumeRoleWithWebIdentity"
					Condition={
						StringEquals={
							"${local.oidc_provider}:aud"="sts.amazonaws.com",
							"${local.oidc_provider}:sub"="system:serviceaccount:${var.keda_namespace}:keda-operator"
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
	count=var.keda_enabled ? 1 : 0
	name="${local.cluster_name}-keda-irsa"
}
resource "aws_iam_role_policy" "keda_amp_read" {
	count=var.keda_enabled ? 1 : 0
	name="${local.cluster_name}-keda-amp-read-policy"
	policy=jsonencode(
		{
			Statement=[
				{
					Action=[
						"aps:GetLabels",
						"aps:GetMetricMetadata",
						"aps:GetSeries",
						"aps:QueryMetrics"
					],
					Effect="Allow",
					Resource="arn:aws:aps:${var.region}:${local.account_id}:workspace/*"
				}
			]
			Version="2012-10-17"
		})
	role=aws_iam_role.keda[0].id
}
resource "kubernetes_annotations" "keda_operator_sa" {
	annotations={
		"eks.amazonaws.com/role-arn"=aws_iam_role.keda[0].arn
	}
	api_version="v1"
	count=var.keda_enabled ? 1 : 0
	force=true
	kind="ServiceAccount"
	metadata {
		name="keda-operator"
		namespace=var.keda_namespace
	}
}