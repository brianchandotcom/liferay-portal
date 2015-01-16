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

package com.liferay.portlet.ratings.transformer;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class PortletRatingsDefinitionUtil {

	public static String[] getClassNames(String portletId) {
		PortletRatingsDefinition portletRatingsDefinition =
			_serviceTrackerMap.getService(portletId);

		if (portletRatingsDefinition == null) {
			return null;
		}

		return portletRatingsDefinition.getClassNames();
	}

	public static PortletRatingsDefinition.RatingsType getDefaultType(
		String portletId, String className) {

		PortletRatingsDefinition portletRatingsDefinition =
			_serviceTrackerMap.getService(portletId);

		if (portletRatingsDefinition == null) {
			return null;
		}

		return portletRatingsDefinition.getDefaultType(className);
	}

	public static String[] getPortletIds() {
		String[] portletIds = {};

		for (String portletId :_serviceTrackerMap.keySet()) {
			portletIds = ArrayUtil.append(portletIds, portletId);
		}

		return portletIds;
	}

	private static final ServiceTrackerMap<String, PortletRatingsDefinition>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			PortletRatingsDefinition.class, "portletId");

	static {
		_serviceTrackerMap.open();
	}

}