resource "helm_release" "keda" {
	chart="keda"
	count=var.keda_enabled ? 1 : 0
	create_namespace=true
	name="keda"
	namespace=var.keda_namespace
	repository="https://kedacore.github.io/charts"
	version=var.keda_helm_chart_version
}