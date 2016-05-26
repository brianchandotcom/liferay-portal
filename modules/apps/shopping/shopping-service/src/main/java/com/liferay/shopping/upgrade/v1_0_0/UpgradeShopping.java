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

package com.liferay.shopping.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.shopping.upgrade.v1_0_0.util.ShoppingCartTable;
import com.liferay.shopping.upgrade.v1_0_0.util.ShoppingOrderItemTable;
import com.liferay.shopping.upgrade.v1_0_0.util.ShoppingOrderTable;

/**
 * @author Kenneth Chang
 */
public class UpgradeShopping extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			ShoppingCartTable.class,
			new AlterColumnType("itemIds", "TEXT null"));

		alter(
			ShoppingOrderTable.class,
			new AlterColumnType("comments", "TEXT null"));

		alter(
			ShoppingOrderItemTable.class,
			new AlterColumnType("itemId", "TEXT null"));
	}

}