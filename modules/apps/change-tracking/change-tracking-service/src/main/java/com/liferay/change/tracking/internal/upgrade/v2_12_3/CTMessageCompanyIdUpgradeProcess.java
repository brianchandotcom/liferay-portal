/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_12_3;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author István András Dézsi
 */
public class CTMessageCompanyIdUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				"select ctMessageId, messageContent from CTMessage",
				"update CTMessage set messageContent = ? where ctMessageId = ?",
				resultSet -> new Object[] {
					resultSet.getLong("ctMessageId"),
					resultSet.getString("messageContent")
				},
				(values, preparedStatement) -> {
					String messageContent = (String)values[1];

					if (messageContent != null) {
						Message message = (Message)JSONFactoryUtil.deserialize(
							messageContent);

						message.remove("companyId");

						preparedStatement.setString(
							1, JSONFactoryUtil.serialize(message));

						preparedStatement.setLong(2, (Long)values[0]);

						preparedStatement.addBatch();
					}
				},
				"Unable to remove company ID");
		}
	}

}