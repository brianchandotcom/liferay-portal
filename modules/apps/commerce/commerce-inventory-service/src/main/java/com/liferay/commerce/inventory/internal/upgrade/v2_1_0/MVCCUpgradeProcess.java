/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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