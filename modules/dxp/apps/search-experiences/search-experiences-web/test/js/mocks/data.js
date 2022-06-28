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

import {
	DEFAULT_ADVANCED_CONFIGURATION,
	DEFAULT_HIGHLIGHT_CONFIGURATION,
	DEFAULT_PARAMETER_CONFIGURATION,
	DEFAULT_SORT_CONFIGURATION,
} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/data';

export const ENTITY_JSON = {
	'com.liferay.asset.kernel.model.AssetTag': {
		multiple: false,
		title: 'Select Tag',
		url: 'http:…',
	},
	'com.liferay.portal.kernel.model.Group': {
		multiple: false,
		title: 'Select Site',
		url: 'http:…',
	},
	'com.liferay.portal.kernel.model.Organization': {
		multiple: true,
		title: 'Select Organization',
		url: 'http:/…',
	},
	'com.liferay.portal.kernel.model.Role': {
		multiple: false,
		title: 'Select Role',
		url: 'http:…',
	},
	'com.liferay.portal.kernel.model.Team': {
		multiple: false,
		title: 'Select Team',
		url: 'http:…',
	},
	'com.liferay.portal.kernel.model.User': {
		multiple: true,
		title: 'Select User',
		url: 'http:/…',
	},
	'com.liferay.portal.kernel.model.UserGroup': {
		multiple: false,
		title: 'Select User Group',
		url: 'http:…',
	},
};

export const INDEX_FIELDS = [
	{
		languageIdPosition: -1,
		name: 'ddmTemplateKey',
		type: 'keyword',
	},
	{
		languageIdPosition: -1,
		name: 'entryClassPK',
		type: 'keyword',
	},
	{
		languageIdPosition: -1,
		name: 'publishDate',
		type: 'date',
	},
	{
		languageIdPosition: -1,
		name: 'configurationModelFactoryPid',
		type: 'keyword',
	},
	{
		languageIdPosition: 11,
		name: 'description',
		type: 'text',
	},
	{
		languageIdPosition: -1,
		name: 'discussion',
		type: 'keyword',
	},
	{
		languageIdPosition: -1,
		name: 'screenName',
		type: 'keyword',
	},
	{
		languageIdPosition: 15,
		name: 'localized_title',
		type: 'text',
	},
	{
		languageIdPosition: -1,
		name: 'catalogBasePriceList',
		type: 'text',
	},
	{
		languageIdPosition: -1,
		name: 'path',
		type: 'keyword',
	},
];

export const TEXT_MATCH_OVER_MULTIPLE_FIELDS = {
	description_i18n: {
		en_US: 'Search for a text match over multiple text fields.',
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
	title_i18n: {
		en_US: 'Text Match Over Multiple Fields',
	},
};

export const QUERY_SXP_ELEMENTS = [TEXT_MATCH_OVER_MULTIPLE_FIELDS];

export const INITIAL_CONFIGURATION = {
	advancedConfiguration: DEFAULT_ADVANCED_CONFIGURATION,
	aggregationConfiguration: {},
	generalConfiguration: {
		clauseContributorsExcludes: [],
		clauseContributorsIncludes: [],
	},
	highlightConfiguration: DEFAULT_HIGHLIGHT_CONFIGURATION,
	parameterConfiguration: DEFAULT_PARAMETER_CONFIGURATION,
	queryConfiguration: {
		applyIndexerClauses: false,
	},
	sortConfiguration: DEFAULT_SORT_CONFIGURATION,
};

/**
 * Function to mock clause contributors or searchable types
 *
 * Example:
 * mockClassNames('KeywordQueryContributor', true, 1)
 * => [
 *   {
 *       "className": "KeywordQueryContributor_1",
 *       "displayName": "KeywordQueryContributor 1"
 *   },
 *	]
 *
 * @param {string} prefix String to identity each className and displayName
 * @param {boolean} isObject If true, returns the items in object
 * @param {number} itemCount Number of items
 * @return {Array} Generated list of classNames and displayNames
 */
export function mockClassNames(prefix, isObject = true, itemCount = 10) {
	const classNames = [];

	for (let i = 1; i <= itemCount; i++) {
		classNames.push(
			isObject
				? {
						className: `${prefix}_${i}`,
						displayName: `${prefix} ${i}`,
				  }
				: `${prefix}_${i}`
		);
	}

	return classNames;
}

export function mockSearchResults(itemsPerPage = 10) {
	const hits = [];
	const documents = [];

	for (let i = 1; i <= itemsPerPage; i++) {
		const score = Math.random() * 100;

		const fields = {
			assetEntryId: [`4273${i}`],
			classPK: ['0'],
			content_en_US: ['Web Content'],
			createDate: ['20211102190832'],
			ddmTemplateKey: ['BASIC-WEB-CONTENT'],
			defaultLanguageId: ['en_US'],
			entryClassName: ['com.liferay.journal.model.JournalArticle'],
			entryClassPK: ['40116'],
			modified: ['20211102192146'],
			scopeGroupId: ['20123'],
			title_en_US: [`Article Number ${i}`],
			userId: ['20127'],
			userName: ['test test'],
			visible: ['true'],
		};

		const documentFields = {};

		documentFields['assetTitle'] = {values: [`Article Number ${i}`]};

		Object.entries(fields).forEach(([key, value]) => {
			documentFields[key] = {values: value};
		});

		hits.push({
			_explanation: {},
			_id: `com.liferay.journal.model.JournalArticle_PORTLET_${i}`,
			_index: 'liferay-20099',
			_score: score,
			_type: 'LiferayDocumentType',
			fields,
		});

		documents.push({
			documentFields,
			explanation: '',
			id: `com.liferay.journal.model.JournalArticle_PORTLET_${i}`,
			score,
		});
	}

	const response = {
		hits: {
			hits,
			total: {
				value: 2,
			},
		},
		timed_out: false,
	};

	const searchHits = {
		hits: documents,
		maxScore: 65.878,
		totalHits: 1000,
	};

	return {
		page: 0,
		pageSize: itemsPerPage,
		requestString: '',
		response,
		responseString: JSON.stringify(response),
		searchHits,
	};
}
