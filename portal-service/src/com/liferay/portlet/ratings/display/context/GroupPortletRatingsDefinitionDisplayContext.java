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

import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
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
public class GroupPortletRatingsDefinitionDisplayContext {

	public GroupPortletRatingsDefinitionDisplayContext(
		UnicodeProperties groupTypeSettings, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId());

		_companyPortletRatingsDefinitionDisplayContext =
			new CompanyPortletRatingsDefinitionDisplayContext(
				companyPortletPreferences, request);

		_populateRatingsTypeMaps(groupTypeSettings, request);
	}

	public Map<String, Map<String, RatingsType>> getGroupRatingsTypeMaps() {
		return Collections.unmodifiableMap(_groupRatingsTypeMaps);
	}

	private void _populateRatingsTypeMaps(
		UnicodeProperties groupTypeSettings, HttpServletRequest request) {

		Map<String, PortletRatingsDefinitionValues>
			portletRatingsDefinitionValuesMap =
				PortletRatingsDefinitionUtil.
					getPortletRatingsDefinitionValuesMap();

		for (String className : portletRatingsDefinitionValuesMap.keySet()) {
			PortletRatingsDefinitionValues portletRatingsDefinitionValues =
				portletRatingsDefinitionValuesMap.get(className);

			if (portletRatingsDefinitionValues == null) {
				continue;
			}

			String propertyKey = RatingsDataTransformerUtil.getPropertyKey(
				className);

			Map<String, RatingsType> ratingsTypeMap = new HashMap<>();

			Map<String, Map<String, RatingsType>> companyRatingsTypeMaps =
				_companyPortletRatingsDefinitionDisplayContext.
					getCompanyRatingsTypeMaps();

			String portletId = portletRatingsDefinitionValues.getPortletId();

			Map<String, RatingsType> companyRatingsTypeMap =
				companyRatingsTypeMaps.get(portletId);

			String groupRatingsTypeString = PropertiesParamUtil.getString(
				groupTypeSettings, request, propertyKey,
				companyRatingsTypeMap.get(className).getValue());

			ratingsTypeMap.put(
				className, RatingsType.parse(groupRatingsTypeString));

			_groupRatingsTypeMaps.put(portletId, ratingsTypeMap);
		}
	}

	private final CompanyPortletRatingsDefinitionDisplayContext
		_companyPortletRatingsDefinitionDisplayContext;
	private final Map<String, Map<String, RatingsType>>
		_groupRatingsTypeMaps = new HashMap<>();

}