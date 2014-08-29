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

package com.liferay.kernel.servlet.taglib;

import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class DynamicIncludeUtil {

	public static List<DynamicInclude> getDynamicIncludes(String key) {
		return _instance._dynamicIncludes.getService(key);
	}

	private DynamicIncludeUtil() {
		_dynamicIncludes = ServiceTrackerCollections.multiValueMap(
			DynamicInclude.class, "key");

		_dynamicIncludes.open();
	}

	private static DynamicIncludeUtil _instance = new DynamicIncludeUtil();

	private ServiceTrackerMap<String, List<DynamicInclude>> _dynamicIncludes;

}