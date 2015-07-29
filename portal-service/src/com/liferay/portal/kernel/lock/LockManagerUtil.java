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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Tina Tian
 */
public class LockManagerUtil {

	public static void clear() {
		_getLockManager().clear();
	}

	public static Lock createLock(
		long lockId, long companyId, long userId, String userName) {

		return _getLockManager().createLock(
			lockId, companyId, userId, userName);
	}

	public static Lock getLock(String className, long key)
		throws PortalException {

		return _getLockManager().getLock(className, key);
	}

	public static Lock getLock(String className, String key)
		throws PortalException {

		return _getLockManager().getLock(className, key);
	}

	public static Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		return _getLockManager().getLockByUuidAndCompanyId(uuid, companyId);
	}

	public static boolean hasLock(long userId, String className, long key) {
		return _getLockManager().hasLock(userId, className, key);
	}

	public static boolean hasLock(long userId, String className, String key) {
		return _getLockManager().hasLock(userId, className, key);
	}

	public static boolean isLocked(String className, long key) {
		return _getLockManager().isLocked(className, key);
	}

	public static boolean isLocked(String className, String key) {
		return _getLockManager().isLocked(className, key);
	}

	public static Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _getLockManager().lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _getLockManager().lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(String className, String key, String owner) {
		return _getLockManager().lock(className, key, owner);
	}

	public static Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		return _getLockManager().lock(
			className, key, expectedOwner, updatedOwner);
	}

	public static Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		return _getLockManager().refresh(uuid, companyId, expirationTime);
	}

	public static void unlock(String className, long key) {
		_getLockManager().unlock(className, key);
	}

	public static void unlock(String className, String key) {
		_getLockManager().unlock(className, key);
	}

	public static void unlock(String className, String key, String owner) {
		_getLockManager().unlock(className, key, owner);
	}

	private static LockManager _getLockManager() {
		return _instance._serviceTracker.getService();
	}

	private LockManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(LockManager.class);

		_serviceTracker.open();
	}

	private static final LockManagerUtil _instance = new LockManagerUtil();

	private final ServiceTracker<LockManager, LockManager> _serviceTracker;

}