/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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