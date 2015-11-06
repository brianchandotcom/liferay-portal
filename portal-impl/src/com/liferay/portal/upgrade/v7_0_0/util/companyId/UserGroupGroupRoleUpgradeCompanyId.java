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

package com.liferay.portal.upgrade.v7_0_0.util.companyId;

import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdUtil;

/**
 * @author Cristina González
 */
public class UserGroupGroupRoleUpgradeCompanyId
	implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "UserGroupGroupRole";
	}

	@Override
	public void upgradeProcess() throws Exception {
		String select =
			"select g.companyId, uggr.userGroupId, uggr.groupId, uggr.roleId " +
				"from Group_ g,  UserGroupGroupRole uggr " +
				"where g.groupId=uggr.groupId;";

		String update =
			"update UserGroupGroupRole set companyId = ? " +
				"where userGroupId = ? and groupId = ? and roleId = ?";

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"UserGroupGroupRole", select, update, "companyId", "userGroupId",
			"groupId", "roleId");
	}

}