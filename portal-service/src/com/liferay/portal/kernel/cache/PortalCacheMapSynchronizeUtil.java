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

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheMapSynchronizeUtil {

	public static <K extends Serializable, V> void synchronize(
		PortalCache<K, V> portalCache, Map<? extends K, ? extends V> map,
		Synchronizer<K, V> synchronizer) {

		portalCache.registerCacheListener(
			new SynchronizeCacheListener<K, V>(map, synchronizer));
	}

	public interface Synchronizer<K extends Serializable, V> {

		public void onSynchronize(
			Map<? extends K, ? extends V> map, K key, V value, int timeToLive);

	}

	private static class SynchronizeCacheListener<K extends Serializable, V>
		implements CacheListener<K, V> {

		public SynchronizeCacheListener(
			Map<? extends K, ? extends V> map,
			Synchronizer<K, V> synchronizer) {

			_map = map;
			_synchronizer = synchronizer;
		}

		@Override
		public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyRemoveAll(PortalCache<K, V> portalCache) {
			_map.clear();
		}

		private final Map<? extends K, ? extends V> _map;
		private final Synchronizer<K, V> _synchronizer;

	}

}