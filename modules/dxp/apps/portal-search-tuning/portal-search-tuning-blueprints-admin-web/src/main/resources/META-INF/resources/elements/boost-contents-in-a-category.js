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

/**
 * Keep these in sync with the elements in
 * portal-search-tuning-blueprints-resources/
 * src/main/resources/META-INF/search/elements
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
							assetCategoryIds:
								'${configuration.asset_category_ids}',
							boost: '${configuration.boost}',
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Boost and promote contents in a given category',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Contents in a Category',
		},
	},
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
						helpText: 'Add asset category IDs',
						label: 'Asset Categories',
						name: 'asset_category_ids',
						type: 'multiselect',
					},
					{
						defaultValue: 10,
						label: 'Boost',
						name: 'boost',
						type: 'slider',
					},
				],
			},
		],
	},
};
