/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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