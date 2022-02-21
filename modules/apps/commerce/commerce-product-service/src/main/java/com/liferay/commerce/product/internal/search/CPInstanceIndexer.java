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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CPInstanceIndexer extends BaseIndexer<CPInstance> {

	public static final String CLASS_NAME = CPInstance.class.getName();

	public CPInstanceIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			CPField.SKU, Field.UID);
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
			searchContext.getAttribute(CPField.CP_DEFINITION_ID));

		if (cpDefinitionId > 0) {
			contextBooleanFilter.addRequiredTerm(
				CPField.CP_DEFINITION_ID, cpDefinitionId);
		}

		Map<String, Serializable> attributes = searchContext.getAttributes();

		if (attributes.containsKey(CPField.CP_DEFINITION_STATUS)) {
			int cpDefinitionStatus = GetterUtil.getInteger(
				attributes.get(CPField.CP_DEFINITION_STATUS));

			if (cpDefinitionStatus == WorkflowConstants.STATUS_ANY) {
				contextBooleanFilter.addRangeTerm(
					CPField.CP_DEFINITION_STATUS,
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_SCHEDULED);
			}
			else {
				contextBooleanFilter.addRequiredTerm(
					CPField.CP_DEFINITION_STATUS, cpDefinitionStatus);
			}
		}

		if (attributes.containsKey(CPField.HAS_CHILD_CP_DEFINITIONS)) {
			boolean hasChildCPDefinitions = GetterUtil.getBoolean(
				attributes.get(CPField.HAS_CHILD_CP_DEFINITIONS));

			contextBooleanFilter.addRequiredTerm(
				CPField.HAS_CHILD_CP_DEFINITIONS, hasChildCPDefinitions);
		}

		if (attributes.containsKey(CPField.PUBLISHED)) {
			boolean published = GetterUtil.getBoolean(
				attributes.get(CPField.PUBLISHED));

			contextBooleanFilter.addRequiredTerm(CPField.PUBLISHED, published);
		}

		if (attributes.containsKey(CPField.PURCHASABLE)) {
			boolean purchasable = GetterUtil.getBoolean(
				attributes.get(CPField.PURCHASABLE));

			contextBooleanFilter.addRequiredTerm(
				CPField.PURCHASABLE, purchasable);
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
		addSearchLocalizedTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);

		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		addSearchTerm(
			searchQuery, searchContext, CPField.EXTERNAL_REFERENCE_CODE, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}

		String keywords = searchContext.getKeywords();

		if (Validator.isNotNull(keywords)) {
			try {
				keywords = StringUtil.toLowerCase(keywords);

				BooleanQuery booleanQuery = new BooleanQueryImpl();

				booleanQuery.add(
					new TermQueryImpl("sku.1_10_ngram", keywords),
					BooleanClauseOccur.SHOULD);

				MultiMatchQuery multiMatchQuery = new MultiMatchQuery(
					searchContext.getKeywords());

				multiMatchQuery.addField(CPField.SKU);
				multiMatchQuery.addField("sku.reverse");
				multiMatchQuery.setType(MultiMatchQuery.Type.PHRASE_PREFIX);

				booleanQuery.add(multiMatchQuery, BooleanClauseOccur.SHOULD);

				if (searchContext.isAndSearch()) {
					searchQuery.add(booleanQuery, BooleanClauseOccur.MUST);
				}
				else {
					searchQuery.add(booleanQuery, BooleanClauseOccur.SHOULD);
				}
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
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
			LocalizationUtil.getDefaultLanguageId(cpDefinition.getName());

		for (String languageId : languageIds) {
			String name = cpDefinition.getName(languageId);

			if (languageId.equals(cpDefinitionDefaultLanguageId)) {
				document.addText(Field.NAME, name);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				name);
		}

		document.addText(Field.NAME, cpDefinition.getName());

		document.addText(
			CPField.EXTERNAL_REFERENCE_CODE,
			cpInstance.getExternalReferenceCode());

		document.addText(Field.CONTENT, cpInstance.getSku());
		document.addDateSortable(
			CPField.DISPLAY_DATE, cpInstance.getDisplayDate());
		document.addTextSortable(CPField.SKU, cpInstance.getSku());
		document.addKeyword(
			CPField.CP_DEFINITION_ID, cpInstance.getCPDefinitionId());
		document.addKeyword(
			CPField.CP_DEFINITION_STATUS, cpDefinition.getStatus());
		document.addKeyword(CPField.PUBLISHED, cpInstance.isPublished());
		document.addKeyword(CPField.PURCHASABLE, cpInstance.isPurchasable());
		document.addKeyword(
			CPField.EXTERNAL_REFERENCE_CODE,
			cpInstance.getExternalReferenceCode());
		document.addKeyword(
			CPField.HAS_CHILD_CP_DEFINITIONS,
			_cpDefinitionLocalService.hasChildCPDefinitions(
				cpDefinition.getCPDefinitionId()));
		document.addKeyword(CPField.UNSPSC, cpInstance.getUnspsc());

		CommerceCatalog commerceCatalog = cpDefinition.getCommerceCatalog();

		document.addKeyword(
			"commerceCatalogId", commerceCatalog.getCommerceCatalogId());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpInstance + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, CPField.SKU, Field.CONTENT);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPInstance cpInstance) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpInstance.getCompanyId(),
			getDocument(cpInstance), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_cpInstanceLocalService.getCPInstance(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexCPInstances(companyId);
	}

	private void _reindexCPInstances(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpInstanceLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CPInstance cpInstance) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(cpInstance));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce product definition " +
								cpInstance.getCPInstanceId(),
							portalException);
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