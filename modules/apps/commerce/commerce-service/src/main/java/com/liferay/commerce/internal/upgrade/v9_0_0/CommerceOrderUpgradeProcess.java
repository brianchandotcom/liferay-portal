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

package com.liferay.commerce.internal.upgrade.v9_0_0;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.internal.order.status.CompletedCommerceOrderStatusImpl;
import com.liferay.commerce.internal.order.status.ShippedCommerceOrderStatusImpl;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian I. Kim
 */
public class CommerceOrderUpgradeProcess extends UpgradeProcess {

	public CommerceOrderUpgradeProcess(
		CommerceOrderLocalService commerceOrderLocalService,
		CommerceShipmentLocalService commerceShipmentLocalService,
		CommerceOrderStatusRegistry commerceOrderStatusRegistry) {

		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceShipmentLocalService = commerceShipmentLocalService;
		_commerceOrderStatusRegistry = commerceOrderStatusRegistry;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateCommerceOrder();
	}

	private void _updateCommerceOrder() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select commerceOrderId, orderStatus from CommerceOrder " +
					"where orderStatus = ? or orderStatus = ? ")) {

			preparedStatement.setInt(
				1, CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED);
			preparedStatement.setInt(
				2, CommerceOrderConstants.ORDER_STATUS_SHIPPED);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int orderStatus = resultSet.getInt(2);

					if (orderStatus ==
							CommerceOrderConstants.
								ORDER_STATUS_PARTIALLY_SHIPPED) {

						_updateShippedCommerceOrderStatus(
							_commerceOrderLocalService.getCommerceOrder(
								resultSet.getLong(1)));
					}
					else if (orderStatus ==
								CommerceOrderConstants.ORDER_STATUS_SHIPPED) {

						_updateCompletedCommerceOrderStatus(
							_commerceOrderLocalService.getCommerceOrder(
								resultSet.getLong(1)));
					}
				}
			}
		}
	}

	private void _updateCompletedCommerceOrderStatus(
			CommerceOrder commerceOrder)
		throws Exception {

		CommerceOrderStatus completedCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				CompletedCommerceOrderStatusImpl.KEY);

		int[] commerceShipmentStatuses =
			_commerceShipmentLocalService.
				getCommerceShipmentStatusesByCommerceOrderId(
					commerceOrder.getCommerceOrderId());

		if (completedCommerceOrderStatus.isTransitionCriteriaMet(
				commerceOrder) &&
			(commerceShipmentStatuses.length == 1) &&
			(commerceShipmentStatuses[0] ==
				CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED)) {

			runSQL(
				StringBundler.concat(
					"update CommerceOrder set orderStatus = ",
					CommerceOrderConstants.ORDER_STATUS_COMPLETED,
					" where commerceOrderId = ",
					commerceOrder.getCommerceOrderId()));
		}
	}

	private void _updateShippedCommerceOrderStatus(CommerceOrder commerceOrder)
		throws Exception {

		CommerceOrderStatus shippedCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				ShippedCommerceOrderStatusImpl.KEY);

		if (shippedCommerceOrderStatus.isTransitionCriteriaMet(commerceOrder)) {
			runSQL(
				StringBundler.concat(
					"update CommerceOrder set orderStatus = ",
					CommerceOrderConstants.ORDER_STATUS_SHIPPED,
					" where commerceOrderId = ",
					commerceOrder.getCommerceOrderId()));

			int[] commerceShipmentStatuses =
				_commerceShipmentLocalService.
					getCommerceShipmentStatusesByCommerceOrderId(
						commerceOrder.getCommerceOrderId());

			if ((commerceShipmentStatuses.length == 1) &&
				(commerceShipmentStatuses[0] ==
					CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED)) {

				runSQL(
					StringBundler.concat(
						"update CommerceOrder set orderStatus = ",
						CommerceOrderConstants.ORDER_STATUS_COMPLETED,
						" where commerceOrderId = ",
						commerceOrder.getCommerceOrderId()));
			}
		}
	}

	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommerceOrderStatusRegistry _commerceOrderStatusRegistry;
	private final CommerceShipmentLocalService _commerceShipmentLocalService;

}