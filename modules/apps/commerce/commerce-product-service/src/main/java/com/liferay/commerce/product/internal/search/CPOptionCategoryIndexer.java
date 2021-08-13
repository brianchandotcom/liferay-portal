/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CPOptionCategoryIndexer extends BaseIndexer<CPOptionCategory> {

	public static final String CLASS_NAME = CPOptionCategory.class.getName();

	public static final String FIELD_KEY = "key";

	public CPOptionCategoryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.MODIFIED_DATE, Field.NAME, Field.UID, FIELD_KEY);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, FIELD_KEY, false);
		addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
	}

	@Override
	protected void doDelete(CPOptionCategory cpOptionCategory)
		throws Exception {

		deleteDocument(
			cpOptionCategory.getCompanyId(),
			cpOptionCategory.getCPOptionCategoryId());
	}

	@Override
	protected Document doGetDocument(CPOptionCategory cpOptionCategory)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing option " + cpOptionCategory);
		}

		Document document = getBaseModelDocument(CLASS_NAME, cpOptionCategory);

		String cpOptionCategoryDefaultLanguageId =
			LocalizationUtil.getDefaultLanguageId(cpOptionCategory.getTitle());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			cpOptionCategory.getTitle());

		for (String languageId : languageIds) {
			String description = cpOptionCategory.getDescription(languageId);
			String title = cpOptionCategory.getTitle(languageId);

			if (languageId.equals(cpOptionCategoryDefaultLanguageId)) {
				document.addText(Field.DESCRIPTION, description);
				document.addText(Field.TITLE, title);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				title);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				description);

			document.addText(FIELD_KEY, cpOptionCategory.getKey());
			document.addText(Field.CONTENT, title);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + cpOptionCategory + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPOptionCategory cpOptionCategory)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpOptionCategory.getCompanyId(),
			getDocument(cpOptionCategory), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_cpOptionCategoryLocalService.getCPOptionCategory(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPOptionCategorys(companyId);
	}

	protected void reindexCPOptionCategorys(long companyId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpOptionCategoryLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CPOptionCategory cpOptionCategory) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(cpOptionCategory));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce product option " +
								cpOptionCategory.getCPOptionCategoryId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionCategoryIndexer.class);

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}