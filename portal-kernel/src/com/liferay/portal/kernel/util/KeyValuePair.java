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
public class KeyValuePair implements Comparable<KeyValuePair>, Serializable {

	public KeyValuePair() {
		this(null, null);
	}

	public KeyValuePair(String key, String value) {
		_key = key;
		_value = value;
	}

	@Override
	public int compareTo(KeyValuePair kvp) {
		return _key.compareTo(kvp.getKey());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KeyValuePair)) {
			return false;
		}

		KeyValuePair kvp = (KeyValuePair)object;

		if (Objects.equals(_key, kvp._key)) {
			return true;
		}

		return false;
	}

	public String getKey() {
		return _key;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		if (_key != null) {
			return _key.hashCode();
		}

		return 0;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setValue(String value) {
		_value = value;
	}

	private String _key;
	private String _value;

}