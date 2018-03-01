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
import com.liferay.commerce.product.search.CPInstanceIndexer;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
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

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceOrderItemIndexer extends BaseIndexer<CommerceOrderItem> {

	public static final String CLASS_NAME = CommerceOrderItem.class.getName();

	public static final String FIELD_COMMERCE_ORDER_ID = "commerceOrderId";

	public static final String FIELD_SKU = CPInstanceIndexer.FIELD_SKU;

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long commerceOrderId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_COMMERCE_ORDER_ID));

		contextBooleanFilter.addRequiredTerm(
			FIELD_COMMERCE_ORDER_ID, commerceOrderId);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, FIELD_SKU, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, true);
	}

	@Override
	protected void doDelete(CommerceOrderItem commerceOrderItem)
		throws Exception {

		deleteDocument(
			commerceOrderItem.getCompanyId(),
			commerceOrderItem.getCommerceOrderItemId());
	}

	@Override
	protected Document doGetDocument(CommerceOrderItem commerceOrderItem)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing order item " + commerceOrderItem);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceOrderItem);

		document.addLocalizedKeyword(
			Field.TITLE, commerceOrderItem.getTitleMap());
		document.addKeyword(FIELD_SKU, commerceOrderItem.getSku());
		document.addNumber(
			FIELD_COMMERCE_ORDER_ID, commerceOrderItem.getCommerceOrderId());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceOrderItem + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return null;
	}

	@Override
	protected void doReindex(CommerceOrderItem commerceOrderItem)
		throws Exception {

		Document document = getDocument(commerceOrderItem);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceOrderItem.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(classPK);

		doReindex(commerceOrderItem);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceOrderItems(companyId);
	}

	protected void reindexCommerceOrderItems(long companyId) throws Exception {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceOrderItemLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CommerceOrderItem>() {

				@Override
				public void performAction(CommerceOrderItem commerceOrderItem) {
					try {
						Document document = getDocument(commerceOrderItem);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce order item " +
									commerceOrderItem.getCommerceOrderItemId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemIndexer.class);

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}