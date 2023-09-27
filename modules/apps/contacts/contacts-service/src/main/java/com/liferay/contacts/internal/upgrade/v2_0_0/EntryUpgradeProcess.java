/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jonathan Lee
 */
public class EntryUpgradeProcess extends UpgradeProcess {

	public EntryUpgradeProcess(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateEntries();
	}

	private void _updateEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, emailAddress, entryId from Contacts_Entry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");
				String emailAddress = resultSet.getString("emailAddress");

				User user = _userLocalService.fetchUserByEmailAddress(
					companyId, emailAddress);

				if (user == null) {
					continue;
				}

				long entryId = resultSet.getLong("entryId");

				runSQL("delete from Contacts_Entry where entryId = " + entryId);
			}
		}
	}

	private final UserLocalService _userLocalService;

}