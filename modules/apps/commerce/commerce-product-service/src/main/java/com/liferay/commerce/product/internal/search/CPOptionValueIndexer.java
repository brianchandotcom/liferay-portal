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
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
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
public class CPOptionValueIndexer extends BaseIndexer<CPOptionValue> {

	public static final String CLASS_NAME = CPOptionValue.class.getName();

	public CPOptionValueIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID, CPField.CP_OPTION_ID, CPField.KEY);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long cpOptionId = GetterUtil.getLong(
			searchContext.getAttribute(CPField.CP_OPTION_ID));

		if (cpOptionId > 0) {
			contextBooleanFilter.addRequiredTerm(
				CPField.CP_OPTION_ID, cpOptionId);
		}
	}

	@Override
	protected void doDelete(CPOptionValue cpOptionValue) throws Exception {
		deleteDocument(
			cpOptionValue.getCompanyId(), cpOptionValue.getCPOptionValueId());
	}

	@Override
	protected Document doGetDocument(CPOptionValue cpOptionValue)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing option value " + cpOptionValue);
		}

		Document document = getBaseModelDocument(CLASS_NAME, cpOptionValue);

		String cpOptionValueDefaultLanguageId =
			LocalizationUtil.getDefaultLanguageId(cpOptionValue.getName());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			cpOptionValue.getName());

		for (String languageId : languageIds) {
			String name = cpOptionValue.getName(languageId);

			if (languageId.equals(cpOptionValueDefaultLanguageId)) {
				document.addText(Field.NAME, name);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				name);
			document.addNumber(Field.PRIORITY, cpOptionValue.getPriority());
			document.addText(CPField.KEY, cpOptionValue.getKey());
			document.addText(Field.CONTENT, name);
			document.addNumber(
				CPField.CP_OPTION_ID, cpOptionValue.getCPOptionId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpOptionValue + " indexed successfully");
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
	protected void doReindex(CPOptionValue cpOptionValue) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpOptionValue.getCompanyId(),
			getDocument(cpOptionValue), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_cpOptionValueLocalService.getCPOptionValue(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexCPOptionValues(companyId);
	}

	private void _reindexCPOptionValues(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpOptionValueLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CPOptionValue cpOptionValue) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(cpOptionValue));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce product option " +
								cpOptionValue.getCPOptionValueId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionValueIndexer.class);

	@Reference
	private CPOptionValueLocalService _cpOptionValueLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}