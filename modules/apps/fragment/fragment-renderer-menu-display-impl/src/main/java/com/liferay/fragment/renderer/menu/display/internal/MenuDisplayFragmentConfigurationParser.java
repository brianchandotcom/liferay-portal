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

package com.liferay.fragment.renderer.menu.display.internal;

import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.ContextualMenu;
import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.DisplayStyle;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

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
				configuration, editableValues, "hoveredItemColor"));

		String selectedItemColor = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues, "selectedItemColor"));

		MenuDisplayFragmentConfiguration.Source source = _getSource(
			configuration, editableValues);

		int sublevels = _getSublevels(configuration, editableValues);

		return new MenuDisplayFragmentConfiguration(
			displayStyle, hoveredItemColor, selectedItemColor, source,
			sublevels);
	}

	private JSONObject _createJSONObject(String value) {
		try {
			return JSONFactoryUtil.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	private DisplayStyle _getDisplayStyle(
		String configuration, String editableValues) {

		String displayStyle = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues, "displayStyle"));

		return DisplayStyle.parse(displayStyle);
	}

	private MenuDisplayFragmentConfiguration.Source _getSource(
		String configuration, String editableValues) {

		String source = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues, "source"));

		if (JSONUtil.isValid(source)) {
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
				configuration, editableValues, "sublevels"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MenuDisplayFragmentConfigurationParser.class);

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}