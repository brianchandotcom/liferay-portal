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
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
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
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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

		long siteGroupId = GetterUtil.getLong(
			searchContext.getAttribute("siteGroupId"));

		if (siteGroupId > 0) {
			TermFilter termFilter = new TermFilter(
				"siteGroupId", String.valueOf(siteGroupId));

			contextBooleanFilter.add(termFilter, BooleanClauseOccur.MUST);
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

		document.addNumberSortable(
			Field.ENTRY_CLASS_PK, commerceOrder.getCommerceOrderId());
		document.addKeyword("advanceStatus", commerceOrder.getAdvanceStatus());
		document.addNumber("itemsQuantity", getItemsQuantity(commerceOrder));
		document.addKeyword("orderStatus", commerceOrder.getOrderStatus());
		document.addKeyword("siteGroupId", commerceOrder.getSiteGroupId());
		document.addNumber("total", commerceOrder.getTotal());

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
		Document document = getDocument(commerceOrder);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceOrder.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(classPK);

		doReindex(commerceOrder);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceOrders(companyId);
	}

	protected int getItemsQuantity(CommerceOrder commerceOrder) {
		int count = 0;

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			count += commerceOrderItem.getQuantity();
		}

		return count;
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

	protected void reindexCommerceOrders(long companyId) throws Exception {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceOrderLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommerceOrder>() {

				@Override
				public void performAction(CommerceOrder commerceOrder) {
					try {
						Document document = getDocument(commerceOrder);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce order item " +
									commerceOrder.getCommerceOrderId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderIndexer.class);

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder> _modelResourcePermission;

}