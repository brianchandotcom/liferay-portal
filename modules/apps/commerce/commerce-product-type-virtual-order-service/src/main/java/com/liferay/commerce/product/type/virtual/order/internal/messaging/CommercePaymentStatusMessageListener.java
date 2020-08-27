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

package com.liferay.commerce.product.type.virtual.order.internal.messaging;

import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.product.type.virtual.order.util.CommerceVirtualOrderItemChecker;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "destination.name=" + CommerceDestinationNames.PAYMENT_STATUS,
	service = MessageListener.class
)
public class CommercePaymentStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		int paymentStatus = message.getInteger("paymentStatus");

		if (paymentStatus != CommerceOrderConstants.PAYMENT_STATUS_PAID) {
			return;
		}

		long commerceOrderId = message.getLong("commerceOrderId");

		_commerceVirtualOrderItemChecker.checkCommerceVirtualOrderItems(
			commerceOrderId);
	}

	@Reference
	private CommerceVirtualOrderItemChecker _commerceVirtualOrderItemChecker;

}