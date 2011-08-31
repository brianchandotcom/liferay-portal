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
			"portletSetupCss", StringPool.BLANK);

		try {
			return _toJSONObject(portletSetup, css).toString();
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

		JSONObject jsonObj = null;

		if (Validator.isNotNull(css)) {
			jsonObj = JSONFactoryUtil.createJSONObject(css);

			jsonObj.put("cssValue", true);
		}
		else {
			jsonObj = JSONFactoryUtil.createJSONObject();
		}

		JSONObject portletData = JSONFactoryUtil.createJSONObject();

		jsonObj.put("portletData", portletData);

		JSONObject titles = JSONFactoryUtil.createJSONObject();

		portletData.put("titles", titles);

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (int i = 0; i < locales.length; i++) {
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			String title = portletSetup.getValue(
				"portletSetupTitle_" + languageId, null);

			if (Validator.isNotNull(languageId)) {
				titles.put(languageId, title);
			}
		}

		boolean useCustomTitle = GetterUtil.getBoolean(
			portletSetup.getValue("portletSetupUseCustomTitle", null));
		boolean showBorders = GetterUtil.getBoolean(
			portletSetup.getValue("portletSetupShowBorders", null), true);
		String linkToLayoutUuid = GetterUtil.getString(
			portletSetup.getValue("portletSetupLinkToLayoutUuid", null));

		portletData.put("useCustomTitle", useCustomTitle);
		portletData.put("showBorders", showBorders);
		portletData.put("portletLinksTarget", linkToLayoutUuid);

		return jsonObj;
	}

	private static Log _log = LogFactoryUtil.getLog(PortletSetupUtil.class);

}