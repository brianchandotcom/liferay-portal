locals {
	common_labels={
		"app.kubernetes.io/managed-by"="terraform"
		"app.kubernetes.io/part-of"="liferay-gitops"
		"environment"="internal"
	}
}