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
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = Indexer.class)
public class CPInstanceIndexer extends BaseIndexer<CPInstance> {

	public static final String CLASS_NAME = CPInstance.class.getName();

	public static final String FIELD_CP_DEFINITION_ID = "CPDefinitionId";

	public static final String FIELD_PURCHASABLE = "purchasable";

	public static final String FIELD_SKU = "sku";

	public CPInstanceIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			FIELD_SKU, Field.UID);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextBooleanFilter.addRequiredTerm(Field.STATUS, status);
		}

		long cpDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_CP_DEFINITION_ID));

		if (cpDefinitionId > 0) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_CP_DEFINITION_ID, cpDefinitionId);
		}

		Map<String, Serializable> attributes = searchContext.getAttributes();

		if (attributes.containsKey(FIELD_PURCHASABLE)) {
			boolean purchasable = GetterUtil.getBoolean(
				attributes.get(FIELD_PURCHASABLE));

			contextBooleanFilter.addRequiredTerm(
				FIELD_PURCHASABLE, purchasable);
		}

		String[] fieldNames = (String[])searchContext.getAttribute("OPTIONS");

		if (fieldNames == null) {
			return;
		}

		for (String fieldName : fieldNames) {
			String fieldValue = (String)searchContext.getAttribute(fieldName);

			contextBooleanFilter.addRequiredTerm(fieldName, fieldValue);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, FIELD_SKU, false);
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
	protected void doDelete(CPInstance cpInstance) throws Exception {
		deleteDocument(cpInstance.getCompanyId(), cpInstance.getCPInstanceId());
	}

	@Override
	protected Document doGetDocument(CPInstance cpInstance) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing definition " + cpInstance);
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		Document document = getBaseModelDocument(CLASS_NAME, cpInstance);

		List<String> languageIds =
			_cpDefinitionLocalService.getCPDefinitionLocalizationLanguageIds(
				cpInstance.getCPDefinitionId());

		String cpDefinitionDefaultLanguageId =
			LocalizationUtil.getDefaultLanguageId(cpDefinition.getTitle());

		for (String languageId : languageIds) {
			String title = cpDefinition.getTitle(languageId);

			if (languageId.equals(cpDefinitionDefaultLanguageId)) {
				document.addText(Field.TITLE, title);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				title);
		}

		document.addText(Field.TITLE, cpDefinition.getTitle());

		document.addText(Field.CONTENT, cpInstance.getSku());

		document.addText(FIELD_SKU, cpInstance.getSku());
		document.addKeyword(
			FIELD_CP_DEFINITION_ID, cpInstance.getCPDefinitionId());
		document.addKeyword(FIELD_PURCHASABLE, cpInstance.getPurchasable());

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap =
				_cpInstanceHelper.getCPDefinitionOptionRelsMap(
					cpInstance.getDDMContent());

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelListMapEntry :
					cpDefinitionOptionRelListMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionRelListMapEntry.getKey();

			CPOption cpOption = cpDefinitionOptionRel.getCPOption();

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRelListMapEntry.getValue();

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRels.get(0);

			for (String languageId : languageIds) {
				document.addText(
					LocalizationUtil.getLocalizedName(
						"ATTRIBUTE_" + cpOption.getKey() + "_TITLE",
						languageId),
					cpDefinitionOptionValueRel.getTitle(languageId));

				document.addText(
					LocalizationUtil.getLocalizedName(
						"ATTRIBUTE_" +
							cpDefinitionOptionRel.getCPDefinitionOptionRelId() +
								"_TITLE",
						languageId),
					cpDefinitionOptionValueRel.getTitle(languageId));
			}

			document.addNumber(
				"ATTRIBUTE_" + cpOption.getKey() + "_VALUE_ID",
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());

			document.addNumber(
				"ATTRIBUTE_" +
					cpDefinitionOptionRel.getCPDefinitionOptionRelId() +
						"_VALUE_ID",
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpInstance + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, FIELD_SKU, Field.CONTENT);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPInstance cpInstance) throws Exception {
		Document document = getDocument(cpInstance);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpInstance.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(classPK);

		doReindex(cpInstance);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPInstances(companyId);
	}

	protected void reindexCPInstances(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpInstanceLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CPInstance>() {

				@Override
				public void performAction(CPInstance cpInstance) {
					try {
						Document document = getDocument(cpInstance);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce product definition " +
									cpInstance.getCPInstanceId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceIndexer.class);

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}