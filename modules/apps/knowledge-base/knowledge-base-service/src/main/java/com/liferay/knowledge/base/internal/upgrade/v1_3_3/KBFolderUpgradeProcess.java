/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_3_3;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class KBFolderUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, String> urlTitles = _getInitialUrlTitles(connection);

		for (Map.Entry<Long, String> entry : urlTitles.entrySet()) {
			String uniqueUrlTitle = _findUniqueUrlTitle(
				connection, entry.getValue());

			for (int i = 1; uniqueUrlTitle == null; i++) {
				uniqueUrlTitle = _findUniqueUrlTitle(
					connection, entry.getValue() + StringPool.DASH + i);
			}

			_updateKBFolder(connection, entry.getKey(), uniqueUrlTitle);
		}
	}

	private String _findUniqueUrlTitle(Connection connection, String urlTitle)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from KBFolder where KBFolder.urlTitle like " +
					"?")) {

			preparedStatement.setString(1, urlTitle + "%");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					return urlTitle;
				}

				int kbFolderCount = resultSet.getInt(1);

				if (kbFolderCount == 0) {
					return urlTitle;
				}

				return null;
			}
		}
	}

	private Map<Long, String> _getInitialUrlTitles(Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select kbFolderId, name from KBFolder where " +
					"(KBFolder.urlTitle is null) or (KBFolder.urlTitle = '')");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Map<Long, String> urlTitles = new HashMap<>();

			while (resultSet.next()) {
				long kbFolderId = resultSet.getLong(1);
				String name = resultSet.getString(2);

				String urlTitle = _getUrlTitle(kbFolderId, name);

				urlTitles.put(kbFolderId, urlTitle);
			}

			return urlTitles;
		}
	}

	/**
	 * See {@link
	 * com.liferay.knowledge.base.util.KnowledgeBaseUtil#getUrlTitle(long,
	 * String)}
	 */
	private String _getUrlTitle(long id, String title) {
		if (title == null) {
			return String.valueOf(id);
		}

		title = StringUtil.toLowerCase(title.trim());

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			title = String.valueOf(id);
		}
		else {
			title = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				title);
		}

		return title.substring(0, 75);
	}

	private void _updateKBFolder(
			Connection connection, long kbFolderId, String urlTitle)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update KBFolder set KBFolder.urlTitle = ? where " +
					"KBFolder.kbFolderId = ?")) {

			preparedStatement.setString(1, urlTitle);
			preparedStatement.setLong(2, kbFolderId);

			preparedStatement.execute();
		}
	}

}