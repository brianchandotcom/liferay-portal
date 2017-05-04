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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
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
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = Indexer.class)
public class CPDefinitionIndexer extends BaseIndexer<CPDefinition> {

	public static final String FIELD_BASE_SKU = "baseSKU";
	public static final String FIELD_SKUS = "skus";

	public CPDefinitionIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	public static final String CLASS_NAME = CPDefinition.class.getName();

	@Override
	public void postProcessSearchQuery(
		BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
		SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);

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

		addSearchTerm(searchQuery, searchContext, FIELD_BASE_SKU, false);
		addSearchTerm(searchQuery, searchContext, FIELD_SKUS, false);

	}


	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	protected void doDelete(CPDefinition cpDefinition) throws Exception {
		deleteDocument(cpDefinition.getCompanyId(), cpDefinition.getCPDefinitionId());
	}


	@Override
	protected Document doGetDocument(CPDefinition cpDefinition)
		throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing definition " + cpDefinition);
		}

		Document document = getBaseModelDocument(CLASS_NAME, cpDefinition);

		String cpDefinitionDefaultLanguageId = LocalizationUtil.
			getDefaultLanguageId(cpDefinition.getTitle());

		List<String> languageIds = _cpDefinitionLocalService.
			getCPDefinitionLocalizationLanguageIds(
				cpDefinition.getCPDefinitionId());

		for (String languageId : languageIds) {
			String description = cpDefinition.getDescription(languageId);
			String title = cpDefinition.getTitle(languageId);

			if (languageId.equals(cpDefinitionDefaultLanguageId)) {
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

			document.addText(Field.CONTENT, description);
		}

		document.addText(FIELD_BASE_SKU, cpDefinition.getBaseSKU());

		String[] skus =  _cpInstanceLocalService.getSKUs(
			cpDefinition.getCPDefinitionId());

		document.addText(FIELD_SKUS, skus);

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpDefinition + " indexed successfully");
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
	protected void doReindex(CPDefinition cpDefinition) throws Exception {
		Document document = getDocument(cpDefinition);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpDefinition.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionLocalService.getCPDefinition(classPK);

		doReindex(cpDefinition);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPDefinitions(companyId);
	}

	protected void reindexCPDefinitions(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpDefinitionLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CPDefinition>() {

				@Override
				public void performAction(CPDefinition cpDefinition) {
					try {
						Document document = getDocument(cpDefinition);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce product option " +
									cpDefinition.getCPDefinitionId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionIndexer.class);

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}