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
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class SXPElementIndexer extends BaseIndexer<SXPElement> {

	public static final String CLASS_NAME = SXPElement.class.getName();

	public SXPElementIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.HIDDEN, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.UID, "readOnly");
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

		return _sxpElementModelResourcePermission.contains(
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
	protected void doDelete(SXPElement sxpElement) throws Exception {
		deleteDocument(sxpElement.getCompanyId(), sxpElement.getSXPElementId());
	}

	@Override
	protected Document doGetDocument(SXPElement sxpElement) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing document " + sxpElement.getSXPElementId());
		}

		Document document = getBaseModelDocument(CLASS_NAME, sxpElement);

		document.addDate(Field.MODIFIED_DATE, sxpElement.getModifiedDate());
		document.addKeyword(Field.HIDDEN, sxpElement.isHidden());
		document.addKeyword(Field.TYPE, sxpElement.getType());
		document.addKeyword("readOnly", sxpElement.isReadOnly());

		for (Locale locale :
				LanguageUtil.getCompanyAvailableLocales(
					sxpElement.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(
						Field.DESCRIPTION, languageId)),
				sxpElement.getDescription(locale), true);
			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(Field.TITLE, languageId)),
				sxpElement.getTitle(locale), true);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				sxpElement.getDescription(locale));
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				sxpElement.getTitle(locale));
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + sxpElement + " indexed successfully");
		}

		return document;
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_sxpElementLocalService.getSXPElement(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexSXPElements(companyId);
	}

	@Override
	protected void doReindex(SXPElement sxpElement) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), sxpElement.getCompanyId(),
			getDocument(sxpElement), isCommitImmediately());
	}

	private Summary _createSummary(
		Document document, String descriptionField, String titleField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		return new Summary(
			document.get(prefix + titleField, titleField),
			document.get(prefix + descriptionField, descriptionField));
	}

	private void _reindexSXPElements(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_sxpElementLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(SXPElement sxpElement) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(sxpElement));
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
		SXPElementIndexer.class);

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.search.experiences.model.SXPElement)"
	)
	private ModelResourcePermission<SXPElement>
		_sxpElementModelResourcePermission;

}