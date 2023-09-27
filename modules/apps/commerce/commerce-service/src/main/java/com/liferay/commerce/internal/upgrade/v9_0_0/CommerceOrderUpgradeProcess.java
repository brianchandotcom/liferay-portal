/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v9_0_0;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian I. Kim
 */
public class CommerceOrderUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateCommerceOrder();
	}

	private List<Integer> _getCommerceShipmentStatuses(
			Connection connection, long commerceOrderId)
		throws SQLException {

		PreparedStatement preparedStatement2 = connection.prepareStatement(
			StringBundler.concat(
				"select distinct CommerceShipment.status from ",
				"CommerceShipment left join CommerceShipmentItem on ",
				"CommerceShipmentItem.commerceShipmentId = ",
				"CommerceShipment.commerceShipmentId inner join ",
				"CommerceOrderItem on CommerceOrderItem.commerceOrderItemId = ",
				"CommerceShipmentItem.commerceOrderItemId where ",
				"CommerceOrderItem.commerceOrderId = ?"));

		preparedStatement2.setLong(1, commerceOrderId);

		List<Integer> commerceShipmentStatuses = new ArrayList<>();

		try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
			while (resultSet2.next()) {
				int status = resultSet2.getInt("status");

				commerceShipmentStatuses.add(status);
			}
		}

		return commerceShipmentStatuses;
	}

	private boolean _isAllOrderItemsShipped(
			Connection connection, long commerceOrderId)
		throws Exception {

		PreparedStatement preparedStatement4 = connection.prepareStatement(
			"select shippedQuantity, quantity from CommerceOrderItem where " +
				"commerceOrderId = ?");

		preparedStatement4.setLong(1, commerceOrderId);

		try (ResultSet resultSet4 = preparedStatement4.executeQuery()) {
			while (resultSet4.next()) {
				int quantity = resultSet4.getInt(1);
				int shippedQuantity = resultSet4.getInt(2);

				if ((shippedQuantity < quantity) &&
					_isShippable(connection, commerceOrderId)) {

					return false;
				}
			}
		}

		return true;
	}

	private boolean _isShippable(Connection connection, long commerceOrderId)
		throws Exception {

		PreparedStatement preparedStatement3 = connection.prepareStatement(
			"select distinct shippable from CommerceOrderItem where " +
				"commerceOrderId = ?");

		preparedStatement3.setLong(1, commerceOrderId);

		try (ResultSet resultSet3 = preparedStatement3.executeQuery()) {
			while (resultSet3.next()) {
				boolean shippable = resultSet3.getBoolean("shippable");

				if (shippable) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _isTransitionCriteriaMetCompletedCommerceOrderStatus(
		int orderStatus, boolean shippable) {

		if ((orderStatus == CommerceOrderConstants.ORDER_STATUS_SHIPPED) ||
			((orderStatus == CommerceOrderConstants.ORDER_STATUS_PROCESSING) &&
			 !shippable)) {

			return true;
		}

		return false;
	}

	private boolean _isTransitionCriteriaMetShippedCommerceOrderStatus(
		int orderStatus, boolean allOrderItemsShipped) {

		if (((orderStatus == CommerceOrderConstants.ORDER_STATUS_PROCESSING) ||
			 (orderStatus ==
				 CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED)) &&
			allOrderItemsShipped) {

			return true;
		}

		return false;
	}

	private void _updateCommerceOrder() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select commerceOrderId, orderStatus from CommerceOrder " +
					"where orderStatus = ? or orderStatus = ?")) {

			preparedStatement1.setInt(
				1, CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED);
			preparedStatement1.setInt(
				2, CommerceOrderConstants.ORDER_STATUS_SHIPPED);

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					long commerceOrderId = resultSet1.getLong(1);

					int orderStatus = resultSet1.getInt(2);

					List<Integer> commerceShipmentStatuses =
						_getCommerceShipmentStatuses(
							connection, commerceOrderId);

					boolean shippable = _isShippable(
						connection, commerceOrderId);

					if (orderStatus ==
							CommerceOrderConstants.
								ORDER_STATUS_PARTIALLY_SHIPPED) {

						_updateShippedCommerceOrderStatus(
							commerceOrderId, orderStatus,
							_isAllOrderItemsShipped(
								connection, commerceOrderId),
							commerceShipmentStatuses);
					}
					else if (orderStatus ==
								CommerceOrderConstants.ORDER_STATUS_SHIPPED) {

						_updateCompletedCommerceOrderStatus(
							commerceOrderId, orderStatus, shippable,
							commerceShipmentStatuses);
					}
				}
			}
		}
	}

	private void _updateCompletedCommerceOrderStatus(
			long commerceOrderId, int orderStatus, boolean shippable,
			List<Integer> commerceShipmentStatuses)
		throws Exception {

		if (_isTransitionCriteriaMetCompletedCommerceOrderStatus(
				orderStatus, shippable) &&
			(commerceShipmentStatuses.size() == 1) &&
			(commerceShipmentStatuses.get(0) ==
				CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED)) {

			runSQL(
				StringBundler.concat(
					"update CommerceOrder set orderStatus = ",
					CommerceOrderConstants.ORDER_STATUS_COMPLETED,
					" where commerceOrderId = ", commerceOrderId));
		}
	}

	private void _updateShippedCommerceOrderStatus(
			long commerceOrderId, int orderStatus, boolean allOrderItemsShipped,
			List<Integer> commerceShipmentStatuses)
		throws Exception {

		if (_isTransitionCriteriaMetShippedCommerceOrderStatus(
				orderStatus, allOrderItemsShipped)) {

			runSQL(
				StringBundler.concat(
					"update CommerceOrder set orderStatus = ",
					CommerceOrderConstants.ORDER_STATUS_SHIPPED,
					" where commerceOrderId = ", commerceOrderId));

			if ((commerceShipmentStatuses.size() == 1) &&
				(commerceShipmentStatuses.get(0) ==
					CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED)) {

				runSQL(
					StringBundler.concat(
						"update CommerceOrder set orderStatus = ",
						CommerceOrderConstants.ORDER_STATUS_COMPLETED,
						" where commerceOrderId = ", commerceOrderId));
			}
		}
	}

}