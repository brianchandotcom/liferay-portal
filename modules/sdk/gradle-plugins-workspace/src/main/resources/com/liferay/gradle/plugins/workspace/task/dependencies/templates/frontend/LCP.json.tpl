{
	"cpu": 0.1,
	"env": {
		"LIFERAY_ROUTES_CLIENT_EXTENSION": "/etc/liferay/lxc/ext-init-metadata",
		"LIFERAY_ROUTES_DXP": "/etc/liferay/lxc/dxp-metadata"
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