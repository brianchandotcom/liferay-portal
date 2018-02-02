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
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
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
			CommerceOrganizationHelper commerceOrganizationHelper,
			CommerceOrganizationService commerceOrganizationService,
			CommercePriceFormatter commercePriceFormatter,
			RenderRequest renderRequest,
			WorkflowTaskManager workflowTaskManager)
		throws PortalException {

		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commerceOrganizationHelper = commerceOrganizationHelper;
		_commerceOrganizationService = commerceOrganizationService;
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

		_searchContainer = initSearchContainer();
	}

	public long getAccountOrganizationId() {
		return ParamUtil.getLong(
			_commerceOrderRequestHelper.getRequest(), "accountOrganizationId");
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

	public List<ManagementBarFilterItem> getManagementBarFilterItems()
		throws PortalException {

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		Group group = themeDisplay.getScopeGroup();

		Organization siteOrganization =
			_commerceOrganizationService.getOrganization(
				group.getOrganizationId());

		Sort sort = SortFactoryUtil.create(Field.NAME + "_sortable", false);

		BaseModelSearchResult<Organization> baseModelSearchResult =
			_commerceOrganizationService.searchOrganizations(
				siteOrganization.getOrganizationId(), null,
				CommerceOrganizationConstants.TYPE_ACCOUNT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new Sort[] {sort});

		List<Organization> organizations =
			baseModelSearchResult.getBaseModels();

		List<ManagementBarFilterItem> managementBarFilterItems =
			new ArrayList<>(organizations.size() + 1);

		managementBarFilterItems.add(getManagementBarFilterItem(null));

		for (Organization organization : organizations) {
			managementBarFilterItems.add(
				getManagementBarFilterItem(organization));
		}

		return managementBarFilterItems;
	}

	public String getManagementBarFilterValue() throws PortalException {
		long organizationId = getAccountOrganizationId();

		if (organizationId > 0) {
			Organization organization =
				_commerceOrganizationService.getOrganization(organizationId);

			return organization.getName();
		}

		return "all";
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		List<NavigationItem> navigationItems = new ArrayList<>();

		int orderStatus = getOrderStatus();

		for (int curOrderStatus : _ORDER_STATUSES) {
			int curCount = _orderStatusCounts.get(curOrderStatus);

			PortletURL orderStatusURL = getPortletURL();

			orderStatusURL.setParameter(
				"orderStatus", String.valueOf(curOrderStatus));

			NavigationItem statusNavigationItem = new NavigationItem();

			statusNavigationItem.setActive(curOrderStatus == orderStatus);
			statusNavigationItem.setHref(orderStatusURL.toString());
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
			"accountOrganizationId",
			String.valueOf(getAccountOrganizationId()));
		portletURL.setParameter(
			"orderStatus", String.valueOf(getOrderStatus()));

		return portletURL;
	}

	public SearchContainer<CommerceOrder> getSearchContainer() {
		return _searchContainer;
	}

	public boolean isShowManagementBarFilter() {
		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return true;
		}

		return false;
	}

	protected SearchContext buildSearchContext(
			int orderStatus, int start, int end, String orderByCol,
			String orderByType)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName("orderStatus");

		searchContext.addFacet(facet);

		if (orderStatus != CommerceOrderConstants.ORDER_STATUS_ANY) {
			searchContext.setAttribute(
				"orderStatus", String.valueOf(orderStatus));
		}

		searchContext.setAttribute(
			"siteGroupId", _commerceOrderRequestHelper.getScopeGroupId());

		searchContext.setCompanyId(_commerceOrderRequestHelper.getCompanyId());

		Organization organization = null;

		if (isShowManagementBarFilter()) {
			long accountOrganizationId = getAccountOrganizationId();

			if (accountOrganizationId > 0) {
				organization = _commerceOrganizationService.getOrganization(
					accountOrganizationId);
			}
		}
		else {
			organization = _commerceOrganizationHelper.getCurrentOrganization(
				_commerceOrderRequestHelper.getRequest());
		}

		if (organization != null) {
			List<Organization> descendantOrganizations =
				organization.getDescendants();

			long[] groupIds = new long[descendantOrganizations.size() + 1];

			groupIds[0] = organization.getGroupId();

			for (int i = 0; i < descendantOrganizations.size(); i++) {
				Organization descendantOrganization =
					descendantOrganizations.get(i);

				groupIds[i + 1] = descendantOrganization.getGroupId();
			}

			searchContext.setGroupIds(groupIds);
		}

		searchContext.setStart(start);
		searchContext.setEnd(end);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Sort[] sorts = CommerceUtil.getCommerceOrderSorts(
			orderByCol, orderByType);

		searchContext.setSorts(sorts);

		return searchContext;
	}

	protected ManagementBarFilterItem getManagementBarFilterItem(
			Organization organization)
		throws PortalException {

		String label = "all";
		long organizationId = 0;

		if (organization != null) {
			label = organization.getName();
			organizationId = organization.getOrganizationId();
		}

		boolean active = false;

		if (organizationId == getAccountOrganizationId()) {
			active = true;
		}

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter(
			"accountOrganizationId", String.valueOf(organizationId));

		return new ManagementBarFilterItem(
			active, String.valueOf(organizationId), label,
			portletURL.toString());
	}

	protected SearchContainer<CommerceOrder> initSearchContainer()
		throws PortalException {

		SearchContainer<CommerceOrder> searchContainer =
			new CommerceOrderSearch(
				_commerceOrderRequestHelper.getLiferayPortletRequest(),
				getPortletURL());

		int orderStatus = getOrderStatus();

		SearchContext searchContext = buildSearchContext(
			orderStatus, searchContainer.getStart(), searchContainer.getEnd(),
			searchContainer.getOrderByCol(), searchContainer.getOrderByType());

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			_commerceOrderLocalService.searchCommerceOrders(searchContext);

		Facet facet = searchContext.getFacet("orderStatus");

		FacetCollector facetCollector = facet.getFacetCollector();

		int anyCount = 0;

		for (int curOrderStatus : _ORDER_STATUSES) {
			if (curOrderStatus == CommerceOrderConstants.ORDER_STATUS_ANY) {
				continue;
			}

			TermCollector termCollector = facetCollector.getTermCollector(
				String.valueOf(curOrderStatus));

			if (termCollector != null) {
				int curCount = termCollector.getFrequency();

				_orderStatusCounts.put(curOrderStatus, curCount);

				anyCount += curCount;
			}
		}

		_orderStatusCounts.put(
			CommerceOrderConstants.ORDER_STATUS_ANY, anyCount);

		if (orderStatus != CommerceOrderConstants.ORDER_STATUS_ANY) {
			HttpServletRequest httpServletRequest =
				_commerceOrderRequestHelper.getRequest();

			String orderStatusLabel = LanguageUtil.get(
				httpServletRequest,
				CommerceOrderConstants.getOrderStatusLabel(orderStatus));

			searchContainer.setEmptyResultsMessage(
				LanguageUtil.format(
					httpServletRequest, "no-x-orders-were-found",
					StringUtil.toLowerCase(orderStatusLabel), false));
		}

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceOrderRequestHelper.getLiferayPortletResponse()));

		searchContainer.setTotal(baseModelSearchResult.getLength());
		searchContainer.setResults(baseModelSearchResult.getBaseModels());

		return searchContainer;
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
	private final CommerceOrganizationHelper _commerceOrganizationHelper;
	private final CommerceOrganizationService _commerceOrganizationService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final Map<Integer, Integer> _orderStatusCounts = new HashMap<>();
	private final SearchContainer<CommerceOrder> _searchContainer;
	private final WorkflowTaskManager _workflowTaskManager;

}