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
					date_format: 'yyyyMMdd',
					evaluation_type: 'in_range',
					parameter_name: '${time.current_date}',
					value: ['${config.start_date}', '${config.end_date}'],
				},
			},
		],
		description: {
			en_US: 'Boost contents in a category for the given period of time',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Contents in a Category for a Period of Time',
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
			format: 'YYYYMMDD',
			key: 'start_date',
			label: 'Create Date: From',
			type: 'date',
		},
		{
			format: 'YYYYMMDD',
			key: 'end_date',
			label: 'Create Date: To',
			type: 'date',
		},
		{
			defaultValue: 10,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
