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

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.shipment.web.internal.portlet.action.ActionHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemDisplayContext
	extends BaseCommerceShipmentDisplayContext<CommerceShipmentItem> {

	public CommerceShipmentItemDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CommerceOrderItemService commerceOrderItemService,
		CommerceShipmentItemService commerceShipmentItemService,
		PortletResourcePermission portletResourcePermission) {

		super(actionHelper, httpServletRequest, portletResourcePermission);

		_commerceOrderItemService = commerceOrderItemService;
		_commerceShipmentItemService = commerceShipmentItemService;
	}

	public CommerceOrderItem getCommerceOrderItem() throws PortalException {
		CommerceShipmentItem commerceShipmentItem = getCommerceShipmentItem();

		if (commerceShipmentItem == null) {
			return null;
		}

		return _commerceOrderItemService.getCommerceOrderItem(
			commerceShipmentItem.getCommerceOrderItemId());
	}

	@Override
	public CommerceShipment getCommerceShipment() throws PortalException {
		CommerceShipmentItem commerceShipmentItem = getCommerceShipmentItem();

		if (commerceShipmentItem == null) {
			return null;
		}

		return commerceShipmentItem.getCommerceShipment();
	}

	public CommerceShipmentItem getCommerceShipmentItem()
		throws PortalException {

		if (_commerceShipmentItem != null) {
			return _commerceShipmentItem;
		}

		_commerceShipmentItem = actionHelper.getCommerceShipmentItem(
			cpRequestHelper.getRenderRequest());

		return _commerceShipmentItem;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_shipment/edit_commerce_shipment");

		return portletURL;
	}

	public int getToSendQuantity() throws PortalException {
		CommerceOrderItem commerceOrderItem = getCommerceOrderItem();

		return _commerceShipmentItemService.
			getCommerceShipmentOrderItemsQuantity(
				getCommerceShipmentId(),
				commerceOrderItem.getCommerceOrderItemId());
	}

	private final CommerceOrderItemService _commerceOrderItemService;
	private CommerceShipmentItem _commerceShipmentItem;
	private final CommerceShipmentItemService _commerceShipmentItemService;

}