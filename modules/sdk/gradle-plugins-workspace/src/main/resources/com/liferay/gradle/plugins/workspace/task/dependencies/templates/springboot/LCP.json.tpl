{
	"cpu": 0.2,
	"environments": {
		"dev": {
			"loadBalancer": {
				"cdn": false,
				"targetPort": 8081
			}
		},
		"infra": {
			"deploy": false
		}
	},
	"id": "__CLIENT_EXTENSION_ID__",
	"kind": "Deployment",
	"loadBalancer": {
		"cdn": true,
		"targetPort": 8081
	},
	"memory": 300,
	"scale": 1
}