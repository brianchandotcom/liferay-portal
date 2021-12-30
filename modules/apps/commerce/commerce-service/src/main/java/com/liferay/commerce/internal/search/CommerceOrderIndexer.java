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

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.FilterBuilders;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CommerceOrderIndexer extends BaseIndexer<CommerceOrder> {

	public static final String CLASS_NAME = CommerceOrder.class.getName();

	public CommerceOrderIndexer() {
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _modelResourcePermission.contains(
			permissionChecker, entryClassPK, actionId);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long[] groupIds = searchContext.getGroupIds();

		if ((groupIds == null) || (groupIds.length == 0)) {
			contextBooleanFilter.addTerm(
				Field.GROUP_ID, "-1", BooleanClauseOccur.MUST);
		}

		long[] commerceAccountIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceAccountIds"), null);

		if (commerceAccountIds != null) {
			BooleanFilter commerceAccountIdBooleanFilter = new BooleanFilter();

			for (long commerceAccountId : commerceAccountIds) {
				Filter termFilter = new TermFilter(
					"commerceAccountId", String.valueOf(commerceAccountId));

				commerceAccountIdBooleanFilter.add(
					termFilter, BooleanClauseOccur.SHOULD);
			}

			commerceAccountIdBooleanFilter.add(
				new MissingFilter("commerceAccountId"),
				BooleanClauseOccur.SHOULD);

			contextBooleanFilter.add(
				commerceAccountIdBooleanFilter, BooleanClauseOccur.MUST);
		}

		int[] orderStatuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute("orderStatuses"), null);

		if (orderStatuses != null) {
			BooleanFilter orderStatusesBooleanFilter = new BooleanFilter();

			for (long orderStatus : orderStatuses) {
				Filter termFilter = new TermFilter(
					"orderStatus", String.valueOf(orderStatus));

				orderStatusesBooleanFilter.add(
					termFilter, BooleanClauseOccur.SHOULD);
			}

			orderStatusesBooleanFilter.add(
				new MissingFilter("orderStatus"), BooleanClauseOccur.SHOULD);

			if (GetterUtil.getBoolean(
					searchContext.getAttribute("negateOrderStatuses"))) {

				contextBooleanFilter.add(
					orderStatusesBooleanFilter, BooleanClauseOccur.MUST_NOT);
			}
			else {
				contextBooleanFilter.add(
					orderStatusesBooleanFilter, BooleanClauseOccur.MUST);
			}
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		super.postProcessSearchQuery(
			searchQuery, fullQueryBooleanFilter, searchContext);

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, "purchaseOrderNumber", false);
		addSearchTerm(
			searchQuery, searchContext, "externalReferenceCode", false);

		String keywords = searchContext.getKeywords();

		if (Validator.isNotNull(keywords)) {
			try {
				keywords = StringUtil.toLowerCase(keywords);

				searchQuery.add(
					_getTrailingWildcardQuery(Field.ENTRY_CLASS_PK, keywords),
					BooleanClauseOccur.SHOULD);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	@Override
	protected void doDelete(CommerceOrder commerceOrder) throws Exception {
		deleteDocument(
			commerceOrder.getCompanyId(), commerceOrder.getCommerceOrderId());
	}

	@Override
	protected Document doGetDocument(CommerceOrder commerceOrder)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing order item " + commerceOrder);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceOrder);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		document.addNumberSortable(
			Field.ENTRY_CLASS_PK, commerceOrder.getCommerceOrderId());
		document.addKeyword(Field.STATUS, commerceOrder.getStatus());
		document.addKeyword("advanceStatus", commerceOrder.getAdvanceStatus());
		document.addKeyword(
			"commerceAccountId", commerceOrder.getCommerceAccountId());
		document.addKeyword(
			"commerceChannelId", commerceChannel.getCommerceChannelId());
		document.addNumber("itemsQuantity", _getItemsQuantity(commerceOrder));
		document.addKeyword("orderStatus", commerceOrder.getOrderStatus());
		document.addKeyword(
			"purchaseOrderNumber", commerceOrder.getPurchaseOrderNumber());
		document.addKeyword(
			"externalReferenceCode", commerceOrder.getExternalReferenceCode());
		document.addNumber("total", commerceOrder.getTotal());
		document.addDate("orderDate", commerceOrder.getOrderDate());
		document.addDateSortable("orderDate", commerceOrder.getOrderDate());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + commerceOrder + " indexed successfully");
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
	protected void doReindex(CommerceOrder commerceOrder) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceOrder.getCompanyId(),
			getDocument(commerceOrder), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_commerceOrderLocalService.getCommerceOrder(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexCommerceOrders(companyId);
	}

	@Override
	protected boolean isUseSearchResultPermissionFilter(
		SearchContext searchContext) {

		Boolean useSearchResultPermissionFilter =
			(Boolean)searchContext.getAttribute(
				"useSearchResultPermissionFilter");

		if (useSearchResultPermissionFilter != null) {
			return useSearchResultPermissionFilter;
		}

		return super.isUseSearchResultPermissionFilter(searchContext);
	}

	private int _getItemsQuantity(CommerceOrder commerceOrder) {
		int count = 0;

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			count += commerceOrderItem.getQuantity();
		}

		return count;
	}

	private WildcardQuery _getTrailingWildcardQuery(
		String field, String value) {

		return new WildcardQueryImpl(field, value + StringPool.STAR);
	}

	private void _reindexCommerceOrders(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceOrderLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceOrder commerceOrder) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceOrder));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce order item " +
								commerceOrder.getCommerceOrderId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderIndexer.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder> _modelResourcePermission;

}