/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v5_9_0;

import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.CompanyLocalService;
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
			accountEntryOrganizationRelLocalService,
		CompanyLocalService companyLocalService) {

		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				String selectCommerceAccountOrganizationRel =
					StringBundler.concat(
						"select * from CommerceAccountOrganizationRel where ",
						"companyId = ", companyId, " order by ",
						"commerceAccountId asc, organizationId asc");

				try (Statement selectStatement = connection.createStatement()) {
					ResultSet resultSet = selectStatement.executeQuery(
						selectCommerceAccountOrganizationRel);

					while (resultSet.next()) {
						long accountEntryId = resultSet.getLong(
							"commerceAccountId");
						long organizationId = resultSet.getLong(
							"organizationId");

						_accountEntryOrganizationRelLocalService.
							addAccountEntryOrganizationRel(
								accountEntryId, organizationId);
					}
				}
			});
	}

	private final AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;
	private final CompanyLocalService _companyLocalService;

}