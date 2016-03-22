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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Miguel Pastor
 */
public abstract class ConcurrentUpgradePortletPreferences
	extends BaseUpgradePortletPreferences {

	@Override
	protected void updatePortletPreferences() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(4);

			sb.append("select portletPreferencesId, ownerId, ownerType, ");
			sb.append("plid, portletId, preferences from PortletPreferences");

			String whereClause = getUpdatePortletPreferencesWhereClause();

			if (Validator.isNotNull(whereClause)) {
				sb.append(" where ");
				sb.append(whereClause);
			}

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update PortletPreferences set preferences = ? " +
							"where portletPreferencesId = ?");
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from PortletPreferences where " +
							"portletPreferencesId = ?");
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long portletPreferencesId = rs.getLong(
						"portletPreferencesId");
					long ownerId = rs.getLong("ownerId");
					int ownerType = rs.getInt("ownerType");
					long plid = rs.getLong("plid");
					String portletId = rs.getString("portletId");
					String preferences = GetterUtil.getString(
						rs.getString("preferences"));

					long companyId = 0;

					if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
						companyId = getCompanyId(
							"select companyId from PortletItem where " +
								"portletItemId = ?",
							ownerId);
					}
					else if (ownerType ==
								PortletKeys.PREFS_OWNER_TYPE_COMPANY) {

						companyId = ownerId;
					}
					else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
						Object[] group = getGroup(ownerId);

						if (group != null) {
							companyId = (Long)group[1];
						}
					}
					else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
						Object[] layout = getLayout(plid);

						if (layout != null) {
							companyId = (Long)layout[1];
						}
					}
					else if (ownerType ==
								PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION) {

						companyId = getCompanyId(
							"select companyId from Organization_ where " +
								"organizationId = ?",
							ownerId);
					}
					else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
						companyId = getCompanyId(
							"select companyId from User_ where userId = ?",
							ownerId);
					}
					else {
						throw new UnsupportedOperationException(
							"Unsupported owner type " + ownerType);
					}

					if (companyId > 0) {
						String newPreferences = upgradePreferences(
							companyId, ownerId, ownerType, plid, portletId,
							preferences);

						if (!preferences.equals(newPreferences)) {
							ps2.setString(1, newPreferences);
							ps2.setLong(2, portletPreferencesId);

							ps2.addBatch();
						}
					}
					else {
						ps3.setLong(1, portletPreferencesId);

						ps3.addBatch();
					}
				}

				ps2.executeBatch();
				ps3.executeBatch();
			}
		}
	}

}