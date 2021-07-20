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

package com.liferay.commerce.account.internal.upgrade.v4_0_0;

import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Drew Brokke
 */
public class CommerceAccountOrganizationRelUpgradeProcess
	extends UpgradeProcess {

	public CommerceAccountOrganizationRelUpgradeProcess(
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService) {

		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long oldCompanyId = CompanyThreadLocal.getCompanyId();

		String selectCommerceAccountOrganizationRel =
			"select * from CommerceAccountOrganizationRel order by " +
				"commerceAccountId asc, organizationId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(
				selectCommerceAccountOrganizationRel);

			while (resultSet.next()) {
				long accountEntryId = resultSet.getLong("commerceAccountId");
				long organizationId = resultSet.getLong("organizationId");

				CompanyThreadLocal.setCompanyId(resultSet.getLong("companyId"));

				_accountEntryOrganizationRelLocalService.
					addAccountEntryOrganizationRel(
						accountEntryId, organizationId);
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(oldCompanyId);
		}
	}

	private final AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

}