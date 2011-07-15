/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class KeyValuePair<K extends Comparable<K>, V>
	implements Comparable<KeyValuePair<K, V>>, Serializable {

	public KeyValuePair() {
		this(null, null);
	}

	public KeyValuePair(K key, V value) {
		_key = key;
		_value = value;
	}

	public K getKey() {
		return _key;
	}

	public void setKey(K key) {
		_key = key;
	}

	public V getValue() {
		return _value;
	}

	public void setValue(V value) {
		_value = value;
	}

	public int compareTo(KeyValuePair<K, V> kvp) {
		return _key.compareTo(kvp.getKey());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KeyValuePair)) {
			return false;
		}

		KeyValuePair<K, V> kvp = (KeyValuePair<K, V>)obj;

		if (Validator.equals(_key, kvp._key)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		if (_key != null) {
			return _key.hashCode();
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{key=");
		sb.append(_key);
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	private K _key;
	private V _value;

}