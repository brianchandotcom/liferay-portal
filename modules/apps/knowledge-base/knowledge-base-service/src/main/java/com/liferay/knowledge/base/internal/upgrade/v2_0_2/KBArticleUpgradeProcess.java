/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v2_0_2;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class KBArticleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (SafeCloseable safeCloseable = addTemporaryIndex(
				"KBArticle", false, "groupId", "kbFolderId", "urlTitle")) {

			boolean changed = true;

			while (changed) {
				changed = _renameConflictingKBArticleFriendlyURLs();
			}

			changed = true;

			while (changed) {
				changed = _renameConflictingKBFolderFriendlyURLs();
			}
		}
	}

	private String _getUniqueUrlTitle(String urlTitle, int n) {
		String suffix = _DEFAULT_SUFFIX;

		int i = urlTitle.lastIndexOf(CharPool.DASH);

		if (i != -1) {
			Matcher matcher = _pattern.matcher(urlTitle);

			matcher.region(i, urlTitle.length());

			if (matcher.matches()) {
				int counter = GetterUtil.getInteger(urlTitle.substring(i + 1));

				int spreadValue = 16 + n;

				suffix = CharPool.DASH + String.valueOf(counter + spreadValue);

				urlTitle = urlTitle.substring(0, i);
			}
		}

		if ((urlTitle.length() + suffix.length()) > _MAX_URL_TITLE_LENGTH) {
			urlTitle = urlTitle.substring(
				0, _MAX_URL_TITLE_LENGTH - suffix.length());
		}

		return urlTitle + suffix;
	}

	private boolean _renameConflictingFriendlyURL(
			String selectSQL, String updateSQL)
		throws SQLException {

		try (PreparedStatement preparedStatement1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateSQL);
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				selectSQL);
			ResultSet resultSet = preparedStatement2.executeQuery()) {

			int count = 0;

			while (resultSet.next()) {
				count++;

				long id = resultSet.getLong(1);

				String urlTitle = resultSet.getString(2);

				preparedStatement1.setString(
					1, _getUniqueUrlTitle(urlTitle, count));

				preparedStatement1.setLong(2, id);

				preparedStatement1.addBatch();
			}

			preparedStatement1.executeBatch();

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
				"select distinct kbArticle2.kbArticleId, kbArticle2.urlTitle ",
				"from KBArticle kbArticle1 inner join KBArticle kbArticle2 on ",
				"kbArticle1.groupId = kbArticle2.groupId and ",
				"kbArticle1.kbFolderId = kbArticle2.kbFolderId and ",
				"kbArticle1.urlTitle = kbArticle2.urlTitle where ",
				"kbArticle1.kbArticleId < kbArticle2.kbArticleId"),
			"update KBArticle set urlTitle = ? where kbArticleId = ?");
	}

	private boolean _renameConflictingKBFolderFriendlyURLs()
		throws SQLException {

		return _renameConflictingFriendlyURL(
			StringBundler.concat(
				"select distinct kbArticle2.kbFolderId, kbArticle2.urlTitle ",
				"from KBFolder kbArticle1 inner join KBFolder kbArticle2 on ",
				"kbArticle1.groupId = kbArticle2.groupId and ",
				"kbArticle1.parentKBFolderId = kbArticle2.parentKBFolderId ",
				"and kbArticle1.urlTitle = kbArticle2.urlTitle where ",
				"kbArticle1.kbFolderId < kbArticle2.kbFolderId"),
			"update KBFolder set urlTitle = ? where kbFolderId = ?");
	}

	private static final String _DEFAULT_SUFFIX = "-1";

	private static final int _MAX_URL_TITLE_LENGTH = 74;

	private static final Pattern _pattern = Pattern.compile("-\\d+$");

}