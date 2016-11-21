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

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Tina Tian
 */
public class LockManagerUtil {

	public static void clear() {
		_lockManager.clear();
	}

	public static Lock createLock(
		long lockId, long companyId, long userId, String userName) {

		return _lockManager.createLock(lockId, companyId, userId, userName);
	}

	public static Lock getLock(String className, long key)
		throws PortalException {

		return _lockManager.getLock(className, key);
	}

	public static Lock getLock(String className, String key)
		throws PortalException {

		return _lockManager.getLock(className, key);
	}

	public static Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		return _lockManager.getLockByUuidAndCompanyId(uuid, companyId);
	}

	public static boolean hasLock(long userId, String className, long key) {
		return _lockManager.hasLock(userId, className, key);
	}

	public static boolean hasLock(long userId, String className, String key) {
		return _lockManager.hasLock(userId, className, key);
	}

	public static boolean isLocked(String className, long key) {
		return _lockManager.isLocked(className, key);
	}

	public static boolean isLocked(String className, String key) {
		return _lockManager.isLocked(className, key);
	}

	public static Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _lockManager.lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _lockManager.lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(String className, String key, String owner) {
		return _lockManager.lock(className, key, owner);
	}

	public static Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		return _lockManager.lock(className, key, expectedOwner, updatedOwner);
	}

	public static Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		return _lockManager.refresh(uuid, companyId, expirationTime);
	}

	public static void unlock(String className, long key) {
		_lockManager.unlock(className, key);
	}

	public static void unlock(String className, String key) {
		_lockManager.unlock(className, key);
	}

	public static void unlock(String className, String key, String owner) {
		_lockManager.unlock(className, key, owner);
	}

	private static volatile LockManager _lockManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			LockManager.class, LockManagerUtil.class, "_lockManager", false);

}