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
		category: 'match',
		clauses: [
			{
				context: 'query',
				occur: 'must',
				query: {
					query: {
						simple_query_string: {
							boost: '${configuration.boost}',
							default_operator: '${configuration.operator}',
							fields: '${configuration.fields}',
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
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
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
						label: 'Fields',
						name: 'fields',
						type: 'fieldMappingList',
						typeOptions: {
							boost: true,
						},
					},
					{
						label: 'Operator',
						name: 'operator',
						type: 'select',
						typeOptions: {
							options: [
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
					},
					{
						defaultValue: 1,
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
