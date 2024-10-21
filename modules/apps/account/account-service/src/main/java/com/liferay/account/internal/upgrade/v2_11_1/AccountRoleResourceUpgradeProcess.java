/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_11_1;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Lianne Louie
 */
public class AccountRoleResourceUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updatePermission(
			AccountActionKeys.UPDATE_ORGANIZATIONS, "EDIT_ORGANIZATIONS");
		_updatePermission(
			ActionKeys.UPDATE_SUBORGANIZATIONS, "EDIT_SUBORGANIZATIONS");
		_updatePermission(
			AccountActionKeys.UPDATE_SUBORGANIZATIONS_ACCOUNTS,
			"EDIT_SUBORGANIZATIONS_ACCOUNTS");
	}

	private void _updatePermission(String newName, String oldName)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ResourceAction set actionId = ? where actionId = ?")) {

			preparedStatement.setString(1, newName);
			preparedStatement.setString(2, oldName);

			preparedStatement.executeUpdate();
		}
	}

}