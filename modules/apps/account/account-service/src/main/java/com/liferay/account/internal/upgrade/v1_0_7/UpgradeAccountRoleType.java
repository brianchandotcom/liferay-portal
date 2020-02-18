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

package com.liferay.account.internal.upgrade.v1_0_7;

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
public class UpgradeAccountRoleType extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(8);

			sb.append("select distinct Role_.roleId from Role_ inner join ");
			sb.append("AccountRole on AccountRole.roleId = Role_.roleId ");
			sb.append("where Role_.classNameId = ");
			sb.append(PortalUtil.getClassNameId(AccountRole.class));
			sb.append(" and Role_.type_ = ");
			sb.append(RoleConstants.TYPE_PROVIDER);
			sb.append(" and AccountRole.accountEntryId = ");
			sb.append(AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update Role_ set type_ = " +
								RoleConstants.TYPE_ACCOUNT +
									" where roleId = ?"))) {

				try (ResultSet rs = ps1.executeQuery()) {
					while (rs.next()) {
						long roleId = rs.getLong("roleId");

						ps2.setLong(1, roleId);

						ps2.addBatch();
					}

					ps2.executeBatch();
				}
			}
		}
	}

}