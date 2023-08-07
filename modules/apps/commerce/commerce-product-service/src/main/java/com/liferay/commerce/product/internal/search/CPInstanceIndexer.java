/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
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
import com.liferay.portal.kernel.util.Localization;
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
@Component(service = Indexer.class)
public class CPInstanceIndexer extends BaseIndexer<CPInstance> {

	public static final String CLASS_NAME = CPInstance.class.getName();

	public CPInstanceIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			CPField.SKU, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
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

		addSearchTerm(
			searchQuery, searchContext, CPField.EXTERNAL_REFERENCE_CODE, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
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
			_log.debug("Indexing commerce product instance " + cpInstance);
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		Document document = getBaseModelDocument(CLASS_NAME, cpInstance);

		document.addKeyword(
			CPField.CP_DEFINITION_ID, cpInstance.getCPDefinitionId());
		document.addKeyword(
			CPField.CP_DEFINITION_STATUS, cpDefinition.getStatus());
		document.addDateSortable(
			CPField.DISPLAY_DATE, cpInstance.getDisplayDate());
		document.addKeyword(
			CPField.EXTERNAL_REFERENCE_CODE,
			cpInstance.getExternalReferenceCode());
		document.addText(
			CPField.EXTERNAL_REFERENCE_CODE,
			cpInstance.getExternalReferenceCode());
		document.addKeyword(
			CPField.HAS_CHILD_CP_DEFINITIONS,
			_cpDefinitionLocalService.hasChildCPDefinitions(
				cpDefinition.getCPDefinitionId()));
		document.addKeyword(CPField.PUBLISHED, cpInstance.isPublished());
		document.addKeyword(CPField.PURCHASABLE, cpInstance.isPurchasable());
		document.addTextSortable(CPField.SKU, cpInstance.getSku());
		document.addKeyword(CPField.UNSPSC, cpInstance.getUnspsc());
		document.addText(Field.CONTENT, cpInstance.getSku());
		document.addText(Field.NAME, cpDefinition.getName());

		List<String> languageIds =
			_cpDefinitionLocalService.getCPDefinitionLocalizationLanguageIds(
				cpInstance.getCPDefinitionId());

		String cpDefinitionDefaultLanguageId =
			_localization.getDefaultLanguageId(cpDefinition.getName());

		for (String languageId : languageIds) {
			String name = cpDefinition.getName(languageId);

			if (languageId.equals(cpDefinitionDefaultLanguageId)) {
				document.addText(Field.NAME, name);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				_localization.getLocalizedName(Field.NAME, languageId), name);
		}

		CommerceCatalog commerceCatalog = cpDefinition.getCommerceCatalog();

		if (commerceCatalog != null) {
			document.addKeyword(
				"accountEntryId", commerceCatalog.getAccountEntryId());
			document.addKeyword(
				"commerceCatalogId", commerceCatalog.getCommerceCatalogId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Commerce product instance " + cpInstance +
					" indexed successfully");
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
			cpInstance.getCompanyId(), getDocument(cpInstance));
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
							"Unable to index commerce product instance " +
								cpInstance,
							portalException);
					}
				}
			});

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceIndexer.class);

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private Localization _localization;

}