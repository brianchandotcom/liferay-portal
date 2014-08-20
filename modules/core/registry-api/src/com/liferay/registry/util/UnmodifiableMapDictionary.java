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

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class UnmodifiableMapDictionary <K, V> extends Dictionary<K, V> {

	public UnmodifiableMapDictionary(Map<K, V> map) {
		if (map == null) {
			_map = Collections.emptyMap();
		}
		else {
			_map = Collections.unmodifiableMap(map);
		}

		_keys = Collections.enumeration(_map.keySet());
		_elements = Collections.enumeration(_map.values());
	}

	@Override
	public Enumeration<V> elements() {
		return _elements;
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
	public Enumeration<K> keys() {
		return _keys;
	}

	@Override
	public V put(K key, V value) {
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

	private final Enumeration<V> _elements;
	private final Enumeration<K> _keys;
	private final Map<K, V> _map;

}