/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
public interface PortalCache<K extends Serializable, V> {

	public V get(K key);

	public String getName();

	public void put(K key, V value);

	public void put(K key, V value, int timeToLive);

	public void registerCacheListener(CacheListener<K, V> cacheListener);

	public void registerCacheListener(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope);

	public void remove(K key);

	public void removeAll();

	public void unregisterCacheListener(CacheListener<K, V> cacheListener);

	public void unregisterCacheListeners();

}