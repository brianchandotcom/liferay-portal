/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const BOOST_ALL_KEYWORDS_MATCH = {
	description_i18n: {
		en_US:
			'Boost contents if the search keywords match in the given fields.',
		ja_JP:
			'検索キーワードが指定されたフィールドに一致する場合、コンテンツをブーストする',
	},
	elementDefinition: {
		category: 'boost',
		configuration: {
			queryConfiguration: {
				queryEntries: [
					{
						clauses: [
							{
								context: 'query',
								occur: 'should',
								query: {
									multi_match: {
										boost: '${configuration.boost}',
										fields: '${configuration.fields}',
										operator: 'and',
										query: '${configuration.keywords}',
										type: '${configuration.type}',
									},
								},
							},
						],
					},
				],
			},
		},
		icon: 'thumbs-up',
		uiConfiguration: {
			fieldSets: [
				{
					fields: [
						{
							defaultValue: [
								{
									boost: 2.0,
									field: 'localized_title',
									locale: '${context.language_id}',
								},
								{
									boost: 1.0,
									field: 'content',
									locale: '${context.language_id}',
								},
							],
							label: 'Field',
							name: 'fields',
							type: 'fieldMappingList',
							typeOptions: {
								boost: true,
							},
						},
						{
							defaultValue: 'best_fields',
							label: 'Match Type',
							name: 'type',
							type: 'select',
							typeOptions: {
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
							},
						},
						{
							defaultValue: 10,
							label: 'Boost',
							name: 'boost',
							type: 'number',
							typeOptions: {
								min: 0,
							},
						},
						{
							helpText:
								'If this is set, the search terms entered in the search bar will be replaced by this value.',
							label: 'Text to Match',
							name: 'keywords',
							type: 'keywords',
							typeOptions: {
								required: 'false',
							},
						},
					],
				},
			],
		},
	},
	externalReferenceCode: 'BOOST_ALL_KEYWORDS_MATCH',
	readOnly: true,
	title_i18n: {
		en_US: 'Boost All Keywords Match',
		ja_JP: '全マッチキーワードのブースト',
	},
};

export const BOOST_ASSET_TYPE = {
	description_i18n: {
		en_US: 'Boost the given asset type.',
		ja_JP: '指定されたアセットタイプをブーストする',
	},
	elementDefinition: {
		category: 'boost',
		configuration: {
			queryConfiguration: {
				queryEntries: [
					{
						clauses: [
							{
								context: 'query',
								occur: 'should',
								query: {
									term: {
										entryClassName: {
											boost: '${configuration.boost}',
											value:
												'${configuration.entry_class_name}',
										},
									},
								},
							},
						],
					},
				],
			},
		},
		icon: 'thumbs-up',
		uiConfiguration: {
			fieldSets: [
				{
					fields: [
						{
							label: 'Asset Type',
							name: 'entry_class_name',
							type: 'searchableType',
						},
						{
							defaultValue: 10,
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
	},
	externalReferenceCode: 'BOOST_ASSET_TYPE',
	readOnly: true,
	title_i18n: {
		en_US: 'Boost Asset Type',
		ja_JP: 'アセットタイプのブースト',
	},
};

export const TEXT_MATCH_OVER_MULTIPLE_FIELDS = {
	description_i18n: {
		en_US: 'Search for a text match over multiple text fields.',
		ja_JP: '複数のテキストフィールドを対象に一致するテキストを検索します。',
	},
	elementDefinition: {
		category: 'match',
		configuration: {
			queryConfiguration: {
				queryEntries: [
					{
						clauses: [
							{
								context: 'query',
								occur: 'must',
								query: {
									multi_match: {
										boost: '${configuration.boost}',
										fields: '${configuration.fields}',
										fuzziness: '${configuration.fuzziness}',
										minimum_should_match:
											'${configuration.minimum_should_match}',
										operator: '${configuration.operator}',
										query: '${configuration.keywords}',
										slop: '${configuration.slop}',
										type: '${configuration.type}',
									},
								},
							},
						],
					},
				],
			},
		},
		icon: 'picture',
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
							label: 'Fields',
							name: 'fields',
							type: 'fieldMappingList',
							typeOptions: {
								boost: true,
							},
						},
						{
							defaultValue: 'or',
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
							defaultValue: 'best_fields',
							label: 'Match Type',
							name: 'type',
							type: 'select',
							typeOptions: {
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
							},
						},
						{
							defaultValue: 'AUTO',
							helpText:
								'Only use fuzziness with the following match types: most fields, best fields, bool prefix.',
							label: 'Fuzziness',
							name: 'fuzziness',
							type: 'select',
							typeOptions: {
								nullable: true,
								options: [
									{
										label: 'Auto',
										value: 'AUTO',
									},
									{
										label: '0',
										value: '0',
									},
									{
										label: '1',
										value: '1',
									},
									{
										label: '2',
										value: '2',
									},
								],
							},
						},
						{
							defaultValue: '0',
							label: 'Minimum Should Match',
							name: 'minimum_should_match',
							type: 'text',
							typeOptions: {
								nullable: true,
							},
						},
						{
							defaultValue: '',
							helpText:
								'Only use slop with the following match types: phrase, phrase prefix.',
							label: 'Slop',
							name: 'slop',
							type: 'number',
							typeOptions: {
								min: 0,
								nullable: true,
								step: 1,
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
						{
							helpText:
								'If this is set, the search terms entered in the search bar will be replaced by this value.',
							label: 'Text to Match',
							name: 'keywords',
							type: 'keywords',
							typeOptions: {
								required: false,
							},
						},
					],
				},
			],
		},
	},
	externalReferenceCode: 'TEXT_MATCH_OVER_MULTIPLE_FIELDS',
	readOnly: true,
	title_i18n: {
		en_US: 'Text Match Over Multiple Fields',
		ja_JP: '複数のテキストフィールドを対象に一致するテキストを検索',
	},
};

export const USER_CREATED_ELEMENT = {
	description_i18n: {
		en_US: 'Test user created element description.',
	},
	elementDefinition: {
		category: 'boost',
		configuration: {
			queryConfiguration: {
				queryEntries: [
					{
						clauses: [
							{
								context: 'query',
								occur: 'should',
								query: {
									term: {
										entryClassName: {
											boost: '${configuration.boost}',
											value:
												'${configuration.entry_class_name}',
										},
									},
								},
							},
						],
					},
				],
			},
		},
		icon: 'thumbs-up',
		uiConfiguration: {
			fieldSets: [
				{
					fields: [
						{
							label: 'Asset Type',
							name: 'entry_class_name',
							type: 'searchableType',
						},
						{
							defaultValue: 10,
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
	},
	title_i18n: {
		en_US: 'User Created Element',
	},
};

export const QUERY_SXP_ELEMENTS = [TEXT_MATCH_OVER_MULTIPLE_FIELDS];
