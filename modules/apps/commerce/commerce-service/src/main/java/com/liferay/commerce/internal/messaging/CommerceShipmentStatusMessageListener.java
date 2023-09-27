/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.messaging;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	property = "destination.name=" + DestinationNames.COMMERCE_SHIPMENT_STATUS,
	service = MessageListener.class
)
public class CommerceShipmentStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObject = _jsonFactory.createJSONObject(
			String.valueOf(message.getPayload()));

		long commerceShipmentId = jsonObject.getLong("commerceShipmentId");

		List<CommerceOrder> shippedCommerceOrders =
			_commerceOrderLocalService.
				getShippedCommerceOrdersByCommerceShipmentId(
					commerceShipmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceOrder commerceOrder : shippedCommerceOrders) {
			_commerceOrderEngine.checkCommerceOrderShipmentStatus(
				commerceOrder, true);
		}
	}

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}