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

package com.liferay.commerce.product.search;

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
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
@Component(immediate = true, service = Indexer.class)
public class CPDefinitionOptionValueRelIndexer
	extends BaseIndexer<CPDefinitionOptionValueRel> {

	public static final String CLASS_NAME =
		CPDefinitionOptionValueRel.class.getName();

	public static final String FIELD_CP_DEFINITION_OPTION_REL_ID =
		"CPDefinitionOptionRelId";

	public static final String FIELD_KEY = "key";

	public CPDefinitionOptionValueRelIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.TITLE, Field.UID, FIELD_KEY);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long cpDefinitionOptionRelId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_CP_DEFINITION_OPTION_REL_ID));

		if (cpDefinitionOptionRelId > 0) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_CP_DEFINITION_OPTION_REL_ID, cpDefinitionOptionRelId);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, FIELD_KEY, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
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
	protected void doDelete(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws Exception {

		deleteDocument(
			cpDefinitionOptionValueRel.getCompanyId(),
			cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
	}

	@Override
	protected Document doGetDocument(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing definition option rel " + cpDefinitionOptionValueRel);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, cpDefinitionOptionValueRel);

		String cpDefinitionOptionValueRelDefaultLanguageId =
			LocalizationUtil.getDefaultLanguageId(
				cpDefinitionOptionValueRel.getTitle());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			cpDefinitionOptionValueRel.getTitle());

		for (String languageId : languageIds) {
			String title = cpDefinitionOptionValueRel.getTitle(languageId);

			if (languageId.equals(
					cpDefinitionOptionValueRelDefaultLanguageId)) {

				document.addText(Field.TITLE, title);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				title);

			document.addText(FIELD_KEY, cpDefinitionOptionValueRel.getKey());
			document.addText(Field.CONTENT, title);
		}

		document.addNumber(
			Field.PRIORITY, cpDefinitionOptionValueRel.getPriority());
		document.addKeyword(
			FIELD_CP_DEFINITION_OPTION_REL_ID,
			cpDefinitionOptionValueRel.getCPDefinitionOptionRelId());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + cpDefinitionOptionValueRel +
					" indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.TITLE, FIELD_KEY);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws Exception {

		Document document = getDocument(cpDefinitionOptionValueRel);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpDefinitionOptionValueRel.getCompanyId(),
			document, isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRel(classPK);

		doReindex(cpDefinitionOptionValueRel);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPDefinitionOptionValueRels(companyId);
	}

	protected void reindexCPDefinitionOptionValueRels(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpDefinitionOptionValueRelLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CPDefinitionOptionValueRel>() {

				@Override
				public void performAction(
					CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

					try {
						Document document = getDocument(
							cpDefinitionOptionValueRel);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index definition option rel " +
									cpDefinitionOptionValueRel.
										getCPDefinitionOptionValueRelId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionOptionValueRelIndexer.class);

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}