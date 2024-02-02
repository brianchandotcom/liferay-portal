/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.upgrade.v3_1_0;

import com.liferay.contacts.model.Entry;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Danny Situ
 */
public class EntryResourceUpgradeProcess extends UpgradeProcess {

	public EntryResourceUpgradeProcess(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from Contacts_Entry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long entryId = resultSet.getLong("entryId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");

				_resourceLocalService.addResources(
					companyId, 0, userId, Entry.class.getName(), entryId, false,
					false, false);
			}
		}
	}

	private final ResourceLocalService _resourceLocalService;

}