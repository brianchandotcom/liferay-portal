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
		category: 'filter',
		clauses: [
			{
				context: 'query',
				occur: 'filter',
				query: {
					query: {
						range: {
							createDate: {
								from: '${config.start_date}',
								include_lower: true,
								include_upper: true,
								to: '${config.end_date}',
							},
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Limit search to contents created within the given time',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Limit Search to Contents Created Within a Period of Time',
		},
	},
	uiConfigurationJSON: [
		{
			key: 'start_date',
			label: 'Create Date: From',
			type: 'date',
		},
		{
			key: 'end_date',
			label: 'Create Date: To',
			type: 'date',
		},
	],
};
