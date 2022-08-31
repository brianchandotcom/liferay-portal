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

package com.liferay.portal.upgrade.v7_1_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;

/**
 * @author Christopher Kian
 */
public class UpgradePortalPreferences extends UpgradeProcess {

	protected String convertDefaultReminderQueries(
			String localizedPreference, String preferences)
		throws Exception {

		Document document = SAXReaderUtil.read(preferences);

		Element rootElement = document.getRootElement();

		Iterator<Element> iterator = rootElement.elementIterator();

		while (iterator.hasNext()) {
			Element preferenceElement = iterator.next();

			String preferenceName = preferenceElement.elementText("name");

			if (preferenceName.equals("reminderQueries")) {
				Element defaultReminderQueryElement =
					preferenceElement.createCopy();

				Element nameElement = defaultReminderQueryElement.element(
					"name");

				defaultReminderQueryElement.remove(nameElement);

				nameElement.setText(localizedPreference);

				defaultReminderQueryElement.add(nameElement);

				rootElement.add(defaultReminderQueryElement);

				break;
			}
		}

		return document.formattedString(StringPool.DOUBLE_SPACE);
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeOrganizationReminderQueries();
	}

	protected void upgradeOrganizationReminderQueries() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select PortalPreferences.portalPreferencesId, ",
					"PortalPreferences.preferences, Organization_.companyId ",
					"from PortalPreferences inner join Organization_ on ",
					"PortalPreferences.ownerId = Organization_.organizationId ",
					"where PortalPreferences.ownerType = ",
					PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION,
					" and preferences like '%reminderQueries%'"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update PortalPreferences set preferences = ? where " +
						"portalPreferencesId = ?")) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				String preferences = resultSet.getString("preferences");

				String defaultLanguageId =
					UpgradeProcessUtil.getDefaultLanguageId(companyId);

				String localizedPreference =
					"reminderQueries_" + defaultLanguageId;

				if (preferences.contains(localizedPreference)) {
					continue;
				}

				preparedStatement2.setString(
					1,
					convertDefaultReminderQueries(
						localizedPreference, preferences));

				long portalPreferencesId = resultSet.getLong(
					"portalPreferencesId");

				preparedStatement2.setLong(2, portalPreferencesId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}