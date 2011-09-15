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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateAssetClassTypeId();
		updateIGImageClassName();
	}

	protected long getJournalStructureId(String structureId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long journalStructureId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select id_ from JournalStructure where structureId = ?");

			ps.setString(1, structureId);

			rs = ps.executeQuery();

			if (rs.next()) {
				journalStructureId = rs.getLong("id_");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return journalStructureId;
	}

	protected void updateAssetClassTypeId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select resourcePrimKey, structureId from JournalArticle " +
					"where structureId != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");
				String structureId = rs.getString("structureId");

				long journalStructureId = getJournalStructureId(
					structureId);

				runSQL(
					"update AssetEntry set classTypeId = " +
						journalStructureId + " where classPK = " +
							resourcePrimKey);
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

		runSQL(
			"update AssetEntry set classNameId = " + dlFileEntryClassNameId +
				" where classNameId = " + igImageClassNameId);
	}

}