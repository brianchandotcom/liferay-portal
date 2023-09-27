/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class ObjectValuePair<K, V> implements Serializable {

	public ObjectValuePair() {
	}

	public ObjectValuePair(K key, V value) {
		_key = key;
		_value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectValuePair<?, ?>)) {
			return false;
		}

		ObjectValuePair<K, V> kvp = (ObjectValuePair<K, V>)object;

		if (Objects.equals(_key, kvp._key)) {
			return true;
		}

		return false;
	}

	public K getKey() {
		return _key;
	}

	public V getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		if (_key != null) {
			return _key.hashCode();
		}

		return 0;
	}

	public void setKey(K key) {
		_key = key;
	}

	public void setValue(V value) {
		_value = value;
	}

	private static final long serialVersionUID = 6341296770402285296L;

	private K _key;
	private V _value;

}