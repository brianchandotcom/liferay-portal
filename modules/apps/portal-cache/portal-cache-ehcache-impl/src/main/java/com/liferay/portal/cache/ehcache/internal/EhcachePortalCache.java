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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.ehcache.internal.event.PortalCacheCacheEventListener;

import java.io.Serializable;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.event.NotificationScope;
import net.sf.ehcache.event.RegisteredEventListeners;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
public class EhcachePortalCache<K extends Serializable, V>
	extends BaseEhcachePortalCache<K, V> {

	public EhcachePortalCache(
		EhcachePortalCacheManager<K, V> ehcachePortalCacheManager,
		EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration) {

		super(ehcachePortalCacheManager, ehcachePortalCacheConfiguration);

		_cacheManager = ehcachePortalCacheManager.getEhcacheManager();
	}

	@Override
	public Ehcache getEhcache() {
		if (_ehcache == null) {
			synchronized (this) {
				if (_ehcache == null) {
					synchronized (_cacheManager) {
						if (!_cacheManager.cacheExists(getPortalCacheName())) {
							_cacheManager.addCache(getPortalCacheName());
						}
					}

					_ehcache = _cacheManager.getCache(getPortalCacheName());

					RegisteredEventListeners registeredEventListeners =
						_ehcache.getCacheEventNotificationService();

					registeredEventListeners.registerListener(
						new PortalCacheCacheEventListener<>(
							aggregatedPortalCacheListener, this),
						NotificationScope.ALL);
				}
			}
		}

		return _ehcache;
	}

	@Override
	protected void dispose() {
		_cacheManager.removeCache(getPortalCacheName());
	}

	@Override
	protected void resetEhcache() {
		_ehcache = null;
	}

	private final CacheManager _cacheManager;
	private volatile Ehcache _ehcache;

}