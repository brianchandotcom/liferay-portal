/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v5_9_0;

import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Drew Brokke
 */
public class CommerceAccountUserRelUpgradeProcess extends UpgradeProcess {

	public CommerceAccountUserRelUpgradeProcess(
		AccountEntryUserRelLocalService accountEntryUserRelLocalService) {

		_accountEntryUserRelLocalService = accountEntryUserRelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long oldCompanyId = CompanyThreadLocal.getCompanyId();

		String selectCommerceAccountUserRel =
			"select * from CommerceAccountUserRel order by commerceAccountId " +
				"asc, commerceAccountUserId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(
				selectCommerceAccountUserRel);

			while (resultSet.next()) {
				long accountEntryId = resultSet.getLong("commerceAccountId");
				long accountUserId = resultSet.getLong("commerceAccountUserId");

				CompanyThreadLocal.setCompanyId(resultSet.getLong("companyId"));

				_accountEntryUserRelLocalService.addAccountEntryUserRel(
					accountEntryId, accountUserId);
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(oldCompanyId);
		}
	}

	private final AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;

}