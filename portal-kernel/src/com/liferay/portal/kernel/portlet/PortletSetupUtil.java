/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletSetupUtil {

	public static JSONObject cssToJSONObject(PortletPreferences portletSetup)
		throws Exception {

		String css = portletSetup.getValue("portletSetupCss", StringPool.BLANK);

		return _toJSONObject(portletSetup, css);
	}

	public static JSONObject cssToJSONObject(
			PortletPreferences portletSetup, String css)
		throws Exception {

		return _toJSONObject(portletSetup, css);
	}

	public static String cssToJSONString(PortletPreferences portletSetup) {
		String css = portletSetup.getValue("portletSetupCss", StringPool.BLANK);

		try {
			JSONObject jsonObject = _toJSONObject(portletSetup, css);

			return jsonObject.toString();
		}
		catch (Exception exception) {
			css = null;

			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return css;
	}

	private static JSONObject _toJSONObject(
			PortletPreferences portletSetup, String css)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Transform CSS to JSON " + css);
		}

		JSONObject cssJSONObject = null;

		if (Validator.isNotNull(css)) {
			cssJSONObject = JSONFactoryUtil.createJSONObject(css);

			cssJSONObject.put("hasCssValue", true);
		}
		else {
			cssJSONObject = JSONFactoryUtil.createJSONObject();
		}

		JSONObject portletDataJSONObject = JSONFactoryUtil.createJSONObject();

		cssJSONObject.put("portletData", portletDataJSONObject);

		JSONObject titlesJSONObject = JSONFactoryUtil.createJSONObject();

		portletDataJSONObject.put("titles", titlesJSONObject);

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNotNull(languageId)) {
				String title = portletSetup.getValue(
					"portletSetupTitle_" + languageId, null);

				titlesJSONObject.put(languageId, title);
			}
		}

		String portletDecoratorId = GetterUtil.getString(
			portletSetup.getValue("portletSetupPortletDecoratorId", null));

		portletDataJSONObject.put("portletDecoratorId", portletDecoratorId);

		boolean useCustomTitle = GetterUtil.getBoolean(
			portletSetup.getValue("portletSetupUseCustomTitle", null));

		portletDataJSONObject.put("useCustomTitle", useCustomTitle);

		return cssJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletSetupUtil.class);

}