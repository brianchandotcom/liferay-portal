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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.CacheManagerListener;

import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheManagerEventListener
	implements CacheManagerEventListener {

	public PortalCacheManagerEventListener(
		CacheManagerListener cacheManagerListener) {

		_cacheManagerListener = cacheManagerListener;
	}

	@Override
	public void dispose() {
		_cacheManagerListener.dispose();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortalCacheManagerEventListener)) {
			return false;
		}

		PortalCacheManagerEventListener portalCacheManagerEventListener =
			(PortalCacheManagerEventListener)obj;

		return _cacheManagerListener.equals(
			portalCacheManagerEventListener._cacheManagerListener);
	}

	public CacheManagerListener getCacheManagerListener() {
		return _cacheManagerListener;
	}

	@Override
	public Status getStatus() {
		return Status.STATUS_ALIVE;
	}

	@Override
	public int hashCode() {
		return _cacheManagerListener.hashCode();
	}

	@Override
	public void init() {
		_cacheManagerListener.init();
	}

	@Override
	public void notifyCacheAdded(String name) {
		_cacheManagerListener.notifyCacheAdded(name);
	}

	@Override
	public void notifyCacheRemoved(String name) {
		_cacheManagerListener.notifyCacheRemoved(name);
	}

	private final CacheManagerListener _cacheManagerListener;

}