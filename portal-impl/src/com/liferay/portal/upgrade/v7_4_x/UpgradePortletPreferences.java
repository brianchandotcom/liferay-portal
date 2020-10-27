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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.impl.PortletPreferenceValueImpl;
import com.liferay.portal.upgrade.v7_4_x.util.PortletPreferencesTable;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.Preference;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Preston Crary
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"create table PortletPreferenceValue (mvccVersion LONG ",
				"default 0 not null, ctCollectionId LONG default 0 not null, ",
				"portletPreferenceValueId LONG not null, companyId LONG, ",
				"portletPreferencesId LONG, name VARCHAR(255) null, index_ ",
				"INTEGER, smallValue VARCHAR(255) null, largeValue TEXT null, ",
				"readOnly BOOLEAN, primary key (portletPreferenceValueId, ",
				"ctCollectionId))"));

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(
						StringBundler.concat(
							"select ctCollectionId, portletPreferencesId, ",
							"companyId, preferences from PortletPreferences ",
							"where CAST_CLOB_TEXT(preferences) != '",
							PortletConstants.DEFAULT_PREFERENCES,
							"' and preferences is not null")));
			PreparedStatement insertPreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into PortletPreferenceValue (mvccVersion, ",
							"ctCollectionId, portletPreferenceValueId, ",
							"companyId, portletPreferencesId, name, index_, ",
							"smallValue, largeValue, readOnly) values (0, ?, ",
							"?, ?, ?, ?, ?, ?, ?, ?)")));
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

				long ctCollectionId = resultSet.getLong("ctCollectionId");
				long companyId = resultSet.getLong("companyId");
				long portletPreferencesId = resultSet.getLong(
					"portletPreferencesId");

				for (Preference preference : preferenceMap.values()) {
					String[] values = preference.getValues();

					for (int i = 0; i < values.length; i++) {
						insertPreparedStatement.setLong(1, ctCollectionId);
						insertPreparedStatement.setLong(
							2,
							increment(PortletPreferenceValue.class.getName()));
						insertPreparedStatement.setLong(3, companyId);
						insertPreparedStatement.setLong(
							4, portletPreferencesId);
						insertPreparedStatement.setString(
							5, preference.getName());
						insertPreparedStatement.setInt(6, i);

						String value = values[i];

						if (value.length() <=
								PortletPreferenceValueImpl.
									SMALL_VALUE_MAX_LENGTH) {

							insertPreparedStatement.setString(7, value);
							insertPreparedStatement.setString(8, null);
						}
						else {
							insertPreparedStatement.setString(7, null);
							insertPreparedStatement.setString(8, value);
						}

						insertPreparedStatement.setBoolean(
							9, preference.isReadOnly());

						insertPreparedStatement.addBatch();
					}
				}
			}

			insertPreparedStatement.executeBatch();
		}

		alter(
			PortletPreferencesTable.class,
			new AlterTableDropColumn("preferences"));
	}

}