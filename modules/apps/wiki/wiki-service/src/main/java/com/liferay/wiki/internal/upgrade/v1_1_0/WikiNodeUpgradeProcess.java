/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class WikiNodeUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateWikiNodeName();
	}

	private void _updateWikiNodeName() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select nodeId, name from WikiNode");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String name = resultSet.getString("name");

				if (!Validator.isNumber(name)) {
					continue;
				}

				long nodeId = resultSet.getLong("nodeId");

				runSQL(
					StringBundler.concat(
						"update WikiNode set name = 'Node ", name,
						"' where nodeId = ", nodeId));
			}
		}
	}

}