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

package com.liferay.commerce.shipment.web.internal.display.context;

import com.liferay.commerce.configuration.CommerceShippingGroupServiceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.commerce.shipment.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.shipment.web.internal.util.CommerceShipmentPortletUtil;
import com.liferay.commerce.util.comparator.CommerceWarehouseNameComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentDisplayContext
	extends BaseCommerceShipmentDisplayContext<CommerceShipment> {

	public CommerceShipmentDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CommerceOrderItemService commerceOrderItemService,
		CommerceOrderService commerceOrderService,
		CommerceShipmentService commerceShipmentService,
		CommerceWarehouseService commerceWarehouseService,
		ConfigurationProvider configurationProvider,
		PortletResourcePermission portletResourcePermission) {

		super(
			actionHelper, httpServletRequest,
			CommerceShipment.class.getSimpleName(), portletResourcePermission);

		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderService = commerceOrderService;
		_commerceShipmentService = commerceShipmentService;
		_commerceWarehouseService = commerceWarehouseService;
		_configurationProvider = configurationProvider;
	}

	public List<CommerceOrderItem> getCommerceOrderItems(long commerceOrderId)
		throws PortalException {

		if (commerceOrderId <= 0) {
			return Collections.emptyList();
		}

		return
			_commerceOrderItemService.getAvailableForShipmentCommerceOrderItems(
				commerceOrderId);
	}

	public List<CommerceOrder> getCommerceOrders() throws PortalException {
		return _commerceOrderService.getCommerceOrders(
			cpRequestHelper.getScopeGroupId(),
			CommerceShipmentConstants.ALLOWED_ORDER_STATUSES);
	}

	public String getCommerceShipmentStatusLabel(int status) {
		return LanguageUtil.get(
			cpRequestHelper.getLocale(),
			CommerceShipmentConstants.getShipmentStatusLabel(status));
	}

	public int getCommerceWarehouseItemQuantity(
			long commerceOrderItemId, long commerceWarehouseId)
		throws PortalException {

		return _commerceOrderItemService.getCommerceWarehouseItemQuantity(
			commerceOrderItemId, commerceWarehouseId);
	}

	public List<CommerceWarehouse> getCommerceWarehouses()
		throws PortalException {

		if (_commerceWarehouses != null) {
			return _commerceWarehouses;
		}

		CommerceShippingGroupServiceConfiguration
			commerceShippingGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					CommerceShippingGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						cpRequestHelper.getScopeGroupId(),
						CommerceConstants.SHIPPING_SERVICE_NAME));

		String commerceShippingOriginLocatorKey =
			commerceShippingGroupServiceConfiguration.
				commerceShippingOriginLocatorKey();

		if (commerceShippingOriginLocatorKey.equals("address")) {
			CommerceWarehouse commerceWarehouse =
				_commerceWarehouseService.fetchDefaultCommerceWarehouse(
					cpRequestHelper.getScopeGroupId());

			if (commerceWarehouse == null) {
				_commerceWarehouses = Collections.emptyList();
			}
			else {
				_commerceWarehouses = Collections.singletonList(
					commerceWarehouse);
			}

			return _commerceWarehouses;
		}

		_commerceWarehouses = _commerceWarehouseService.getCommerceWarehouses(
			cpRequestHelper.getScopeGroupId(), true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new CommerceWarehouseNameComparator(true));

		return _commerceWarehouses;
	}

	public String getNavigation() {
		return ParamUtil.getString(
			cpRequestHelper.getRequest(), "navigation", "all");
	}

	public String[] getNavigationKeys() {
		int[] shipmentStatuses = CommerceShipmentConstants.SHIPMENT_STATUSES;

		String[] navigationKeys = new String[0];

		navigationKeys = ArrayUtil.append(navigationKeys, "all");

		for (int shipmentStatus : shipmentStatuses) {
			navigationKeys = ArrayUtil.append(
				navigationKeys,
				CommerceShipmentConstants.getShipmentStatusLabel(
					shipmentStatus));
		}

		return navigationKeys;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("navigation", getNavigation());

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceShipment> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceShipment> orderByComparator =
			CommerceShipmentPortletUtil.getCommerceShipmentOrderByComparator(
				getOrderByCol(), getOrderByType());

		String emptyResultsMessage = "no-shipments-were-found";

		String navigation = getNavigation();

		if (!navigation.equals("all")) {
			emptyResultsMessage = LanguageUtil.format(
				cpRequestHelper.getRequest(), "no-x-shipments-were-found",
				navigation, true);
		}

		searchContainer.setEmptyResultsMessage(emptyResultsMessage);
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total;
		List<CommerceShipment> results;

		Integer shipmentStatus = CommerceShipmentConstants.getShipmentStatus(
			navigation);

		if (!navigation.equals("all") && (shipmentStatus != null)) {
			total = _commerceShipmentService.getCommerceShipmentsCountByS_S(
				cpRequestHelper.getScopeGroupId(), shipmentStatus);
			results = _commerceShipmentService.getCommerceShipmentsByS_S(
				cpRequestHelper.getScopeGroupId(), shipmentStatus,
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);
		}
		else {
			total =
				_commerceShipmentService.getCommerceShipmentsCountBySiteGroupId(
					cpRequestHelper.getScopeGroupId());
			results =
				_commerceShipmentService.getCommerceShipmentsBySiteGroupId(
					cpRequestHelper.getScopeGroupId(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderService _commerceOrderService;
	private final CommerceShipmentService _commerceShipmentService;
	private List<CommerceWarehouse> _commerceWarehouses;
	private final CommerceWarehouseService _commerceWarehouseService;
	private final ConfigurationProvider _configurationProvider;

}