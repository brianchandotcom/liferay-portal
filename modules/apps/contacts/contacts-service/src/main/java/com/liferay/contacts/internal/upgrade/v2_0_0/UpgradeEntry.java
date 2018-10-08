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
public class UpgradeEntry extends UpgradeProcess {

	public UpgradeEntry(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateEntries();
	}

	protected void updateEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select companyId, emailAddress, entryId from Contacts_Entry");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String emailAddress = rs.getString("emailAddress");

				User user = _userLocalService.fetchUserByEmailAddress(
					companyId, emailAddress);

				if (user == null) {
					continue;
				}

				long entryId = rs.getLong("entryId");

				runSQL("delete from Contacts_Entry where entryId = " + entryId);
			}
		}
	}

	private final UserLocalService _userLocalService;

}