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

package com.liferay.portal.upgrade.v6_1_0.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Cristina González
 */
public class UpdateSyncUtil {

	public static void updateSyncs(Connection connection) throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(10);

			sb.append("select DLFileEntry.fileEntryId as fileId, ");
			sb.append("DLFileEntry.groupId as groupId, DLFileEntry.companyId");
			sb.append(" as companyId, DLFileEntry.createDate as createDate, ");
			sb.append("DLFileEntry.folderId as parentFolderId, 'file' as ");
			sb.append("type from DLFileEntry union all select ");
			sb.append("DLFolder.folderId as fileId, DLFolder.groupId as ");
			sb.append("groupId, DLFolder.companyId as companyId, ");
			sb.append("DLFolder.createDate as createDate, ");
			sb.append("DLFolder.parentFolderId as parentFolderId, 'folder' ");
			sb.append("as type from DLFolder");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"insert into DLSync (syncId, companyId, createDate, " +
							"modifiedDate, fileId, repositoryId, " +
							"parentFolderId, event, type_) " +
							"values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long fileId = rs.getLong("fileId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					Timestamp createDate = rs.getTimestamp("createDate");
					long parentFolderId = rs.getLong("parentFolderId");
					String type = rs.getString("type");

					ps2.setLong(1, increment());
					ps2.setLong(2, companyId);
					ps2.setTimestamp(3, createDate);
					ps2.setTimestamp(4, createDate);
					ps2.setLong(5, fileId);
					ps2.setLong(6, groupId);
					ps2.setLong(7, parentFolderId);
					ps2.setString(8, "add");
					ps2.setString(9, type);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected static long increment() {
		DB db = DBManagerUtil.getDB();

		return db.increment();
	}

}