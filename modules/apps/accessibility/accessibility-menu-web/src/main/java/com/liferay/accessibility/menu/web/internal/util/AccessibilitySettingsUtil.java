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

package com.liferay.accessibility.menu.web.internal.util;

import com.liferay.accessibility.menu.web.internal.configuration.AccessibilityMenuConfiguration;
import com.liferay.accessibility.menu.web.internal.constants.AccessibilitySettingConstants;
import com.liferay.accessibility.menu.web.internal.model.AccessibilitySetting;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Evan Thibodeau
 */
public class AccessibilitySettingsUtil {

	public static List<AccessibilitySetting> getAccessibilitySettings(
		HttpServletRequest httpServletRequest) {

		return ListUtil.fromArray(
			new AccessibilitySetting(
				"c-prefers-link-underline", true,
				AccessibilitySettingConstants.
					ACCESSIBILITY_SETTING_SHOW_UNDERLINE,
				LanguageUtil.get(
					httpServletRequest, "show-underline-effect-in-links"),
				_getSessionClicksValue(
					httpServletRequest,
					AccessibilitySettingConstants.
						ACCESSIBILITY_SETTING_SHOW_UNDERLINE)),
			new AccessibilitySetting(
				"c-prefers-letter-spacing-1", false,
				AccessibilitySettingConstants.
					ACCESSIBILITY_SETTING_INCREASE_TEXT_SPACING,
				LanguageUtil.get(httpServletRequest, "increased-text-spacing"),
				_getSessionClicksValue(
					httpServletRequest,
					AccessibilitySettingConstants.
						ACCESSIBILITY_SETTING_INCREASE_TEXT_SPACING)),
			new AccessibilitySetting(
				"c-prefers-expanded-text", false,
				AccessibilitySettingConstants.ACCESSIBILITY_SETTING_EXPAND_TEXT,
				LanguageUtil.get(httpServletRequest, "expanded-text"),
				_getSessionClicksValue(
					httpServletRequest,
					AccessibilitySettingConstants.
						ACCESSIBILITY_SETTING_EXPAND_TEXT)));
	}

	public static boolean isAccessibilityMenuEnabled(
		HttpServletRequest httpServletRequest,
		ConfigurationProvider configurationProvider) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			AccessibilityMenuConfiguration accessibilityMenuConfiguration =
				configurationProvider.getGroupConfiguration(
					AccessibilityMenuConfiguration.class,
					themeDisplay.getScopeGroupId());

			return accessibilityMenuConfiguration.enableAccessibilityMenu();
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return false;
	}

	private static Boolean _getSessionClicksValue(
		HttpServletRequest httpServletRequest, String accessibilitySettingKey) {

		String sessionClicksValueString = GetterUtil.getString(
			SessionClicks.get(
				httpServletRequest, accessibilitySettingKey, null));

		if (Validator.isNull(sessionClicksValueString)) {
			return null;
		}

		return GetterUtil.getBoolean(sessionClicksValueString);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccessibilitySettingsUtil.class);

}