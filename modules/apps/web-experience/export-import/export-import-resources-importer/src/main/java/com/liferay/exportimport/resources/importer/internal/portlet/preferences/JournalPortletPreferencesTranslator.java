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
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "portlet.preferences.translator.portlet.id=com_liferay_journal_content_web_portlet_JournalContentPortlet",
	service = PortletPreferencesTranslator.class
)
public class JournalPortletPreferencesTranslator
	implements PortletPreferencesTranslator {

	@Override
	public void translate(
			JSONObject portletPreferencesJSONObject, String key,
			PortletPreferences portletPreferences)
		throws PortletException {

		String value = portletPreferencesJSONObject.getString(key);

		if (key.equals("articleId")) {
			String articleId = FileUtil.stripExtension(value);

			articleId = StringUtil.toUpperCase(articleId);

			value = StringUtil.replace(
				articleId, CharPool.SPACE, CharPool.DASH);
		}

		portletPreferences.setValue(key, value);
	}

}