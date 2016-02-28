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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateFileEntries();
		updateFileVersions();
	}

	protected List<Long> getFileVersionIds(long folderId, String name)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select fileVersionId from DLFileVersion where folderId = ? " +
					"and name = ? order by version desc")) {

			ps.setLong(1, folderId);
			ps.setString(2, name);

			try (ResultSet rs = ps.executeQuery()) {
				List<Long> fileVersionIds = new ArrayList<>();

				while (rs.next()) {
					long fileVersionId = rs.getLong("fileVersionId");

					fileVersionIds.add(fileVersionId);
				}

				return fileVersionIds;
			}
		}
	}

	protected void updateFileEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Long> tableIds = new ArrayList<>();

			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry");

			try (PreparedStatement ps = connection.prepareStatement(
					"select tableId from ExpandoTable where classNameId = " +
						classNameId);
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long tableId = rs.getLong("tableId");

					tableIds.add(tableId);
				}
			}

			try (PreparedStatement ps = connection.prepareStatement(
					"select uuid_, fileEntryId, groupId, folderId, name, " +
						"title from DLFileEntry");
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String uuid_ = rs.getString("uuid_");
					long fileEntryId = rs.getLong("fileEntryId");
					long groupId = rs.getLong("groupId");
					long folderId = rs.getLong("folderId");
					String name = rs.getString("name");
					String title = rs.getString("title");

					String extension = FileUtil.getExtension(title);

					runSQL(
						"update DLFileEntry set extension = '" + extension +
							"' where uuid_ = '" + uuid_ + "' and groupId = " +
								groupId);

					long latestFileVersionId = 0;

					List<Long> fileVersionIds = getFileVersionIds(
						folderId, name);

					if (!fileVersionIds.isEmpty()) {
						latestFileVersionId = fileVersionIds.get(0);
					}

					for (long tableId : tableIds) {
						runSQL(
							"update ExpandoRow set classPK = " +
								latestFileVersionId + " where tableId = " +
									tableId + " and classPK = " + fileEntryId);

						runSQL(
							"update ExpandoValue set classPK = " +
								latestFileVersionId + " where tableId = " +
									tableId + " and classPK = " + fileEntryId);
					}
				}
			}
		}
	}

	protected void updateFileVersion(
			long fileVersionId, String extension, String title,
			String description, String extraSettings)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update DLFileVersion set extension = ?, title = ?, " +
					"description = ?, extraSettings = ? where fileVersionId " +
						"= ?")) {

			ps.setString(1, extension);
			ps.setString(2, title);
			ps.setString(3, description);
			ps.setString(4, extraSettings);
			ps.setLong(5, fileVersionId);

			ps.executeUpdate();
		}
	}

	protected void updateFileVersions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select folderId, name, extension, title, description, " +
					"extraSettings from DLFileEntry");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				String extension = rs.getString("extension");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String extraSettings = rs.getString("extraSettings");

				List<Long> fileVersionIds = getFileVersionIds(folderId, name);

				for (long fileVersionId : fileVersionIds) {
					updateFileVersion(
						fileVersionId, extension, title, description,
						extraSettings);
				}
			}
		}
	}

}