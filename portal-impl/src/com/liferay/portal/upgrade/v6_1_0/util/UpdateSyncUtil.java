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
			StringBundler sb1 = new StringBundler(11);

			sb1.append("select DLFileEntry.fileEntryId as fileId, ");
			sb1.append("DLFileEntry.groupId as groupId, ");
			sb1.append("DLFileEntry.companyId as companyId, ");
			sb1.append("DLFileEntry.createDate as createDate, ");
			sb1.append("DLFileEntry.folderId as parentFolderId, 'file' as ");
			sb1.append("type from DLFileEntry union all select ");
			sb1.append("DLFolder.folderId as fileId, DLFolder.groupId as ");
			sb1.append("groupId, DLFolder.companyId as companyId, ");
			sb1.append("DLFolder.createDate as createDate, ");
			sb1.append("DLFolder.parentFolderId as parentFolderId, 'folder' ");
			sb1.append("as type from DLFolder");

			StringBundler sb2 = new StringBundler(3);

			sb2.append("insert into DLSync (syncId, companyId, createDate, ");
			sb2.append("modifiedDate, fileId, repositoryId, parentFolderId, ");
			sb2.append("event, type_) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb1.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb2.toString());
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					long fileId = rs.getLong("fileId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					Timestamp createDate = rs.getTimestamp("createDate");
					long parentFolderId = rs.getLong("parentFolderId");
					String type = rs.getString("type");

					ps2.setLong(1, _increment());
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

	private static long _increment() {
		DB db = DBManagerUtil.getDB();

		return db.increment();
	}

}