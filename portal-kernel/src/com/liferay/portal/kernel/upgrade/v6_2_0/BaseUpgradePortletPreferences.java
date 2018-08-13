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

package com.liferay.portal.kernel.upgrade.v6_2_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Minhchau Dang
 * @author Alberto Chaparro
 */
public abstract class BaseUpgradePortletPreferences
	extends com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences {

	protected void deletePortletPreferencesByOwnerType(
			int ownerType, String whereClause, String leftColumnName,
			String[]... joinTables)
		throws Exception {

		StringBundler sb = new StringBundler(9 * joinTables.length + 5);

		sb.append("delete from PortletPreferences where ownerType = ");
		sb.append(String.valueOf(ownerType));

		for (String[] joinTable : joinTables) {
			sb.append(" and not exists (select 1 from ");
			sb.append(joinTable[1]);
			sb.append(" where PortletPreferences.");
			sb.append(leftColumnName);
			sb.append(" = ");
			sb.append(joinTable[1]);
			sb.append(StringPool.PERIOD);
			sb.append(joinTable[0]);
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		if (Validator.isNotNull(whereClause)) {
			sb.append(" and (");
			sb.append(whereClause);
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		runSQL(sb.toString());
	}

	protected void updatePortletPreferences() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String whereClause = getUpdatePortletPreferencesWhereClause();

			deletePortletPreferencesByOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, whereClause, "ownerId",
				new String[] {"portletItemId", "PortletItem"});

			deletePortletPreferencesByOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, whereClause, "ownerId",
				new String[] {"companyId", "Company"});

			deletePortletPreferencesByOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_GROUP, whereClause, "ownerId",
				new String[] {"groupId", "Group_"});

			deletePortletPreferencesByOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, whereClause, "plid",
				new String[] {"plid", "Layout"},
				new String[] {"layoutRevisionId", "LayoutRevision"});

			deletePortletPreferencesByOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION, whereClause,
				"ownerId", new String[] {"organizationId", "Organization_"});

			deletePortletPreferencesByOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_USER, whereClause, "ownerId",
				new String[] {"userId", "User_"});

			updatePortletPreferencesWithOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, whereClause, "ownerId",
				"PortletItem", "portletItemId");

			updatePortletPreferencesWithOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, whereClause, "ownerId",
				"Company", "companyId");

			updatePortletPreferencesWithOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_GROUP, whereClause, "ownerId",
				"Group_", "groupId");

			updatePortletPreferencesWithOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, whereClause, "plid",
				"Layout", "plid");

			updatePortletPreferencesWithOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, whereClause, "plid",
				"LayoutRevision", "layoutRevisionId");

			updatePortletPreferencesWithOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION, whereClause,
				"ownerId", "Organization_", "organizationId");

			updatePortletPreferencesWithOwnerType(
				PortletKeys.PREFS_OWNER_TYPE_USER, whereClause, "ownerId",
				"User_", "userId");
		}
	}

	protected void updatePortletPreferencesWithOwnerType(
			int ownerType, String whereClause, String leftColumnName,
			String rightTableName, String rightColumnName)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(" where PortletPreferences.ownerType = ");
		sb.append(String.valueOf(ownerType));

		if (Validator.isNotNull(whereClause)) {
			sb.append(" and (");
			sb.append(whereClause);
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		String sql = StringBundler.concat(
			"select PortletPreferences.portletPreferencesId as ",
			"portletPreferencesId, PortletPreferences.ownerId as ownerId, ",
			"PortletPreferences.plid as plid, PortletPreferences.portletId as ",
			"portletId, PortletPreferences.preferences as preferences, ",
			rightTableName,
			".companyId as companyId from PortletPreferences inner join ",
			rightTableName, " on PortletPreferences.", leftColumnName, " = ",
			rightTableName, StringPool.PERIOD, rightColumnName, sb.toString());

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps1 = connection.prepareStatement(sql);
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update PortletPreferences set preferences = ? where " +
							"portletPreferencesId = ?");
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long portletPreferencesId = rs.getLong(
						"portletPreferencesId");

					long ownerId = rs.getLong("ownerId");
					long plid = rs.getLong("plid");
					String portletId = rs.getString("portletId");
					String preferences = GetterUtil.getString(
						rs.getString("preferences"));

					String newPreferences = upgradePreferences(
						companyId, ownerId, ownerType, plid, portletId,
						preferences);

					if (!preferences.equals(newPreferences)) {
						ps2.setString(1, newPreferences);
						ps2.setLong(2, portletPreferencesId);

						ps2.addBatch();
					}
				}

				ps2.executeBatch();
			}
		}
	}

}