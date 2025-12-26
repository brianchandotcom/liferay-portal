locals {
	common_labels={
		"app.kubernetes.io/managed-by"=local.terraform_manager_name
		"app.kubernetes.io/part-of"="liferay-gitops"
		"environment"="internal"
	}
	terraform_manager_name="liferay-cloud-native-terraform"
}