variable "grafana_workspace_api_key" {
	sensitive=true
	type=string
}
variable "grafana_workspace_endpoint" {
	type=string
}
variable "prometheus_workspace_endpoint" {
	type=string
}
variable "region" {
	type=string
}