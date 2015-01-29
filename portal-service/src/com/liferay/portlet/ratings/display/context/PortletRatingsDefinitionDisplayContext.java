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

import com.liferay.portlet.ratings.RatingsType;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionUtil;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionValues;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class PortletRatingsDefinitionDisplayContext {

	public PortletRatingsDefinitionDisplayContext() {
		_portletRatingsDefinitionMap = _populatePortletRatingsDefinitionMap();
	}

	public Map<String, Map<String, RatingsType>>
		getPortletRatingsDefinitionMap() {

		return Collections.unmodifiableMap(_portletRatingsDefinitionMap);
	}

	private Map<String, Map<String, RatingsType>>
		_populatePortletRatingsDefinitionMap() {

		Map<String, Map<String, RatingsType>> portletRatingsDefinitionMap =
			new HashMap<>();

		Map<String, PortletRatingsDefinitionValues>
			portletRatingsDefinitionValuesMap =
				PortletRatingsDefinitionUtil.
					getPortletRatingsDefinitionValuesMap();

		for (String className : portletRatingsDefinitionValuesMap.keySet()) {
			PortletRatingsDefinitionValues portletRatingsDefinitionValues =
				portletRatingsDefinitionValuesMap.get(className);

			String portletId = portletRatingsDefinitionValues.getPortletId();

			Map<String, RatingsType> ratingsTypeMap =
				portletRatingsDefinitionMap.get(portletId);

			if (ratingsTypeMap == null) {
				ratingsTypeMap = new HashMap<>();
			}

			ratingsTypeMap.put(
				className,
				portletRatingsDefinitionValues.getDefaultRatingsType());

			portletRatingsDefinitionMap.put(portletId, ratingsTypeMap);
		}

		return portletRatingsDefinitionMap;
	}

	private final Map<String, Map<String, RatingsType>>
		_portletRatingsDefinitionMap;

}