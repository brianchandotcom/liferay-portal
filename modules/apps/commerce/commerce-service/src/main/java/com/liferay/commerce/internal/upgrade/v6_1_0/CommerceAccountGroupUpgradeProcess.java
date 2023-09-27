/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v6_1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class CommerceAccountGroupUpgradeProcess extends UpgradeProcess {

	public CommerceAccountGroupUpgradeProcess(
		AccountGroupLocalService accountGroupLocalService,
		ResourceLocalService resourceLocalService) {

		_accountGroupLocalService = accountGroupLocalService;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String selectCommerceAccountGroupSQL =
			"select * from CommerceAccountGroup order by " +
				"commerceAccountGroupId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(
				selectCommerceAccountGroupSQL);

			while (resultSet.next()) {
				boolean system = resultSet.getBoolean("system_");

				if (system) {
					continue;
				}

				long accountGroupId = resultSet.getLong(
					"commerceAccountGroupId");

				AccountGroup accountGroup =
					_accountGroupLocalService.createAccountGroup(
						accountGroupId);

				accountGroup.setExternalReferenceCode(
					resultSet.getString("externalReferenceCode"));
				accountGroup.setCompanyId(resultSet.getLong("companyId"));
				accountGroup.setUserId(resultSet.getLong("userId"));
				accountGroup.setUserName(resultSet.getString("userName"));
				accountGroup.setCreateDate(
					resultSet.getTimestamp("createDate"));
				accountGroup.setModifiedDate(
					resultSet.getTimestamp("modifiedDate"));
				accountGroup.setDefaultAccountGroup(system);
				accountGroup.setName(resultSet.getString("name"));
				accountGroup.setType(
					_toAccountGroupType(resultSet.getInt("type_")));

				_accountGroupLocalService.addAccountGroup(accountGroup);

				_resourceLocalService.addResources(
					resultSet.getLong("companyId"), 0,
					resultSet.getLong("userId"), AccountGroup.class.getName(),
					accountGroupId, false, false, false);
			}
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("CommerceAccountGroup")
		};
	}

	private String _toAccountGroupType(Integer type) {
		if (Objects.equals(type, 2)) {
			return AccountConstants.ACCOUNT_GROUP_TYPE_GUEST;
		}
		else if (Objects.equals(type, 1)) {
			return AccountConstants.ACCOUNT_GROUP_TYPE_DYNAMIC;
		}
		else if (Objects.equals(type, 0)) {
			return AccountConstants.ACCOUNT_GROUP_TYPE_STATIC;
		}

		return AccountConstants.ACCOUNT_GROUP_TYPE_STATIC;
	}

	private final AccountGroupLocalService _accountGroupLocalService;
	private final ResourceLocalService _resourceLocalService;

}