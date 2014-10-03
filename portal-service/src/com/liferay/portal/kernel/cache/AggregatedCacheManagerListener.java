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

package com.liferay.portal.kernel.cache;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Tina Tian
 */
public class AggregatedCacheManagerListener implements CacheManagerListener {

	public boolean addCacheListener(CacheManagerListener cacheManagerListener) {
		if (cacheManagerListener == null) {
			return false;
		}

		return _cacheManagerListeners.add(cacheManagerListener);
	}

	public void clearAll() {
		_cacheManagerListeners.clear();
	}

	@Override
	public void dispose() throws PortalCacheException {
		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.dispose();
		}
	}

	public Set<CacheManagerListener> getCacheManagerListeners() {
		return Collections.unmodifiableSet(_cacheManagerListeners);
	}

	@Override
	public void init() throws PortalCacheException {
		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.init();
		}
	}

	@Override
	public void notifyCacheAdded(String name) {
		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.notifyCacheAdded(name);
		}
	}

	@Override
	public void notifyCacheRemoved(String name) {
		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.notifyCacheRemoved(name);
		}
	}

	public boolean removeCacheListener(
		CacheManagerListener cacheManagerListener) {

		if (cacheManagerListener == null) {
			return false;
		}

		return _cacheManagerListeners.remove(cacheManagerListener);
	}

	private final Set<CacheManagerListener> _cacheManagerListeners =
		new CopyOnWriteArraySet<CacheManagerListener>();

}