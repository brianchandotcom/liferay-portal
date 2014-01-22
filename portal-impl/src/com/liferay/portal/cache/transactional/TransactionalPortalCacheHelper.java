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

		PortalCacheMap portalCacheMap = _popPortalCacheMap();

		for (Map.Entry
				<PortalCache<? extends Serializable, ?>, UncommittedBuffer>
					portalCacheMapEntry : portalCacheMap.entrySet()) {

			PortalCache<Serializable, Object> portalCache =
				(PortalCache<Serializable, Object>)portalCacheMapEntry.getKey();

			UncommittedBuffer uncommittedBuffer =
				portalCacheMapEntry.getValue();

			uncommittedBuffer.commitTo(portalCache);
		}

		portalCacheMap.clear();
	}

	public static boolean isEnabled() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return false;
		}

		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return !portalCacheMaps.isEmpty();
	}

	public static void rollback() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		PortalCacheMap portalCacheMap = _popPortalCacheMap();

		portalCacheMap.clear();
	}

	protected static <K extends Serializable, V> V get(
		PortalCache<K, V> portalCache, K key) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			return null;
		}

		ValueEntry valueEntry = uncommittedBuffer.get(key);

		if (valueEntry == null) {
			return null;
		}

		return (V)valueEntry._value;
	}

	protected static <K extends Serializable, V> void put(
		PortalCache<K, V> portalCache, K key, V value, boolean quiet, int ttl) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			uncommittedBuffer = new UncommittedBuffer();

			portalCacheMap.put(portalCache, uncommittedBuffer);
		}

		uncommittedBuffer.put(key, new ValueEntry(value, quiet, ttl));
	}

	protected static <K extends Serializable, V> void removeAll(
		PortalCache<K, V> portalCache) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			uncommittedBuffer = new UncommittedBuffer();

			portalCacheMap.put(portalCache, uncommittedBuffer);
		}

		uncommittedBuffer.removeAll();
	}

	private static PortalCacheMap _peekPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return portalCacheMaps.get(portalCacheMaps.size() - 1);
	}

	private static PortalCacheMap _popPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return portalCacheMaps.remove(portalCacheMaps.size() - 1);
	}

	private static void _pushPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		portalCacheMaps.add(new PortalCacheMap());
	}

	private static ValueEntry _NULL_HOLDER_VALUE_ENTRY = new ValueEntry(
		TransactionalPortalCache.NULL_HOLDER, false, -1);

	private static ThreadLocal<List<PortalCacheMap>>
		_portalCacheMapsThreadLocal =
			new InitialThreadLocal<List<PortalCacheMap>>(
				TransactionalPortalCacheHelper.class.getName() +
					"._portalCacheMapsThreadLocal",
				new ArrayList<PortalCacheMap>());

	private static class PortalCacheMap
		extends HashMap
			<PortalCache<? extends Serializable, ?>, UncommittedBuffer> {
	}

	private static class UncommittedBuffer {

		public void commitTo(PortalCache<Serializable, Object> portalCache) {
			if (_removeAll) {
				portalCache.removeAll();
			}

			for (Map.Entry<? extends Serializable, ValueEntry> entry :
					_uncommittedMap.entrySet()) {

				ValueEntry valueEntry = entry.getValue();

				valueEntry.commitTo(portalCache, entry.getKey());
			}
		}

		public ValueEntry get(Serializable key) {
			ValueEntry valueEntry = _uncommittedMap.get(key);

			if ((valueEntry == null) && _removeAll) {
				valueEntry = _NULL_HOLDER_VALUE_ENTRY;
			}

			return valueEntry;
		}

		public void put(Serializable key, ValueEntry valueEntry) {
			ValueEntry oldValueEntry = _uncommittedMap.put(key, valueEntry);

			if (oldValueEntry != null) {
				oldValueEntry.merge(valueEntry);
			}
		}

		public void removeAll() {
			_uncommittedMap.clear();

			_removeAll = true;
		}

		private boolean _removeAll;
		private Map<Serializable, ValueEntry> _uncommittedMap =
			new HashMap<Serializable, ValueEntry>();

	}

	private static class ValueEntry {

		public ValueEntry(Object value, boolean quiet, int ttl) {
			_value = value;
			_quiet = quiet;
			_ttl = ttl;
		}

		public void commitTo(
			PortalCache<Serializable, Object> portalCache, Serializable key) {

			if (_value == TransactionalPortalCache.NULL_HOLDER) {
				portalCache.remove(key);
			}
			else if (_quiet) {
				if (_ttl >= 0) {
					portalCache.putQuiet(key, _value, _ttl);
				}
				else {
					portalCache.putQuiet(key, _value);
				}
			}
			else {
				if (_ttl >= 0) {
					portalCache.put(key, _value, _ttl);
				}
				else {
					portalCache.put(key, _value);
				}
			}
		}

		public void merge(ValueEntry valueEntry) {
			if (!_quiet) {
				valueEntry._quiet = false;
			}
		}

		private boolean _quiet;
		private int _ttl;
		private Object _value;

	}

}