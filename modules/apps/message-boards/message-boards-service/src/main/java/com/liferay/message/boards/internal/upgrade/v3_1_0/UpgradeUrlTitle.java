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

package com.liferay.message.boards.internal.upgrade.v3_1_0;

import com.liferay.message.boards.internal.upgrade.v3_1_0.util.MBMessageTable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class UpgradeUrlTitle extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("MBMessage", "urlTitle")) {
			alter(
				MBMessageTable.class,
				new AlterColumnType("urlTitle", "VARCHAR(255) null"));
		}

		_populateUrlTitle();
	}

	private String _findUniqueUrlTitle(Connection con, String urlTitle)
		throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
				"select count(*) from MBMessage where MBMessage.urlTitle " +
					"like ?");

			ps.setString(1, urlTitle + "%");

			rs = ps.executeQuery();

			if (!rs.next()) {
				return urlTitle;
			}

			int mbMessageCount = rs.getInt(1);

			if (mbMessageCount == 0) {
				return urlTitle;
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(ps);
			DataAccess.cleanUp(rs);
		}
	}

	private Map<Long, String> _getInitialUrlTitles(Connection con)
		throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
				"select messageId, subject from MBMessage where " +
					"(MBMessage.urlTitle is null) or (MBMessage.urlTitle = " +
						"'')");

			rs = ps.executeQuery();

			Map<Long, String> urlTitles = new HashMap<>();

			while (rs.next()) {
				long messageId = rs.getLong(1);
				String subject = rs.getString(2);

				String urlTitle = _getUrlTitle(messageId, subject);

				urlTitles.put(messageId, urlTitle);
			}

			return urlTitles;
		}
		finally {
			DataAccess.cleanUp(ps);
			DataAccess.cleanUp(rs);
		}
	}

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

		return title.substring(0, Math.min(title.length(), 254));
	}

	private void _populateUrlTitle() throws SQLException {
		Map<Long, String> urlTitles = _getInitialUrlTitles(connection);

		for (Map.Entry<Long, String> entry : urlTitles.entrySet()) {
			String uniqueUrlTitle = _findUniqueUrlTitle(
				connection, entry.getValue());

			for (int i = 1; uniqueUrlTitle == null; i++) {
				uniqueUrlTitle = _findUniqueUrlTitle(
					connection, entry.getValue() + StringPool.DASH + i);
			}

			_updateMBMessage(connection, entry.getKey(), uniqueUrlTitle);
		}
	}

	private void _updateMBMessage(
			Connection con, long messageId, String urlTitle)
		throws SQLException {

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(
				"update MBMessage set MBMessage.urlTitle = ? where " +
					"MBMessage.messageId = ?");

			ps.setString(1, urlTitle);
			ps.setLong(2, messageId);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

}