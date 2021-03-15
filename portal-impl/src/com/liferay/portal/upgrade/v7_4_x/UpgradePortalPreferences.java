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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.PortalPreferenceValue;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.impl.PortalPreferenceValueImpl;
import com.liferay.portal.upgrade.v7_4_x.util.PortalPreferencesTable;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.Preference;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Preston Crary
 */
public class UpgradePortalPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"create table PortalPreferenceValue (mvccVersion LONG default ",
				"0 not null, portalPreferenceValueId LONG not null primary ",
				"key, portalPreferencesId LONG, index_ INTEGER, key_ ",
				"VARCHAR(255) null, largeValue TEXT null, namespace ",
				"VARCHAR(255) null, smallValue VARCHAR(255) null)"));

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(
						StringBundler.concat(
							"select portalPreferencesId, preferences from ",
							"PortalPreferences where ",
							"CAST_CLOB_TEXT(preferences) != '",
							PortletConstants.DEFAULT_PREFERENCES,
							"' and preferences is not null")));
			PreparedStatement insertPreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into PortalPreferenceValue (mvccVersion, ",
							"portalPreferenceValueId, portalPreferencesId, ",
							"index_, key_, largeValue, namespace, smallValue) ",
							"values (0, ?, ?, ?, ?, ?, ?, ?)")));
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String preferences = resultSet.getString("preferences");

				if (preferences.isEmpty()) {
					continue;
				}

				Map<String, Preference> preferenceMap =
					PortletPreferencesFactoryImpl.createPreferencesMap(
						preferences);

				if (preferenceMap.isEmpty()) {
					continue;
				}

				long portalPreferencesId = resultSet.getLong(
					"portalPreferencesId");

				for (Preference preference : preferenceMap.values()) {
					String namespace = null;

					String key = preference.getName();

					int index = key.indexOf(CharPool.POUND);

					if (index > 0) {
						namespace = key.substring(0, index);

						key = key.substring(index + 1);
					}

					String[] values = preference.getValues();

					for (int i = 0; i < values.length; i++) {
						String value = values[i];

						String largeValue = null;
						String smallValue = null;

						if (value.length() >
								PortalPreferenceValueImpl.
									SMALL_VALUE_MAX_LENGTH) {

							largeValue = value;
						}
						else {
							smallValue = value;
						}

						insertPreparedStatement.setLong(
							1,
							increment(PortalPreferenceValue.class.getName()));
						insertPreparedStatement.setLong(2, portalPreferencesId);
						insertPreparedStatement.setInt(3, i);
						insertPreparedStatement.setString(4, key);
						insertPreparedStatement.setString(5, largeValue);
						insertPreparedStatement.setString(6, namespace);
						insertPreparedStatement.setString(7, smallValue);

						insertPreparedStatement.addBatch();
					}
				}
			}

			insertPreparedStatement.executeBatch();
		}

		alter(
			PortalPreferencesTable.class,
			new AlterTableDropColumn("preferences"));
	}

}