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

package com.liferay.commerce.inventory.internal.upgrade.v2_5_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Brian I. Kim
 */
public class CommerceInventoryReplenishmentItemUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement s = connection.createStatement();

			 ResultSet resultSet = s.executeQuery(StringBundler.concat(
				 "select CIReplenishmentItemId from CIReplenishmentItem left ",
				 "join CIWarehouseItem on CIReplenishmentItem.companyId = ",
				 "CIWarehouseItem.companyId and CIReplenishmentItem.sku = ",
				 "CIWarehouseItem.sku and ",
				 "CIReplenishmentItem.commerceInventoryWarehouseId = ",
				 "CIWarehouseItem.commerceInventoryWarehouseId where ",
				 "CIWarehouseItem.sku is null"))) {

			while (resultSet.next()) {
				long commerceInventoryReplenishmentItemId = resultSet.getLong(
					"CIReplenishmentItemId");

				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format(
							"Removing CIReplenishmentItem entry id: %s",
							commerceInventoryReplenishmentItemId));
				}

				runSQL(
					String.format(
						_DELETE_CIREPLENISHMENTITEM_SQL,
						commerceInventoryReplenishmentItemId));
			}
		}
	}

	private static final String _DELETE_CIREPLENISHMENTITEM_SQL =
		"delete from CIReplenishmentItem where CIReplenishmentItemId = %d";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryReplenishmentItemUpgradeProcess.class);

}