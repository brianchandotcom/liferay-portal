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

package com.liferay.registry.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class UnmodifiableDictionaryMap <K, V> implements Map<K, V> {

	public UnmodifiableDictionaryMap(Dictionary<K, V> dictionary) {
		Map<K, V> map = new HashMap<K, V>();

		if (dictionary != null) {
			for (Enumeration<K> enumeration = dictionary.keys();
					enumeration.hasMoreElements();) {

				K key = enumeration.nextElement();

				map.put(key, dictionary.get(key));
			}
		}

		_map = Collections.unmodifiableMap(map);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _map.containsValue(value);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return _map.entrySet();
	}

	@Override
	public V get(Object key) {
		return _map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return _map.keySet();
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public Collection<V> values() {
		return _map.values();
	}

	private final Map<K, V> _map;

}