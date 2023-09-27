/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.menu.display.internal;

import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.ContextualMenu;
import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.DisplayStyle;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = MenuDisplayFragmentConfigurationParser.class)
public class MenuDisplayFragmentConfigurationParser {

	public MenuDisplayFragmentConfiguration parse(
		String configuration, String editableValues, long groupId) {

		DisplayStyle displayStyle = _getDisplayStyle(
			configuration, editableValues);

		String hoveredItemColor = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "hoveredItemColor"));

		String selectedItemColor = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "selectedItemColor"));

		MenuDisplayFragmentConfiguration.Source source = _getSource(
			configuration, editableValues);

		int sublevels = _getSublevels(configuration, editableValues);

		return new MenuDisplayFragmentConfiguration(
			displayStyle, hoveredItemColor, selectedItemColor, source,
			sublevels);
	}

	private JSONObject _createJSONObject(String value) {
		try {
			return _jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return _jsonFactory.createJSONObject();
		}
	}

	private DisplayStyle _getDisplayStyle(
		String configuration, String editableValues) {

		String displayStyle = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "displayStyle"));

		return DisplayStyle.parse(displayStyle);
	}

	private MenuDisplayFragmentConfiguration.Source _getSource(
		String configuration, String editableValues) {

		String source = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "source"));

		if (JSONUtil.isJSONObject(source)) {
			JSONObject jsonObject = _createJSONObject(source);

			if (jsonObject.has("contextualMenu")) {
				return ContextualMenu.parse(
					jsonObject.getString("contextualMenu"));
			}
			else if (jsonObject.has("siteNavigationMenuId")) {
				return new MenuDisplayFragmentConfiguration.
					SiteNavigationMenuSource(
						jsonObject.getLong("parentSiteNavigationMenuItemId"),
						jsonObject.getBoolean("privateLayout"),
						jsonObject.getLong("siteNavigationMenuId"));
			}
		}

		return MenuDisplayFragmentConfiguration.DEFAULT_SOURCE;
	}

	private int _getSublevels(String configuration, String editableValues) {
		return GetterUtil.getInteger(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "sublevels"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MenuDisplayFragmentConfigurationParser.class);

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

}