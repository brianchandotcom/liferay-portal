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

package com.liferay.commerce.internal.upgrade.v4_10_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_10_0.util.CommerceOrderItemTable;

/**
 * @author Luca Pellizzon
 */
public class CommerceOrderItemUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"deliveryMaxSubscriptionCycles", "LONG");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"deliverySubscriptionLength", "INTEGER");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"deliverySubscriptionType", "VARCHAR(75)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"deliverySubTypeSettings", "VARCHAR(75)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"depth", "DOUBLE");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"freeShipping", "BOOLEAN");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"height", "DOUBLE");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"maxSubscriptionCycles", "LONG");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"shipSeparately", "BOOLEAN");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"shippable", "BOOLEAN");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"shippingExtraPrice", "DOUBLE");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"subscriptionLength", "INTEGER");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"subscriptionType", "VARCHAR(75)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"subscriptionTypeSettings", "VARCHAR(75)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"weight", "DOUBLE");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"width", "DOUBLE");

		String updateCommerceOrderItemSQL = StringBundler.concat(
			"UPDATE CommerceOrderItem SET shippable = ?, freeShipping = ?, ",
			"shipSeparately = ?, shippingExtraPrice = ?, width = ?, height = ",
			"?, depth = ?, weight = ?, subscriptionLength = ?, ",
			"subscriptionType = ?, subscriptionTypeSettings = ?, ",
			"maxSubscriptionCycles = ?, deliverySubscriptionLength = ?, ",
			"deliverySubscriptionType = ?, deliverySubTypeSettings = ?, ",
			"deliveryMaxSubscriptionCycles = ? WHERE CPInstanceId = ? AND ",
			"commerceOrderItemId = ?");

		String getCPInstanceSQL = StringBundler.concat(
			"SELECT  CPInstance.CPInstanceId, CPDefinition.shippable, ",
			"CPDefinition.freeShipping, CPDefinition.shipSeparately, ",
			"CPDefinition.shippingExtraPrice, CPDefinition.width, ",
			"CPDefinition.height, CPDefinition.depth, CPDefinition.weight, ",
			"CPDefinition.subscriptionLength, CPDefinition.subscriptionType, ",
			"CPDefinition.subscriptionTypeSettings, ",
			"CPDefinition.maxSubscriptionCycles, ",
			"CPDefinition.deliverySubscriptionLength, ",
			"CPDefinition.deliverySubscriptionType, ",
			"CPDefinition.deliverySubTypeSettings, ",
			"CPDefinition.deliveryMaxSubscriptionCycles, ",
			"CPInstance.overrideSubscriptionInfo, CPInstance.width, ",
			"CPInstance.height, CPInstance.depth, CPInstance.weight, ",
			"CPInstance.subscriptionLength, CPInstance.subscriptionType, ",
			"CPInstance.subscriptionTypeSettings, ",
			"CPInstance.maxSubscriptionCycles, ",
			"CPInstance.deliverySubscriptionLength, ",
			"CPInstance.deliverySubscriptionType, ",
			"CPInstance.deliverySubTypeSettings, ",
			"CPInstance.deliveryMaxSubscriptionCycles, ",
			"CommerceOrderItem.commerceOrderItemId FROM CPInstance JOIN ",
			"CPDefinition ON CPInstance.CPDefinitionId = ",
			"CPDefinition.CPDefinitionId JOIN CommerceOrderItem ON ",
			"CPInstance.CPInstanceId = CommerceOrderItem.CPInstanceId JOIN ",
			"CommerceOrder ON CommerceOrder.commerceOrderId = ",
			"CommerceOrderItem.commerceOrderId AND CommerceOrder.orderStatus ",
			"= 2");

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCommerceOrderItemSQL);
			Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s.executeQuery(getCPInstanceSQL)) {

			while (rs.next()) {
				long cpInstanceId = rs.getLong(1);
				boolean shippable = rs.getBoolean(2);
				boolean freeShipping = rs.getBoolean(3);
				boolean shipSeparately = rs.getBoolean(4);
				double shippingExtraPrice = rs.getDouble(5);

				double width = rs.getDouble(19);

				if (width <= 0) {
					width = rs.getDouble(6);
				}

				double height = rs.getDouble(20);

				if (height <= 0) {
					height = rs.getDouble(7);
				}

				double depth = rs.getDouble(21);

				if (depth <= 0) {
					depth = rs.getDouble(8);
				}

				double weight = rs.getDouble(22);

				if (weight <= 0) {
					weight = rs.getDouble(9);
				}

				int subscriptionLength = rs.getInt(10);
				String subscriptionType = rs.getString(11);
				String subscriptionTypeSettings = rs.getString(12);
				long maxSubscriptionCycles = rs.getLong(13);
				int deliverySubscriptionLength = rs.getInt(14);
				String deliverySubscriptionType = rs.getString(15);
				String deliverySubscriptionTypeSettings = rs.getString(16);
				long deliveryMaxSubscriptionCycles = rs.getLong(17);

				boolean overrideSubscription = rs.getBoolean(18);

				if (overrideSubscription) {
					subscriptionLength = rs.getInt(23);
					subscriptionType = rs.getString(24);
					subscriptionTypeSettings = rs.getString(25);
					maxSubscriptionCycles = rs.getLong(26);
					deliverySubscriptionLength = rs.getInt(27);
					deliverySubscriptionType = rs.getString(28);
					deliverySubscriptionTypeSettings = rs.getString(29);
					deliveryMaxSubscriptionCycles = rs.getLong(30);
				}

				long commerceOrderItemId = rs.getLong(31);

				ps1.setBoolean(1, shippable);
				ps1.setBoolean(2, freeShipping);
				ps1.setBoolean(3, shipSeparately);
				ps1.setDouble(4, shippingExtraPrice);
				ps1.setDouble(5, width);
				ps1.setDouble(6, height);
				ps1.setDouble(7, depth);
				ps1.setDouble(8, weight);
				ps1.setInt(9, subscriptionLength);
				ps1.setString(10, subscriptionType);
				ps1.setString(11, subscriptionTypeSettings);
				ps1.setLong(12, maxSubscriptionCycles);
				ps1.setInt(13, deliverySubscriptionLength);
				ps1.setString(14, deliverySubscriptionType);
				ps1.setString(15, deliverySubscriptionTypeSettings);
				ps1.setLong(16, deliveryMaxSubscriptionCycles);
				ps1.setLong(17, cpInstanceId);
				ps1.setLong(18, commerceOrderItemId);

				ps1.addBatch();
			}

			ps1.executeBatch();
		}
	}

}