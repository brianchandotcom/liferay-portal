/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_12_4;

import com.liferay.change.tracking.model.CTProcess;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author David Truong
 */
public class CTProcessResourceUpgradeProcess extends UpgradeProcess {

	public CTProcessResourceUpgradeProcess(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select ctProcessId, companyId, userId from CTProcess");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long ctProcessId = resultSet.getLong("ctProcessId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");

				_resourceLocalService.addResources(
					companyId, 0, userId, CTProcess.class.getName(),
					ctProcessId, false, false, false);
			}
		}
	}

	private final ResourceLocalService _resourceLocalService;

}