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
 */
public class RatingsDataTransformerHelperUtil {

	public static String[] getClassNames(String portletId) {
		RatingsDataTransformerHelper ratingsDataTransformerHelper =
			_serviceTrackerMap.getService(portletId);

		if (ratingsDataTransformerHelper == null) {
			return null;
		}

		return ratingsDataTransformerHelper.getClassNames();
	}

	public static String getDefaultType(String portletId, String className) {
		RatingsDataTransformerHelper ratingsDataTransformerHelper =
			_serviceTrackerMap.getService(portletId);

		if (ratingsDataTransformerHelper == null) {
			return null;
		}

		return ratingsDataTransformerHelper.getDefaultType(className);
	}

	public static String[] getPortletIds() {
		String[] portletIds = {};

		for (String portletId :_serviceTrackerMap.keySet()) {
			portletIds = ArrayUtil.append(portletIds, portletId);
		}

		return portletIds;
	}

	private static final ServiceTrackerMap<String, RatingsDataTransformerHelper>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			RatingsDataTransformerHelper.class, "portletId");

	static {
		_serviceTrackerMap.open();
	}

}