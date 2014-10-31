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

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Shuyang Zhou
 */
public abstract class IncreasableEntry<K, V> {

	public IncreasableEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IncreasableEntry<?, ?>)) {
			return false;
		}

		IncreasableEntry<K, V> increasableEntry = (IncreasableEntry<K, V>)obj;

		if (Validator.equals(key, increasableEntry.key) &&
			Validator.equals(value, increasableEntry.value)) {

			return true;
		}

		return false;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, key);

		return HashUtil.hash(hash, value);
	}

	public abstract IncreasableEntry<K, V> increase(V deltaValue);

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{key=");
		sb.append(key);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	protected final K key;
	protected final V value;

}