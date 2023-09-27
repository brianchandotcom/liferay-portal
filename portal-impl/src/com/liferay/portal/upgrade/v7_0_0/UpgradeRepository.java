/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;

/**
 * @author Adolfo Pérez
 */
public class UpgradeRepository extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateRepositoryPortletId();
	}

	protected String[][] getRenamePortletNamesArray() {
		return new String[][] {
			{"19", "com.liferay.message.boards"}, {"33", "com.liferay.blogs"},
			{"36", "com.liferay.wiki"}
		};
	}

	protected void updateRepositoryPortletId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (String[] renamePortletNames : getRenamePortletNamesArray()) {
				String oldPortletName = renamePortletNames[0];
				String newPortletName = renamePortletNames[1];

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							"update Repository set portletId = ?, name = ? " +
								"where portletId = ?")) {

					preparedStatement.setString(1, newPortletName);
					preparedStatement.setString(2, newPortletName);
					preparedStatement.setString(3, oldPortletName);

					preparedStatement.executeUpdate();
				}
			}
		}
	}

}