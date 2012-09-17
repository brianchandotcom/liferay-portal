/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
 * @author Edward Han
 * @author Shuyang Zhou
 */
public interface CacheListener<K extends Serializable, V> {

	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException;

	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException;

	public void notifyEntryPut(PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException;

	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException;

	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException;

	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException;

}