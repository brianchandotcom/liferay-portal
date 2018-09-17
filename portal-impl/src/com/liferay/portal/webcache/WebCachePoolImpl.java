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

package com.liferay.portal.webcache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePool;

/**
 * @author Brian Wing Shun Chan
 */
public class WebCachePoolImpl implements WebCachePool {

	public void afterPropertiesSet() {
		_portalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.SINGLE_VM, _CACHE_NAME);
	}

	@Override
	public void clear() {
		_portalCache.removeAll();
	}

	@Override
	public Object get(String key, WebCacheItem wci) {
		Object obj = _portalCache.get(key);

		if (obj != null) {
			return obj;
		}

		try {
			obj = wci.convert(key);

			if (obj == null) {
				return null;
			}

			int timeToLive = (int)(wci.getRefreshTime() / Time.SECOND);

			_portalCache.put(key, obj, timeToLive);
		}
		catch (WebCacheException wce) {
			if (_log.isWarnEnabled()) {
				Throwable cause = wce.getCause();

				if (cause != null) {
					_log.warn(cause, cause);
				}
				else {
					_log.warn(wce, wce);
				}
			}
		}

		return obj;
	}

	@Override
	public void remove(String key) {
		_portalCache.remove(key);
	}

	private static final String _CACHE_NAME = WebCachePool.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		WebCachePoolImpl.class);

	private PortalCache<String, Object> _portalCache;

}