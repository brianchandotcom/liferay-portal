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

package com.liferay.exportimport.resources.importer.internal.portlet.preferences;

import com.liferay.exportimport.resources.importer.portlet.preferences.PortletPreferencesTranslator;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "portlet.preferences.translator.portlet.id=com_liferay_rss_web_portlet_RSSPortlet",
	service = PortletPreferencesTranslator.class
)
public class RSSPortletPreferencesTranslator
	implements PortletPreferencesTranslator {

	@Override
	public void translate(
			JSONObject portletPreferencesJSONObject, String key,
			PortletPreferences portletPreferences)
		throws PortletException {

		if (!key.equals("titles") && !key.equals("urls")) {
			String value = portletPreferencesJSONObject.getString(key);

			portletPreferences.setValue(key, value);

			return;
		}

		List<String> valuesList = new ArrayList<>();

		JSONObject jsonObject = portletPreferencesJSONObject.getJSONObject(key);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String jsonObjectKey = iterator.next();

			valuesList.add(jsonObject.getString(jsonObjectKey));
		}

		String[] values = valuesList.toArray(new String[0]);

		portletPreferences.setValues(key, values);
	}

}