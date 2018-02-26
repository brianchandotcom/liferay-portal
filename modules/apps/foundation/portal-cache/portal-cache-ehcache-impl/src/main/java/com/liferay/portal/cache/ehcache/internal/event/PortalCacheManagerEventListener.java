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

import com.liferay.portal.kernel.cache.PortalCacheManagerListener;

import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheManagerEventListener
	implements CacheManagerEventListener {

	public PortalCacheManagerEventListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		_portalCacheManagerListener = portalCacheManagerListener;
	}

	@Override
	public void dispose() {
		_portalCacheManagerListener.dispose();
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

		return _portalCacheManagerListener.equals(
			portalCacheManagerEventListener._portalCacheManagerListener);
	}

	public PortalCacheManagerListener getCacheManagerListener() {
		return _portalCacheManagerListener;
	}

	@Override
	public Status getStatus() {
		return Status.STATUS_ALIVE;
	}

	@Override
	public int hashCode() {
		return _portalCacheManagerListener.hashCode();
	}

	@Override
	public void init() {
		_portalCacheManagerListener.init();
	}

	@Override
	public void notifyCacheAdded(String portalCacheName) {
		_portalCacheManagerListener.notifyPortalCacheAdded(portalCacheName);
	}

	@Override
	public void notifyCacheRemoved(String portalCacheName) {
		_portalCacheManagerListener.notifyPortalCacheRemoved(portalCacheName);
	}

	private final PortalCacheManagerListener _portalCacheManagerListener;

}