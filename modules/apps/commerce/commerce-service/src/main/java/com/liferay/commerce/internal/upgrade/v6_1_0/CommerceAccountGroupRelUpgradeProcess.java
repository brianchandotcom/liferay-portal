/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v6_1_0;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

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
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("CommerceAccountGroupRel")
		};
	}

	private final AccountGroupRelLocalService _accountGroupRelLocalService;

}