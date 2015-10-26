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

import com.liferay.portal.kernel.util.PrefsParamUtil;
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

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class CompanyPortletRatingsDefinitionDisplayContext {

	public CompanyPortletRatingsDefinitionDisplayContext(
		PortletPreferences companyPortletPreferences,
		HttpServletRequest request) {

		_populateRatingsTypeMaps(companyPortletPreferences, request);
	}

	public Map<String, Map<String, RatingsType>> getCompanyRatingsTypeMaps() {
		return Collections.unmodifiableMap(_companyRatingsTypeMaps);
	}

	public RatingsType getRatingsType(String portletId, String className) {
		Map<String, RatingsType> ratingsTypeMap = _companyRatingsTypeMaps.get(
			portletId);

		return ratingsTypeMap.get(className);
	}

	private void _populateRatingsTypeMaps(
		PortletPreferences companyPortletPreferences,
		HttpServletRequest request) {

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

			RatingsType ratingsType =
				portletRatingsDefinitionValues.getDefaultRatingsType();

			String companyRatingsTypeString = PrefsParamUtil.getString(
				companyPortletPreferences, request, propertyKey,
				ratingsType.getValue());

			ratingsTypeMap.put(
				className, RatingsType.parse(companyRatingsTypeString));

			_companyRatingsTypeMaps.put(portletId, ratingsTypeMap);
		}
	}

	private final Map<String, Map<String, RatingsType>>
		_companyRatingsTypeMaps = new HashMap<>();

}