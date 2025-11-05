{
	"#child-nodes": [
		{
			"#tag-name": "name",
			"#value": "__WORKFLOW_DEFINITION_NAME__"
		},
		{
			"#tag-name": "version",
			"#value": "1"
		},
		{
			"#child-nodes": [
				{
					"#tag-name": "name",
					"#value": "start"
				},
				{
					"#cdata-value": [
						"{",
						"    \"xy\": [",
						"        273,",
						"        25",
						"    ]",
						"}"
					],
					"#tag-name": "metadata"
				},
				{
					"#tag-name": "initial",
					"#value": "true"
				},
				{
					"#child-nodes": [
						{
							"#tag-name": "label",
							"#value": "Start",
							"language-id": "en_US"
						}
					],
					"#tag-name": "labels"
				},
				{
					"#child-nodes": [
						{
							"#child-nodes": [
								{
									"#child-nodes": [
										{
											"#tag-name": "label",
											"#value": "__WORKFLOW_DEFINITION_NAME__",
											"language-id": "en_US"
										}
									],
									"#tag-name": "labels"
								},
								{
									"#tag-name": "name",
									"#value": "__WORKFLOW_TASK_NAME__"
								},
								{
									"#tag-name": "target",
									"#value": "__WORKFLOW_TASK_NAME__"
								},
								{
									"#tag-name": "default",
									"#value": "true"
								}
							],
							"#tag-name": "transition"
						}
					],
					"#tag-name": "transitions"
				}
			],
			"#tag-name": "state"
		},
		{
			"#child-nodes": [
				{
					"#tag-name": "name",
					"#value": "end"
				},
				{
					"#tag-name": "description",
					"#value": "Conclude the workflow."
				},
				{
					"#cdata-value": [
						"{",
						"    \"terminal\": true,",
						"    \"xy\": [",
						"        277,",
						"        459",
						"    ]",
						"}"
					],
					"#tag-name": "metadata"
				},
				{
					"#child-nodes": [
						{
							"#tag-name": "label",
							"#value": "End",
							"language-id": "en_US"
						}
					],
					"#tag-name": "labels"
				}
			],
			"#tag-name": "state"
		},
		{
			"#child-nodes": [
				{
					"#tag-name": "name",
					"#value": "__WORKFLOW_TASK_NAME__"
				},
				{
					"#tag-name": "description",
					"#value": "Ask a user to work on the item."
				},
				{
					"#cdata-value": [
						"{",
						"    \"xy\": [",
						"        276,",
						"        242",
						"    ]",
						"}"
					],
					"#tag-name": "metadata"
				},
				{
					"#child-nodes": [
						{
							"#tag-name": "user"
						}
					],
					"#tag-name": "assignments"
				},
				{
					"#child-nodes": [
						{
							"#tag-name": "label",
							"#value": "__WORKFLOW_DEFINITION_NAME__",
							"language-id": "en_US"
						}
					],
					"#tag-name": "labels"
				},
				{
					"#child-nodes": [
						{
							"#child-nodes": [
								{
									"#child-nodes": [
										{
											"#tag-name": "label",
											"#value": "end",
											"language-id": "en_US"
										}
									],
									"#tag-name": "labels"
								},
								{
									"#tag-name": "name",
									"#value": "end"
								},
								{
									"#tag-name": "target",
									"#value": "end"
								},
								{
									"#tag-name": "default",
									"#value": "true"
								}
							],
							"#tag-name": "transition"
						}
					],
					"#tag-name": "transitions"
				}
			],
			"#tag-name": "task"
		}
	],
	"#tag-name": "workflow-definition",
	"xmlns": "urn:liferay.com:liferay-workflow_7.4.0",
	"xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
	"xsi:schemaLocation": "urn:liferay.com:liferay-workflow_7.4.0 http://www.liferay.com/dtd/liferay-workflow-definition_7_4_0.xsd"
}