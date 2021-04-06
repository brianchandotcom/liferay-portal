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
						function_score: {
							boost: '${configuration.boost}',
							field_value_factor: {
								factor: '${configuration.factor}',
								field:
									'content${context.language_id}_length_sortable',
								missing: 1,
								modifier: '${configuration.modifier}',
							},
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US:
				'Boost contents with longer content translation for the current language',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Longer Contents',
		},
	},
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
						defaultValue: 30,
						label: 'Boost',
						name: 'boost',
						type: 'slider',
					},
					{
						defaultValue: 1.5,
						label: 'Factor',
						name: 'factor',
						type: 'number',
						typeOptions: {
							min: 0,
						},
					},
					{
						defaultValue: 'ln',
						label: 'Modifier',
						name: 'modifier',
						type: 'select',
						typeOptions: {
							options: [
								{
									label: 'None',
									value: 'none',
								},
								{
									label: 'log',
									value: 'log',
								},
								{
									label: 'log1p',
									value: 'log1p',
								},
								{
									label: 'log2p',
									value: 'log2p',
								},
								{
									label: 'ln',
									value: 'ln',
								},
								{
									label: 'ln1p',
									value: 'ln1p',
								},
								{
									label: 'ln2p',
									value: 'ln2p',
								},
								{
									label: 'Square',
									value: 'square',
								},
								{
									label: 'sqrt',
									value: 'sqrt',
								},
								{
									label: 'Reciprocal',
									value: 'reciprocal',
								},
							],
						},
					},
				],
			},
		],
	},
};
