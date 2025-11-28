{
	"#child-nodes": [
		{
			"#tag-name": "name",
			"#value": "[$WORKFLOW_DEFINITION_NAME$]"
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
											"#value": "[$WORKFLOW_DEFINITION_NAME$]",
											"language-id": "en_US"
										}
									],
									"#tag-name": "labels"
								},
								{
									"#tag-name": "name",
									"#value": "[$WORKFLOW_NODE_NAME$]"
								},
								{
									"#tag-name": "target",
									"#value": "[$WORKFLOW_NODE_NAME$]"
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
		"#tag-name": "llm",
		"#child-nodes": [
			{
			"#tag-name": "name",
			"#value": "[$WORKFLOW_NODE_NAME$]"
			},
			{
			"#tag-name": "metadata",
			"#cdata-value": [
				"{",
				"    \"xy\": [",
				"        276,",
				"        242",
				"    ]",
				"}"
			]
			},
			{
			"#tag-name": "labels",
			"#child-nodes": [
				{
				"#tag-name": "label",
				"#value": "[$WORKFLOW_DEFINITION_NAME$]",
				"language-id": "en_US"
				}
			]
			},
			{
			"#tag-name": "input-variables",
			"#cdata-value": ["[$WORKFLOW_NODE_INPUT_VARIABLES$]"]
			},
			{
			"#tag-name": "output-variables",
			"#cdata-value": [
				"[",
				"    {",
				"        \"name\": \"rewrittenText\",",
				"        \"type\": \"string\"",
				"    }",
				"]"
			]
			},
			{
			"#tag-name": "prompt",
			"#cdata-value": ["[$WORKFLOW_NODE_SETTING_PROMPT$]"]
			},
			{
			"#tag-name": "transitions",
			"#child-nodes": [
				{
				"#tag-name": "transition",
				"#child-nodes": [
					{
					"#tag-name": "labels",
					"#child-nodes": [
						{
						"#tag-name": "label",
						"#value": "end",
						"language-id": "en_US"
						}
					]
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
				]
				}
			]
			},
			{
			"#tag-name": "user-message",
			"#cdata-value": ["[$WORKFLOW_NODE_SETTING_USER_MESSAGE$]"]
			}
		]
		}
	],
	"#tag-name": "workflow-definition",
	"xmlns": "urn:liferay.com:liferay-workflow_7.4.0",
	"xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
	"xsi:schemaLocation": "urn:liferay.com:liferay-workflow_7.4.0 http://www.liferay.com/dtd/liferay-workflow-definition_7_4_0.xsd"
}