/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class IdentityKey<K> implements Serializable {

	public IdentityKey(K key) {
		_key = key;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof IdentityKey)) {
			return false;
		}

		IdentityKey<K> identityKey = (IdentityKey<K>)object;

		if (_key == identityKey._key) {
			return true;
		}

		return false;
	}

	public K getKey() {
		return _key;
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	private static final long serialVersionUID = 1L;

	private final K _key;

}