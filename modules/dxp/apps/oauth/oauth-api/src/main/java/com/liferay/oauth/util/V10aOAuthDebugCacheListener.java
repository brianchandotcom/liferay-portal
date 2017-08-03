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

package com.liferay.oauth.util;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Igor Beslic
 */
public class V10aOAuthDebugCacheListener<K extends Serializable, V>
	implements PortalCacheListener<K, V> {

	@Override
	public void dispose() {
	}

	@Override
	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		logDebug("Entry evicted", key, value);
	}

	@Override
	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		logDebug("Entry expired", key, value);
	}

	@Override
	public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		logDebug("Entry put", key, value);
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		logDebug("Entry removed", key, value);
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		logDebug("Entry updated", key, value);
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		if (_log.isDebugEnabled()) {
			_log.debug("Remove all " + portalCache.getPortalCacheName());
		}
	}

	protected void logDebug(String method, K key, V value) {
		if (!_log.isDebugEnabled()) {
			return;
		}

		if (!(value instanceof OAuthAccessor)) {
			return;
		}

		OAuthAccessor oAuthAccessor = (OAuthAccessor)value;

		StringBundler sb = new StringBundler(7);

		sb.append(method);
		sb.append("  ");
		sb.append(key);
		sb.append(":");
		sb.append(oAuthAccessor.getRequestToken());
		sb.append(":");
		sb.append(oAuthAccessor.getProperty(OAuthAccessorConstants.AUTHORIZED));

		_log.debug(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		V10aOAuthDebugCacheListener.class);

}