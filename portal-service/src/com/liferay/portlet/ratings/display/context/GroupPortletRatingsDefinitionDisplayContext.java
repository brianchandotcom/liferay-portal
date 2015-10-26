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

import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.ratings.RatingsType;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionUtil;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionValues;
import com.liferay.portlet.ratings.transformer.RatingsDataTransformerUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class GroupPortletRatingsDefinitionDisplayContext {

	public GroupPortletRatingsDefinitionDisplayContext(
		UnicodeProperties groupTypeSettings, HttpServletRequest request) {

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

			String portletId = portletRatingsDefinitionValues.getPortletId();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (!PortletLocalServiceUtil.hasPortlet(
					themeDisplay.getCompanyId(), portletId)) {

				continue;
			}

			Map<String, RatingsType> ratingsTypeMap = new HashMap<>();

			String propertyKey = RatingsDataTransformerUtil.getPropertyKey(
				className);

			String groupRatingsTypeString = PropertiesParamUtil.getString(
				groupTypeSettings, request, propertyKey);

			RatingsType ratingsType = null;

			if (Validator.isNotNull(groupRatingsTypeString)) {
				ratingsType = RatingsType.parse(groupRatingsTypeString);
			}

			ratingsTypeMap.put(className, ratingsType);

			_groupRatingsTypeMaps.put(portletId, ratingsTypeMap);
		}
	}

	private final Map<String, Map<String, RatingsType>>
		_groupRatingsTypeMaps = new HashMap<>();

}