/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String portalCacheManagerName, String portalCacheName, boolean mvcc) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		return (PortalCache<K, V>)portalCacheManager.getPortalCache(
			portalCacheName, mvcc);
	}

	public static <K extends Serializable, V> PortalCache<K, V> getPortalCache(
		String portalCacheManagerName, String portalCacheName, boolean mvcc,
		boolean sharded) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		return (PortalCache<K, V>)portalCacheManager.getPortalCache(
			portalCacheName, mvcc, sharded);
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

	public static void removePortalCaches(
		String portalCacheManagerName, long companyId) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		portalCacheManager.removePortalCaches(companyId);
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