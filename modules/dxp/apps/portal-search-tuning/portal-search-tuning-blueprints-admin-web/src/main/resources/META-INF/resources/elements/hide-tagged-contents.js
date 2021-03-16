/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export default {
	elementTemplateJSON: {
		category: 'hide',
		clauses: [
			{
				context: 'query',
				occur: 'filter',
				query: {
					query: {
						bool: {
							must_not: [
								{
									term: {
										'assetTagNames.raw': {
											value: '${config.asset_tag}',
										},
									},
								},
							],
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Hide contents tagged with',
		},
		enabled: true,
		icon: 'hidden',
		title: {
			en_US: 'Hide Tagged Contents',
		},
	},
	uiConfigurationJSON: [
		{
			helpText: 'Add asset tag value',
			key: 'asset_tag',
			label: 'Asset Tag',
			type: 'text',
		},
	],
};
