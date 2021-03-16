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
					evaluation_type: 'eq',
					parameter_name: '${time.time_of_day}',
					value: '${config.time_of_day}',
				},
			},
		],
		description: {
			en_US: 'Boost contents in a category based on the time of day',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Contents in a Category for the Time of Day',
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
			key: 'time_of_day',
			label: 'Time of Day',
			type: 'select',
			typeOptions: [
				{
					label: 'Morning (4am - 12am)',
					value: 'morning',
				},
				{
					label: 'Afternoon (12pm - 5pm)',
					value: 'afternoon',
				},
				{
					label: 'Evening (5pm - 8pm)',
					value: 'evening',
				},
				{
					label: 'Night (8pm - 4am)',
					value: 'night',
				},
			],
		},
		{
			defaultValue: 20,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
