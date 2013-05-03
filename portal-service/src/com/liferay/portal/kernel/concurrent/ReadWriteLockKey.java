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

import com.liferay.portal.kernel.util.Validator;

/**
 * <p>
 * Represents a key that is used by ReadWriteLockRegistry. T must also be
 * immutable and properly implement the equals and hashCode methods.
 * </p>
 *
 * @author Shuyang Zhou
 * @see    ReadWriteLockRegistry
 */
public class ReadWriteLockKey<T> {

	public ReadWriteLockKey(T key, boolean writeLock) {
		_key = key;
		_writeLock = writeLock;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ReadWriteLockKey<?>)) {
			return false;
		}

		ReadWriteLockKey<T> readWriteLockKey = (ReadWriteLockKey<T>)obj;

		if (Validator.equals(_key, readWriteLockKey._key)) {
			return true;
		}

		return false;
	}

	public T getKey() {
		return _key;
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	public boolean isWriteLock() {
		return _writeLock;
	}

	private final T _key;
	private final boolean _writeLock;

}