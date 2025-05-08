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

import java.util.Date;

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
		processConcurrently(
			"select * from CommerceAccountGroupRel order by " +
				"commerceAccountGroupRelId asc",
			resultSet -> new Object[] {
				resultSet.getLong("commerceAccountGroupRelId"),
				resultSet.getLong("companyId"), resultSet.getLong("userId"),
				resultSet.getString("userName"),
				resultSet.getTimestamp("createDate"),
				resultSet.getTimestamp("modifiedDate"),
				resultSet.getLong("commerceAccountGroupId"),
				resultSet.getLong("classNameId"), resultSet.getLong("classPK")
			},
			values -> {
				AccountGroupRel accountGroupRel =
					_accountGroupRelLocalService.createAccountGroupRel(
						(Long)values[0]);

				accountGroupRel.setCompanyId((Long)values[1]);
				accountGroupRel.setUserId((Long)values[2]);
				accountGroupRel.setUserName((String)values[3]);
				accountGroupRel.setCreateDate((Date)values[4]);
				accountGroupRel.setModifiedDate((Date)values[5]);
				accountGroupRel.setAccountGroupId((Long)values[6]);
				accountGroupRel.setClassNameId((Long)values[7]);
				accountGroupRel.setClassPK((Long)values[8]);

				_accountGroupRelLocalService.addAccountGroupRel(
					accountGroupRel);
			},
			"Unable to migrate CommerceAccountGroupRel to AccountGroupRel");
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("CommerceAccountGroupRel")
		};
	}

	private final AccountGroupRelLocalService _accountGroupRelLocalService;

}