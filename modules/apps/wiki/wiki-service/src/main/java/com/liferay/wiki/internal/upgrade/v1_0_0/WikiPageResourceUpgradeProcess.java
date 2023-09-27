/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Akos Thurzo
 */
public class WikiPageResourceUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateWikiPageResources();
	}

	private long _getGroupId(long resourcePrimKey) throws Exception {
		long groupId = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select groupId from WikiPage where resourcePrimKey = ?")) {

			preparedStatement.setLong(1, resourcePrimKey);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					groupId = resultSet.getLong("groupId");
				}
			}
		}

		return groupId;
	}

	private void _updateWikiPageResources() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select resourcePrimKey from WikiPageResource");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long resourcePrimKey = resultSet.getLong("resourcePrimKey");

				runSQL(
					StringBundler.concat(
						"update WikiPageResource set groupId = ",
						_getGroupId(resourcePrimKey),
						" where resourcePrimKey = ", resourcePrimKey));
			}
		}
	}

}