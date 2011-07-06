/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletSetupUtil {

	public static final JSONObject cssToJSON(
			PortletPreferences portletSetup, String css)
		throws Exception {

		return _toJSONObject(portletSetup, css);
	}

	public static final String cssToString(PortletPreferences portletSetup) {
		String css = portletSetup.getValue(
			"portlet-setup-css", StringPool.BLANK);

		try {
			if (Validator.isNotNull(css)) {
				return _toJSONObject(portletSetup, css).toString();
			}
		}
		catch (Exception e) {
			css = null;

			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		return css;
	}

	private static final JSONObject _toJSONObject(
			PortletPreferences portletSetup, String css)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Transform CSS to JSON " + css);
		}

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(css);

		JSONObject portletData = JSONFactoryUtil.createJSONObject();

		jsonObj.put("portletData", portletData);

		JSONObject titles = JSONFactoryUtil.createJSONObject();

		portletData.put("titles", titles);

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (int i = 0; i < locales.length; i++) {
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			String title = portletSetup.getValue(
				"portlet-setup-title-" + languageId, null);

			if (Validator.isNotNull(languageId)) {
				titles.put(languageId, title);
			}
		}

		boolean useCustomTitle = GetterUtil.getBoolean(
			portletSetup.getValue("portlet-setup-use-custom-title", null));
		boolean showBorders = GetterUtil.getBoolean(
			portletSetup.getValue("portlet-setup-show-borders", null), true);
		String portletInitialWindowState = GetterUtil.getString(
			portletSetup.getValue("lfr-portlet-initial-window-state",
				WindowState.NORMAL.toString()));
		String linkToLayoutUuid = GetterUtil.getString(
			portletSetup.getValue("portlet-setup-link-to-layout-uuid", null));

		portletData.put("useCustomTitle", useCustomTitle);
		portletData.put("showBorders", showBorders);
		portletData.put("portletInitialWindowState", portletInitialWindowState);
		portletData.put("portletLinksTarget", linkToLayoutUuid);

		return jsonObj;
	}

	private static Log _log = LogFactoryUtil.getLog(PortletSetupUtil.class);

}