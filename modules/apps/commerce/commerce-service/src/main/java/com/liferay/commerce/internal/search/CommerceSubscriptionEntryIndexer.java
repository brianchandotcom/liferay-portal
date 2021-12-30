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

package com.liferay.commerce.internal.search;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CommerceSubscriptionEntryIndexer
	extends BaseIndexer<CommerceSubscriptionEntry> {

	public static final String CLASS_NAME =
		CommerceSubscriptionEntry.class.getName();

	public static final String FIELD_COMMERCE_ORDER_ITEM_ID =
		"commerceOrderItemId";

	public static final String FIELD_CP_INSTANCE_ID = "CPInstanceId";

	public static final String FIELD_MAX_SUBSCRIPTION_CYCLES =
		"maxSubscriptionCycles";

	public static final String FIELD_SKU = "sku";

	public static final String FIELD_SUBSCRIPTION_STATUS = "subscriptionStatus";

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		Long maxSubscriptionCycles = (Long)searchContext.getAttribute(
			FIELD_MAX_SUBSCRIPTION_CYCLES);

		if (maxSubscriptionCycles != null) {
			contextBooleanFilter.addTerm(
				FIELD_MAX_SUBSCRIPTION_CYCLES,
				String.valueOf(maxSubscriptionCycles), BooleanClauseOccur.MUST);
		}

		Integer subscriptionStatus = (Integer)searchContext.getAttribute(
			FIELD_SUBSCRIPTION_STATUS);

		if (subscriptionStatus != null) {
			contextBooleanFilter.addTerm(
				FIELD_SUBSCRIPTION_STATUS, String.valueOf(subscriptionStatus),
				BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_COMMERCE_ORDER_ITEM_ID, false);
		addSearchTerm(searchQuery, searchContext, FIELD_CP_INSTANCE_ID, false);
		addSearchTerm(searchQuery, searchContext, FIELD_SKU, false);
	}

	@Override
	protected void doDelete(CommerceSubscriptionEntry commerceSubscriptionEntry)
		throws Exception {

		deleteDocument(
			commerceSubscriptionEntry.getCompanyId(),
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());
	}

	@Override
	protected Document doGetDocument(
			CommerceSubscriptionEntry commerceSubscriptionEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing subscription entry " + commerceSubscriptionEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commerceSubscriptionEntry);

		document.addNumber(
			FIELD_COMMERCE_ORDER_ITEM_ID,
			commerceSubscriptionEntry.getCommerceOrderItemId());
		document.addNumber(
			FIELD_CP_INSTANCE_ID, commerceSubscriptionEntry.getCPInstanceId());
		document.addNumber(
			FIELD_MAX_SUBSCRIPTION_CYCLES,
			commerceSubscriptionEntry.getMaxSubscriptionCycles());
		document.addKeyword(
			FIELD_SUBSCRIPTION_STATUS,
			commerceSubscriptionEntry.getSubscriptionStatus());

		String sku = StringPool.BLANK;

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		if (commerceOrderItem != null) {
			sku = commerceOrderItem.getSku();
		}

		document.addText(FIELD_SKU, sku);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceSubscriptionEntry +
					" indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.NAME);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(
			CommerceSubscriptionEntry commerceSubscriptionEntry)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceSubscriptionEntry.getCompanyId(),
			getDocument(commerceSubscriptionEntry), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexCommerceSubscriptionEntries(companyId);
	}

	private void _reindexCommerceSubscriptionEntries(long companyId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceSubscriptionEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceSubscriptionEntry commerceSubscriptionEntry) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceSubscriptionEntry));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						long commerceSubscriptionEntryId =
							commerceSubscriptionEntry.
								getCommerceSubscriptionEntryId();

						_log.warn(
							"Unable to index subscription entry " +
								commerceSubscriptionEntryId,
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSubscriptionEntryIndexer.class);

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}