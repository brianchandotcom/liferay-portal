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

import textMatchOverMultipleFields from '../sxp_elements/textMatchOverMultipleFields';

/**
 * This variable is not currently being used, but it used to be used for
 * applying the baseline clauses. This has been removed for the moment to avoid
 * confusion. Leaving this value as-is in case it gets implemented again in
 * some type of form.
 */
export const BASELINE_CLAUSE_CONTRIBUTORS_CONFIGURATION = {
	clauseContributorsExcludes: [
		'com.liferay.account.internal.search.spi.model.query.contributor.AccountEntryKeywordQueryContributor',
		'com.liferay.account.internal.search.spi.model.query.contributor.AccountGroupKeywordQueryContributor',
		'com.liferay.address.internal.search.spi.model.query.contributor.AddressKeywordQueryContributor',
		'com.liferay.asset.categories.internal.search.spi.model.query.contributor.AssetCategoryKeywordQueryContributor',
		'com.liferay.asset.categories.internal.search.spi.model.query.contributor.AssetVocabularyKeywordQueryContributor',
		'com.liferay.asset.tags.internal.search.spi.model.query.contributor.AssetTagKeywordQueryContributor',
		'com.liferay.blogs.internal.search.spi.model.query.contributor.BlogsEntryKeywordQueryContributor',
		'com.liferay.calendar.internal.search.spi.model.query.contributor.CalendarBookingKeywordQueryContributor',
		'com.liferay.calendar.internal.search.spi.model.query.contributor.CalendarKeywordQueryContributor',
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
		'com.liferay.layout.internal.search.spi.model.query.contributor.LayoutKeywordQueryContributor',
		'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBMessageKeywordQueryContributor',
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
		'com.liferay.account.internal.search.spi.model.query.contributor.AccountEntryModelPreFilterContributor',
		'com.liferay.account.internal.search.spi.model.query.contributor.OrganizationModelPreFilterContributor',
		'com.liferay.account.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
		'com.liferay.analytics.settings.web.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
		'com.liferay.asset.categories.internal.search.spi.model.query.contributor.AssetCategoryModelPreFilterContributor',
		'com.liferay.data.engine.internal.search.spi.model.query.contributor.DEDataListViewModelPreFilterContributor',
		'com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor.DDLRecordSetModelPreFilterContributor',
		'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMStructureLayoutModelPreFilterContributor',
		'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMStructureModelPreFilterContributor',
		'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMTemplateModelPreFilterContributor',
		'com.liferay.exportimport.internal.search.spi.model.query.contributor.ExportImportConfigurationModelPreFilterContributor',
		'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBCategoryModelPreFilterContributor',
		'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBThreadModelPreFilterContributor',
		'com.liferay.organizations.internal.search.spi.model.query.contributor.OrganizationModelPreFilterContributor',
		'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoInstanceModelPreFilterContributor',
		'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoInstanceTokenModelPreFilterContributor',
		'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoLogModelPreFilterContributor',
		'com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor.KaleoTaskInstanceTokenModelPreFilterContributor',
		'com.liferay.redirect.internal.search.spi.model.query.contributor.RedirectNotFoundEntryModelPreFilterContributor',
		'com.liferay.segments.internal.search.spi.model.query.contributor.SegmentsEntryModelPreFilterContributor',
		'com.liferay.segments.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
	],
	clauseContributorsIncludes: [
		'com.liferay.blogs.internal.search.spi.model.query.contributor.BlogsEntryModelPreFilterContributor',
		'com.liferay.bookmarks.internal.search.spi.model.query.contributor.BookmarksEntryModelPreFilterContributor',
		'com.liferay.bookmarks.internal.search.spi.model.query.contributor.BookmarksFolderModelPreFilterContributor',
		'com.liferay.calendar.internal.search.spi.model.query.contributor.CalendarBookingModelPreFilterContributor',
		'com.liferay.change.tracking.internal.search.spi.model.query.contributor.CTModelPreFilterContributor',
		'com.liferay.document.library.internal.search.spi.model.query.contributor.DLFileEntryModelPreFilterContributor',
		'com.liferay.document.library.internal.search.spi.model.query.contributor.DLFolderModelPreFilterContributor',
		'com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor.DDLRecordModelPreFilterContributor',
		'com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor.DDMFormInstanceRecordModelPreFilterContributor',
		'com.liferay.layout.internal.search.spi.model.query.contributor.LayoutModelPreFilterContributor',
		'com.liferay.message.boards.internal.search.spi.model.query.contributor.MBMessageModelPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.StagingModelPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.WorkflowStatusModelPreFilterContributor',
		'com.liferay.users.admin.internal.search.spi.model.query.contributor.UserModelPreFilterContributor',
		'com.liferay.asset.internal.search.spi.model.query.contributor.AssetEntryModelPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.AssetCategoryIdsQueryPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.AssetTagNamesQueryPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.FolderIdQueryPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.GroupIdQueryPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.LayoutQueryPreFilterContributor',
		'com.liferay.portal.search.internal.spi.model.query.contributor.UserIdQueryPreFilterContributor',
	],
};

export const CUSTOM_JSON_SXP_ELEMENT = {
	description_i18n: {
		en_US: Liferay.Language.get('editable-json-text-area'),
	},
	elementDefinition: {
		category: 'custom',
		configuration: {},
		icon: 'custom-field',
	},
	title_i18n: {en_US: Liferay.Language.get('custom-json-element')},
};

export const DEFAULT_ADVANCED_CONFIGURATION = {};

export const DEFAULT_BASELINE_SXP_ELEMENTS = [];

export const DEFAULT_EDIT_SXP_ELEMENT = textMatchOverMultipleFields;

export const DEFAULT_HIGHLIGHT_CONFIGURATION = {};

export const DEFAULT_PARAMETER_CONFIGURATION = {};

export const DEFAULT_SORT_CONFIGURATION = {};

export const DEFAULT_SXP_ELEMENT_ICON = 'code';
