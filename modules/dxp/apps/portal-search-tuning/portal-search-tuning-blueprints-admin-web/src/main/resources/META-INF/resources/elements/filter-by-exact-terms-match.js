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
						terms: {
							'${config.field}': '${config.values}',
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US:
				'Filter results by one or multiple terms. At least one has to match',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Filter by Exact Terms Match',
		},
	},
	uiConfigurationJSON: [
		{
			defaultValue: {
				field: '',
				locale: '',
			},
			key: 'field',
			label: 'Field',
			type: 'field',
		},
		{
			defaultValue: [],
			key: 'values',
			label: 'Values',
			type: 'multiselect',
		},
	],
};
