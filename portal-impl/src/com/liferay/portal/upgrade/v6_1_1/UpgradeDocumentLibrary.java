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

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from DLFileEntry where groupId = ? and " +
					"folderId = ? and title = ?")) {

			ps.setLong(1, groupId);
			ps.setLong(2, folderId);
			ps.setString(3, title);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

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
			PreparedStatement ps = connection.prepareStatement(
				"select fileEntryId, groupId, folderId, title, extension, " +
					"version from DLFileEntry");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String title = rs.getString("title");
				String extension = GetterUtil.getString(
					rs.getString("extension"));
				String version = rs.getString("version");

				String periodAndExtension = StringPool.PERIOD.concat(extension);

				if (!title.endsWith(periodAndExtension)) {
					continue;
				}

				title = FileUtil.stripExtension(title);

				String uniqueTitle = title;

				int count = 0;

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

				uniqueTitle += periodAndExtension;

				updateFileEntry(fileEntryId, version, uniqueTitle);
			}
		}
	}

	protected void updateFileEntry(
			long fileEntryId, String version, String newTitle)
		throws SQLException {

		try (PreparedStatement ps1 = connection.prepareStatement(
				"update DLFileEntry set title = ? where fileEntryId = ?")) {

			ps1.setString(1, newTitle);
			ps1.setLong(2, fileEntryId);

			ps1.executeUpdate();

			try (PreparedStatement ps2 = connection.prepareStatement(
					"update DLFileVersion set title = ? where fileEntryId = " +
						"? and version = ?")) {

				ps2.setString(1, newTitle);
				ps2.setLong(2, fileEntryId);
				ps2.setString(3, version);

				ps2.executeUpdate();
			}
		}
	}

}