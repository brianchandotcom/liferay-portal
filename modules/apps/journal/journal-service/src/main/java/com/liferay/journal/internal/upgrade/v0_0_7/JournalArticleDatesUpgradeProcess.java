/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v0_0_7;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * @author Mariano Álvaro Sáiz
 */
public class JournalArticleDatesUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateCreateDate();
		_updateModifiedDate();
	}

	private void _updateCreateDate() throws Exception {
		try (Statement s = connection.createStatement();
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set createDate = ? where " +
						"resourcePrimKey = ?")) {

			try (ResultSet resultSet = s.executeQuery(
					StringBundler.concat(
						"select resourcePrimKey, min(createDate) from ",
						"JournalArticle group by resourcePrimKey having ",
						"count(*) > 1"))) {

				while (resultSet.next()) {
					long resourcePrimKey = resultSet.getLong(1);

					Timestamp createDate = resultSet.getTimestamp(2);

					preparedStatement.setTimestamp(1, createDate);

					preparedStatement.setLong(2, resourcePrimKey);

					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
			}
		}
	}

	private void _updateModifiedDate() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select classPK, version, AssetEntry.modifiedDate from ",
					"AssetEntry, (select modifiedDate, ",
					"JournalArticle.resourcePrimKey, version from ",
					"JournalArticle, (select resourcePrimKey, max(version) as ",
					"maxVersion from JournalArticle where status = ? group by ",
					"resourcePrimKey) LatestVersion where ",
					"JournalArticle.resourcePrimKey = ",
					"LatestVersion.resourcePrimKey and version = maxVersion) ",
					"JournalArticle where classNameId = ? and classPK = ",
					"JournalArticle.resourcePrimKey and ",
					"AssetEntry.modifiedDate != JournalArticle.modifiedDate"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set modifiedDate = ? where " +
						"resourcePrimKey = ? and version = ?")) {

			preparedStatement1.setInt(1, WorkflowConstants.STATUS_APPROVED);
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long resourcePrimKey = resultSet.getLong(1);
					Double latestVersion = resultSet.getDouble(2);

					Timestamp assetModifiedDate = resultSet.getTimestamp(3);

					preparedStatement2.setTimestamp(1, assetModifiedDate);

					preparedStatement2.setLong(2, resourcePrimKey);
					preparedStatement2.setDouble(3, latestVersion);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

}