/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.util;

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
public class UMDictionaryMap<K, V> implements Map<K, V> {

	public UMDictionaryMap(Dictionary<K, V> dictionary) {
		Map<K, V> map = new HashMap<>();

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