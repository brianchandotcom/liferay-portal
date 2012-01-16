/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v6_0_0.util.ShoppingItemTable;

/**
 * @author Alexander Chow
 */
public class UpgradeShopping extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update ShoppingItem set groupId = (select groupId from ");
		sb.append("ShoppingCategory where ShoppingCategory.categoryId = ");
		sb.append("ShoppingItem.categoryId)");

		runSQL(sb.toString());

		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type ShoppingItem smallImageURL STRING null");
			runSQL("alter_column_type ShoppingItem mediumImageURL STRING null");
			runSQL("alter_column_type ShoppingItem largeImageURL STRING null");
		}
		else {
			upgradeTable(
				ShoppingItemTable.TABLE_NAME, ShoppingItemTable.TABLE_COLUMNS,
				ShoppingItemTable.TABLE_SQL_CREATE,
				ShoppingItemTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}