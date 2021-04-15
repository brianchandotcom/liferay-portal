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

import {QUERY_ELEMENTS} from '../../../src/main/resources/META-INF/resources/js/utils/data';
import {
	getElementOutput,
	getUIConfigurationValues,
} from '../../../src/main/resources/META-INF/resources/js/utils/utils';

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
		language_id_position: -1,
		name: 'ddmTemplateKey',
		type: 'keyword',
	},
	{
		language_id_position: -1,
		name: 'entryClassPK',
		type: 'keyword',
	},
	{
		language_id_position: -1,
		name: 'publishDate',
		type: 'date',
	},
	{
		language_id_position: -1,
		name: 'configurationModelFactoryPid',
		type: 'keyword',
	},
	{
		language_id_position: 11,
		name: 'description',
		type: 'text',
	},
	{
		language_id_position: -1,
		name: 'discussion',
		type: 'keyword',
	},
	{
		language_id_position: -1,
		name: 'screenName',
		type: 'keyword',
	},
	{
		language_id_position: 15,
		name: 'localized_title',
		type: 'text',
	},
	{
		language_id_position: -1,
		name: 'catalogBasePriceList',
		type: 'text',
	},
	{
		language_id_position: -1,
		name: 'path',
		type: 'keyword',
	},
];

export const SEARCHABLE_ASSET_TYPES = [
	'com.liferay.wiki.model.WikiPage',
	'com.liferay.commerce.product.model.CPDefinition',
	'com.liferay.document.library.kernel.model.DLFileEntry',
	'com.liferay.bookmarks.model.BookmarksFolder',
	'com.liferay.blogs.model.BlogsEntry',
	'com.liferay.document.library.kernel.model.DLFolder',
	'com.liferay.dynamic.data.lists.model.DDLRecord',
	'com.liferay.bookmarks.model.BookmarksEntry',
	'com.liferay.journal.model.JournalArticle',
	'com.liferay.journal.model.JournalFolder',
	'com.liferay.message.boards.model.MBMessage',
	'com.liferay.calendar.model.CalendarBooking',
	'com.liferay.knowledge.base.model.KBArticle',
];

export const SELECTED_ELEMENTS = QUERY_ELEMENTS.map((element, index) => ({
	...element,
	id: index,
	uiConfigurationValues: getUIConfigurationValues(
		element.uiConfigurationJSON
	),
}));

export const ELEMENT_OUTPUTS = SELECTED_ELEMENTS.map(getElementOutput);

export const INITIAL_CONFIGURATION = {
	advanced_configuration: {
		query_processing: {
			exclude_query_contributors: '',
			exclude_query_post_processors: '',
		},
		source: {
			fetch_source: true,
			source_excludes: '',
			source_includes: '',
		},
	},
	aggregation_configuration: [],
	facet_configuration: [],
	framework_configuration: {
		apply_indexer_clauses: false,
		searchable_asset_types: SEARCHABLE_ASSET_TYPES,
	},
	parameter_configuration: {},
	query_configuration: [],
	sort_configuration: {},
};

export const mockSearchResults = (itemsPerPage = 10) => {
	const hits = [];

	for (var i = 1; i <= itemsPerPage; i++) {
		hits.push({
			b_assetEntryId: i,
			b_author: 'Test Test',
			b_created: '2/1/21',
			b_summary:
				'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
			b_title: `Article Number ${i}`,
			b_type: 'Web Content Article',
			b_viewURL: '',
			id: `com.liferay.journal.model.JournalArticle_PORTLET_${i}`,
			score: Math.random() * 100,
		});
	}

	return {
		aggregations: {},
		facets: [],
		hits,
		meta: {
			executionTime: '0.061',
			keywords: 'test',
			totalHits: 1362,
		},
		pagination: {activePage: 1, totalPages: 100},
		suggest: {},
	};
};
