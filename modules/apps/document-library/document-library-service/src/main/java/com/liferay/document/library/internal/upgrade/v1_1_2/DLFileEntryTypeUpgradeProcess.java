/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v1_1_2;

import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public class DLFileEntryTypeUpgradeProcess extends UpgradeProcess {

	public DLFileEntryTypeUpgradeProcess(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select fileEntryTypeId, companyId, userId from " +
					"DLFileEntryType where fileEntryTypeKey in ('IMAGE " +
						"GALLERY IMAGE', 'Image Gallery Image')");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long fileEntryTypeId = resultSet.getLong("fileEntryTypeId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");

				_resourceLocalService.addResources(
					companyId, 0, userId,
					"com.liferay.document.library.kernel.model.DLFileEntryType",
					String.valueOf(fileEntryTypeId), false, false, true);
			}
		}
	}

	private final ResourceLocalService _resourceLocalService;

}