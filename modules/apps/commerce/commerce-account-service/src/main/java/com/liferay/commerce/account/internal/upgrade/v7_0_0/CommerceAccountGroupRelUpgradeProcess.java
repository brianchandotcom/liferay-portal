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

package com.liferay.commerce.account.internal.upgrade.v7_0_0;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Drew Brokke
 */
public class CommerceAccountGroupRelUpgradeProcess extends UpgradeProcess {

	public CommerceAccountGroupRelUpgradeProcess(
		AccountGroupRelLocalService accountGroupRelLocalService) {

		_accountGroupRelLocalService = accountGroupRelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String selectCommerceAccountGroupRelSQL =
			"select * from CommerceAccountGroupRel order by " +
				"commerceAccountGroupRelId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(
				selectCommerceAccountGroupRelSQL);

			while (resultSet.next()) {
				long accountGroupRelId = resultSet.getLong(
					"commerceAccountGroupRelId");

				AccountGroupRel accountGroupRel =
					_accountGroupRelLocalService.createAccountGroupRel(
						accountGroupRelId);

				accountGroupRel.setCompanyId(resultSet.getLong("companyId"));
				accountGroupRel.setUserId(resultSet.getLong("userId"));
				accountGroupRel.setUserName(resultSet.getString("userName"));
				accountGroupRel.setCreateDate(
					resultSet.getTimestamp("createDate"));
				accountGroupRel.setModifiedDate(
					resultSet.getTimestamp("modifiedDate"));
				accountGroupRel.setAccountGroupId(
					resultSet.getLong("commerceAccountGroupId"));
				accountGroupRel.setClassNameId(
					resultSet.getLong("classNameId"));
				accountGroupRel.setClassPK(resultSet.getLong("classPK"));

				_accountGroupRelLocalService.addAccountGroupRel(
					accountGroupRel);
			}

			runSQL("drop table CommerceAccountGroupRel");
		}
	}

	private final AccountGroupRelLocalService _accountGroupRelLocalService;

}