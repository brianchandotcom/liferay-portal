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

package com.liferay.portal.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 */
public class WebAppPool {

	public static void clear() {
		_webAppPool.clear();
	}

	public static Object get(Long webAppId, String key) {
		Map<String, Object> map = _webAppPool.get(webAppId);

		if (map == null) {
			return null;
		}

		return map.get(key);
	}

	public static void put(Long webAppId, String key, Object obj) {
		Map<String, Object> map = _webAppPool.get(webAppId);

		if (map == null) {
			map = new ConcurrentHashMap<>();

			Map<String, Object> previousMap = _webAppPool.putIfAbsent(
				webAppId, map);

			if (previousMap != null) {
				map = previousMap;
			}
		}

		map.put(key, obj);
	}

	public static Object remove(Long webAppId, String key) {
		Map<String, Object> map = _webAppPool.get(webAppId);

		if (map == null) {
			return null;
		}

		return map.remove(key);
	}

	private static final ConcurrentMap<Long, Map<String, Object>> _webAppPool =
		new ConcurrentHashMap<>();

}