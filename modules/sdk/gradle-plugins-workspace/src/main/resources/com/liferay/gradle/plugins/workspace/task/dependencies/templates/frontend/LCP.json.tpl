{
	"cpu": 0.1,
	"env": {
		"DXP_METADATA_PATH": "/etc/liferay/lxc/dxp-metadata",
		"EXT_INIT_METADATA_PATH": "/etc/liferay/lxc/ext-init-metadata"
	},
	"environments": {
		"dev": {
			"loadBalancer": {
				"cdn": false,
				"targetPort": 80
			}
		},
		"infra": {
			"deploy": false
		}
	},
	"id": "__PROJECT_ID__",
	"kind": "Deployment",
	"livenessProbe": {
		"httpGet": {
			"path": "/",
			"port": 80
		}
	},
	"loadBalancer": {
		"cdn": true,
		"targetPort": 80
	},
	"memory": 50,
	"readinessProbe": {
		"httpGet": {
			"path": "/",
			"port": 80
		}
	},
	"scale": 1
}