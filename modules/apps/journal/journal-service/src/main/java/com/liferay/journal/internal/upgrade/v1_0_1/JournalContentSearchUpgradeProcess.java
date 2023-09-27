/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v1_0_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jonathan McCann
 */
public class JournalContentSearchUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradePortletId();
	}

	private void _upgradePortletId() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from JournalContentSearch where portletId like " +
					"'56%'");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select contentSearchId from JournalContentSearch where " +
					"groupId = ? AND privateLayout = ? AND layoutId = ? AND " +
						"portletId = ? AND articleId = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalContentSearch set portletId = ? where " +
						"contentSearchId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long contentSearchId = resultSet.getLong("contentSearchId");
				long groupId = resultSet.getLong("groupId");
				boolean privateLayout = resultSet.getBoolean("privateLayout");
				long layoutId = resultSet.getLong("layoutId");
				String portletId = resultSet.getString("portletId");
				String articleId = resultSet.getString("articleId");

				String newPortletId = StringUtil.replaceFirst(
					portletId, _OLD_ROOT_PORTLET_ID, _NEW_ROOT_PORTLET_ID);

				preparedStatement2.setLong(1, groupId);
				preparedStatement2.setBoolean(2, privateLayout);
				preparedStatement2.setLong(3, layoutId);
				preparedStatement2.setString(4, newPortletId);
				preparedStatement2.setString(5, articleId);

				try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
					if (resultSet2.next()) {
						runSQL(
							"delete from JournalContentSearch where " +
								"contentSearchId = " + contentSearchId);
					}
					else {
						preparedStatement3.setString(1, newPortletId);
						preparedStatement3.setLong(2, contentSearchId);

						preparedStatement3.addBatch();
					}
				}
			}

			preparedStatement3.executeBatch();
		}
	}

	private static final String _NEW_ROOT_PORTLET_ID =
		"com_liferay_journal_content_web_portlet_JournalContentPortlet";

	private static final String _OLD_ROOT_PORTLET_ID = "56";

}