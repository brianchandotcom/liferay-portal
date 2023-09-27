/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerProvider;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Preston Crary
 */
public class DummyPortalCache<K extends Serializable, V>
	implements PortalCache<K, V> {

	public DummyPortalCache(
		String portalCacheManagerName, String portalCacheName) {

		_portalCacheManagerName = portalCacheManagerName;
		_portalCacheName = portalCacheName;
	}

	@Override
	public V get(K key) {
		return null;
	}

	@Override
	public List<K> getKeys() {
		return Collections.emptyList();
	}

	@Override
	public PortalCacheManager<K, V> getPortalCacheManager() {
		return (PortalCacheManager<K, V>)
			PortalCacheManagerProvider.getPortalCacheManager(
				_portalCacheManagerName);
	}

	@Override
	public String getPortalCacheName() {
		return _portalCacheName;
	}

	@Override
	public boolean isMVCC() {
		return false;
	}

	@Override
	public boolean isSharded() {
		return false;
	}

	@Override
	public void put(K key, V value) {
	}

	@Override
	public void put(K key, V value, int timeToLive) {
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope) {
	}

	@Override
	public void remove(K key) {
	}

	@Override
	public void removeAll() {
	}

	@Override
	public void unregisterPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {
	}

	@Override
	public void unregisterPortalCacheListeners() {
	}

	private final String _portalCacheManagerName;
	private final String _portalCacheName;

}