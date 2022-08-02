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

package com.liferay.notification.internal.upgrade.v1_2_0;

import com.liferay.notification.constants.NotificationsQueryEntryConstants;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Mateus Santana
 */
public class NotificationQueueEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn("NotificationQueueEntry", "status", "INTEGER");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select notificationQueueEntryId, sent from " +
					"NotificationQueueEntry");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				SQLTransformer.transform(
					"update NotificationQueueEntry set status = ? where " +
						"notificationQueueEntryId = ?"))) {

			while (resultSet.next()) {
				if (resultSet.getBoolean("sent")) {
					preparedStatement2.setInt(
						1, NotificationsQueryEntryConstants.STATUS_SENT);
				}
				else {
					preparedStatement2.setInt(
						1, NotificationsQueryEntryConstants.STATUS_UNSENT);
				}

				preparedStatement2.setLong(
					2, resultSet.getLong("notificationQueueEntryId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}