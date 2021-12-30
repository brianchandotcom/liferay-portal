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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.ThemeSetting;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.ThemeSettingImpl;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;

/**
 * @author Eudaldo Alonso
 */
public class ActionUtil {

	public static void deleteThemeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties, String device) {

		String keyPrefix = ThemeSettingImpl.namespaceProperty(device);

		Set<String> keys = typeSettingsUnicodeProperties.keySet();

		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (key.startsWith(keyPrefix)) {
				iterator.remove();
			}
		}
	}

	public static String getColorSchemeId(
			long companyId, String themeId, String colorSchemeId)
		throws Exception {

		Theme theme = ThemeLocalServiceUtil.getTheme(companyId, themeId);

		if (!theme.hasColorSchemes()) {
			colorSchemeId = StringPool.BLANK;
		}

		if (Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = ThemeLocalServiceUtil.getColorScheme(
				companyId, themeId, colorSchemeId);

			colorSchemeId = colorScheme.getColorSchemeId();
		}

		return colorSchemeId;
	}

	public static void updateLookAndFeel(
			ActionRequest actionRequest, long companyId, long liveGroupId,
			long stagingGroupId, boolean privateLayout, long layoutId,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws Exception {

		String[] devices = StringUtil.split(
			ParamUtil.getString(actionRequest, "devices"));

		for (String device : devices) {
			String deviceThemeId = ParamUtil.getString(
				actionRequest, device + "ThemeId");
			String deviceColorSchemeId = ParamUtil.getString(
				actionRequest, device + "ColorSchemeId");
			String deviceCss = ParamUtil.getString(
				actionRequest, device + "Css");

			boolean deviceInheritLookAndFeel = ParamUtil.getBoolean(
				actionRequest, device + "InheritLookAndFeel");

			if (deviceInheritLookAndFeel) {
				deviceThemeId = ThemeFactoryUtil.getDefaultRegularThemeId(
					companyId);
				deviceColorSchemeId = StringPool.BLANK;

				deleteThemeSettingsProperties(
					typeSettingsUnicodeProperties, device);
			}
			else if (Validator.isNotNull(deviceThemeId)) {
				deviceColorSchemeId = getColorSchemeId(
					companyId, deviceThemeId, deviceColorSchemeId);

				updateThemeSettingsProperties(
					actionRequest, companyId, typeSettingsUnicodeProperties,
					device, deviceThemeId, true);
			}

			long groupId = liveGroupId;

			if (stagingGroupId > 0) {
				groupId = stagingGroupId;
			}

			LayoutServiceUtil.updateLayout(
				groupId, privateLayout, layoutId,
				typeSettingsUnicodeProperties.toString());

			LayoutServiceUtil.updateLookAndFeel(
				groupId, privateLayout, layoutId, deviceThemeId,
				deviceColorSchemeId, deviceCss);
		}
	}

	public static UnicodeProperties updateThemeSettingsProperties(
			ActionRequest actionRequest, long companyId,
			UnicodeProperties typeSettingsUnicodeProperties, String device,
			String deviceThemeId, boolean layout)
		throws Exception {

		Theme theme = ThemeLocalServiceUtil.getTheme(companyId, deviceThemeId);

		deleteThemeSettingsProperties(typeSettingsUnicodeProperties, device);

		Map<String, ThemeSetting> themeSettings =
			theme.getConfigurableSettings();

		if (themeSettings.isEmpty()) {
			return typeSettingsUnicodeProperties;
		}

		_setThemeSettingProperties(
			actionRequest, typeSettingsUnicodeProperties, themeSettings, device,
			layout);

		return typeSettingsUnicodeProperties;
	}

	private static void _setThemeSettingProperties(
			ActionRequest actionRequest,
			UnicodeProperties typeSettingsUnicodeProperties,
			Map<String, ThemeSetting> themeSettings, String device,
			boolean isLayout)
		throws Exception {

		Layout layout = null;

		if (isLayout) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);
		}

		for (Map.Entry<String, ThemeSetting> entry : themeSettings.entrySet()) {
			String key = entry.getKey();
			ThemeSetting themeSetting = entry.getValue();

			String property = StringBundler.concat(
				device, "ThemeSettingsProperties--", key,
				StringPool.DOUBLE_DASH);

			String value = ParamUtil.getString(
				actionRequest, property, themeSetting.getValue());

			if ((isLayout &&
				 !Objects.equals(
					 value,
					 layout.getDefaultThemeSetting(key, device, false))) ||
				(!isLayout && !value.equals(themeSetting.getValue()))) {

				typeSettingsUnicodeProperties.setProperty(
					ThemeSettingImpl.namespaceProperty(device, key), value);
			}
		}
	}

}