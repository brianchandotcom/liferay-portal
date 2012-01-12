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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v5_2_3.util.UserTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeUser extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type User_ greeting VARCHAR(255) null");
		}
		else {

			// User_

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				UserTable.TABLE_NAME, UserTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(UserTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(UserTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}

		StringBundler sb = new StringBundler(9);

		sb.append("update User_ set firstName = (select Contact_.firstName ");
		sb.append("from Contact_ where Contact_.contactId = ");
		sb.append("User_.contactId), middleName = (select ");
		sb.append("Contact_.middleName from Contact_ where ");
		sb.append("Contact_.contactId = User_.contactId), lastName = ");
		sb.append("(select Contact_.lastName from Contact_ where ");
		sb.append("Contact_.contactId = User_.contactId), jobTitle = (select ");
		sb.append("Contact_.jobTitle from Contact_ where ");
		sb.append("Contact_.contactId = User_.contactId)");

		runSQL(sb.toString());
	}

}