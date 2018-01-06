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

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipment.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.shipment.web.internal.util.CommerceShipmentPortletUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentDisplayContext
	extends BaseCommerceShipmentDisplayContext<CommerceShipment> {

	public CommerceShipmentDisplayContext(
			ActionHelper actionHelper,
			CommerceShippingMethodLocalService
				commerceShippingMethodLocalService,
			HttpServletRequest httpServletRequest,
			CommerceAddressService commerceAddressService,
			CommerceShipmentService commerceShipmentService,
			UserLocalService userLocalService)
		throws PortalException {

		super(
			actionHelper, commerceShippingMethodLocalService,
			httpServletRequest, CommerceShipment.class.getSimpleName());

		_commerceAddressService = commerceAddressService;
		_commerceShipmentService = commerceShipmentService;
		_userLocalService = userLocalService;
	}

	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceAddressService.getCommerceAddresses(
			themeDisplay.getScopeGroupId(), User.class.getName(),
			getShipmentUserId());
	}

	public long getCommerceAddressId() throws PortalException {
		long commerceAddressId = 0;

		CommerceShipment commerceShipment = getCommerceShipment();

		if (commerceShipment != null) {
			commerceAddressId = commerceShipment.getCommerceAddressId();
		}

		return commerceAddressId;
	}

	@Override
	public SearchContainer<CommerceShipment> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceShipment> orderByComparator =
			CommerceShipmentPortletUtil.getCommerceShipmentOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setEmptyResultsMessage("no-shipments-were-found");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceShipmentService.getCommerceShipmentsCount(
			themeDisplay.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CommerceShipment> results =
			_commerceShipmentService.getCommerceShipments(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	public long getShipmentUserId() throws PortalException {
		long shipmentUserId = 0;

		CommerceShipment commerceShipment = getCommerceShipment();

		if (commerceShipment != null) {
			shipmentUserId = commerceShipment.getShipmentUserId();
		}

		return shipmentUserId;
	}

	public List<User> getUsers() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _userLocalService.getGroupUsers(
			themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	private final CommerceAddressService _commerceAddressService;
	private final CommerceShipmentService _commerceShipmentService;
	private final UserLocalService _userLocalService;

}