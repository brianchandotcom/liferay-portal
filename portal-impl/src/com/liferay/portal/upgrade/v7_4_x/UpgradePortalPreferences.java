/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.model.PortalPreferenceValue;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.model.impl.PortalPreferenceValueImpl;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.Preference;

import java.sql.PreparedStatement;

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

		processConcurrently(
			SQLTransformer.transform(
				StringBundler.concat(
					"select portalPreferencesId, preferences from ",
					"PortalPreferences where preferences not like '",
					PortletConstants.DEFAULT_PREFERENCES,
					"' and preferences is not null")),
			StringBundler.concat(
				"insert into PortalPreferenceValue (mvccVersion, ",
				"portalPreferenceValueId, portalPreferencesId, index_, key_, ",
				"largeValue, namespace, smallValue) values (0, ?, ?, ?, ?, ?, ",
				"?, ?)"),
			resultSet -> new Object[] {
				resultSet.getLong("portalPreferencesId"),
				resultSet.getString("preferences")
			},
			(values, preparedStatement) -> {
				long portalPreferencesId = (Long)values[0];
				String preferences = (String)values[1];

				_upgradePortalPreferences(
					portalPreferencesId, preferences, preparedStatement);
			},
			null);
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				"PortalPreferences", "preferences")
		};
	}

	private void _upgradePortalPreferences(
			long portalPreferencesId, String preferences,
			PreparedStatement preparedStatement)
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
						PortalPreferenceValueImpl.SMALL_VALUE_MAX_LENGTH) {

					largeValue = value;
				}
				else {
					smallValue = value;
				}

				preparedStatement.setLong(
					1, increment(PortalPreferenceValue.class.getName()));
				preparedStatement.setLong(2, portalPreferencesId);
				preparedStatement.setInt(3, i);
				preparedStatement.setString(4, key);
				preparedStatement.setString(5, largeValue);
				preparedStatement.setString(6, namespace);
				preparedStatement.setString(7, smallValue);

				preparedStatement.addBatch();
			}
		}
	}

}