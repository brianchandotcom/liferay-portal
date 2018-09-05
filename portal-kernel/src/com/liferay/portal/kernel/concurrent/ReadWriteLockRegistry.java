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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 * Registry for {@link ReadWriteLock} objects with {@link ReadWriteLockKey} as
 * keys. The behavior of acquiring and releasing locks is provided by a {@link
 * ConcurrentHashMap}. This class is completely thread safe and ensures that
 * only one {@link ReadWriteLock} exists per key.
 * </p>
 *
 * @author     Shuyang Zhou
 * @see        ReadWriteLock
 * @see        ReadWriteLockKey
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ReadWriteLockRegistry {

	public Lock acquireLock(ReadWriteLockKey<?> readWriteLockKey) {
		ReadWriteLock readWriteLock = _readWriteLockMap.get(readWriteLockKey);

		if (readWriteLock == null) {
			ReadWriteLock newReadWriteLock = new ReentrantReadWriteLock();

			readWriteLock = _readWriteLockMap.putIfAbsent(
				readWriteLockKey, newReadWriteLock);

			if (readWriteLock == null) {
				readWriteLock = newReadWriteLock;
			}
		}

		if (readWriteLockKey.isWriteLock()) {
			return readWriteLock.writeLock();
		}

		return readWriteLock.readLock();
	}

	public void releaseLock(ReadWriteLockKey<?> readWriteLockKey) {
		if (readWriteLockKey.isWriteLock()) {
			_readWriteLockMap.remove(readWriteLockKey);
		}
	}

	private final ConcurrentMap<ReadWriteLockKey<?>, ReadWriteLock>
		_readWriteLockMap = new ConcurrentHashMap<>();

}