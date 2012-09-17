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

package com.liferay.portal.cache.transactional;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCacheHelper {

	public static void begin() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		_pushPortalCacheMap();
	}

	public static void commit() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>
				portalCacheMap = _popPortalCacheMap();

		for (Map.Entry<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>
				portalCacheMapEntry : portalCacheMap.entrySet()) {

			@SuppressWarnings("unchecked")
			PortalCache<Serializable, Object> portalCache =
				(PortalCache<Serializable, Object>)portalCacheMapEntry.getKey();

			Map<? extends Serializable, ?> uncommittedMap =
				portalCacheMapEntry.getValue();

			for (Map.Entry<? extends Serializable, ?> uncommittedMapEntry :
					uncommittedMap.entrySet()) {

				portalCache.put(
					uncommittedMapEntry.getKey(),
					uncommittedMapEntry.getValue());
			}
		}

		portalCacheMap.clear();
	}

	public static boolean isEnabled() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return false;
		}

		List<Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>>
				portalCacheList = _portalCacheListThreadLocal.get();

		return !portalCacheList.isEmpty();
	}

	public static void rollback() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>
				portalCacheMap = _popPortalCacheMap();

		portalCacheMap.clear();
	}

	@SuppressWarnings("unchecked")
	protected static <K extends Serializable, V> V get(
		PortalCache<K, V> portalCache, K key) {

		Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>
				portalCacheMap = _peekPortalCacheMap();

		Map<? extends Serializable, ?> uncommittedMap = portalCacheMap.get(
			portalCache);

		if (uncommittedMap == null) {
			return null;
		}

		return (V)uncommittedMap.get(key);
	}

	protected static <K extends Serializable, V> void put(
		PortalCache<K, V> portalCache, K key, V value) {

		Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>
				portalCacheMap = _peekPortalCacheMap();

		@SuppressWarnings("unchecked")
		Map<Serializable, Object> uncommittedMap =
			(Map<Serializable, Object>)portalCacheMap.get(portalCache);

		if (uncommittedMap == null) {
			uncommittedMap = new HashMap<Serializable, Object>();

			portalCacheMap.put(portalCache, uncommittedMap);
		}

		uncommittedMap.put(key, value);
	}

	protected static <K extends Serializable, V> void remove(
		PortalCache<K, V> portalCache, K key) {

		Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>
				portalCacheMap = _peekPortalCacheMap();

		Map<? extends Serializable, ?> uncommittedMap = portalCacheMap.get(
			portalCache);

		if (uncommittedMap != null) {
			uncommittedMap.remove(key);
		}
	}

	protected static <K extends Serializable, V> void removeAll(
		PortalCache<K, V> portalCache) {

		Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>
				portalCacheMap = _peekPortalCacheMap();

		Map<? extends Serializable, ?> uncommittedMap = portalCacheMap.get(
			portalCache);

		if (uncommittedMap != null) {
			uncommittedMap.clear();
		}
	}

	private static Map<
		PortalCache<? extends Serializable, ?>,
		Map<? extends Serializable, ?>> _peekPortalCacheMap() {

		List<Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>>
				portalCacheList = _portalCacheListThreadLocal.get();

		return portalCacheList.get(portalCacheList.size() - 1);
	}

	private static Map<
		PortalCache<? extends Serializable, ?>,
		Map<? extends Serializable, ?>> _popPortalCacheMap() {

		List<Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>>
				portalCacheList = _portalCacheListThreadLocal.get();

		return portalCacheList.remove(portalCacheList.size() - 1);
	}

	private static void _pushPortalCacheMap() {
		List<Map<
			PortalCache<? extends Serializable, ?>,
			Map<? extends Serializable, ?>>>
				portalCacheList = _portalCacheListThreadLocal.get();

		portalCacheList.add(
			new HashMap<
				PortalCache<? extends Serializable, ?>,
				Map<? extends Serializable, ?>>());
	}

	private static ThreadLocal<List<Map<
		PortalCache<? extends Serializable, ?>,
		Map<? extends Serializable, ?>>>>
			_portalCacheListThreadLocal = new InitialThreadLocal<List<Map<
				PortalCache<? extends Serializable, ?>,
				Map<? extends Serializable, ?>>>>(
					TransactionalPortalCacheHelper.class.getName() +
						"._portalCacheListThreadLocal",
					new ArrayList<Map<
						PortalCache<? extends Serializable, ?>,
						Map<? extends Serializable, ?>>>());

}