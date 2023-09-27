/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v5_9_0;

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