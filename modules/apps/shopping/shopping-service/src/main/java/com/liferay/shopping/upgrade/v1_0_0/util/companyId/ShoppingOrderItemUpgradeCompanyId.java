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

package com.liferay.shopping.upgrade.v1_0_0.util.companyId;

import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdUtil;

/**
 * @author Cristina González
 */
public class ShoppingOrderItemUpgradeCompanyId
	implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "ShoppingOrderItem";
	}

	@Override
	public void upgradeProcess() throws Exception {
		String select =
			"select si.companyId, soi.orderItemId from ShoppingItem si, " +
				"ShoppingOrderItem soi where si.itemId=soi.itemId";

		String update =
			"update ShoppingOrderItem set companyId = ? where orderItemId = ?";

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"ShoppingOrderItem", select, update, "companyId", "orderItemId");
	}

}