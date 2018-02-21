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

package com.liferay.portlet.configuration.kernel.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletSetupUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletConfigurationUtil {

	public static String getPortletCustomCSSClassName(
			PortletPreferences portletSetup)
		throws Exception {

		String customCSSClassName = StringPool.BLANK;

		String css = portletSetup.getValue("portletSetupCss", StringPool.BLANK);

		if (Validator.isNotNull(css)) {
			JSONObject cssJSONObject = PortletSetupUtil.cssToJSONObject(
				portletSetup, css);

			JSONObject advancedDataJSONObject = cssJSONObject.getJSONObject(
				"advancedData");

			if (advancedDataJSONObject != null) {
				customCSSClassName = advancedDataJSONObject.getString(
					"customCSSClassName");
			}
		}

		return customCSSClassName;
	}

	public static String getPortletTitle(
		PortletPreferences portletSetup, String languageId) {

		if (!isUseCustomTitle(portletSetup)) {
			return null;
		}

		return portletSetup.getValue("portletSetupTitle_" + languageId, null);
	}

	public static Map<Locale, String> getPortletTitleMap(
		PortletPreferences portletSetup) {

		if (!isUseCustomTitle(portletSetup)) {
			return null;
		}

		Map<Locale, String> map = new HashMap<>();

		boolean empty = true;

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String portletTitle = GetterUtil.getString(
				getPortletTitle(portletSetup, LocaleUtil.toLanguageId(locale)));

			map.put(locale, portletTitle);

			if (Validator.isNotNull(portletTitle)) {
				empty = false;
			}
		}

		if (!empty) {
			return map;
		}

		return null;
	}

	protected static boolean isUseCustomTitle(PortletPreferences portletSetup) {
		return GetterUtil.getBoolean(
			portletSetup.getValue("portletSetupUseCustomTitle", null));
	}

}