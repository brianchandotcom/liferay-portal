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

package com.liferay.notifications.web.upgrade.v2_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.UserNotificationEventLocalService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeUserNotificationEvent extends UpgradeProcess {

	public UpgradeUserNotificationEvent(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("Notifications_UserNotificationEvent")) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select userNotificationEventId, actionRequired from " +
					"Notifications_UserNotificationEvent");

			rs = ps.executeQuery();

			while (rs.next()) {
				long userNotificationEventId = rs.getLong(
					"userNotificationEventId");
				boolean actionRequired = rs.getBoolean("actionRequired");

				UserNotificationEvent userNotificationEvent =
					_userNotificationEventLocalService.getUserNotificationEvent(
						userNotificationEventId);

				userNotificationEvent.setActionRequired(actionRequired);

				_userNotificationEventLocalService.updateUserNotificationEvent(
					userNotificationEvent);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private final UserNotificationEventLocalService
		_userNotificationEventLocalService;

}