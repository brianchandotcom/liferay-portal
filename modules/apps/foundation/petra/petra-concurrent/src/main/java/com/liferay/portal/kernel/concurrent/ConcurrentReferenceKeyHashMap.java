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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.memory.FinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.memory.FinalizeManager.ReferenceFactory;

import java.lang.ref.Reference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentReferenceKeyHashMap<K, V>
	extends ConcurrentMapperHashMap<K, Reference<K>, V, V> {

	public ConcurrentReferenceKeyHashMap(
		ConcurrentMap<Reference<K>, V> innerConcurrentMap,
		ReferenceFactory referenceFactory) {

		super(innerConcurrentMap);

		_referenceFactory = referenceFactory;
	}

	public ConcurrentReferenceKeyHashMap(
		int initialCapacity, float loadFactor, int concurrencyLevel,
		ReferenceFactory referenceFactory) {

		this(
			new ConcurrentHashMap<Reference<K>, V>(
				initialCapacity, loadFactor, concurrencyLevel),
			referenceFactory);
	}

	public ConcurrentReferenceKeyHashMap(
		int initialCapacity, ReferenceFactory referenceFactory) {

		this(
			new ConcurrentHashMap<Reference<K>, V>(initialCapacity),
			referenceFactory);
	}

	public ConcurrentReferenceKeyHashMap(
		Map<? extends K, ? extends V> map, ReferenceFactory referenceFactory) {

		this(new ConcurrentHashMap<Reference<K>, V>(), referenceFactory);

		putAll(map);
	}

	public ConcurrentReferenceKeyHashMap(ReferenceFactory referenceFactory) {
		this(new ConcurrentHashMap<Reference<K>, V>(), referenceFactory);
	}

	@Override
	protected Reference<K> mapKey(K key) {
		return FinalizeManager.register(
			key, _keyFinalizeAction, _referenceFactory);
	}

	@Override
	protected Reference<K> mapKeyForQuery(K key) {
		return _referenceFactory.createReference(key, null);
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
	protected K unmapKey(Reference<K> reference) {
		K key = reference.get();

		reference.clear();

		return key;
	}

	@Override
	protected K unmapKeyForQuery(Reference<K> reference) {
		return reference.get();
	}

	@Override
	protected V unmapValue(V value) {
		return value;
	}

	@Override
	protected V unmapValueForQuery(V value) {
		return value;
	}

	private final FinalizeAction _keyFinalizeAction = new FinalizeAction() {

		@Override
		public void doFinalize(Reference<?> reference) {
			innerConcurrentMap.remove(reference);
		}

	};

	private final ReferenceFactory _referenceFactory;

}