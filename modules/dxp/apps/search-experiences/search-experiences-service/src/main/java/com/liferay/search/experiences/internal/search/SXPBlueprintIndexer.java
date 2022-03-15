/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class SXPBlueprintIndexer extends BaseIndexer<SXPBlueprint> {

	public static final String CLASS_NAME = SXPBlueprint.class.getName();

	public SXPBlueprintIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.STATUS, Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.DESCRIPTION, Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String languageId = LocaleUtil.toLanguageId(locale);

		return _createSummary(
			document,
			LocalizationUtil.getLocalizedName(Field.DESCRIPTION, languageId),
			LocalizationUtil.getLocalizedName(Field.TITLE, languageId));
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _sxpBlueprintModelResourcePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		String[] localizedFieldNames =
			_searchLocalizationHelper.getLocalizedFieldNames(
				new String[] {Field.DESCRIPTION, Field.TITLE}, searchContext);

		queryConfig.addHighlightFieldNames(localizedFieldNames);
	}

	@Override
	protected void doDelete(SXPBlueprint sxpBlueprint) throws Exception {
		deleteDocument(
			sxpBlueprint.getCompanyId(), sxpBlueprint.getSXPBlueprintId());
	}

	@Override
	protected Document doGetDocument(SXPBlueprint sxpBlueprint)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing document " + sxpBlueprint.getSXPBlueprintId());
		}

		Document document = getBaseModelDocument(CLASS_NAME, sxpBlueprint);

		document.addDate(Field.MODIFIED_DATE, sxpBlueprint.getModifiedDate());
		document.addKeyword(Field.STATUS, sxpBlueprint.getStatus());

		for (Locale locale :
				LanguageUtil.getCompanyAvailableLocales(
					sxpBlueprint.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(
						Field.DESCRIPTION, languageId)),
				sxpBlueprint.getDescription(locale), true);
			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(Field.TITLE, languageId)),
				sxpBlueprint.getTitle(locale), true);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				sxpBlueprint.getDescription(locale));
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				sxpBlueprint.getTitle(locale));
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + sxpBlueprint + " indexed successfully");
		}

		return document;
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_sxpBlueprintLocalService.getSXPBlueprint(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexSXPBlueprints(companyId);
	}

	@Override
	protected void doReindex(SXPBlueprint sxpBlueprint) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), sxpBlueprint.getCompanyId(),
			getDocument(sxpBlueprint), isCommitImmediately());
	}

	private Summary _createSummary(
		Document document, String descriptionField, String titleField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		return new Summary(
			document.get(prefix + titleField, titleField),
			document.get(prefix + descriptionField, descriptionField));
	}

	private void _reindexSXPBlueprints(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_sxpBlueprintLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(SXPBlueprint sxpBlueprint) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(sxpBlueprint));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintIndexer.class);

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.search.experiences.model.SXPBlueprint)"
	)
	private ModelResourcePermission<SXPBlueprint>
		_sxpBlueprintModelResourcePermission;

}