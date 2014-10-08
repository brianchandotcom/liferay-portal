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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class LockRegistry {

	public static ReentrantLock allocateLock(String groupName, String key) {
		ConcurrentHashMap<String, ReentrantLock> lockGroup = _lockGroupMap.get(
			groupName);

		if (lockGroup == null) {
			lockGroup = new ConcurrentHashMap<String, ReentrantLock>();

			ConcurrentHashMap<String, ReentrantLock> oldLockGroup =
				_lockGroupMap.putIfAbsent(groupName, lockGroup);

			if (oldLockGroup != null) {
				lockGroup = oldLockGroup;
			}
		}

		ReentrantLock lock = lockGroup.get(key);

		if (lock == null) {
			lock = new ReentrantLock();

			ReentrantLock oldLock = lockGroup.putIfAbsent(key, lock);

			if (oldLock != null) {
				lock = oldLock;
			}
		}

		return lock;
	}

	public static ReentrantLock finallyFreeLock(
		String groupName, String key, boolean unlock) {

		if (_prematureLockReleases.containsKey(groupName + key)) {
			return freeLock(groupName, key, unlock);
		}

		return null;
	}

	public static void freeAllLock() {
		freeAllLock(false);
	}

	public static void freeAllLock(boolean unlock) {
		if (unlock == true) {
			for (Map<String, ReentrantLock> lockGroup :
					_lockGroupMap.values()) {

				for (ReentrantLock lock : lockGroup.values()) {
					lock.unlock();
				}
			}
		}

		_lockGroupMap.clear();
		_prematureLockReleases.clear();
	}

	public static Map<String, ReentrantLock> freeLock(String groupName) {
		return freeLock(groupName, false);
	}

	public static Map<String, ReentrantLock> freeLock(
		String groupName, boolean unlock) {

		Map<String, ReentrantLock> lockGroup = _lockGroupMap.remove(groupName);

		if (lockGroup == null) {
			_prematureLockReleases.put(groupName, _dummyValue);

			return null;
		}

		if (unlock == true) {
			for (ReentrantLock lock : lockGroup.values()) {
				lock.unlock();
			}

			_prematureLockReleases.remove(groupName);
		}

		return lockGroup;
	}

	public static ReentrantLock freeLock(String groupName, String key) {
		return freeLock(groupName, key, false);
	}

	public static ReentrantLock freeLock(
		String groupName, String key, boolean unlock) {

		Map<String, ReentrantLock> lockGroup = _lockGroupMap.get(groupName);

		String prematureLockReleasesKey = groupName + key;

		if (lockGroup == null) {
			_prematureLockReleases.put(prematureLockReleasesKey, _dummyValue);

			return null;
		}

		ReentrantLock lock = lockGroup.remove(key);

		if (lock == null) {
			_prematureLockReleases.put(prematureLockReleasesKey, _dummyValue);

			return null;
		}

		if (unlock) {
			lock.unlock();

			_prematureLockReleases.remove(prematureLockReleasesKey);
		}

		return lock;
	}

	public static ReentrantLock getLock(String groupName, String key) {
		ConcurrentHashMap<String, ReentrantLock> lockGroup = _lockGroupMap.get(
			groupName);

		if (lockGroup == null) {
			return null;
		}

		return lockGroup.get(key);
	}

	private static final Object _dummyValue = new Object();
	private static final ConcurrentHashMap
		<String, ConcurrentHashMap<String, ReentrantLock>>
			_lockGroupMap =
				new ConcurrentHashMap
					<String, ConcurrentHashMap<String, ReentrantLock>>();
	private static final Map<String, Object> _prematureLockReleases =
		new ConcurrentHashMap<String, Object>();

}