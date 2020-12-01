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
				"portletPreferencesId LONG, index_ INTEGER, largeValue TEXT ",
				"null, name VARCHAR(255) null, readOnly BOOLEAN, smallValue ",
				"VARCHAR(255) null, primary key (portletPreferenceValueId, ",
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
							"companyId, portletPreferencesId, index_, ",
							"largeValue, name, readOnly, smallValue) values ",
							"(0, ?, ?, ?, ?, ?, ?, ?, ?, ?)")));
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
						String value = values[i];

						String largeValue = null;
						String smallValue = null;

						if (value.length() >
								PortletPreferenceValueImpl.
									SMALL_VALUE_MAX_LENGTH) {

							largeValue = value;
						}
						else {
							smallValue = value;
						}

						insertPreparedStatement.setLong(1, ctCollectionId);
						insertPreparedStatement.setLong(
							2,
							increment(PortletPreferenceValue.class.getName()));
						insertPreparedStatement.setLong(3, companyId);
						insertPreparedStatement.setLong(
							4, portletPreferencesId);
						insertPreparedStatement.setInt(5, i);
						insertPreparedStatement.setString(6, largeValue);
						insertPreparedStatement.setString(
							7, preference.getName());
						insertPreparedStatement.setBoolean(
							8, preference.isReadOnly());
						insertPreparedStatement.setString(9, smallValue);

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