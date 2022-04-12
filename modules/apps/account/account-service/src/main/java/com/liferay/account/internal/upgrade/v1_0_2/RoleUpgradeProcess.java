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

package com.liferay.account.internal.upgrade.v1_0_2;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Pei-Jung Lan
 */
public class RoleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from Role_ where name = '" +
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR +
					"'");

		_updateRole(
			"Account Power User",
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);
		_updateRole(
			"Account Owner",
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);
		_updateRole(
			"Account User",
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER);
	}

	private void _updateRole(String oldName, String newName) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Role_ set name = ?, title = NULL where name = ?")) {

			preparedStatement.setString(1, newName);
			preparedStatement.setString(2, oldName);

			preparedStatement.executeUpdate();
		}
	}

}