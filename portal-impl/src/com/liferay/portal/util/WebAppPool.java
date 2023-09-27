/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static void put(Long webAppId, String key, Object object) {
		Map<String, Object> map = _webAppPool.get(webAppId);

		if (map == null) {
			map = new ConcurrentHashMap<>();

			Map<String, Object> previousMap = _webAppPool.putIfAbsent(
				webAppId, map);

			if (previousMap != null) {
				map = previousMap;
			}
		}

		map.put(key, object);
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