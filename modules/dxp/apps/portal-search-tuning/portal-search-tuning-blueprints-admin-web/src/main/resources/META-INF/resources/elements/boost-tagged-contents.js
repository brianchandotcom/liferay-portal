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
		category: 'boost',
		clauses: [
			{
				context: 'query',
				occur: 'should',
				query: {
					query: {
						terms: {
							'assetTagNames.raw': '${config.asset_tags}',
							boost: '${config.boost}',
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Boost contents having at least one of the given tags',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Tagged Contents',
		},
	},
	uiConfigurationJSON: [
		{
			defaultValue: [],
			key: 'asset_tags',
			label: 'Asset Tags',
			type: 'multiselect',
		},
		{
			defaultValue: 5,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
