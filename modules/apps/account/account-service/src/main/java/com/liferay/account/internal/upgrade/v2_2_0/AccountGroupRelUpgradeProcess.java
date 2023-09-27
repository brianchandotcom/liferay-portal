/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_2_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.Date;
import java.sql.PreparedStatement;

/**
 * @author Drew Brokke
 */
public class AccountGroupRelUpgradeProcess extends UpgradeProcess {

	public AccountGroupRelUpgradeProcess(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				try {
					_updateDefaultValues(company);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"AccountGroupRel", "userId LONG", "userName VARCHAR(75) null",
				"createDate DATE null", "modifiedDate DATE null")
		};
	}

	private void _updateDefaultValues(Company company) throws Exception {
		User guestUser = company.getGuestUser();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update AccountGroupRel set userId = ?, userName = ?, " +
					"createDate = ?, modifiedDate = ? where companyId = ? " +
						"and userId = 0")) {

			preparedStatement.setLong(1, guestUser.getUserId());
			preparedStatement.setString(2, guestUser.getFullName());

			Date date = new Date(System.currentTimeMillis());

			preparedStatement.setDate(3, date);
			preparedStatement.setDate(4, date);

			preparedStatement.setLong(5, company.getCompanyId());

			preparedStatement.executeUpdate();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountGroupRelUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;

}