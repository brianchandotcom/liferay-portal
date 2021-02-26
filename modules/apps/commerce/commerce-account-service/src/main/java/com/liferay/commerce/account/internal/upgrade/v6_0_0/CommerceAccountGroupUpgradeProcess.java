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

package com.liferay.commerce.account.internal.upgrade.v6_0_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Drew Brokke
 */
public class CommerceAccountGroupUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String selectCommerceAccountGroupSQL =
			"select * from CommerceAccountGroup order by " +
				"commerceAccountGroupId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet rs = selectStatement.executeQuery(
				selectCommerceAccountGroupSQL);

			while (rs.next()) {
				boolean system = rs.getBoolean("system_");

				if (system) {
					continue;
				}

				long accountGroupId = rs.getLong("commerceAccountGroupId");

				AccountGroup accountGroup =
					AccountGroupLocalServiceUtil.createAccountGroup(
						accountGroupId);

				accountGroup.setCompanyId(rs.getLong("companyId"));
				accountGroup.setCreateDate(rs.getTimestamp("createDate"));
				accountGroup.setDefaultAccountGroup(system);
				accountGroup.setExternalReferenceCode(
					rs.getString("externalReferenceCode"));
				accountGroup.setUserId(rs.getLong("userId"));
				accountGroup.setUserName(rs.getString("userName"));
				accountGroup.setModifiedDate(rs.getTimestamp("modifiedDate"));
				accountGroup.setName(rs.getString("name"));
				accountGroup.setType(
					CommerceAccountGroupImpl.toAccountGroupType(
						rs.getInt("type_")));

				AccountGroupLocalServiceUtil.addAccountGroup(accountGroup);
			}

			runSQL("drop table CommerceAccountGroup");
		}
	}

}