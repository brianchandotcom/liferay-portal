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

package com.liferay.portal.kernel.cache.index;

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheIndexer<I, K extends IndexedCacheKey<I>, V> {

	public PortalCacheIndexer(PortalCache<K, V> portalCache) {
		_portalCache = portalCache;

		_portalCache.registerCacheListener(new IndexerCacheListener());

		for (K indexedCacheKey : _portalCache.getKeys()) {
			_addIndexedCacheKey(indexedCacheKey);
		}
	}

	public Set<K> getIndexedCacheKeys(I index) {
		return _indexedCacheKeys.get(index);
	}

	public void removeIndexedCacheKeys(I index) {
		Set<K> indexedCacheKeys = _indexedCacheKeys.remove(index);

		if (indexedCacheKeys == null) {
			return;
		}

		for (K indexedCacheKey : indexedCacheKeys) {
			_portalCache.remove(indexedCacheKey);
		}
	}

	private void _addIndexedCacheKey(K indexedCacheKey) {
		I index = indexedCacheKey.getIndex();

		Set<K> indexedCacheKeys = _indexedCacheKeys.get(index);

		if (indexedCacheKeys == null) {
			indexedCacheKeys = new HashSet<>();

			Set<K> previousIndexedCacheKeys = _indexedCacheKeys.putIfAbsent(
				index, indexedCacheKeys);

			if (previousIndexedCacheKeys != null) {
				indexedCacheKeys = previousIndexedCacheKeys;
			}
		}

		indexedCacheKeys.add(indexedCacheKey);
	}

	private void _removeIndexedCacheKey(K indexedCacheKey) {
		I index = indexedCacheKey.getIndex();

		while (true) {
			Set<K> indexedCacheKeys = _indexedCacheKeys.get(index);

			if (indexedCacheKeys == null) {
				return;
			}

			Set<K> newIndexedCacheKeys = new HashSet<>(indexedCacheKeys);

			if (!newIndexedCacheKeys.remove(indexedCacheKey)) {
				return;
			}

			if (newIndexedCacheKeys.isEmpty()) {
				if (_indexedCacheKeys.remove(index, indexedCacheKeys)) {
					return;
				}
			}
			else if (_indexedCacheKeys.replace(
						index, indexedCacheKeys, newIndexedCacheKeys)) {

				return;
			}
		}
	}

	private final ConcurrentMap<I, Set<K>> _indexedCacheKeys =
		new ConcurrentHashMap<>();
	private final PortalCache<K, V> _portalCache;

	private class IndexerCacheListener implements CacheListener<K, V> {

		@Override
		public void dispose() {
			_indexedCacheKeys.clear();
		}

		@Override
		public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_removeIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_removeIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryPut(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_addIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_removeIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {
		}

		@Override
		public void notifyRemoveAll(PortalCache<K, V> portalCache) {
			_indexedCacheKeys.clear();
		}

	}

}