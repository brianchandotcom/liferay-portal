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

package com.liferay.commerce.internal.order.status;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.order.status.key=" + ShippedCommerceOrderStatusImpl.KEY,
		"commerce.order.status.priority:Integer=" + ShippedCommerceOrderStatusImpl.PRIORITY
	},
	service = CommerceOrderStatus.class
)
public class ShippedCommerceOrderStatusImpl implements CommerceOrderStatus {

	public static final int KEY = CommerceOrderConstants.ORDER_STATUS_SHIPPED;

	public static final int PRIORITY = 60;

	@Override
	public CommerceOrder doTransition(CommerceOrder commerceOrder, long userId)
		throws PortalException {

		commerceOrder.setOrderStatus(KEY);

		if (!_commerceShippingHelper.isShippable(commerceOrder)) {
			commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
				commerceOrder, CommerceOrderConstants.ORDER_STATUS_COMPLETED,
				userId);
		}

		return _commerceOrderService.updateCommerceOrder(commerceOrder);
	}

	@Override
	public int getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale, CommerceOrderConstants.getOrderStatusLabel(KEY));
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public boolean isComplete(CommerceOrder commerceOrder) {
		if ((commerceOrder.getOrderStatus() ==
				CommerceOrderConstants.ORDER_STATUS_SHIPPED) ||
			(commerceOrder.getOrderStatus() ==
				CommerceOrderConstants.ORDER_STATUS_COMPLETED)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTransitionCriteriaMet(CommerceOrder commerceOrder)
		throws PortalException {

		boolean allOrderItemsShipped = true;

		for (CommerceOrderItem shippedCommerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			if ((shippedCommerceOrderItem.getShippedQuantity() <
					shippedCommerceOrderItem.getQuantity()) &&
				shippedCommerceOrderItem.isShippable()) {

				allOrderItemsShipped = false;
			}
		}

		if (((commerceOrder.getOrderStatus() ==
				CommerceOrderConstants.ORDER_STATUS_PROCESSING) ||
			 (commerceOrder.getOrderStatus() ==
				 CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED)) &&
			allOrderItemsShipped) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isValidForOrder(CommerceOrder commerceOrder)
		throws PortalException {

		if (!_commerceShippingHelper.isShippable(commerceOrder)) {
			return false;
		}

		return true;
	}

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

}