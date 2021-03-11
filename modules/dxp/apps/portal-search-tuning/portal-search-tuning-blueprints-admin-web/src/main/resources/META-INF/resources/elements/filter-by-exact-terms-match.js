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
							'${configuration.field}': '${configuration.values}',
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
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
						label: 'Field',
						name: 'field',
						type: 'fieldMapping',
					},
					{
						label: 'Values',
						name: 'values',
						type: 'multiselect',
					},
				],
			},
		],
	},
};
