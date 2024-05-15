/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_2_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Thalles Montenegro
 */
public class ObjectDefinitionUpgradeProcess extends UpgradeProcess {

	public ObjectDefinitionUpgradeProcess(
		CompanyLocalService companyLocalService,
		UserLocalService userLocalService) {

		_companyLocalService = companyLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				try (PreparedStatement preparedStatement1 =
						connection.prepareStatement(
							StringBundler.concat(
								"update ObjectDefinition left join User_ on ",
								"ObjectDefinition.userId = User_.userId set ",
								"ObjectDefinition.userId = ? where User_.",
								"userId is null and ObjectDefinition.",
								"companyId = ?"))) {

					User user = _userLocalService.getUserByScreenName(
						company.getCompanyId(), "default-service-account");

					preparedStatement1.setLong(1, user.getUserId());

					preparedStatement1.setLong(2, company.getCompanyId());

					preparedStatement1.executeUpdate();
				}
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final UserLocalService _userLocalService;

}