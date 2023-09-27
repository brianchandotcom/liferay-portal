/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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