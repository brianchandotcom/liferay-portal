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

package com.liferay.commerce.account.internal.upgrade.v5_0_0;

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