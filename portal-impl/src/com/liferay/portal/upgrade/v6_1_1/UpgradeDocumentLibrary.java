/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateFileEntries();
		updateFileVersions();
	}

	protected void updateFileEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileEntryId, groupId, folderId, title, extension " +
					"from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String title = rs.getString("title");
				String extension = rs.getString("extension");

				String appendExtension = StringPool.PERIOD + extension;

				if (title.endsWith(appendExtension)) {
					title = FileUtil.stripExtension(title);

					String uniqueTitle = title;
					int counter = 0;

					while (hasFile(groupId, folderId, uniqueTitle) ||
						hasFile(
							groupId, folderId, uniqueTitle + appendExtension)) {

						counter++;

						uniqueTitle = title + String.valueOf(counter);
					}

					if (counter > 0) {
						runSQL(
							"update DLFileEntry set title = '" + uniqueTitle +
								appendExtension + "' where fileEntryId = " +
									fileEntryId);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileVersionId, groupId, folderId, title, extension " +
					"from DLFileVersion");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileVersionId = rs.getLong("fileVersionId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String title = rs.getString("title");
				String extension = rs.getString("extension");

				String appendExtension = StringPool.PERIOD + extension;

				if (title.endsWith(appendExtension)) {
					title = FileUtil.stripExtension(title);

					String uniqueTitle = title;
					int counter = 0;

					while (hasVersion(groupId, folderId, uniqueTitle) ||
						hasVersion(
							groupId, folderId, uniqueTitle + appendExtension)) {

						counter++;

						uniqueTitle = title + String.valueOf(counter);
					}

					if (counter > 0) {
						runSQL(
							"update DLFileVersion set title = '" + uniqueTitle +
								appendExtension + "' where fileVersionId = " +
									fileVersionId);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected boolean hasFile(long groupId, long folderId, String title)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select count(*) from DLFileEntry where groupId = " + groupId +
					" and folderId =" + folderId + " and title = '" + title +
						"'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long count = rs.getLong(1);

				if (count > 0) {
					return true;
				}
				else {
					return false;
				}
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected boolean hasVersion(long groupId, long folderId, String title)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select count(*) from DLFileVersion where groupId = " +
					groupId + " and folderId =" + folderId + " and title = '" +
						title +	"'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long count = rs.getLong(1);

				if (count > 0) {
					return true;
				}
				else {
					return false;
				}
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}