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

package com.liferay.portal.kernel.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Carlos Sierra Andrés
 * @author Raymond Augé
 */
public class DynamicIncludeUtil {

	public static List<DynamicInclude> getDynamicIncludes(String key) {
		return _instance._dynamicIncludes.getService(key);
	}

	public static boolean hasDynamicInclude(String key) {
		List<DynamicInclude> dynamicIncludes = getDynamicIncludes(key);

		if ((dynamicIncludes == null) || dynamicIncludes.isEmpty()) {
			return false;
		}

		return true;
	}

	public static void include(
		HttpServletRequest request, HttpServletResponse response, String key,
		boolean ascendingPriority) {

		List<DynamicInclude> dynamicIncludes = getDynamicIncludes(key);

		if ((dynamicIncludes == null) || dynamicIncludes.isEmpty()) {
			return;
		}

		Iterator<DynamicInclude> iterator = null;

		if (ascendingPriority) {
			iterator = dynamicIncludes.iterator();
		}
		else {
			iterator = ListUtil.reverseIterator(dynamicIncludes);
		}

		while (iterator.hasNext()) {
			DynamicInclude dynamicInclude = iterator.next();

			try {
				dynamicInclude.include(request, response, key);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private DynamicIncludeUtil() {
		_dynamicIncludes = ServiceTrackerCollections.multiValueMap(
			DynamicInclude.class, "key");

		_dynamicIncludes.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicIncludeUtil.class);

	private static final DynamicIncludeUtil _instance =
		new DynamicIncludeUtil();

	private final ServiceTrackerMap<String, List<DynamicInclude>>
		_dynamicIncludes;

}