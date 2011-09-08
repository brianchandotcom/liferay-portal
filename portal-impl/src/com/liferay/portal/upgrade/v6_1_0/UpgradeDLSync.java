/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Miguel Pastor
 */
public class UpgradeDLSync extends UpgradeProcess {

	protected void updateDLSync() throws Exception {

		Connection con1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		try {
			con1 = DataAccess.getConnection();

			StringBundler sb = new StringBundler(10);

			sb.append("SELECT DLFileEntry.fileEntryId as id, ");
			sb.append("DLFileEntry.groupId as groupId, DLFileEntry.companyId");
			sb.append(" as companyId, DLFileEntry.createDate as createDate, ");
			sb.append("DLFileEntry.folderId as parentFolderId, \"file\" as ");
			sb.append("type from DLFileEntry UNION ALL ");
			sb.append("SELECT DLFolder.folderId as id, DLFolder.groupId as ");
			sb.append("groupId, DLFolder.companyId as companyId, ");
			sb.append("DLFolder.createDate as createDate, ");
			sb.append("DLFolder.parentFolderId as parentFolderId, ");
			sb.append("\"folder\" as type  from DLFolder");

			String sql1 = sb.toString();

			ps1 = con1.prepareStatement(sql1);

			rs1 = ps1.executeQuery();

			while (rs1.next()) {
				long syncId = increment();
				long id = rs1.getLong("id");
				long groupId = rs1.getLong("groupId");
				long companyId = rs1.getLong("companyId");
				Date createDate = rs1.getDate("createDate");
				long parentFolderId = rs1.getLong("parentFolderId");
				String type = rs1.getString("type");

				Connection con2 = null;
				PreparedStatement ps2 = null;

				try {
					con2 = DataAccess.getConnection();

					StringBundler sb2 = new StringBundler(4);

					sb2.append("insert into DLSync (syncId, companyId, ");
					sb2.append("createDate, modifiedDate, fileId, ");
					sb2.append("repositoryId, parentFolderId, event, type_) ");
					sb2.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

					String sql2 = sb2.toString();

					ps2 = con2.prepareStatement(sql2);

					ps2.setLong(1, syncId);
					ps2.setLong(2, companyId);
					ps2.setDate(3, createDate);
					ps2.setDate(4, createDate);
					ps2.setLong(5, id);
					ps2.setLong(6, groupId);
					ps2.setLong(7, parentFolderId);
					ps2.setString(8, "add");
					ps2.setString(9, type);

					ps2.executeUpdate();
				}
				finally {
					DataAccess.cleanUp(con2, ps2);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con1, ps1, rs1);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateDLSync();
	}

	public long increment() throws SystemException {
		DB db = DBFactoryUtil.getDB();

		return db.increment();
	}

}