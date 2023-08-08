/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.webcache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;

/**
 * @author Brian Wing Shun Chan
 */
public class WebCachePoolUtil {

	public static void clear() {
		_portalCache.removeAll();
	}

	public static Object get(String key, WebCacheItem webCacheItem) {
		Object object = _portalCache.get(key);

		if (object != null) {
			return object;
		}

		try {
			object = webCacheItem.convert(key);

			if (object == null) {
				return null;
			}

			int timeToLive = (int)(webCacheItem.getRefreshTime() / Time.SECOND);

			if (timeToLive > 0) {
				_portalCache.put(key, object, timeToLive);
			}
		}
		catch (WebCacheException webCacheException) {
			if (_log.isWarnEnabled()) {
				Throwable throwable = webCacheException.getCause();

				if (throwable != null) {
					_log.warn(throwable, throwable);
				}
				else {
					_log.warn(webCacheException);
				}
			}
		}

		return object;
	}

	public static void remove(String key) {
		_portalCache.remove(key);
	}

	private static final String _CACHE_NAME = WebCachePoolUtil.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		WebCachePoolUtil.class);

	private static final PortalCache<String, Object> _portalCache =
		PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.SINGLE_VM, _CACHE_NAME);

}