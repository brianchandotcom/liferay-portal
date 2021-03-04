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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v3_1_1;

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.definition.NotificationType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Rafael Praxedes
 */
public class UpgradeKaleoNotification extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select kaleoNotificationId, notificationTypes  from " +
					"KaleoNotification where notificationTypes like ? OR " +
						"notificationTypes like ?");
			PreparedStatement ps2 = connection.prepareStatement(
				"update KaleoNotification set notificationTypes = ? where " +
					"kaleoNotificationId = ?")) {

			ps1.setString(1, "%im%");
			ps1.setString(2, "%private-message%");

			ResultSet rs = ps1.executeQuery();

			while (rs.next()) {
				String[] notificationTypes = Stream.of(
					StringUtil.split(rs.getString("notificationTypes"))
				).filter(
					notificationType -> !Objects.equals(notificationType, "im")
				).filter(
					notificationType -> !Objects.equals(
						notificationType, "private-message")
				).toArray(
					String[]::new
				);

				if (ArrayUtil.isEmpty(notificationTypes)) {
					notificationTypes = new String[] {
						NotificationType.USER_NOTIFICATION.getValue()
					};
				}

				ps2.setString(1, StringUtil.merge(notificationTypes));

				ps2.setLong(2, rs.getLong("kaleoNotificationId"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
		catch (SQLException sqlException) {
			throw new UpgradeException(sqlException);
		}
	}

}