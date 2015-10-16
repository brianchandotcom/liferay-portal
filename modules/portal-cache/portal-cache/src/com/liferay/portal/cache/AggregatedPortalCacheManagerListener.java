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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Tina Tian
 */
public class AggregatedPortalCacheManagerListener
	implements PortalCacheManagerListener {

	public boolean addPortalCacheListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		if (portalCacheManagerListener == null) {
			return false;
		}

		return _portalCacheManagerListeners.add(portalCacheManagerListener);
	}

	public void clearAll() {
		_portalCacheManagerListeners.clear();
	}

	@Override
	public void dispose() throws PortalCacheException {
		for (PortalCacheManagerListener portalCacheManagerListener :
				_portalCacheManagerListeners) {

			portalCacheManagerListener.dispose();
		}
	}

	public Set<PortalCacheManagerListener> getPortalCacheManagerListeners() {
		return Collections.unmodifiableSet(_portalCacheManagerListeners);
	}

	@Override
	public void init() throws PortalCacheException {
		for (PortalCacheManagerListener portalCacheManagerListener :
				_portalCacheManagerListeners) {

			portalCacheManagerListener.init();
		}
	}

	@Override
	public void notifyPortalCacheAdded(String portalCacheName) {
		for (PortalCacheManagerListener portalCacheManagerListener :
				_portalCacheManagerListeners) {

			portalCacheManagerListener.notifyPortalCacheAdded(portalCacheName);
		}
	}

	@Override
	public void notifyPortalCacheRemoved(String portalCacheName) {
		for (PortalCacheManagerListener portalCacheManagerListener :
				_portalCacheManagerListeners) {

			portalCacheManagerListener.notifyPortalCacheRemoved(
				portalCacheName);
		}
	}

	public boolean removePortalCacheListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		if (portalCacheManagerListener == null) {
			return false;
		}

		return _portalCacheManagerListeners.remove(portalCacheManagerListener);
	}

	private final Set<PortalCacheManagerListener> _portalCacheManagerListeners =
		new CopyOnWriteArraySet<>();

}