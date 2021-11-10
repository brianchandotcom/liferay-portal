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
	description: 'Boost contents matching all the words in the search phrase',
	description_i18n: {
		en_US: 'Boost contents matching all the words in the search phrase',
	},
	elementDefinition: {
		category: 'boost',
		icon: 'thumbs-up',
		sxpBlueprint: {
			configuration: {
				queryConfiguration: {
					queryEntries: [
						{
							clauses: [
								{
									context: 'query',
									occur: 'should',
									query: {
										wrapper: {
											query: {
												multi_match: {
													boost:
														'${configuration.boost}',
													fields:
														'${configuration.fields}',
													operator: 'and',
													query:
														'${configuration.keywords}',
													type:
														'${configuration.type}',
												},
											},
										},
									},
								},
							],
						},
					],
				},
			},
		},
		uiConfiguration: {
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
									boost: '1',
									field: 'content',
									locale: '${context.language_id}',
								},
							],
							label: 'Field',
							name: 'fields',
							options: [
								{
									label: 'boost',
									value: 'true',
								},
							],
							type: 'fieldMappingList',
						},
						{
							defaultValue: {
								value: 'best_fields',
							},
							label: 'Match Type',
							name: 'type',
							options: [
								{
									label: 'Best Fields',
									value: 'best_fields',
								},
								{
									label: 'Most Fields',
									value: 'most_fields',
								},
								{
									label: 'Cross Fields',
									value: 'cross_fields',
								},
								{
									label: 'Phrase',
									value: 'phrase',
								},
								{
									label: 'Phrase Prefix',
									value: 'phrase_prefix',
								},
								{
									label: 'Boolean Prefix',
									value: 'bool_prefix',
								},
							],
							type: 'select',
						},
						{
							defaultValue: {
								value: '10',
							},
							label: 'Boost',
							name: 'boost',
							options: [
								{
									label: 'min',
									value: '0',
								},
							],
							type: 'number',
						},
						{
							helpText:
								'If this is set, the search terms entered in the search bar will be replaced by this value.',
							label: 'Text to Match',
							name: 'keywords',
							options: [
								{
									label: 'required',
									value: 'false',
								},
							],
							type: 'keywords',
						},
					],
				},
			],
		},
	},
	title: 'Boost All Keywords Match',
	title_i18n: {
		en_US: 'Boost All Keywords Match',
	},
};
