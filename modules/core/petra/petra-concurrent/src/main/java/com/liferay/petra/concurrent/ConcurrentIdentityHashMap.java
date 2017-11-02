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

package com.liferay.petra.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentIdentityHashMap<K, V>
	extends ConcurrentMapperHashMap<K, IdentityKey<K>, V, V> {

	public ConcurrentIdentityHashMap() {
		this(new ConcurrentHashMap<IdentityKey<K>, V>());
	}

	public ConcurrentIdentityHashMap(
		ConcurrentMap<IdentityKey<K>, V> innerConcurrentMap) {

		super(innerConcurrentMap);
	}

	public ConcurrentIdentityHashMap(int initialCapacity) {
		this(new ConcurrentHashMap<IdentityKey<K>, V>(initialCapacity));
	}

	public ConcurrentIdentityHashMap(
		int initialCapacity, float loadFactor, int concurrencyLevel) {

		this(
			new ConcurrentHashMap<IdentityKey<K>, V>(
				initialCapacity, loadFactor, concurrencyLevel));
	}

	public ConcurrentIdentityHashMap(Map<? extends K, ? extends V> map) {
		this(new ConcurrentHashMap<IdentityKey<K>, V>());

		putAll(map);
	}

	@Override
	protected IdentityKey<K> mapKey(K key) {
		return new IdentityKey<>(key);
	}

	@Override
	protected IdentityKey<K> mapKeyForQuery(K key) {
		return new IdentityKey<>(key);
	}

	@Override
	protected V mapValue(K key, V value) {
		return value;
	}

	@Override
	protected V mapValueForQuery(V value) {
		return value;
	}

	@Override
	protected K unmapKey(IdentityKey<K> identityKey) {
		return identityKey.getKey();
	}

	@Override
	protected K unmapKeyForQuery(IdentityKey<K> identityKey) {
		return identityKey.getKey();
	}

	@Override
	protected V unmapValue(V value) {
		return value;
	}

	@Override
	protected V unmapValueForQuery(V value) {
		return value;
	}

}