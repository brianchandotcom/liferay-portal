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

				try (PreparedStatement ps = connection.prepareStatement(
						"update Repository set portletId = ?, name = ? where " +
							"portletId = ?")) {

					ps.setString(1, newPortletName);
					ps.setString(2, newPortletName);
					ps.setString(3, oldPortletName);

					ps.executeUpdate();
				}
			}
		}
	}

}