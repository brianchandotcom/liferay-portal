/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.upgrade.v3_10_1;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Carolina Barbosa
 */
public class NotificationRecipientSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select NotificationRecipient.notificationRecipientId ",
					"from NotificationRecipient inner join ",
					"NotificationQueueEntry on NotificationRecipient.classPK ",
					"= NotificationQueueEntry.notificationQueueEntryId where ",
					"NotificationQueueEntry.type_ = '",
					NotificationConstants.TYPE_USER_NOTIFICATION, "'"));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select NotificationRecipient.notificationRecipientId ",
					"from NotificationRecipient inner join ",
					"NotificationTemplate on NotificationRecipient.classPK = ",
					"NotificationTemplate.notificationTemplateId where ",
					"NotificationTemplate.type_ = '",
					NotificationConstants.TYPE_USER_NOTIFICATION, "'"));
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from NotificationRecipientSetting where " +
						"notificationRecipientId = ? and name = " +
							"'singleRecipient'")) {

			while (resultSet1.next()) {
				preparedStatement3.setLong(
					1, resultSet1.getLong("notificationRecipientId"));

				preparedStatement3.addBatch();
			}

			while (resultSet2.next()) {
				preparedStatement3.setLong(
					1, resultSet2.getLong("notificationRecipientId"));

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
	}

}