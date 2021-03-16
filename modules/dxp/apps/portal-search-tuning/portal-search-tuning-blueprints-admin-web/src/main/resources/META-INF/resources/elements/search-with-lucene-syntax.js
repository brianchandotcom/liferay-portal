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
		category: 'match',
		clauses: [
			{
				context: 'query',
				occur: 'must',
				query: {
					query: {
						simple_query_string: {
							boost: '${config.boost}',
							default_operator: '${config.operator}',
							fields: '${config.fields}',
							query: '${keywords}',
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Enable searching using the Lucene syntax',
		},
		enabled: true,
		icon: 'picture',
		title: {
			en_US: 'Search with the Lucene Syntax',
		},
	},
	uiConfigurationJSON: [
		{
			boost: true,
			defaultValue: [
				{
					boost: '2',
					field: 'localized_title',
					locale: '${context.language_id}',
				},
				{
					boost: '2',
					field: 'content',
					locale: '${context.language_id}',
				},
			],
			key: 'fields',
			label: 'Fields',
			type: 'field-list',
			typeOptions: [
				{
					label: 'Title',
					value: 'localized_title',
				},
				{
					label: 'Description',
					value: 'description',
				},
				{
					label: 'Content',
					value: 'content',
				},
				{
					label: 'Name',
					value: 'name',
				},
			],
		},
		{
			key: 'operator',
			label: 'Operator',
			type: 'select',
			typeOptions: [
				{
					label: 'OR',
					value: 'or',
				},
				{
					label: 'AND',
					value: 'and',
				},
			],
		},
		{
			defaultValue: 1,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
