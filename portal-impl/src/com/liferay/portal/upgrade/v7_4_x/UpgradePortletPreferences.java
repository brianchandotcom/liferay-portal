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

		processConcurrently(
			SQLTransformer.transform(
				StringBundler.concat(
					"select ctCollectionId, portletPreferencesId, companyId, ",
					"preferences from PortletPreferences where preferences ",
					"not like '", PortletConstants.DEFAULT_PREFERENCES,
					"%' and preferences is not null")),
			resultSet -> new Object[] {
				resultSet.getLong("ctCollectionId"),
				resultSet.getLong("portletPreferencesId"),
				resultSet.getLong("companyId"),
				resultSet.getString("preferences")
			},
			values -> {
				long ctCollectionId = (Long)values[0];
				long portletPreferencesId = (Long)values[1];
				long companyId = (Long)values[2];
				String preferences = (String)values[3];

				_upgradePortletPreferences(
					ctCollectionId, portletPreferencesId, companyId,
					preferences);
			},
			null);

		alter(
			PortletPreferencesTable.class,
			new AlterTableDropColumn("preferences"));
	}

	private void _upgradePortletPreferences(
			long ctCollectionId, long portletPreferencesId, long companyId,
			String preferences)
		throws Exception {

		if (preferences.isEmpty()) {
			return;
		}

		Map<String, Preference> preferenceMap =
			PortletPreferencesFactoryImpl.createPreferencesMap(preferences);

		if (preferenceMap.isEmpty()) {
			return;
		}

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into PortletPreferenceValue (mvccVersion, ",
							"ctCollectionId, portletPreferenceValueId, ",
							"companyId, portletPreferencesId, index_, ",
							"largeValue, name, readOnly, smallValue) values ",
							"(0, ?, ?, ?, ?, ?, ?, ?, ?, ?)")))) {

			for (Preference preference : preferenceMap.values()) {
				String[] values = preference.getValues();

				for (int i = 0; i < values.length; i++) {
					String value = values[i];

					String largeValue = null;
					String smallValue = null;

					if (value.length() >
							PortletPreferenceValueImpl.SMALL_VALUE_MAX_LENGTH) {

						largeValue = value;
					}
					else {
						smallValue = value;
					}

					preparedStatement.setLong(1, ctCollectionId);
					preparedStatement.setLong(
						2, increment(PortletPreferenceValue.class.getName()));
					preparedStatement.setLong(3, companyId);
					preparedStatement.setLong(4, portletPreferencesId);
					preparedStatement.setInt(5, i);
					preparedStatement.setString(6, largeValue);
					preparedStatement.setString(7, preference.getName());
					preparedStatement.setBoolean(8, preference.isReadOnly());
					preparedStatement.setString(9, smallValue);

					preparedStatement.addBatch();
				}
			}

			preparedStatement.executeBatch();
		}
	}

}