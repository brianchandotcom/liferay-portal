/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			PortletPreferences portletPreferences)
		throws Exception {

		String customCSSClassName = StringPool.BLANK;

		String css = portletPreferences.getValue(
			"portletSetupCss", StringPool.BLANK);

		if (Validator.isNotNull(css)) {
			JSONObject cssJSONObject = PortletSetupUtil.cssToJSONObject(
				portletPreferences, css);

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
		PortletPreferences portletPreferences, String languageId) {

		if (!isUseCustomTitle(portletPreferences)) {
			return null;
		}

		return portletPreferences.getValue(
			"portletSetupTitle_" + languageId, null);
	}

	public static Map<Locale, String> getPortletTitleMap(
		PortletPreferences portletPreferences) {

		if (!isUseCustomTitle(portletPreferences)) {
			return null;
		}

		Map<Locale, String> map = new HashMap<>();

		boolean empty = true;

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String portletTitle = GetterUtil.getString(
				getPortletTitle(
					portletPreferences, LocaleUtil.toLanguageId(locale)));

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

	protected static boolean isUseCustomTitle(
		PortletPreferences portletPreferences) {

		return GetterUtil.getBoolean(
			portletPreferences.getValue("portletSetupUseCustomTitle", null));
	}

}