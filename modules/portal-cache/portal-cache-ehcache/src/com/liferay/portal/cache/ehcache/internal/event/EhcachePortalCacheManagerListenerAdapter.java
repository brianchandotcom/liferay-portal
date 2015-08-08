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

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;

import net.sf.ehcache.event.CacheManagerEventListener;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheManagerListenerAdapter
	implements PortalCacheManagerListener {

	public EhcachePortalCacheManagerListenerAdapter(
		CacheManagerEventListener cacheManagerEventListener) {

		_cacheManagerEventListener = cacheManagerEventListener;
	}

	@Override
	public void dispose() throws PortalCacheException {
		_cacheManagerEventListener.dispose();
	}

	@Override
	public void init() throws PortalCacheException {
		_cacheManagerEventListener.init();
	}

	@Override
	public void notifyPortalCacheAdded(String portalCacheName) {
		_cacheManagerEventListener.notifyCacheAdded(portalCacheName);
	}

	@Override
	public void notifyPortalCacheRemoved(String portalCacheName) {
		_cacheManagerEventListener.notifyCacheRemoved(portalCacheName);
	}

	private final CacheManagerEventListener _cacheManagerEventListener;

}