/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_0_1;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pei-Jung Lan
 */
public class RoleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						StringBundler.concat(
							"select distinct Role_.roleId from Role_ inner ",
							"join AccountRole on AccountRole.roleId = ",
							"Role_.roleId where AccountRole.accountEntryId = ",
							AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
							" and Role_.classNameId = ",
							PortalUtil.getClassNameId(AccountRole.class),
							" and Role_.type_ = ",
							RoleConstants.TYPE_PROVIDER));
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection,
						"update Role_ set type_ = " +
							RoleConstants.TYPE_ACCOUNT + " where roleId = ?")) {

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

}