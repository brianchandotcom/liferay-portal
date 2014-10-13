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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;

import javax.portlet.PortletPreferences;

/**
 * @author Alexander Chow
 */
public class UpgradeSearch extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {PortletKeys.SEARCH};
	}

	protected JSONObject upgradeDataJSONObject(JSONObject dataJSONObject)
		throws Exception {

		JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

		boolean hasBookmarksEntry = false;
		boolean hasDLFileEntry = false;
		boolean hasJournalArticle = false;

		for (int i = 0; i < valuesJSONArray.length(); i++) {
			String value = valuesJSONArray.getString(i);

			if (value.equals(
					"com.liferay.portlet.bookmarks.model.BookmarksEntry")) {

				hasBookmarksEntry = true;
			}

			if (value.equals(DLFileEntryConstants.getClassName())) {
				hasDLFileEntry = true;
			}

			if (value.equals(JournalArticle.class.getName())) {
				hasJournalArticle = true;
			}
		}

		if (!hasBookmarksEntry && !hasDLFileEntry && !hasJournalArticle) {
			return null;
		}

		if (hasBookmarksEntry) {
			valuesJSONArray.put(
				"com.liferay.portlet.bookmarks.model.BookmarksFolder");
		}

		if (hasDLFileEntry) {
			valuesJSONArray.put(DLFolderConstants.getClassName());
		}

		if (hasJournalArticle) {
			valuesJSONArray.put(JournalFolder.class.getName());
		}

		dataJSONObject.put("values", valuesJSONArray);

		return dataJSONObject;
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String searchConfiguration = portletPreferences.getValue(
			"searchConfiguration", null);

		if (Validator.isNull(searchConfiguration)) {
			return null;
		}

		JSONObject searchConfigurationJSONObject =
			JSONFactoryUtil.createJSONObject(searchConfiguration);

		JSONArray oldFacetsJSONArray =
			searchConfigurationJSONObject.getJSONArray("facets");

		if (oldFacetsJSONArray == null) {
			return null;
		}

		JSONArray newFacetsJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < oldFacetsJSONArray.length(); i++) {
			JSONObject oldFacetJSONObject = oldFacetsJSONArray.getJSONObject(i);

			String fieldName = oldFacetJSONObject.getString("fieldName");

			if (fieldName.equals("entryClassName")) {
				JSONObject oldDataJSONObject = oldFacetJSONObject.getJSONObject(
					"data");

				JSONObject newDataJSONObject = upgradeDataJSONObject(
					oldDataJSONObject);

				if (newDataJSONObject == null) {
					return null;
				}

				oldFacetJSONObject.put("data", newDataJSONObject);
			}

			newFacetsJSONArray.put(oldFacetJSONObject);
		}

		searchConfigurationJSONObject.put("facets", newFacetsJSONArray);

		portletPreferences.setValue(
			"searchConfiguration", searchConfigurationJSONObject.toString());

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}