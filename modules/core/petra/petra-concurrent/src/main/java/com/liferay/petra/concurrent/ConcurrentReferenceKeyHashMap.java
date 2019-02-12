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

import com.liferay.petra.memory.FinalizeAction;
import com.liferay.petra.memory.FinalizeManager;

import java.lang.ref.Reference;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentReferenceKeyHashMap<K, V>
	extends ConcurrentMapperHashMap<K, Reference<K>, V, V> {

	public ConcurrentReferenceKeyHashMap(
		ConcurrentMap<Reference<K>, V> innerConcurrentMap,
		FinalizeManager.ReferenceFactory referenceFactory) {

		super(innerConcurrentMap);

		_referenceFactory = referenceFactory;
	}

	public ConcurrentReferenceKeyHashMap(
		FinalizeManager.ReferenceFactory referenceFactory) {

		this(new ConcurrentHashMap<>(), referenceFactory);
	}

	public ConcurrentReferenceKeyHashMap(
		int initialCapacity,
		FinalizeManager.ReferenceFactory referenceFactory) {

		this(new ConcurrentHashMap<>(initialCapacity), referenceFactory);
	}

	public ConcurrentReferenceKeyHashMap(
		int initialCapacity, float loadFactor, int concurrencyLevel,
		FinalizeManager.ReferenceFactory referenceFactory) {

		this(
			new ConcurrentHashMap<>(
				initialCapacity, loadFactor, concurrencyLevel),
			referenceFactory);
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

	private final FinalizeManager.ReferenceFactory _referenceFactory;

}