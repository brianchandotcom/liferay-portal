/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_11_1;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Lianne Louie
 */
public class AccountRoleResourceUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateResourceAction(
			AccountActionKeys.UPDATE_ORGANIZATIONS, "EDIT_ORGANIZATIONS");
		_updateResourceAction(
			ActionKeys.UPDATE_SUBORGANIZATIONS, "EDIT_SUBORGANIZATIONS");
		_updateResourceAction(
			AccountActionKeys.UPDATE_SUBORGANIZATIONS_ACCOUNTS,
			"EDIT_SUBORGANIZATIONS_ACCOUNTS");
	}

	private boolean _hasResourceAction(String name) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from ResourceAction where actionId = ?")) {

			preparedStatement.setString(1, name);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					if (resultSet.getInt(1) > 0) {
						return true;
					}

					return false;
				}

				return false;
			}
		}
	}

	private void _updateResourceAction(String newName, String oldName)
		throws Exception {

		if (!_hasResourceAction(oldName)) {
			return;
		}

		if (_hasResourceAction(newName)) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"delete from ResourceAction where actionId = ?")) {

				preparedStatement.setString(1, newName);

				preparedStatement.executeUpdate();
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ResourceAction set actionId = ? where actionId = ?")) {

			preparedStatement.setString(1, newName);
			preparedStatement.setString(2, oldName);

			preparedStatement.executeUpdate();
		}
	}

}