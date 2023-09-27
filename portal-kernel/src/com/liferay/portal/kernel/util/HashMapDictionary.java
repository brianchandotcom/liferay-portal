/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public void putAll(Dictionary<? extends K, ? extends V> dictionary) {
		Enumeration<? extends K> enumeration = dictionary.keys();

		while (enumeration.hasMoreElements()) {
			K key = enumeration.nextElement();

			_map.put(key, dictionary.get(key));
		}
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