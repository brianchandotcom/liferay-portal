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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.ratings.kernel.RatingsType;
import com.liferay.ratings.kernel.definition.PortletRatingsDefinitionUtil;
import com.liferay.ratings.kernel.definition.PortletRatingsDefinitionValues;
import com.liferay.ratings.kernel.transformer.RatingsDataTransformerUtil;

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
		HttpServletRequest httpServletRequest) {

		_populateRatingsTypeMaps(companyPortletPreferences, httpServletRequest);
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

			Map<String, RatingsType> ratingsTypeMap = HashMapBuilder.put(
				className,
				() -> {
					String propertyKey =
						RatingsDataTransformerUtil.getPropertyKey(className);

					RatingsType ratingsType =
						portletRatingsDefinitionValues.getDefaultRatingsType();

					return RatingsType.parse(
						PrefsParamUtil.getString(
							companyPortletPreferences, httpServletRequest,
							propertyKey, ratingsType.getValue()));
				}
			).build();

			_companyRatingsTypeMaps.put(portletId, ratingsTypeMap);
		}
	}

	private final Map<String, Map<String, RatingsType>>
		_companyRatingsTypeMaps = new HashMap<>();

}