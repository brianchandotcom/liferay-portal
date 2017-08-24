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

package com.liferay.commerce.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public CommerceOrder getCommerceOrder(RenderRequest renderRequest)
		throws PortalException {

		CommerceOrder commerceOrder = (CommerceOrder)renderRequest.getAttribute(
			CommerceWebKeys.COMMERCE_ORDER);

		if (commerceOrder != null) {
			return commerceOrder;
		}

		long commerceOrderId = ParamUtil.getLong(
			renderRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
				commerceOrderId);
		}

		if (commerceOrder != null) {
			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_ORDER, commerceOrder);
		}

		return commerceOrder;
	}

	public List<CommerceOrderItem> getCommerceOrderItems(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems = new ArrayList<>();

		long[] commerceOrderItemIds = ParamUtil.getLongValues(
			resourceRequest, RowChecker.ROW_IDS);

		for (long commerceOrderItemId : commerceOrderItemIds) {
			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemLocalService.getCommerceOrderItem(
					commerceOrderItemId);

			commerceOrderItems.add(commerceOrderItem);
		}

		return commerceOrderItems;
	}

	public List<CommerceOrder> getCommerceOrders(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CommerceOrder> commerceOrders = new ArrayList<>();

		long[] commerceOrderIds = ParamUtil.getLongValues(
			resourceRequest, RowChecker.ROW_IDS);

		for (long commerceOrderId : commerceOrderIds) {
			CommerceOrder commerceOrder =
				_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

			commerceOrders.add(commerceOrder);
		}

		return commerceOrders;
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}