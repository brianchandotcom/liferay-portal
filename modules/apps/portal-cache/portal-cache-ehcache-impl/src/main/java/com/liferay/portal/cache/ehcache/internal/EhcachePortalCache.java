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

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.cache.ehcache.internal.event.PortalCacheCacheEventListener;

import java.io.Serializable;

import java.util.function.Supplier;

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
		BaseEhcachePortalCacheManager<K, V> baseEhcachePortalCacheManager,
		EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration) {

		super(baseEhcachePortalCacheManager, ehcachePortalCacheConfiguration);

		_cacheManager = baseEhcachePortalCacheManager.getEhcacheManager();

		_ehcacheSupplier = this::_createEhcache;
	}

	@Override
	public Ehcache getEhcache() {
		return _ehcacheDCLSingleton.getSingleton(_ehcacheSupplier);
	}

	@Override
	protected void dispose() {
		_cacheManager.removeCache(getPortalCacheName());
	}

	@Override
	protected void resetEhcache() {
		_ehcacheDCLSingleton.destroy(null);
	}

	private Ehcache _createEhcache() {
		synchronized (_cacheManager) {
			if (!_cacheManager.cacheExists(getPortalCacheName())) {
				_cacheManager.addCache(getPortalCacheName());
			}
		}

		Ehcache ehcache = _cacheManager.getCache(getPortalCacheName());

		RegisteredEventListeners registeredEventListeners =
			ehcache.getCacheEventNotificationService();

		registeredEventListeners.registerListener(
			new PortalCacheCacheEventListener<>(
				aggregatedPortalCacheListener, this),
			NotificationScope.ALL);

		return ehcache;
	}

	private final CacheManager _cacheManager;
	private final DCLSingleton<Ehcache> _ehcacheDCLSingleton =
		new DCLSingleton<>();
	private final Supplier<Ehcache> _ehcacheSupplier;

}