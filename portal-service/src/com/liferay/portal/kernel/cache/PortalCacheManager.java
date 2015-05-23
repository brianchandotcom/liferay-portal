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

import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Proxy;

import java.io.Serializable;

import java.net.URL;

import java.util.Set;

/**
 * @author Joseph Shum
 */
public interface PortalCacheManager<K extends Serializable, V> {

	public static final String PORTAL_CACHE_MANAGER_NAME =
		"portal.cache.manager.name";

	public static final String PORTAL_CACHE_MANAGER_TYPE =
		"portal.cache.manager.type";

	@Proxy
	public void clearAll() throws PortalCacheException;

	public void destroy();

	public PortalCache<K, V> getCache(String name) throws PortalCacheException;

	public PortalCache<K, V> getCache(String name, boolean blocking)
		throws PortalCacheException;

	public Set<CacheManagerListener> getCacheManagerListeners();

	public String getName();

	public boolean isClusterAware();

	@Proxy
	public void reconfigureCaches(URL configurationURL);

	public boolean registerCacheManagerListener(
		CacheManagerListener cacheManagerListener);

	public void removeCache(String name);

	public boolean unregisterCacheManagerListener(
		CacheManagerListener cacheManagerListener);

	public void unregisterCacheManagerListeners();

}