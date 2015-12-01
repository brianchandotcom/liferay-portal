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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.exportimport.staging.Staging;
import com.liferay.portlet.exportimport.staging.StagingConstants;
import com.liferay.util.xml.XMLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Joshua Gok
 */
public class UpgradePortalPreferences extends UpgradeProcess {

	protected String convertStagingPreferencesToJSON(String preferences)
		throws Exception {

		Document newDocument = SAXReaderUtil.createDocument();

		Element newRootElement = SAXReaderUtil.createElement(
			"portlet-preferences");

		newDocument.add(newRootElement);

		Document document = SAXReaderUtil.read(preferences);

		Element rootElement = document.getRootElement();

		Iterator<Element> iterator = rootElement.elementIterator();

		Map<String, String> stagingPreferencesMap = new HashMap<>();

		while (iterator.hasNext()) {
			Element preferenceElement = iterator.next();

			String preferenceName = preferenceElement.elementText("name");

			if (preferenceName.contains(Staging.class.getName())) {
				String preferenceValue = preferenceElement.elementText("value");

				int index = preferenceName.indexOf(StringPool.POUND);

				stagingPreferencesMap.put(
					preferenceName.substring(index + 1), preferenceValue);
			}
			else {
				newRootElement.add(preferenceElement.createCopy());
			}
		}

		JSONArray stagingPreferencesJsonArray =
			JSONFactoryUtil.createJSONArray();

		for (String key : stagingPreferencesMap.keySet()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(key, stagingPreferencesMap.get(key));

			stagingPreferencesJsonArray.put(jsonObject);
		}

		if (stagingPreferencesJsonArray.length() > 0) {
			Element preferenceElement = SAXReaderUtil.createElement(
				"preference");

			Element nameElement = SAXReaderUtil.createElement("name");

			String stagingPreferencesName =
				Staging.class.getName() + StringPool.POUND +
				StagingConstants.STAGING_RECENT_LAYOUT_IDS_MAP;

			nameElement.setText(stagingPreferencesName);

			Element valueElement = SAXReaderUtil.createElement("value");

			valueElement.setText(stagingPreferencesJsonArray.toString());

			preferenceElement.add(nameElement);
			preferenceElement.add(valueElement);

			newRootElement.add(preferenceElement);
		}

		return XMLUtil.formatXML(newDocument);
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradePortalPreferences();
	}

	protected void upgradePortalPreferences() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select portalPreferencesId, preferences from " +
					"PortalPreferences");

			rs = ps.executeQuery();

			while (rs.next()) {
				long portalPreferencesId = rs.getLong("portalPreferencesId");

				String preferences = rs.getString("preferences");

				upgradeUserStagingPreferences(portalPreferencesId, preferences);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeUserStagingPreferences(
			long portalPreferencesId, String preferences)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update PortalPreferences set preferences = ? where " +
					"portalPreferencesId = ?");

			ps.setString(1, convertStagingPreferencesToJSON(preferences));
			ps.setLong(2, portalPreferencesId);
			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

}