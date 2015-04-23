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

import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.net.URL;

import java.util.Set;

/**
 * @author Tina Tian
 */
public class MockPortalCacheManager<K extends Serializable, V>
	implements PortalCacheManager<K, V> {

	public MockPortalCacheManager(String name) {
		_name = name;
	}

	@Override
	public void clearAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void destroy() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortalCache<K, V> getCache(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortalCache<K, V> getCache(String name, boolean blocking) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<CacheManagerListener> getCacheManagerListeners() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isClusterAware() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean registerCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void removeCache(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean unregisterCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void unregisterCacheManagerListeners() {
		throw new UnsupportedOperationException();
	}

	private final String _name;

}