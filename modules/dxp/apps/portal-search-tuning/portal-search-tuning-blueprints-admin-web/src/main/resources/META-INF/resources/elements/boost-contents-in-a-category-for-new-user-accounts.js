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
		category: 'conditional',
		clauses: [
			{
				context: 'query',
				occur: 'should',
				query: {
					query: {
						term: {
							assetCategoryIds: {
								boost: '${configuration.boost}',
								value: '${configuration.asset_category_id}',
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
					date_format: 'yyyyMMdd',
					evaluation_type: 'in_range',
					parameter_name: '${user.user_create_date}',
					value: [
						'${time.current_date|modifier=-${configuration.time_range},dateFormat=yyyyMMdd}',
						'${time.current_date|modifier=+1d,dateFormat=yyyyMMdd}',
					],
				},
			},
		],
		description: {
			en_US:
				'Boost contents in a category for user accounts created withing the given time',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Contents in a Category for New User Accounts',
		},
	},
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
						helpText: 'Add asset category ID',
						label: 'Asset Category',
						name: 'asset_category_id',
						type: 'number',
					},
					{
						defaultValue: 30,
						label: 'Time range',
						name: 'time_range',
						type: 'number',
						typeOptions: {
							unit: 'days',
							unitSuffix: 'd',
						},
					},
					{
						defaultValue: 20,
						label: 'Boost',
						name: 'boost',
						type: 'number',
						typeOptions: {
							min: 0,
						},
					},
				],
			},
		],
	},
};
