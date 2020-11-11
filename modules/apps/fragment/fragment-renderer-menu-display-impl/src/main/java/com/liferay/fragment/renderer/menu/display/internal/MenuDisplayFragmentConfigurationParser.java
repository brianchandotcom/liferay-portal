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
import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

		Map<String, FrontendToken> frontendTokenMap = _getFrontendTokens(
			groupId);

		String hoveredItemColor = _getColorPickerValue(
			configuration, editableValues, "hoveredItemColor",
			frontendTokenMap);

		String selectedItemColor = _getColorPickerValue(
			configuration, editableValues, "selectedItemColor",
			frontendTokenMap);

		MenuDisplayFragmentConfiguration.Source source = _getSource(
			configuration, editableValues);

		int sublevels = _getSublevels(configuration, editableValues);

		return new MenuDisplayFragmentConfiguration(
			displayStyle, hoveredItemColor, selectedItemColor, source,
			sublevels);
	}

	private String _getColorPickerValue(
		String configuration, String editableValues, String fieldName,
		Map<String, FrontendToken> frontendTokenMap) {

		String value = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues, fieldName));

		if (Validator.isNull(value)) {
			return null;
		}

		FrontendToken frontendToken = frontendTokenMap.get(value);

		if (frontendToken == null) {
			return value;
		}

		ArrayList<FrontendTokenMapping> frontendTokenMappings = new ArrayList<>(
			frontendToken.getFrontendTokenMappings(
				FrontendTokenMapping.TYPE_CSS_VARIABLE));

		if (frontendTokenMappings.isEmpty()) {
			return value;
		}

		FrontendTokenMapping frontendTokenMapping = frontendTokenMappings.get(
			0);

		return "var(--" + frontendTokenMapping.getValue() + ")";
	}

	private DisplayStyle _getDisplayStyle(
		String configuration, String editableValues) {

		String displayStyle = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues, "displayStyle"));

		return DisplayStyle.parse(displayStyle);
	}

	private Map<String, FrontendToken> _getFrontendTokens(long groupId) {
		Map<String, FrontendToken> frontendTokens = new HashMap<>();

		try {
			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				groupId, false);

			FrontendTokenDefinition frontendTokenDefinition =
				_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
					layoutSet.getThemeId());

			for (FrontendToken frontendToken :
					frontendTokenDefinition.getFrontendTokens()) {

				JSONObject frontendTokenJSONObject =
					JSONFactoryUtil.createJSONObject(
						frontendToken.getJSON(LocaleUtil.getDefault()));

				frontendTokens.put(
					frontendTokenJSONObject.getString("name"), frontendToken);
			}
		}
		catch (Exception exception) {
			_log.error("Cannot get frontend tokens", exception);
		}

		return frontendTokens;
	}

	private MenuDisplayFragmentConfiguration.Source _getSource(
		String configuration, String editableValues) {

		Object object = _fragmentEntryConfigurationParser.getFieldValue(
			configuration, editableValues, "source");

		if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			if (jsonObject.has("contextualMenu")) {
				String contextualMenu = jsonObject.getString("contextualMenu");

				return ContextualMenu.parse(contextualMenu);
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

		return ContextualMenu.SELF_AND_SIBLINGS;
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
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}