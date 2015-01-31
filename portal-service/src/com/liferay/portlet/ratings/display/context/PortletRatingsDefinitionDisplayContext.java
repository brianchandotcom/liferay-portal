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

package com.liferay.portlet.ratings.display.context;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.ratings.RatingsType;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionUtil;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionValues;
import com.liferay.portlet.ratings.transformer.RatingsDataTransformerUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class PortletRatingsDefinitionDisplayContext {

	public PortletRatingsDefinitionDisplayContext(HttpServletRequest request) {
		_populatePortletRatingsDefinitionMaps(request);
	}

	public Map<String, Map<String, RatingsType>>
		getCompanyPortletRatingsDefinitionMap() {

		return Collections.unmodifiableMap(_companyPortletRatingsDefinitionMap);
	}

	public Map<String, Map<String, RatingsType>>
		getGroupPortletRatingsDefinitionMap() {

		return Collections.unmodifiableMap(_groupPortletRatingsDefinitionMap);
	}

	public boolean showRatingsSection(String[] sections) {
		if (!ArrayUtil.contains(sections, "ratings")) {
			return false;
		}

		Map<String, PortletRatingsDefinitionValues>
			portletRatingsDefinitionValuesMap =
				PortletRatingsDefinitionUtil.
					getPortletRatingsDefinitionValuesMap();

		if (portletRatingsDefinitionValuesMap.isEmpty()) {
			return false;
		}

		return true;
	}

	private void _populatePortletRatingsDefinitionMaps(
		HttpServletRequest request) {

		Map<String, PortletRatingsDefinitionValues>
			portletRatingsDefinitionValuesMap =
				PortletRatingsDefinitionUtil.
					getPortletRatingsDefinitionValuesMap();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId());

		Group liveGroup = (Group)request.getAttribute("site.liveGroup");

		UnicodeProperties groupTypeSettings = new UnicodeProperties();

		if (liveGroup != null) {
			groupTypeSettings = liveGroup.getTypeSettingsProperties();
		}

		for (String className : portletRatingsDefinitionValuesMap.keySet()) {
			PortletRatingsDefinitionValues portletRatingsDefinitionValues =
				portletRatingsDefinitionValuesMap.get(className);

			if (portletRatingsDefinitionValues == null) {
				continue;
			}

			String propertyKey = RatingsDataTransformerUtil.getPropertyKey(
				className);

			RatingsType defaultRatingsType =
				portletRatingsDefinitionValues.getDefaultRatingsType();

			Map<String, RatingsType> ratingsTypeMap = new HashMap<>();

			// Company Ratings Map

			String companyRatingsTypeString = PrefsParamUtil.getString(
				companyPortletPreferences, request, propertyKey,
				defaultRatingsType.getValue());

			ratingsTypeMap.put(
				className, RatingsType.parse(companyRatingsTypeString));

			String portletId = portletRatingsDefinitionValues.getPortletId();

			_companyPortletRatingsDefinitionMap.put(portletId, ratingsTypeMap);

			// Group Ratings Map

			String groupRatingsTypeString = PropertiesParamUtil.getString(
				groupTypeSettings, request, propertyKey,
				companyRatingsTypeString);

			ratingsTypeMap.put(
				className, RatingsType.parse(groupRatingsTypeString));

			_groupPortletRatingsDefinitionMap.put(portletId, ratingsTypeMap);
		}
	}

	private final Map<String, Map<String, RatingsType>>
		_companyPortletRatingsDefinitionMap = new HashMap<>();
	private final Map<String, Map<String, RatingsType>>
		_groupPortletRatingsDefinitionMap = new HashMap<>();

}