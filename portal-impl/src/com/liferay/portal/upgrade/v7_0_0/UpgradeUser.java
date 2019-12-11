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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Samuel Ziemer
 */
public class UpgradeUser extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = null;

		DB db = DBManagerUtil.getDB();

		if ((db.getDBType() == DBType.MARIADB) ||
			(db.getDBType() == DBType.MYSQL)) {

			sb = new StringBundler(7);

			sb.append("update Group_ inner join User_ on Group_.companyId = ");
			sb.append("User_.companyId and Group_.classPK = User_.userId set ");
			sb.append("active_ = [$FALSE$] where Group_.classNameId = ");
			sb.append("(select classNameId from ClassName_ where value = '");
			sb.append("com.liferay.portal.kernel.model.User') and ");
			sb.append("User_.status = 5");
		}
		else {
			sb = new StringBundler(9);

			sb.append("update Group_ set active_ = [$FALSE$] where groupId ");
			sb.append("in (select Group_.groupId from Group_ inner join ");
			sb.append("User_ on Group_.companyId = User_.companyId and ");
			sb.append("Group_.classPK = User_.userId where ");
			sb.append("Group_.classNameId = (select classNameId from ");
			sb.append("ClassName_ where value = ");
			sb.append("'com.liferay.portal.kernel.model.User') and ");
			sb.append("User_.status = 5)");
		}

		runSQL(sb.toString());
	}

}