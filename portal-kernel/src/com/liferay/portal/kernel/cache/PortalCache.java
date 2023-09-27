/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
@ProviderType
public interface PortalCache<K extends Serializable, V> {

	public static final int DEFAULT_TIME_TO_LIVE = 0;

	public V get(K key);

	public List<K> getKeys();

	public PortalCacheManager<K, V> getPortalCacheManager();

	public String getPortalCacheName();

	public boolean isMVCC();

	public boolean isSharded();

	public void put(K key, V value);

	public void put(K key, V value, int timeToLive);

	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener);

	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope);

	public void remove(K key);

	public void removeAll();

	public void unregisterPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener);

	public void unregisterPortalCacheListeners();

}