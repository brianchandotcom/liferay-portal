resource "grafana_data_source" "amp" {
	is_default=true
	json_data_encoded=jsonencode(
		{
			sigV4Auth=true
			sigV4AuthType="ec2_iam_role"
			sigV4Region=var.region
		})
	name="amp"
	type="prometheus"
	url=var.prometheus_workspace_endpoint
}
