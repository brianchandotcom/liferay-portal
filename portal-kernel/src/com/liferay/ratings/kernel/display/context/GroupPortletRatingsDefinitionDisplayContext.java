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

package com.liferay.ratings.kernel.display.context;

import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.ratings.kernel.RatingsType;
import com.liferay.ratings.kernel.definition.PortletRatingsDefinitionUtil;
import com.liferay.ratings.kernel.definition.PortletRatingsDefinitionValues;
import com.liferay.ratings.kernel.transformer.RatingsDataTransformerUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class GroupPortletRatingsDefinitionDisplayContext {

	public GroupPortletRatingsDefinitionDisplayContext(
		UnicodeProperties groupTypeSettings,
		HttpServletRequest httpServletRequest) {

		_populateRatingsTypeMaps(groupTypeSettings, httpServletRequest);
	}

	public Map<String, Map<String, RatingsType>> getGroupRatingsTypeMaps() {
		return Collections.unmodifiableMap(_groupRatingsTypeMaps);
	}

	private void _populateRatingsTypeMaps(
		UnicodeProperties groupTypeSettings,
		HttpServletRequest httpServletRequest) {

		Map<String, PortletRatingsDefinitionValues>
			portletRatingsDefinitionValuesMap =
				PortletRatingsDefinitionUtil.
					getPortletRatingsDefinitionValuesMap();

		for (Map.Entry<String, PortletRatingsDefinitionValues> entry :
				portletRatingsDefinitionValuesMap.entrySet()) {

			PortletRatingsDefinitionValues portletRatingsDefinitionValues =
				entry.getValue();

			if (portletRatingsDefinitionValues == null) {
				continue;
			}

			String portletId = portletRatingsDefinitionValues.getPortletId();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (!PortletLocalServiceUtil.hasPortlet(
					themeDisplay.getCompanyId(), portletId)) {

				continue;
			}

			String className = entry.getKey();

			Map<String, RatingsType> ratingsTypeMap = new HashMap<>();

			String groupRatingsTypeString = PropertiesParamUtil.getString(
				groupTypeSettings, httpServletRequest,
				RatingsDataTransformerUtil.getPropertyKey(className));

			RatingsType ratingsType = null;

			if (Validator.isNotNull(groupRatingsTypeString)) {
				ratingsType = RatingsType.parse(groupRatingsTypeString);
			}

			ratingsTypeMap.put(className, ratingsType);

			_groupRatingsTypeMaps.put(portletId, ratingsTypeMap);
		}
	}

	private final Map<String, Map<String, RatingsType>> _groupRatingsTypeMaps =
		new HashMap<>();

}