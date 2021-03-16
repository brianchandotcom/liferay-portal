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
		category: 'conditional',
		clauses: [
			{
				context: 'query',
				occur: 'should',
				query: {
					query: {
						term: {
							assetCategoryIds: {
								boost: '${config.boost}',
								value: '${config.asset_category_id}',
							},
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [
			{
				configuration: {
					evaluation_type: 'any_word_in',
					parameter_name: '${keywords}',
					value: '${config.keywords}',
				},
			},
		],
		description: {
			en_US:
				'Show Web Contents in a category higher in the results, if searchphrase contains any of the given keywords',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Contents in a Category by Keyword Match',
		},
	},
	uiConfigurationJSON: [
		{
			helpText: 'Add asset category ID',
			key: 'asset_category_id',
			label: 'Asset Category',
			type: 'number',
		},
		{
			defaultValue: [],
			key: 'keywords',
			label: 'Keywords',
			type: 'multiselect',
		},
		{
			defaultValue: 10,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
