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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author Shuyang Zhou
 */
public abstract class IncreasableEntry<K, V> {

	public IncreasableEntry(K key, V value) {
		_key = key;
		_markedValue = new AtomicMarkableReference<V>(value, false);
	}

	public abstract V doIncrease(V originalValue, V deltaValue);

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IncreasableEntry<?, ?>)) {
			return false;
		}

		IncreasableEntry<K, V> increasableEntry = (IncreasableEntry<K, V>)obj;

		if (Validator.equals(_key, increasableEntry._key) &&
			Validator.equals(
				_markedValue.getReference(),
				increasableEntry._markedValue.getReference())) {

			return true;
		}

		return false;
	}

	public final K getKey() {
		return _key;
	}

	public final V getValue() {
		while (true) {
			V value = _markedValue.getReference();

			if (_markedValue.attemptMark(value, true)) {
				return value;
			}
		}
	}

	@Override
	public int hashCode() {
		int hash = 77;

		if (_key != null) {
			hash += _key.hashCode();
		}

		hash = 11 * hash;

		V value = _markedValue.getReference();

		if (value != null) {
			hash += value.hashCode();
		}

		return hash;
	}

	public final boolean increase(V deltaValue) {
		boolean[] marked = {false};

		while (true) {
			V originalValue = _markedValue.get(marked);

			if (marked[0]) {
				return false;
			}

			V newValue = doIncrease(originalValue, deltaValue);

			if (_markedValue.compareAndSet(
					originalValue, newValue, false, false)) {

				return true;
			}
		}
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{key=");
		sb.append(String.valueOf(_key.toString()));
		sb.append(", value=");
		sb.append(String.valueOf(_markedValue.getReference()));
		sb.append("}");

		return sb.toString();
	}

	private final K _key;
	private final AtomicMarkableReference<V> _markedValue;

}