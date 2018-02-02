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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderSearch;
import com.liferay.commerce.product.search.FacetImpl;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderListDisplayContext {

	public CommerceOrderListDisplayContext(
			CommerceOrderLocalService commerceOrderLocalService,
			CommerceOrderNoteService commerceOrderNoteService,
			CommercePriceFormatter commercePriceFormatter,
			RenderRequest renderRequest,
			WorkflowTaskManager workflowTaskManager)
		throws PortalException {

		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commercePriceFormatter = commercePriceFormatter;
		_workflowTaskManager = workflowTaskManager;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
		_commerceOrderDateFormatTime = FastDateFormatFactoryUtil.getTime(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		executeSearch();
	}

	public String getCommerceOrderDate(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatDate.format(
			commerceOrder.getCreateDate());
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId());
	}

	public String getCommerceOrderTime(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatTime.format(
			commerceOrder.getCreateDate());
	}

	public String getCommerceOrderTransitionMessage(String transitionName) {
		if (Validator.isNull(transitionName)) {
			transitionName = "proceed";
		}

		return LanguageUtil.get(
			_commerceOrderRequestHelper.getRequest(), transitionName);
	}

	public List<ObjectValuePair<Long, String>> getCommerceOrderTransitionOVPs(
			CommerceOrder commerceOrder)
		throws PortalException {

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		populateTransitionOVPs(transitionOVPs, commerceOrder, true);
		populateTransitionOVPs(transitionOVPs, commerceOrder, false);

		return transitionOVPs;
	}

	public String getCommerceOrderValue(CommerceOrder commerceOrder)
		throws PortalException {

		return _commercePriceFormatter.format(
			_commerceOrderRequestHelper.getRequest(), commerceOrder.getTotal());
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		List<NavigationItem> navigationItems = new ArrayList<>();

		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		int orderStatus = getOrderStatus();

		for (Map.Entry<Integer, Integer> entry :
				_orderStatusCounts.entrySet()) {

			int curOrderStatus = entry.getKey();
			int curCount = entry.getValue();

			PortletURL statusURL = liferayPortletResponse.createRenderURL();

			statusURL.setParameter(
				"orderStatus", String.valueOf(curOrderStatus));

			NavigationItem statusNavigationItem = new NavigationItem();

			statusNavigationItem.setActive(curOrderStatus == orderStatus);
			statusNavigationItem.setHref(statusURL.toString());
			statusNavigationItem.setLabel(
				getOrderStatusLabel(curOrderStatus, curCount));

			navigationItems.add(statusNavigationItem);
		}

		return navigationItems;
	}

	public int getOrderStatus() {
		return ParamUtil.getInteger(
			_commerceOrderRequestHelper.getRequest(), "orderStatus",
			_ORDER_STATUSES[0]);
	}

	public String getOrderStatusLabel(int status, long count) {
		String label = LanguageUtil.get(
			_commerceOrderRequestHelper.getRequest(),
			CommerceOrderConstants.getOrderStatusLabel(status));

		if (count > 0) {
			StringBundler sb = new StringBundler(4);

			sb.append(label);
			sb.append(" (");
			sb.append(count);
			sb.append(CharPool.CLOSE_PARENTHESIS);

			label = sb.toString();
		}

		return label;
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"orderStatus", String.valueOf(getOrderStatus()));

		return portletURL;
	}

	public SearchContainer<CommerceOrder> getSearchContainer() {
		return _searchContainer;
	}

	protected SearchContext buildSearchContext(
		int orderStatus, int start, int end, String orderByCol,
		String orderByType) {

		SearchContext searchContext = new SearchContext();

		FacetImpl facetImpl = new FacetImpl("orderStatus", searchContext);

		if (orderStatus != CommerceOrderConstants.ORDER_STATUS_ANY) {
			facetImpl.select(String.valueOf(orderStatus));
		}

		searchContext.addFacet(facetImpl);

		searchContext.setAttribute(
			"siteGroupId", _commerceOrderRequestHelper.getScopeGroupId());

		searchContext.setCompanyId(_commerceOrderRequestHelper.getCompanyId());
		searchContext.setStart(start);
		searchContext.setEnd(end);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Sort sort = SortFactoryUtil.getSort(
			CommerceOrder.class, orderByCol, orderByType);

		searchContext.setSorts(sort);

		return searchContext;
	}

	protected void executeSearch() throws PortalException {
		_searchContainer = new CommerceOrderSearch(
			_commerceOrderRequestHelper.getLiferayPortletRequest(),
			getPortletURL());

		int orderStatus = getOrderStatus();

		SearchContext searchContext = buildSearchContext(
			orderStatus, _searchContainer.getStart(), _searchContainer.getEnd(),
			_searchContainer.getOrderByCol(),
			_searchContainer.getOrderByType());

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			_commerceOrderLocalService.searchCommerceOrders(searchContext);

		Facet facet = searchContext.getFacet("orderStatus");

		FacetCollector facetCollector = facet.getFacetCollector();

		for (int curOrderStatus : _ORDER_STATUSES) {
			int count = 0;

			if (curOrderStatus == CommerceOrderConstants.ORDER_STATUS_ANY) {
				count = baseModelSearchResult.getLength();
			}
			else {
				TermCollector termCollector = facetCollector.getTermCollector(
					String.valueOf(curOrderStatus));

				if (termCollector != null) {
					count = termCollector.getFrequency();
				}
			}

			_orderStatusCounts.put(curOrderStatus, count);
		}

		if (orderStatus != CommerceOrderConstants.ORDER_STATUS_ANY) {
			HttpServletRequest httpServletRequest =
				_commerceOrderRequestHelper.getRequest();

			String orderStatusLabel = LanguageUtil.get(
				httpServletRequest,
				CommerceOrderConstants.getOrderStatusLabel(orderStatus));

			_searchContainer.setEmptyResultsMessage(
				LanguageUtil.format(
					httpServletRequest, "no-x-orders-were-found",
					StringUtil.toLowerCase(orderStatusLabel), false));
		}

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceOrderRequestHelper.getLiferayPortletResponse()));

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());
	}

	protected void populateTransitionOVPs(
			List<ObjectValuePair<Long, String>> transitionOVPs,
			CommerceOrder commerceOrder, boolean searchByUserRoles)
		throws PortalException {

		long companyId = commerceOrder.getCompanyId();
		long userId = _commerceOrderRequestHelper.getUserId();

		List<WorkflowTask> workflowTasks = _workflowTaskManager.search(
			companyId, userId, null, CommerceOrder.class.getName(),
			new Long[] {commerceOrder.getCommerceOrderId()}, null, null, false,
			searchByUserRoles, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

		for (WorkflowTask workflowTask : workflowTasks) {
			long workflowTaskId = workflowTask.getWorkflowTaskId();

			List<String> transitionNames =
				_workflowTaskManager.getNextTransitionNames(
					companyId, userId, workflowTaskId);

			for (String transitionName : transitionNames) {
				transitionOVPs.add(
					new ObjectValuePair<>(workflowTaskId, transitionName));
			}
		}
	}

	private static final int[] _ORDER_STATUSES = {
		CommerceOrderConstants.ORDER_STATUS_PENDING,
		CommerceOrderConstants.ORDER_STATUS_ANY,
		CommerceOrderConstants.ORDER_STATUS_CANCELLED,
		CommerceOrderConstants.ORDER_STATUS_PROCESSING,
		CommerceOrderConstants.ORDER_STATUS_COMPLETED
	};

	private final Format _commerceOrderDateFormatDate;
	private final Format _commerceOrderDateFormatTime;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final Map<Integer, Integer> _orderStatusCounts = new HashMap<>();
	private SearchContainer<CommerceOrder> _searchContainer;
	private final WorkflowTaskManager _workflowTaskManager;

}