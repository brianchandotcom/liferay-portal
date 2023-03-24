{
	"cpu": 0.1,
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
	"id": "__CLIENT_EXTENSION_ID__",
	"kind": "Deployment",
	"loadBalancer": {
		"cdn": true,
		"targetPort": 80
	},
	"memory": 50,
	"scale": 1
}