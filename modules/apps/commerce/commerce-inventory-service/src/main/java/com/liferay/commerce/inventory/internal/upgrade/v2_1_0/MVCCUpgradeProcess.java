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

package com.liferay.commerce.inventory.internal.upgrade.v2_1_0;

import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryBookedQuantityTable;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryReplenishmentItemTable;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryWarehouseItemTable;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryWarehouseTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luca Pellizzon
 */
public class MVCCUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable(CommerceInventoryBookedQuantityTable.TABLE_NAME)) {
			alterTableAddColumn(
				"CIBookedQuantity", "mvccVersion", "LONG default 0 not null");
		}

		if (hasTable(CommerceInventoryReplenishmentItemTable.TABLE_NAME)) {
			alterTableAddColumn(
				"CIReplenishmentItem", "mvccVersion",
				"LONG default 0 not null");
		}

		if (hasTable(CommerceInventoryWarehouseTable.TABLE_NAME)) {
			alterTableAddColumn(
				"CIWarehouse", "mvccVersion", "LONG default 0 not null");
		}

		if (hasTable(CommerceInventoryWarehouseItemTable.TABLE_NAME)) {
			alterTableAddColumn(
				"CIWarehouseItem", "mvccVersion", "LONG default 0 not null");
		}
	}

}