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
		User defaultUser = company.getDefaultUser();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update AccountGroupRel set userId = ?, userName = ?, " +
					"createDate = ?, modifiedDate = ? where companyId = ? " +
						"and userId = 0")) {

			preparedStatement.setLong(1, defaultUser.getUserId());
			preparedStatement.setString(2, defaultUser.getFullName());

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