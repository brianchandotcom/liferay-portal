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

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class PortalCacheHelperUtil {

	public static void clearPortalCaches(String portalCacheManagerName) {
		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		portalCacheManager.clearAll();
	}

	public static <K extends Serializable, V> PortalCache<K, V> getPortalCache(
		String portalCacheManagerName, String portalCacheName) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		return (PortalCache<K, V>)portalCacheManager.getPortalCache(
			portalCacheName);
	}

	public static <K extends Serializable, V> PortalCache<K, V> getPortalCache(
		String portalCacheManagerName, String portalCacheName,
		boolean blocking) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		return (PortalCache<K, V>)portalCacheManager.getPortalCache(
			portalCacheName, blocking);
	}

	public static <K extends Serializable, V> PortalCache<K, V> getPortalCache(
		String portalCacheManagerName, String portalCacheName, boolean blocking,
		boolean mvcc) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		return (PortalCache<K, V>)portalCacheManager.getPortalCache(
			portalCacheName, blocking, mvcc);
	}

	public static <K extends Serializable, V> void putWithoutReplicator(
		PortalCache<K, V> portalCache, K key, V value) {

		putWithoutReplicator(
			portalCache, key, value, PortalCache.DEFAULT_TIME_TO_LIVE);
	}

	public static <K extends Serializable, V> void putWithoutReplicator(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

		boolean enabled = SkipReplicationThreadLocal.isEnabled();

		if (!enabled) {
			SkipReplicationThreadLocal.setEnabled(true);
		}

		try {
			portalCache.put(key, value, timeToLive);
		}
		finally {
			if (!enabled) {
				SkipReplicationThreadLocal.setEnabled(false);
			}
		}
	}

	public static void removeAllWithoutReplicator(
		PortalCache<?, ?> portalCache) {

		boolean skip = SkipReplicationThreadLocal.isEnabled();

		if (!skip) {
			SkipReplicationThreadLocal.setEnabled(true);
		}

		try {
			portalCache.removeAll();
		}
		finally {
			if (!skip) {
				SkipReplicationThreadLocal.setEnabled(false);
			}
		}
	}

	public static void removePortalCache(
		String portalCacheManagerName, String portalCacheName) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		portalCacheManager.removePortalCache(portalCacheName);
	}

	public static <K extends Serializable> void removeWithoutReplicator(
		PortalCache<K, ?> portalCache, K key) {

		boolean skip = SkipReplicationThreadLocal.isEnabled();

		if (!skip) {
			SkipReplicationThreadLocal.setEnabled(true);
		}

		try {
			portalCache.remove(key);
		}
		finally {
			if (!skip) {
				SkipReplicationThreadLocal.setEnabled(false);
			}
		}
	}

}