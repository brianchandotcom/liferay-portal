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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Cristina González
 */
public class UpgradeSharding extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("alter table ShoppingItemField add companyId LONG;");

		_updateCompanyId("ShoppingItemField");

		runSQL("alter table ShoppingItemPrice add companyId LONG;");

		_updateCompanyId("ShoppingItemPrice");

		runSQL("alter table ShoppingOrderItem add companyId LONG;");

		_updateCompanyId("ShoppingOrderItem");
	}

	private void _updateCompanyId(String tableName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update " + tableName + " set companyId = ?");

			ps.setLong(1, 0);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}