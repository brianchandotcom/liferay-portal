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

package com.liferay.portal.kernel.util;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class HashMapDictionary<K, V> extends Dictionary<K, V> {

	public HashMapDictionary() {
	}

	public HashMapDictionary(Map<K, V> map) {
		_map.putAll(map);
	}

	@Override
	public Enumeration<V> elements() {
		return Collections.enumeration(_map.values());
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
		return Collections.enumeration(_map.keySet());
	}

	@Override
	public V put(K key, V value) {
		return _map.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		_map.putAll(map);
	}

	@Override
	public V remove(Object key) {
		return _map.remove(key);
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public String toString() {
		return _map.toString();
	}

	private final Map<K, V> _map = new HashMap<>();

}