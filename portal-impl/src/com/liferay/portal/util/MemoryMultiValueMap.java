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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.MultiValueMap;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Chow
 */
public class MemoryMultiValueMap<K extends Serializable, V extends Serializable>
	extends MultiValueMap<K, V> {

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		for (Map.Entry<K, Set<V>> entry : _map.entrySet()) {
			Set<V> values = entry.getValue();

			if (values.contains(value)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Set<V> getAll(Object key) {
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
		Set<V> values = _map.get(key);

		if (values == null) {
			values = new HashSet<V>();
		}

		values.add(value);

		_map.put(key, values);

		return value;
	}

	@Override
	public Set<V> putAll(K key, Collection<? extends V> values) {
		Set<V> oldValues = _map.get(key);

		if (oldValues == null) {
			oldValues = new HashSet<V>();
		}

		oldValues.addAll(values);

		_map.put(key, oldValues);

		return oldValues;
	}

	@Override
	public V remove(Object key) {
		V value = null;

		Set<V> values = _map.remove(key);

		if ((values != null) && !values.isEmpty()) {
			value = values.iterator().next();
		}

		return value;
	}

	private final Map<K, Set<V>> _map = new HashMap<K, Set<V>>();

}