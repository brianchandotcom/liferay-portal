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

package com.liferay.knowledge.base.internal.upgrade.v2_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class UpgradeFriendlyURL extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"create index IX_KB_UPGRADE on KBArticle (groupId, kbFolderId, " +
				"urlTitle[$COLUMN_LENGTH:75$])");

		try {
			boolean changed = true;

			while (changed) {
				changed = _renameConflictingKBArticleFriendlyURLs();
			}

			changed = true;

			while (changed) {
				changed = _renameConflictingKBFolderFriendlyURLs();
			}
		}
		finally {
			runSQL("drop index IX_KB_UPGRADE on KBArticle");
		}
	}

	private String _getUniqueUrlTitle(String urlTitle, int n) {
		Matcher matcher = _pattern.matcher(urlTitle);

		if (!matcher.matches()) {
			return urlTitle + _DEFAULT_SUFFIX;
		}

		int i = urlTitle.lastIndexOf('-');

		if (i == -1) {
			return urlTitle + _DEFAULT_SUFFIX;
		}

		int counter = Integer.parseInt(urlTitle.substring(i + 1));

		int spreadValue = 16 + n;

		return urlTitle + (counter + spreadValue);
	}

	private boolean _renameConflictingFriendlyURL(
			String selectSQL, String updateSQL)
		throws SQLException {

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateSQL);
			PreparedStatement ps2 = connection.prepareStatement(selectSQL);
			ResultSet rs = ps2.executeQuery()) {

			int count = 0;

			while (rs.next()) {
				count++;

				long id = rs.getLong(1);
				String urlTitle = rs.getString(2);

				ps1.setString(1, _getUniqueUrlTitle(urlTitle, count));

				ps1.setLong(2, id);

				ps1.addBatch();
			}

			ps1.executeBatch();

			if (count > 0) {
				return true;
			}

			return false;
		}
	}

	private boolean _renameConflictingKBArticleFriendlyURLs()
		throws SQLException {

		return _renameConflictingFriendlyURL(
			StringBundler.concat(
				"select KB2.kbArticleId, KB2.urlTitle from KBArticle KB1 ",
				"inner join KBArticle KB2 on KB1.groupId = KB2.groupId and ",
				"KB1.kbFolderId = KB2.kbFolderId and KB1.urlTitle = ",
				"KB2.urlTitle where KB1.kbArticleId < KB2.kbArticleId"),
			"update KBArticle set urlTitle = ? where kbArticleId = ?");
	}

	private boolean _renameConflictingKBFolderFriendlyURLs()
		throws SQLException {

		return _renameConflictingFriendlyURL(
			StringBundler.concat(
				"select KB2.kbFolderId, KB2.urlTitle from KBFolder KB1 inner ",
				"join KBFolder KB2 on KB1.groupId = KB2.groupId and ",
				"KB1.parentKBFolderId = KB2.parentKBFolderId and KB1.urlTitle ",
				"= KB2.urlTitle where KB1.kbFolderId < KB2.kbFolderId"),
			"update KBFolder set urlTitle = ? where kbFolderId = ?");
	}

	private static final String _DEFAULT_SUFFIX = "-1";

	private static final Pattern _pattern = Pattern.compile("-\\d+$");

}