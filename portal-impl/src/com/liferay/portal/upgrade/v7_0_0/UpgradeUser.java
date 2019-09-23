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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Samuel Ziemer
 */
public class UpgradeUser extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(7);

		sb.append("update Group_ set active_ = [$FALSE$] where groupId in ");
		sb.append("(select Group_.groupId from Group_ inner join User_ on ");
		sb.append("Group_.companyId = User_.companyId and Group_.classPK = ");
		sb.append("User_.userId where Group_.classNameId = (select ");
		sb.append("classNameId from ClassName_ where value = ");
		sb.append("'com.liferay.portal.kernel.model.User') and User_.status ");
		sb.append("= 5)");

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sb.toString());

		sb = new StringBundler(6);

		sb.append("update Group_ inner join User_ on Group_.companyId = ");
		sb.append("User_.companyId and Group_.classPK = User_.userId set ");
		sb.append("active_ = [$FALSE$] where Group_.classNameId = (select ");
		sb.append("classNameId from ClassName_ where value = '");
		sb.append("com.liferay.portal.kernel.model.User') and User_.status = ");
		sb.append("5");

		String sql = sb.toString();

		dbTypeToSQLMap.add(DBType.MARIADB, sql);
		dbTypeToSQLMap.add(DBType.MYSQL, sql);

		runSQL(dbTypeToSQLMap);
	}

}