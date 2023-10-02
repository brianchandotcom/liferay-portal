/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.model.impl.PortletPreferenceValueImpl;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.Preference;

import java.sql.PreparedStatement;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
			StringBundler.concat(
				"insert into PortletPreferenceValue (mvccVersion, ",
				"ctCollectionId, portletPreferenceValueId, companyId, ",
				"portletPreferencesId, index_, largeValue, name, readOnly, ",
				"smallValue) values (0, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
			resultSet -> new Object[] {
				resultSet.getLong("ctCollectionId"),
				resultSet.getLong("portletPreferencesId"),
				resultSet.getLong("companyId"),
				resultSet.getString("preferences")
			},
			(values, preparedStatement) -> {
				long ctCollectionId = (Long)values[0];
				long portletPreferencesId = (Long)values[1];
				long companyId = (Long)values[2];
				String preferences = (String)values[3];

				_upgradePortletPreferences(
					ctCollectionId, portletPreferencesId, companyId,
					preferences, preparedStatement);
			},
			null);
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				"PortletPreferences", "preferences")
		};
	}

	private void _upgradePortletPreferences(
			long ctCollectionId, long portletPreferencesId, long companyId,
			String preferences, PreparedStatement preparedStatement)
		throws Exception {

		if (preferences.isEmpty()) {
			return;
		}

		Map<String, Preference> preferenceMap =
			PortletPreferencesFactoryImpl.createPreferencesMap(preferences);

		if (preferenceMap.isEmpty()) {
			return;
		}

		for (Preference preference : preferenceMap.values()) {
			int index = 0;

			Set<String> valuesSet = new LinkedHashSet<>(
				Arrays.asList(preference.getValues()));

			for (String value : valuesSet) {
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
				preparedStatement.setInt(5, index);
				preparedStatement.setString(6, largeValue);
				preparedStatement.setString(7, preference.getName());
				preparedStatement.setBoolean(8, preference.isReadOnly());
				preparedStatement.setString(9, smallValue);

				preparedStatement.addBatch();

				index++;
			}
		}
	}

}