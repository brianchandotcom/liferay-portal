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

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class IdentityKey<K> implements Serializable {

	public IdentityKey(K key) {
		_key = key;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IdentityKey)) {
			return false;
		}

		IdentityKey<K> identityKey = (IdentityKey<K>)obj;

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