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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;
import com.liferay.portal.upgrade.v7_0_0.util.AssetEntryTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Gergely Mathe
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			AssetEntryTable.class,
			new AlterColumnType("description", "TEXT null"),
			new AlterColumnType("summary", "TEXT null"));

		updateAssetEntries();
	}

	protected long getDDMStructureId(String structureKey) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select structureId from DDMStructure where structureKey = " +
					"?")) {

			ps.setString(1, structureKey);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("structureId");
				}

				return 0;
			}
		}
	}

	protected void updateAssetEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.journal.model.JournalArticle");

			StringBundler sb = new StringBundler(9);

			sb.append("select JournalArticle.resourcePrimKey from (select ");
			sb.append("JournalArticle.resourcePrimkey as primKey, ");
			sb.append("max(JournalArticle.version) as maxVersion from ");
			sb.append("JournalArticle group by ");
			sb.append("JournalArticle.resourcePrimkey) temp_table inner join ");
			sb.append("JournalArticle on (JournalArticle.indexable = ?) and ");
			sb.append("(JournalArticle.status = 0) and ");
			sb.append("(JournalArticle.resourcePrimkey = temp_table.primKey) ");
			sb.append("and (JournalArticle.version = temp_table.maxVersion)");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString())) {

				ps1.setBoolean(1, false);

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.autoBatch(
							connection.prepareStatement(
								"update AssetEntry set listable = ? where " +
									"classNameId = ? and classPK = ?"));
					ResultSet rs = ps1.executeQuery()) {

					while (rs.next()) {
						long classPK = rs.getLong("resourcePrimKey");

						ps2.setBoolean(1, false);
						ps2.setLong(2, classNameId);
						ps2.setLong(3, classPK);

						ps2.addBatch();
					}

					ps2.executeBatch();
				}
			}
		}
	}

}