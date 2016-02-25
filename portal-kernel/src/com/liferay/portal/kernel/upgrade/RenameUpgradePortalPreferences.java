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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public abstract class RenameUpgradePortalPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, String> preferenceNamesMap = getPreferenceNamesMap();

		for (String oldName : preferenceNamesMap.keySet()) {
			updatePreferences(
				"PortalPreferences", "portalPreferencesId", oldName,
				preferenceNamesMap.get(oldName));
		}
	}

	protected abstract Map<String, String> getPreferenceNamesMap();

	protected void updatePreferences(
			String tableName, String primaryKeyColumnName, String oldValue,
			String newValue)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBundler sb = new StringBundler(9);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set preferences = replace(preferences, '");
		sb.append(oldValue);
		sb.append("', '");
		sb.append(newValue);
		sb.append("') where preferences like '%");
		sb.append(oldValue);
		sb.append("%'");

		try {
			runSQL(sb.toString());
		}
		catch (Exception e) {
			sb = new StringBundler(7);

			sb.append("select ");
			sb.append(primaryKeyColumnName);
			sb.append(", preferences from ");
			sb.append(tableName);
			sb.append(" where preferences like '%");
			sb.append(oldValue);
			sb.append("%'");

			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long primaryKey = rs.getLong(primaryKeyColumnName);
				String preferences = rs.getString("preferences");

				updatePreferences(
					tableName, primaryKeyColumnName, oldValue, newValue,
					primaryKey, preferences);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void updatePreferences(
			String tableName, String primaryKeyColumnName, String oldValue,
			String newValue, long primaryKey, String preferences)
		throws Exception {

		preferences = StringUtil.replace(preferences, oldValue, newValue);

		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBundler sb = new StringBundler(5);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set preferences = ? where ");
		sb.append(primaryKeyColumnName);
		sb.append(" = ?");

		try {
			ps = connection.prepareStatement(sb.toString());

			ps.setString(1, preferences);
			ps.setLong(2, primaryKey);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

}