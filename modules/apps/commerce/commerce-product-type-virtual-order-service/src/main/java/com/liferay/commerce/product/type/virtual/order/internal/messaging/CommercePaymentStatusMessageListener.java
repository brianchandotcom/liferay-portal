/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.internal.messaging;

import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.product.type.virtual.order.util.CommerceVirtualOrderItemChecker;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "destination.name=" + DestinationNames.COMMERCE_PAYMENT_STATUS,
	service = MessageListener.class
)
public class CommercePaymentStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObject = (JSONObject)message.getPayload();

		int paymentStatus = jsonObject.getInt("paymentStatus");

		if (paymentStatus != CommerceOrderPaymentConstants.STATUS_COMPLETED) {
			return;
		}

		long commerceOrderId = jsonObject.getLong("commerceOrderId");

		_commerceVirtualOrderItemChecker.checkCommerceVirtualOrderItems(
			commerceOrderId);
	}

	@Reference
	private CommerceVirtualOrderItemChecker _commerceVirtualOrderItemChecker;

}