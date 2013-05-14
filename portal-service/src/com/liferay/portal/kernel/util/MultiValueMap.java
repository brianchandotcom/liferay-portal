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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Chow
 */
public abstract class MultiValueMap
	<K extends Serializable, V extends Serializable> implements Map<K, V> {

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		throw new UnsupportedOperationException();
	}

	public abstract Set<V> getAll(Object key);

	public abstract Set<V> putAll(K key, Collection<? extends V> values);

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		MultiValueMap<? extends K, ? extends V> multiValueMap = null;

		if (map instanceof MultiValueMap<?, ?>) {
			multiValueMap = (MultiValueMap<? extends K, ? extends V>)map;
		}

		for (K key : map.keySet()) {
			if (multiValueMap != null) {
				putAll(key, multiValueMap.getAll(key));
			}
			else {
				put(key, map.get(key));
			}
		}
	}

	@Override
	public int size() {
		int size = 0;

		for (K key : keySet()) {
			size += size(key);
		}

		return size;
	}

	public int size(Object key) {
		int size = 0;

		Collection<V> values = getAll(key);

		if (values != null) {
			size = values.size();
		}

		return size;
	}

	@Override
	public Collection<V> values() {
		Set<V> values = new HashSet<V>();

		Set<K> keys = keySet();

		for (K key : keys) {
			values.addAll(getAll(key));
		}

		return values;
	}

}