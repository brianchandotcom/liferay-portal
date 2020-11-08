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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = MenuDisplayFragmentConfigurationParser.class)
public class MenuDisplayFragmentConfigurationParser {

	public MenuDisplayFragmentConfiguration parse(
		String configuration, String editableValues) {

		DisplayStyle displayStyle = _getDisplayStyle(
			configuration, editableValues);

		int numberOfSublevels = _getNumberOfSublevels(
			configuration, editableValues);

		MenuDisplayFragmentConfiguration.Source source = _getSource(
			configuration, editableValues);

		return new MenuDisplayFragmentConfiguration(
			displayStyle, numberOfSublevels, source);
	}

	private DisplayStyle _getDisplayStyle(
		String configuration, String editableValues) {

		String displayStyle = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues, "displayStyle"));

		if (Objects.equals(displayStyle, "stacked")) {
			return DisplayStyle.STACKED;
		}

		return DisplayStyle.HORIZONTAL;
	}

	private int _getNumberOfSublevels(
		String configuration, String editableValues) {

		return GetterUtil.getInteger(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues, "sublevels"));
	}

	private MenuDisplayFragmentConfiguration.Source _getSource(
		String configuration, String editableValues) {

		Object object = _fragmentEntryConfigurationParser.getFieldValue(
			configuration, editableValues, "source");

		if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			if (jsonObject.has("contextualMenu")) {
				String contextualMenu = jsonObject.getString("contextualMenu");

				if (Objects.equals(contextualMenu, "same-level")) {
					return ContextualMenu.SAME_LEVEL;
				}

				if (Objects.equals(contextualMenu, "second-level")) {
					return ContextualMenu.SECOND_LEVEL;
				}

				if (Objects.equals(contextualMenu, "upper-level")) {
					return ContextualMenu.UPPER_LEVEL;
				}
			}
			else if (jsonObject.has("siteNavigationMenuId")) {
				long siteNavigationMenuId = jsonObject.getLong(
					"siteNavigationMenuId");

				long parentSiteNavigationMenuItemId = jsonObject.getLong(
					"parentSiteNavigationMenuItemId");

				return new MenuDisplayFragmentConfiguration.
					SiteNavigationMenuSource(
						parentSiteNavigationMenuItemId, siteNavigationMenuId);
			}
		}

		return ContextualMenu.SAME_LEVEL;
	}

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

}