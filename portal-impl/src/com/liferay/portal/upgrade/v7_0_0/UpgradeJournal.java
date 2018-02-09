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

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
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
public class UpgradeJournal extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateCreateDate();
		_updateModifiedDate();
	}

	private void _updateCreateDate() throws SQLException {
		StringBundler sb = new StringBundler(6);

		sb.append("select JournalArticle.resourcePrimKey, ");
		sb.append("min(JournalArticle.createDate) from ");
		sb.append("JournalArticleResource, JournalArticle where ");
		sb.append("JournalArticleResource.resourcePrimKey = ");
		sb.append("JournalArticle.resourcePrimKey group by ");
		sb.append("JournalArticle.resourcePrimKey having count(*) > 1");

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
		StringBundler sb = new StringBundler(13);

		sb.append("select JournalArticle.resourcePrimKey, ");
		sb.append("JournalArticle.modifiedDate, AssetInfo.modifiedDate from (");
		sb.append("select resourcePrimKey, MAX(modifiedDate) as modifiedDate ");
		sb.append("from JournalArticle where status = ? group by ");
		sb.append("resourcePrimKey) JournalArticle inner join (select ");
		sb.append("JournalArticleResource.resourcePrimKey as ");
		sb.append("resourcePrimKey, AssetEntry.modifiedDate as modifiedDate ");
		sb.append("from AssetEntry, JournalArticleResource where ");
		sb.append("AssetEntry.classUuid = JournalArticleResource.uuid_ and ");
		sb.append("AssetEntry.groupId = JournalArticleResource.groupId) ");
		sb.append("AssetInfo on (JournalArticle.resourcePrimKey = ");
		sb.append("AssetInfo.resourcePrimKey) and ");
		sb.append("(JournalArticle.modifiedDate != AssetInfo.modifiedDate)");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set modifiedDate = ? where " +
						"resourcePrimKey = ? and modifiedDate = ?");) {

			ps1.setInt(1, WorkflowConstants.STATUS_APPROVED);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long resourcePrimKey = rs.getLong(1);
					Timestamp journalModifiedDate = rs.getTimestamp(2);
					Timestamp assetModifiedDate = rs.getTimestamp(3);

					ps2.setTimestamp(1, assetModifiedDate);

					ps2.setLong(2, resourcePrimKey);

					ps2.setTimestamp(3, journalModifiedDate);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}