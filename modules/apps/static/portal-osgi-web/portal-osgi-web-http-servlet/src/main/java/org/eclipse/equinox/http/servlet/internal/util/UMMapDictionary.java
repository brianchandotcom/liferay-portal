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

package org.eclipse.equinox.http.servlet.internal.util;

import java.util.*;

/**
 * @author Raymond Augé
 */
public class UMMapDictionary <K, V> extends Dictionary<K, V> {

	public UMMapDictionary(Map<K, V> map) {
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
	public int size() {
		return _map.size();
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
	public Enumeration<V> elements() {
		return _elements;
	}

	@Override
	public V get(Object key) {
		return _map.get(key);
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	private final Enumeration<K> _keys;
	private final Enumeration<V> _elements;
	private final Map<K, V> _map;

}