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

package com.liferay.portal.nio.intraband.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil;
import com.liferay.portal.nio.intraband.proxy.WarnLogExceptionHandler;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseIntrabandPortalCacheManager
	<K extends Serializable, V extends Serializable>
		implements PortalCacheManager<K, V> {

	public static Class<? extends PortalCache<?, ?>> getPortalCacheStubClass() {
		return _STUB_CLASS;
	}

	@Override
	public void destroy() {
		_portalCaches.clear();
	}

	public PortalCache<K, V> fetchPortalCache(String portalCacheName) {
		return _portalCaches.get(portalCacheName);
	}

	@Override
	public PortalCache<K, V> getPortalCache(String portalCacheName) {
		return getPortalCache(portalCacheName, false);
	}

	@Override
	public PortalCache<K, V> getPortalCache(
		String portalCacheName, boolean blocking) {

		PortalCache<K, V> portalCache = _portalCaches.get(portalCacheName);

		if (portalCache == null) {
			portalCache = (PortalCache<K, V>)IntrabandProxyUtil.newStubInstance(
				_STUB_CLASS, portalCacheName, _registrationReference,
				WarnLogExceptionHandler.INSTANCE);

			_portalCaches.put(portalCacheName, portalCache);
		}

		return portalCache;
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		_portalCaches.remove(portalCacheName);
	}

	private static final Class<? extends PortalCache<?, ?>> _STUB_CLASS =
		(Class<? extends PortalCache<?, ?>>)IntrabandProxyUtil.getStubClass(
			PortalCache.class, PortalCache.class.getName());

	private final Map<String, PortalCache<K, V>> _portalCaches =
		new ConcurrentHashMap<>();
	private final RegistrationReference _registrationReference = null;

}