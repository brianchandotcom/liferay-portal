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
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alec Sloan
 */
@Component(
	property = {
		"commerce.order.status.key=" + OnHoldCommerceOrderStatusImpl.KEY,
		"commerce.order.status.priority:Integer=" + OnHoldCommerceOrderStatusImpl.PRIORITY
	},
	service = CommerceOrderStatus.class
)
public class OnHoldCommerceOrderStatusImpl implements CommerceOrderStatus {

	public static final int KEY = CommerceOrderConstants.ORDER_STATUS_ON_HOLD;

	public static final int PRIORITY = -1;

	@Override
	public CommerceOrder doTransition(
			CommerceOrder commerceOrder, long userId, boolean secure)
		throws PortalException {

		if (commerceOrder.getOrderStatus() == KEY) {
			return _commerceOrderEngine.transitionCommerceOrder(
				commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
				userId, secure);
		}

		commerceOrder.setOrderStatus(KEY);

		if (secure) {
			commerceOrder = _commerceOrderService.updateCommerceOrder(
				commerceOrder);
		}
		else {
			commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
				commerceOrder);
		}

		return commerceOrder;
	}

	@Override
	public int getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale, CommerceOrderConstants.getOrderStatusLabel(KEY));
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public boolean isEnabled(CommerceOrder commerceOrder)
		throws PortalException {

		return !commerceOrder.isQuote();
	}

	@Override
	public boolean isTransitionCriteriaMet(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				commerceOrder.getOrderStatus());

		if ((currentCommerceOrderStatus.getKey() !=
				CommerceOrderConstants.ORDER_STATUS_CANCELLED) &&
			(currentCommerceOrderStatus.getKey() !=
				CommerceOrderConstants.ORDER_STATUS_COMPLETED) &&
			(currentCommerceOrderStatus.getKey() !=
				CommerceOrderConstants.ORDER_STATUS_OPEN)) {

			return true;
		}

		return false;
	}

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private volatile CommerceOrderLocalService _commerceOrderLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private Language _language;

}