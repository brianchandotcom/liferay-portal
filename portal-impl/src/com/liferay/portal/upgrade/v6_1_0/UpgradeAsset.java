/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.UpgradeProcessUtil;
import com.liferay.portal.upgrade.v6_1_0.util.AssetEntryTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.journal.model.JournalArticle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Juan Fernández
 * @author Sergio González
 * @author Brian Wing Shun Chan
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type AssetEntry title STRING null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				AssetEntryTable.TABLE_NAME, AssetEntryTable.TABLE_COLUMNS,
				AssetEntryTable.TABLE_SQL_CREATE,
				AssetEntryTable.TABLE_SQL_ADD_INDEXES);
		}

		updateAssetClassTypeId();
		updateIGImageClassName();
	}

	protected long getJournalStructureId(String structureId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select id_ from JournalStructure where structureId = ?");

			ps.setString(1, structureId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("id_");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateAssetClassTypeId() throws Exception {
		long classNameId = PortalUtil.getClassNameId(JournalArticle.class);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourcePrimKey, structureId from JournalArticle " +
					"where structureId != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");
				String structureId = rs.getString("structureId");

				long journalStructureId = getJournalStructureId(structureId);

				runSQL(
					"update AssetEntry set classTypeId = " +
						journalStructureId + " where classNameId = " +
							classNameId + " and classPK = " + resourcePrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateIGImageClassName() throws Exception {
		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class.getName());
		long igImageClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.imagegallery.model.IGImage");

		if (PropsValues.DL_FILE_ENTRY_TYPE_IG_IMAGE_AUTO_CREATE_ON_UPGRADE) {
			UpgradeProcessUtil.setCreateIGImageDocumentType(true);

			updateIGImageClassNameWithClassTypeId(
				dlFileEntryClassNameId, igImageClassNameId);
		}
		else {
			updateIGImageClassNameWithoutClassTypeId(
				dlFileEntryClassNameId, igImageClassNameId);
		}
	}

	protected void updateIGImageClassNameWithClassTypeId(
			long dlFileEntryClassNameId, long igImageClassNameId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileEntryTypeId, companyId from DLFileEntryType " +
					"where name = ?");

			ps.setString(1, DLFileEntryTypeConstants.NAME_IG_IMAGE);

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryTypeId = rs.getLong("fileEntryTypeId");
				long companyId = rs.getLong("companyId");

				StringBundler sb = new StringBundler(8);

				sb.append("update AssetEntry set classNameId = ");
				sb.append(dlFileEntryClassNameId);
				sb.append(", classTypeId = ");
				sb.append(fileEntryTypeId);
				sb.append(" where classNameId = ");
				sb.append(igImageClassNameId);
				sb.append(" AND companyId = ");
				sb.append(companyId);

				runSQL(sb.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateIGImageClassNameWithoutClassTypeId(
			long dlFileEntryClassNameId, long igImageClassNameId)
		throws Exception {

		runSQL(
			"update AssetEntry set classNameId = " + dlFileEntryClassNameId +
				" where classNameId = " + igImageClassNameId);
	}

}