/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_1_1;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Sergio González
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateFileEntries();
	}

	protected boolean hasFileEntry(long groupId, long folderId, String title)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from DLFileEntry where groupId = ? and " +
					"folderId = ? and title = ?")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, folderId);
			preparedStatement.setString(3, title);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	protected void updateFileEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select fileEntryId, groupId, folderId, title, extension, " +
					"version from DLFileEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String title = resultSet.getString("title");

				String extension = GetterUtil.getString(
					resultSet.getString("extension"));

				String periodAndExtension = StringPool.PERIOD.concat(extension);

				if (!title.endsWith(periodAndExtension)) {
					continue;
				}

				title = FileUtil.stripExtension(title);

				String uniqueTitle = title;

				int count = 0;

				long groupId = resultSet.getLong("groupId");
				long folderId = resultSet.getLong("folderId");

				while (hasFileEntry(groupId, folderId, uniqueTitle) ||
					   ((count != 0) &&
						hasFileEntry(
							groupId, folderId,
							uniqueTitle + periodAndExtension))) {

					count++;

					uniqueTitle = title + String.valueOf(count);
				}

				if (count <= 0) {
					continue;
				}

				long fileEntryId = resultSet.getLong("fileEntryId");
				String version = resultSet.getString("version");

				uniqueTitle += periodAndExtension;

				updateFileEntry(fileEntryId, version, uniqueTitle);
			}
		}
	}

	protected void updateFileEntry(
			long fileEntryId, String version, String newTitle)
		throws SQLException {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"update DLFileEntry set title = ? where fileEntryId = ?")) {

			preparedStatement1.setString(1, newTitle);
			preparedStatement1.setLong(2, fileEntryId);

			preparedStatement1.executeUpdate();

			try (PreparedStatement preparedStatement2 =
					connection.prepareStatement(
						"update DLFileVersion set title = ? where " +
							"fileEntryId = ? and version = ?")) {

				preparedStatement2.setString(1, newTitle);
				preparedStatement2.setLong(2, fileEntryId);
				preparedStatement2.setString(3, version);

				preparedStatement2.executeUpdate();
			}
		}
	}

}