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

package com.liferay.commerce.price.list.internal.search;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
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
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CommercePriceEntryIndexer extends BaseIndexer<CommercePriceEntry> {

	public static final String CLASS_NAME = CommercePriceEntry.class.getName();

	public static final String FIELD_COMMERCE_PRICE_LIST_ID =
		"commercePriceListId";

	public static final String FIELD_EXTERNAL_REFERENCE_CODE =
		"externalReferenceCode";

	public CommercePriceEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.UID);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long commercePriceListId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_COMMERCE_PRICE_LIST_ID));

		if (commercePriceListId > 0) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_COMMERCE_PRICE_LIST_ID, commercePriceListId);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_EXTERNAL_REFERENCE_CODE, false);

		addSearchTerm(searchQuery, searchContext, "sku", false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, "cpDefinitionName", false);

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
	protected void doDelete(CommercePriceEntry commercePriceEntry)
		throws Exception {

		deleteDocument(
			commercePriceEntry.getCompanyId(),
			commercePriceEntry.getCommercePriceEntryId());
	}

	@Override
	protected Document doGetDocument(CommercePriceEntry commercePriceEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing price entry " + commercePriceEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commercePriceEntry);

		document.addKeyword(
			FIELD_COMMERCE_PRICE_LIST_ID,
			commercePriceEntry.getCommercePriceListId());
		document.addKeyword(
			FIELD_EXTERNAL_REFERENCE_CODE,
			commercePriceEntry.getExternalReferenceCode());

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		document.addKeyword("cpInstanceId", cpInstance.getCPInstanceId());
		document.addKeyword("sku", cpInstance.getSku());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		document.addLocalizedKeyword(
			"cpDefinitionName", cpDefinition.getNameMap());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commercePriceEntry + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, FIELD_COMMERCE_PRICE_LIST_ID,
			FIELD_COMMERCE_PRICE_LIST_ID);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommercePriceEntry commercePriceEntry)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commercePriceEntry.getCompanyId(),
			getDocument(commercePriceEntry), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_commercePriceEntryLocalService.getCommercePriceEntry(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexCommercePriceEntries(companyId);
	}

	private void _reindexCommercePriceEntries(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commercePriceEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommercePriceEntry commercePriceEntry) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commercePriceEntry));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce price entry " +
								commercePriceEntry.getCommercePriceEntryId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryIndexer.class);

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}