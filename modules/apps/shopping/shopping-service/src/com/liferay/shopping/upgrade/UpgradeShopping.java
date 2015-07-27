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

package com.liferay.shopping.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.shopping.upgrade.v1_0_0.ShoppingCartTable;
import com.liferay.shopping.upgrade.v1_0_0.ShoppingOrderItemTable;
import com.liferay.shopping.upgrade.v1_0_0.ShoppingOrderTable;

import java.sql.SQLException;

/**
 * @author Kenneth Chang
 */
public class UpgradeShopping extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type ShoppingCart itemIds TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				ShoppingCartTable.TABLE_NAME, ShoppingCartTable.TABLE_COLUMNS,
				ShoppingCartTable.TABLE_SQL_CREATE,
				ShoppingCartTable.TABLE_SQL_ADD_INDEXES);
		}

		try {
			runSQL("alter_column_type ShoppingOrder comments TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				ShoppingOrderTable.TABLE_NAME, ShoppingOrderTable.TABLE_COLUMNS,
				ShoppingOrderTable.TABLE_SQL_CREATE,
				ShoppingOrderTable.TABLE_SQL_ADD_INDEXES);
		}

		try {
			runSQL("alter_column_type ShoppingOrderItem itemId TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				ShoppingOrderItemTable.TABLE_NAME,
				ShoppingOrderItemTable.TABLE_COLUMNS,
				ShoppingOrderItemTable.TABLE_SQL_CREATE,
				ShoppingOrderItemTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}