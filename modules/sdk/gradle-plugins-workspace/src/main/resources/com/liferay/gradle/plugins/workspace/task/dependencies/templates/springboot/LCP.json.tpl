{
	"kind": "Deployment",
	"id": "__CLIENT_EXTENSION_ID__",
	"cpu": 0.2,
	"scale": 1,
	"memory": 300,
	"loadBalancer": {
		"cdn": true,
		"targetPort": 8081
	},
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
	}
}