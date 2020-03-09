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
public class UpgradeUrlSubject extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("MBMessage", "urlSubject")) {
			alter(
				MBMessageTable.class,
				new AlterColumnType("urlSubject", "VARCHAR(255) null"));
		}

		_populateUrlSubject();
	}

	private String _findUniqueUrlSubject(Connection con, String urlSubject)
		throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
				"select count(*) from MBMessage where MBMessage.urlSubject " +
					"like ?");

			ps.setString(1, urlSubject + "%");

			rs = ps.executeQuery();

			if (!rs.next()) {
				return urlSubject;
			}

			int mbMessageCount = rs.getInt(1);

			if (mbMessageCount == 0) {
				return urlSubject;
			}

			return urlSubject + StringPool.DASH + mbMessageCount;
		}
		finally {
			DataAccess.cleanUp(ps);
			DataAccess.cleanUp(rs);
		}
	}

	private Map<Long, String> _getInitialUrlSubjects(Connection con)
		throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
				"select messageId, subject from MBMessage where " +
					"(MBMessage.urlSubject is null) or (MBMessage.urlSubject " +
						"= '')");

			rs = ps.executeQuery();

			Map<Long, String> urlSubjects = new HashMap<>();

			while (rs.next()) {
				long messageId = rs.getLong(1);
				String subject = rs.getString(2);

				String urlSubject = _getUrlSubject(messageId, subject);

				urlSubjects.put(messageId, urlSubject);
			}

			return urlSubjects;
		}
		finally {
			DataAccess.cleanUp(ps);
			DataAccess.cleanUp(rs);
		}
	}

	private String _getUrlSubject(long id, String subject) {
		if (subject == null) {
			return String.valueOf(id);
		}

		subject = StringUtil.toLowerCase(subject.trim());

		if (Validator.isNull(subject) || Validator.isNumber(subject) ||
			subject.equals("rss")) {

			subject = String.valueOf(id);
		}
		else {
			subject = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				subject);
		}

		return subject.substring(0, Math.min(subject.length(), 254));
	}

	private void _populateUrlSubject() throws SQLException {
		Map<Long, String> urlSubjects = _getInitialUrlSubjects(connection);

		for (Map.Entry<Long, String> entry : urlSubjects.entrySet()) {
			String uniqueUrlSubject = _findUniqueUrlSubject(
				connection, entry.getValue());

			_updateMBMessage(connection, entry.getKey(), uniqueUrlSubject);
		}
	}

	private void _updateMBMessage(
			Connection con, long messageId, String urlSubject)
		throws SQLException {

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(
				"update MBMessage set MBMessage.urlSubject = ? where " +
					"MBMessage.messageId = ?");

			ps.setString(1, urlSubject);
			ps.setLong(2, messageId);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

}