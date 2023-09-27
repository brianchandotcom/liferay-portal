/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_0_3;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pei-Jung Lan
 */
public class RoleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update Role_ set type_ = ", RoleConstants.TYPE_ORGANIZATION,
				" where name = '",
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER, "'"));

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select distinct Role_.roleId from Role_ where name = '" +
					AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER +
						"'");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from ResourcePermission where roleId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long roleId = resultSet.getLong("roleId");

					preparedStatement2.setLong(1, roleId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}