resource "helm_release" "crossplane" {
	atomic=true
	chart="crossplane"
	cleanup_on_fail=true
	create_namespace=true
	name="crossplane"
	namespace=var.crossplane_namespace
	repository="https://charts.crossplane.io/stable"
	upgrade_install=true
	version="2.1.3"
	wait=true
}