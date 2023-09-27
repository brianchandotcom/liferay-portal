/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v7_1_2;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Luca Pellizzon
 */
public class CommerceAccountGroupCommerceAccountRelUpgradeProcess
	extends UpgradeProcess {

	public CommerceAccountGroupCommerceAccountRelUpgradeProcess(
		AccountGroupRelLocalService accountGroupRelLocalService) {

		_accountGroupRelLocalService = accountGroupRelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String selectCommerceAccountGroupRelSQL =
			"select * from CAccountGroupCAccountRel order by " +
				"CAccountGroupCAccountRelId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(
				selectCommerceAccountGroupRelSQL);

			while (resultSet.next()) {
				long accountGroupRelId = resultSet.getLong(
					"CAccountGroupCAccountRelId");

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
					PortalUtil.getClassNameId(AccountEntry.class.getName()));
				accountGroupRel.setClassPK(
					resultSet.getLong("commerceAccountId"));

				_accountGroupRelLocalService.addAccountGroupRel(
					accountGroupRel);
			}
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("CAccountGroupCAccountRel")
		};
	}

	private final AccountGroupRelLocalService _accountGroupRelLocalService;

}