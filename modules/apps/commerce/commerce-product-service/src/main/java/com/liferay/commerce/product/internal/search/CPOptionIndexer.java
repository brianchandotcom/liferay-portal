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

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
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
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CPOptionIndexer extends BaseIndexer<CPOption> {

	public static final String CLASS_NAME = CPOption.class.getName();

	public CPOptionIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.MODIFIED_DATE, Field.NAME, Field.UID, CPField.KEY);
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
		addSearchTerm(searchQuery, searchContext, CPField.KEY, false);
		addSearchTerm(
			searchQuery, searchContext, CPField.OPTION_VALUE_NAME, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, CPField.OPTION_VALUE_NAME, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	@Override
	protected void doDelete(CPOption cpOption) throws Exception {
		deleteDocument(cpOption.getCompanyId(), cpOption.getCPOptionId());
	}

	@Override
	protected Document doGetDocument(CPOption cpOption) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing option " + cpOption);
		}

		Document document = getBaseModelDocument(CLASS_NAME, cpOption);

		String cpOptionDefaultLanguageId =
			LocalizationUtil.getDefaultLanguageId(cpOption.getName());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			cpOption.getName());

		for (String languageId : languageIds) {
			String description = cpOption.getDescription(languageId);
			String name = cpOption.getName(languageId);

			if (languageId.equals(cpOptionDefaultLanguageId)) {
				document.addText(Field.DESCRIPTION, description);
				document.addText(Field.NAME, name);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				name);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				description);

			document.addText(CPField.KEY, cpOption.getKey());
			document.addText(Field.CONTENT, name);

			document.addText(
				CPField.DDM_FORM_FIELD_TYPE_NAME,
				cpOption.getDDMFormFieldTypeName());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpOption + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.NAME, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPOption cpOption) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpOption.getCompanyId(), getDocument(cpOption),
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_cpOptionLocalService.getCPOption(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPOptions(companyId);
	}

	protected void reindexCPOptions(long companyId) throws PortalException {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpOptionLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CPOption cpOption) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(cpOption));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce product option " +
								cpOption.getCPOptionId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionIndexer.class);

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}