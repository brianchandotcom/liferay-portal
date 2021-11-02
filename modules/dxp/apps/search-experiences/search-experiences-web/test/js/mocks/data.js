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
	getSXPElementOutput,
	getUIConfigurationValues,
} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/utils';
import PASTE_AN_ELASTICSEARCH_QUERY from './sxp_elements/pasteAnElasticsearchQuery';
import TEXT_MATCH_OVER_MULTIPLE_FIELDS from './sxp_elements/textMatchOverMultipleFields';

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

export const KEYWORD_QUERY_CONTRIBUTORS = [
	'com.liferay.account.internal.search.spi.model.query.contributor.AccountEntryKeywordQueryContributor',
	'com.liferay.account.internal.search.spi.model.query.contributor.AccountGroupKeywordQueryContributor',
	'com.liferay.address.internal.search.spi.model.query.contributor.AddressKeywordQueryContributor',
	'com.liferay.asset.categories.internal.search.spi.model.query.contributor.AssetCategoryKeywordQueryContributor',
	'com.liferay.asset.categories.internal.search.spi.model.query.contributor.AssetVocabularyKeywordQueryContributor',
	'com.liferay.asset.tags.internal.search.spi.model.query.contributor.AssetTagKeywordQueryContributor',
	'com.liferay.blogs.internal.search.spi.model.query.contributor.BlogsEntryKeywordQueryContributor',
	'com.liferay.calendar.internal.search.spi.model.query.contributor.CalendarBookingKeywordQueryContributor',
	'com.liferay.calendar.internal.search.spi.model.query.contributor.CalendarKeywordQueryContributor',
	'com.liferay.commerce.internal.search.spi.model.query.contributor.CommerceOrderTypeKeywordQueryContributor',
	'com.liferay.commerce.order.rule.internal.search.spi.model.query.contributor.COREntryKeywordQueryContributor',
	'com.liferay.commerce.shop.by.diagram.internal.search.spi.model.query.contributor.CSDiagramEntryKeywordQueryContributor',
	'com.liferay.contacts.internal.search.spi.model.query.contributor.ContactKeywordQueryContributor',
	'com.liferay.data.engine.internal.search.spi.model.query.contributor.DEDataListViewKeywordQueryContributor',
	'com.liferay.depot.internal.search.spi.model.query.contributor.DepotEntryKeywordQueryContributor',
	'com.liferay.document.library.internal.search.spi.model.query.contributor.DLFileEntryKeywordQueryContributor',
	'com.liferay.document.library.internal.search.spi.model.query.contributor.DLFileEntryTypeKeywordQueryContributor',
	'com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor.DDLRecordKeywordQueryContributor',
	'com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor.DDLRecordSetKeywordQueryContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMFormInstanceRecordKeywordQueryContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMStructureKeywordQueryContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMStructureLayoutKeywordQueryContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMTemplateKeywordQueryContributor',
	'com.liferay.exportimport.internal.search.spi.model.query.contributor.ExportImportConfigurationKeywordQueryContributor',
	'com.liferay.journal.internal.search.spi.model.query.contributor.JournalFolderKeywordQueryContributor',
	'com.liferay.layout.internal.search.spi.model.query.contributor.LayoutKeywordQueryContributor',
	'com.liferay.list.type.internal.search.spi.model.query.contributor.ListTypeDefinitionKeywordQueryContributor',
	'com.liferay.list.type.internal.search.spi.model.query.contributor.ListTypeEntryKeywordQueryContributor',
	'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBMessageKeywordQueryContributor',
	'com.liferay.object.internal.search.spi.model.query.contributor.ObjectActionKeywordQueryContributor',
	'com.liferay.object.internal.search.spi.model.query.contributor.ObjectDefinitionKeywordQueryContributor',
	'com.liferay.object.internal.search.spi.model.query.contributor.ObjectFieldKeywordQueryContributor',
	'com.liferay.object.internal.search.spi.model.query.contributor.ObjectLayoutKeywordQueryContributor',
	'com.liferay.organizations.internal.search.spi.model.query.contributor.OrganizationKeywordQueryContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.AlwaysPresentFieldsKeywordQueryContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.AssetCategoryTitlesKeywordQueryContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.AssetInternalCategoryTitlesKeywordQueryContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.AssetTagNamesKeywordQueryContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.DefaultKeywordQueryContributor',
	'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoInstanceTokenKeywordQueryContributor',
	'com.liferay.redirect.internal.search.spi.model.query.contributor.RedirectEntryKeywordQueryContributor',
	'com.liferay.redirect.internal.search.spi.model.query.contributor.RedirectNotFoundEntryKeywordQueryContributor',
	'com.liferay.segments.internal.search.spi.model.query.contributor.SegmentsEntryKeywordQueryContributor',
	'com.liferay.translation.internal.search.spi.model.query.contributor.TranslationEntryKeywordQueryContributor',
	'com.liferay.user.groups.admin.internal.search.spi.model.query.contributor.UserGroupKeywordQueryContributor',
	'com.liferay.users.admin.internal.search.spi.model.query.contributor.UserKeywordQueryContributor',
];

export const MODEL_PREFILTER_CONTRIBUTORS = [
	'com.liferay.account.internal.search.spi.model.query.contributor.AccountEntryModelPreFilterContributor',
	'com.liferay.account.internal.search.spi.model.query.contributor.OrganizationModelPreFilterContributor',
	'com.liferay.account.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
	'com.liferay.address.internal.search.spi.model.query.contributor.AddressModelPreFilterContributor',
	'com.liferay.analytics.settings.web.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
	'com.liferay.asset.categories.internal.search.spi.model.query.contributor.AssetCategoryModelPreFilterContributor',
	'com.liferay.blogs.internal.search.spi.model.query.contributor.BlogsEntryModelPreFilterContributor',
	'com.liferay.bookmarks.internal.search.spi.model.query.contributor.BookmarksEntryModelPreFilterContributor',
	'com.liferay.bookmarks.internal.search.spi.model.query.contributor.BookmarksFolderModelPreFilterContributor',
	'com.liferay.calendar.internal.search.spi.model.query.contributor.CalendarBookingModelPreFilterContributor',
	'com.liferay.change.tracking.internal.search.spi.model.query.contributor.CTModelPreFilterContributor',
	'com.liferay.commerce.product.internal.search.spi.model.query.contributor.CPDefinitionModelPreFilterContributor',
	'com.liferay.commerce.shop.by.diagram.internal.search.spi.model.query.contributor.CSDiagramEntryModelPreFilterContributor',
	'com.liferay.data.engine.internal.search.spi.model.query.contributor.DEDataListViewModelPreFilterContributor',
	'com.liferay.document.library.internal.search.spi.model.query.contributor.DLFileEntryModelPreFilterContributor',
	'com.liferay.document.library.internal.search.spi.model.query.contributor.DLFolderModelPreFilterContributor',
	'com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor.DDLRecordModelPreFilterContributor',
	'com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor.DDLRecordSetModelPreFilterContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMFormInstanceRecordModelPreFilterContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMStructureLayoutModelPreFilterContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMStructureModelPreFilterContributor',
	'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMTemplateModelPreFilterContributor',
	'com.liferay.exportimport.internal.search.spi.model.query.contributor.ExportImportConfigurationModelPreFilterContributor',
	'com.liferay.journal.internal.search.spi.model.query.contributor.JournalFolderModelPreFilterContributor',
	'com.liferay.layout.internal.search.spi.model.query.contributor.LayoutModelPreFilterContributor',
	'com.liferay.list.type.internal.search.spi.model.query.contributor.ListTypeEntryModelPreFilterContributor',
	'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBCategoryModelPreFilterContributor',
	'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBMessageModelPreFilterContributor',
	'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBThreadModelPreFilterContributor',
	'com.liferay.object.internal.search.spi.model.query.contributor.ObjectActionModelPreFilterContributor',
	'com.liferay.object.internal.search.spi.model.query.contributor.ObjectFieldModelPreFilterContributor',
	'com.liferay.object.internal.search.spi.model.query.contributor.ObjectLayoutModelPreFilterContributor',
	'com.liferay.on.demand.admin.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
	'com.liferay.organizations.internal.search.spi.model.query.contributor.OrganizationModelPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.StagingModelPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.WorkflowStatusModelPreFilterContributor',
	'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoInstanceModelPreFilterContributor',
	'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoInstanceTokenModelPreFilterContributor',
	'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoLogModelPreFilterContributor',
	'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoTaskInstanceTokenModelPreFilterContributor',
	'com.liferay.redirect.internal.search.spi.model.query.contributor.RedirectNotFoundEntryModelPreFilterContributor',
	'com.liferay.segments.internal.search.spi.model.query.contributor.SegmentsEntryModelPreFilterContributor',
	'com.liferay.segments.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
	'com.liferay.users.admin.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
];

export const QUERY_PREFILTER_CONTRIBUTORS = [
	'com.liferay.asset.internal.search.spi.model.query.contributor.AssetEntryModelPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.AssetCategoryIdsQueryPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.AssetTagNamesQueryPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.FolderIdQueryPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.GroupIdQueryPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.LayoutQueryPreFilterContributor',
	'com.liferay.portal.search.internal.spi.model.query.contributor.UserIdQueryPreFilterContributor',
];

export const QUERY_SXP_ELEMENTS = [
	TEXT_MATCH_OVER_MULTIPLE_FIELDS,
	PASTE_AN_ELASTICSEARCH_QUERY,
];

export const SEARCHABLE_TYPES = [
	{
		className: 'com.liferay.blogs.model.BlogsEntry',
		displayName: 'Blogs Entry',
	},
	{
		className: 'com.liferay.bookmarks.model.BookmarksEntry',
		displayName: 'Bookmarks Entry',
	},
	{
		className: 'com.liferay.bookmarks.model.BookmarksFolder',
		displayName: 'Bookmarks Folder',
	},
	{
		className: 'com.liferay.calendar.model.CalendarBooking',
		displayName: 'Calendar Event',
	},
	{
		className: 'com.liferay.commerce.product.model.CPDefinition',
		displayName: 'Commerce Product',
	},
	{
		className: 'com.liferay.document.library.kernel.model.DLFileEntry',
		displayName: 'Document',
	},
	{
		className: 'com.liferay.document.library.kernel.model.DLFolder',
		displayName: 'Documents Folder',
	},
	{
		className: 'com.liferay.dynamic.data.lists.model.DDLRecord',
		displayName: 'Dynamic Data Lists Record',
	},
	{
		className:
			'com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord',
		displayName: 'Form Record',
	},
	{
		className: 'com.liferay.knowledge.base.model.KBArticle',
		displayName: 'Knowledge Base Article',
	},
	{
		className: 'com.liferay.message.boards.model.MBMessage',
		displayName: 'Message Boards Message',
	},
	{
		className: 'com.liferay.portal.kernel.model.Layout',
		displayName: 'Page',
	},
	{
		className: 'com.liferay.portal.kernel.model.User',
		displayName: 'User',
	},
	{
		className: 'com.liferay.journal.model.JournalArticle',
		displayName: 'Web Content Article',
	},
	{
		className: 'com.liferay.journal.model.JournalFolder',
		displayName: 'Web Content Folder',
	},
	{
		className: 'com.liferay.wiki.model.WikiPage',
		displayName: 'Wiki Page',
	},
];

export const SELECTED_SXP_ELEMENTS = QUERY_SXP_ELEMENTS.map(
	(sxpElement, index) => ({
		...sxpElement,
		id: index,
		uiConfigurationValues: getUIConfigurationValues(
			sxpElement.uiConfigurationJSON
		),
	})
);

export const SXP_ELEMENT_OUTPUTS = SELECTED_SXP_ELEMENTS.map(
	getSXPElementOutput
);

export const INITIAL_CONFIGURATION = {
	advanced_configuration: {
		query_processing: {
			exclude_query_contributors: [],
			exclude_query_post_processors: [],
		},
		source: {
			fetch_source: true,
			source_excludes: [],
			source_includes: [],
		},
	},
	aggregation_configuration: {},
	facet_configuration: {},
	framework_configuration: {
		apply_indexer_clauses: false,
		searchable_asset_types: SEARCHABLE_TYPES.map(
			({className}) => className
		),
	},
	highlight_configuration: {},
	parameter_configuration: {},
	query_configuration: [],
	sort_configuration: {},
};

export const mockSearchResults = (itemsPerPage = 10) => {
	const hits = [];

	for (var i = 1; i <= itemsPerPage; i++) {
		const score = Math.random() * 100;

		hits.push({
			_explanation: {},
			_id: `com.liferay.journal.model.JournalArticle_PORTLET_${i}`,
			_index: 'liferay-20099',
			_score: score,
			_type: 'LiferayDocumentType',
			fields: {
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
			},
		});
	}

	return {
		page: 0,
		pageSize: itemsPerPage,
		request: {},
		requestString: '',
		response: {
			hits: {
				hits,
				total: {
					value: 2,
				},
			},
			timed_out: false,
		},
		responseString: '',
		totalHits: 1000,
	};
};
