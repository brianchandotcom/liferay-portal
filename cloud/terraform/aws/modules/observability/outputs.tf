output "grafana_workspace_api_key" {
	value=aws_grafana_workspace_api_key.amg_api_key.key
}
output "grafana_workspace_endpoint" {
	value=aws_grafana_workspace.amg.endpoint
}
output "grafana_workspace_id" {
	value=aws_grafana_workspace.amg.id
}
output "prometheus_workspace_endpoint" {
	value=aws_prometheus_workspace.amp.prometheus_endpoint
}
output "prometheus_workspace_id" {
	value=aws_prometheus_workspace.amp.id
}