{
	"cpu": 0.1,
	"id": "__CLIENT_EXTENSION_ID__",
	"kind": "Deployment",
	"memory": 50,
	"scale": 1,
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
	"loadBalancer": {
		"cdn": true,
		"targetPort": 80
	}
}