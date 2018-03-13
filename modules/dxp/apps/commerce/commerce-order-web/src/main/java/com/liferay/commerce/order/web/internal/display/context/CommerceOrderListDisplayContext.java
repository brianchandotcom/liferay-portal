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

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderChecker;
import com.liferay.commerce.order.web.internal.search.CommerceOrderSearch;
import com.liferay.commerce.order.web.internal.search.facet.NegatableMultiValueFacet;
import com.liferay.commerce.order.web.internal.security.permission.resource.CommerceOrderPermission;
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommercePriceCalculationLocalService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
		CommerceOrderHelper commerceOrderHelper,
		CommerceOrderLocalService commerceOrderLocalService,
		CommerceOrderNoteService commerceOrderNoteService,
		CommerceOrganizationService commerceOrganizationService,
		CommercePriceCalculationLocalService
			commercePriceCalculationLocalService,
		CommercePriceFormatter commercePriceFormatter,
		RenderRequest renderRequest) {

		_commerceOrderHelper = commerceOrderHelper;
		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commerceOrganizationService = commerceOrganizationService;
		_commercePriceCalculationLocalService =
			commercePriceCalculationLocalService;
		_commercePriceFormatter = commercePriceFormatter;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDateTime =
			FastDateFormatFactoryUtil.getDateTime(
				DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
				themeDisplay.getTimeZone());
	}

	public long getAccountOrganizationId() {
		return ParamUtil.getLong(
			_commerceOrderRequestHelper.getRequest(), "accountOrganizationId");
	}

	public String getCommerceOrderDateTime(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatDateTime.format(
			commerceOrder.getCreateDate());
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		if (CommerceOrderPermission.contains(
				_commerceOrderRequestHelper.getPermissionChecker(),
				commerceOrder, ActionKeys.UPDATE_DISCUSSION)) {

			return _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrder.getCommerceOrderId());
		}

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId(), false);
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

		PermissionChecker permissionChecker =
			_commerceOrderRequestHelper.getPermissionChecker();

		ObjectValuePair<Long, String> approveOVP = null;

		if (commerceOrder.isOpen() && commerceOrder.isPending() &&
			CommerceOrderPermission.contains(
				permissionChecker, commerceOrder,
				CommerceOrderActionKeys.APPROVE_COMMERCE_ORDER)) {

			approveOVP = new ObjectValuePair<>(0L, "approve");

			transitionOVPs.add(approveOVP);
		}

		if (commerceOrder.isOpen() && commerceOrder.isApproved() &&
			CommerceOrderPermission.contains(
				permissionChecker, commerceOrder,
				CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "checkout"));
		}

		if (commerceOrder.isOpen() && commerceOrder.isDraft() &&
			!commerceOrder.isEmpty() &&
			CommerceOrderPermission.contains(
				permissionChecker, commerceOrder, ActionKeys.UPDATE)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "submit"));
		}

		int start = transitionOVPs.size();

		transitionOVPs.addAll(
			_commerceOrderHelper.getWorkflowTransitions(
				_commerceOrderRequestHelper.getUserId(), commerceOrder));

		if (approveOVP != null) {
			for (int i = start; i < transitionOVPs.size(); i++) {
				ObjectValuePair<Long, String> objectValuePair =
					transitionOVPs.get(i);

				String value = objectValuePair.getValue();

				if (value.equals(approveOVP.getValue())) {
					approveOVP.setValue("force-" + value);

					break;
				}
			}
		}

		return transitionOVPs;
	}

	public String getCommerceOrderValue(CommerceOrder commerceOrder)
		throws PortalException {

		double value = commerceOrder.getTotal();

		if (commerceOrder.isOpen()) {
			value = _commercePriceCalculationLocalService.getOrderSubtotal(
				commerceOrder);
		}

		return _commercePriceFormatter.format(
			commerceOrder.getSiteGroupId(), value);
	}

	public List<ManagementBarFilterItem> getManagementBarFilterItems()
		throws PortalException {

		Sort sort = SortFactoryUtil.create(Field.NAME + "_sortable", false);

		BaseModelSearchResult<Organization> baseModelSearchResult =
			_commerceOrganizationService.searchOrganizationsByGroup(
				_commerceOrderRequestHelper.getScopeGroupId(),
				_commerceOrderRequestHelper.getUserId(),
				CommerceOrganizationConstants.TYPE_ACCOUNT, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, new Sort[] {sort});

		List<Organization> organizations =
			baseModelSearchResult.getBaseModels();

		List<ManagementBarFilterItem> managementBarFilterItems =
			new ArrayList<>(organizations.size() + 1);

		managementBarFilterItems.add(_getManagementBarFilterItem(null));

		for (Organization organization : organizations) {
			managementBarFilterItems.add(
				_getManagementBarFilterItem(organization));
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
		if (_navigationItems == null) {
			_initSearch();
		}

		return _navigationItems;
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"accountOrganizationId",
			String.valueOf(getAccountOrganizationId()));
		portletURL.setParameter("tabs1", getTabs1());

		return portletURL;
	}

	public SearchContainer<CommerceOrder> getSearchContainer()
		throws PortalException {

		if (_searchContainer == null) {
			_initSearch();
		}

		return _searchContainer;
	}

	public String getTabs1() {
		return ParamUtil.getString(
			_commerceOrderRequestHelper.getRequest(), "tabs1", "pending");
	}

	private SearchContext _buildSearchContext() throws PortalException {
		SearchContext searchContext = new SearchContext();

		TabConfiguration tabConfiguration = _tabConfigurations.get(getTabs1());

		NegatableMultiValueFacet negatableMultiValueFacet =
			new NegatableMultiValueFacet(searchContext);

		negatableMultiValueFacet.setFieldName("orderStatus");
		negatableMultiValueFacet.setNegated(tabConfiguration.negated);

		searchContext.addFacet(negatableMultiValueFacet);

		searchContext.setAttribute(
			"orderStatus", StringUtil.merge(tabConfiguration.orderStatuses));

		searchContext.setAttribute(
			"siteGroupId", _commerceOrderRequestHelper.getScopeGroupId());

		searchContext.setCompanyId(_commerceOrderRequestHelper.getCompanyId());

		long accountOrganizationId = getAccountOrganizationId();

		if (accountOrganizationId > 0) {
			Organization organization =
				_commerceOrganizationService.getOrganization(
					accountOrganizationId);

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

		searchContext.setStart(_searchContainer.getStart());
		searchContext.setEnd(_searchContainer.getEnd());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Sort[] sorts = CommerceUtil.getCommerceOrderSorts(
			_searchContainer.getOrderByCol(),
			_searchContainer.getOrderByType());

		searchContext.setSorts(sorts);

		return searchContext;
	}

	private ManagementBarFilterItem _getManagementBarFilterItem(
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

	private void _initNavigationItems(SearchContext searchContext)
		throws PortalException {

		_navigationItems = new ArrayList<>(_tabConfigurations.size());

		String tabs1 = getTabs1();

		Facet facet = searchContext.getFacet("orderStatus");

		FacetCollector facetCollector = facet.getFacetCollector();

		for (Map.Entry<String, TabConfiguration> entry :
				_tabConfigurations.entrySet()) {

			String name = entry.getKey();
			TabConfiguration tabConfiguration = entry.getValue();

			int count = 0;

			for (TermCollector termCollector :
					facetCollector.getTermCollectors()) {

				int orderStatus = GetterUtil.getInteger(
					termCollector.getTerm());

				boolean match = true;

				if (ArrayUtil.isNotEmpty(tabConfiguration.orderStatuses)) {
					match = ArrayUtil.contains(
						tabConfiguration.orderStatuses, orderStatus);

					if (tabConfiguration.negated) {
						match = !match;
					}
				}

				if (match) {
					count += termCollector.getFrequency();
				}
			}

			PortletURL portletURL = getPortletURL();

			portletURL.setParameter("tabs1", name);

			String label = LanguageUtil.get(
				_commerceOrderRequestHelper.getRequest(), name);

			if (count > 0) {
				StringBundler sb = new StringBundler(4);

				sb.append(label);
				sb.append(" (");
				sb.append(count);
				sb.append(CharPool.CLOSE_PARENTHESIS);

				label = sb.toString();
			}

			NavigationItem navigationItem = new NavigationItem();

			navigationItem.setActive(name.equals(tabs1));
			navigationItem.setHref(portletURL.toString());
			navigationItem.setLabel(label);

			_navigationItems.add(navigationItem);
		}
	}

	private void _initSearch() throws PortalException {
		_searchContainer = new CommerceOrderSearch(
			_commerceOrderRequestHelper.getLiferayPortletRequest(),
			getPortletURL());

		SearchContext searchContext = _buildSearchContext();

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			_commerceOrderLocalService.searchCommerceOrders(searchContext);

		_initNavigationItems(searchContext);

		String tabs1 = getTabs1();

		if (!tabs1.equals("all")) {
			HttpServletRequest httpServletRequest =
				_commerceOrderRequestHelper.getRequest();

			String label = LanguageUtil.get(httpServletRequest, tabs1);

			_searchContainer.setEmptyResultsMessage(
				LanguageUtil.format(
					httpServletRequest, "no-x-orders-were-found",
					StringUtil.toLowerCase(label), false));
		}

		_searchContainer.setRowChecker(
			new CommerceOrderChecker(
				_commerceOrderRequestHelper.getLiferayPortletResponse()));

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());
	}

	private static final Map<String, TabConfiguration> _tabConfigurations =
		new LinkedHashMap<>();

	private final Format _commerceOrderDateFormatDateTime;
	private final CommerceOrderHelper _commerceOrderHelper;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final CommerceOrganizationService _commerceOrganizationService;
	private final CommercePriceCalculationLocalService
		_commercePriceCalculationLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private List<NavigationItem> _navigationItems;
	private SearchContainer<CommerceOrder> _searchContainer;

	private static class TabConfiguration {

		public TabConfiguration(boolean negated, int... orderStatuses) {
			this.orderStatuses = orderStatuses;
			this.negated = negated;
		}

		public final boolean negated;
		public final int[] orderStatuses;

	}

	static {
		_tabConfigurations.put(
			"pending",
			new TabConfiguration(
				false, CommerceOrderConstants.ORDER_STATUS_OPEN));
		_tabConfigurations.put("all", new TabConfiguration(false));
		_tabConfigurations.put(
			"transmitted",
			new TabConfiguration(
				true, CommerceOrderConstants.ORDER_STATUS_CANCELLED,
				CommerceOrderConstants.ORDER_STATUS_COMPLETED,
				CommerceOrderConstants.ORDER_STATUS_OPEN));
		_tabConfigurations.put(
			"completed",
			new TabConfiguration(
				false, CommerceOrderConstants.ORDER_STATUS_CANCELLED,
				CommerceOrderConstants.ORDER_STATUS_COMPLETED));
	}

}