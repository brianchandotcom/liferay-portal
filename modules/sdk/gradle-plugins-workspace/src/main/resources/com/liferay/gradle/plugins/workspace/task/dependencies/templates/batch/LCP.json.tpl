{
	"cpu": 0.2,
	"env": {
		"DXP_METADATA_PATH": "/etc/liferay/lxc/dxp-metadata",
		"EXT_INIT_METADATA_PATH": "/etc/liferay/lxc/ext-init-metadata",
		"LIFERAY_BATCH_OAUTH_APP_ERC": "__batch.oAuthApplicationHeadlessServer__"
	},
	"environments": {
		"infra": {
			"deploy": false
		}
	},
	"id": "__PROJECT_ID__",
	"kind": "Job",
	"memory": 300,
	"scale": 1
}