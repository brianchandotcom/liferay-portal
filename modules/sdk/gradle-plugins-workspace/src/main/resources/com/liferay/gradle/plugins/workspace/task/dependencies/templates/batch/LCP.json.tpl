{
	"cpu": 0.2,
	"env": {
		"LIFERAY_BATCH_OAUTH_APP_ERC": "__batch.oAuthApplicationHeadlessServer__",
		"LIFERAY_ROUTES_CLIENT_EXTENSION": "/etc/liferay/lxc/ext-init-metadata",
		"LIFERAY_ROUTES_DXP": "/etc/liferay/lxc/dxp-metadata"
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