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

package com.liferay.journal.internal.upgrade.v0_0_7;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * @author Mariano Alvaro Saiz
 */
public class UpgradeJournalArticleDates extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateCreateDate();
		_updateModifiedDate();
	}

	private void _updateCreateDate() throws SQLException {
		StringBundler sb = new StringBundler(3);

		sb.append("select resourcePrimKey, min(createDate) from ");
		sb.append("JournalArticle group by resourcePrimKey having count(*) > ");
		sb.append("1");

		try (Statement s = connection.createStatement();
			PreparedStatement ps =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set createDate = ? where " +
						"resourcePrimKey = ?");) {

			try (ResultSet rs = s.executeQuery(sb.toString())) {
				while (rs.next()) {
					long resourcePrimKey = rs.getLong(1);
					Timestamp createDate = rs.getTimestamp(2);

					ps.setTimestamp(1, createDate);

					ps.setLong(2, resourcePrimKey);

					ps.addBatch();
				}

				ps.executeBatch();
			}
		}
	}

	private void _updateModifiedDate() throws SQLException {
		StringBundler sb = new StringBundler(11);

		sb.append("select classPK, version, AssetEntry.modifiedDate from ");
		sb.append("AssetEntry, (select modifiedDate, ");
		sb.append("JournalArticle.resourcePrimkey, version from ");
		sb.append("JournalArticle, (select resourcePrimkey, max(version) as ");
		sb.append("maxVersion from JournalArticle where status = ? group by ");
		sb.append("resourcePrimKey) LatestVersion where ");
		sb.append("JournalArticle.resourcePrimkey = ");
		sb.append("LatestVersion.resourcePrimkey and version = maxVersion) ");
		sb.append("JournalArticle where classNameId = ? and classPK = ");
		sb.append("JournalArticle.resourcePrimkey and ");
		sb.append("AssetEntry.modifiedDate != JournalArticle.modifiedDate");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set modifiedDate = ? where " +
						"resourcePrimKey = ? and version = ?");) {

			ps1.setInt(1, WorkflowConstants.STATUS_APPROVED);
			ps1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long resourcePrimKey = rs.getLong(1);
					Double latestVersion = rs.getDouble(2);
					Timestamp assetModifiedDate = rs.getTimestamp(3);

					ps2.setTimestamp(1, assetModifiedDate);

					ps2.setLong(2, resourcePrimKey);

					ps2.setDouble(3, latestVersion);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

}