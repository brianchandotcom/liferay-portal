/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Tina Tian
 */
public class LockManagerUtil {

	public static Lock createLock(
		long lockId, long companyId, long userId, String userName) {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.createLock(lockId, companyId, userId, userName);
	}

	public static Lock fetchLock(String className, long key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.fetchLock(className, key);
	}

	public static Lock fetchLock(String className, String key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.fetchLock(className, key);
	}

	public static Lock getLock(String className, long key)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.getLock(className, key);
	}

	public static Lock getLock(String className, String key)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.getLock(className, key);
	}

	public static Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.getLockByUuidAndCompanyId(uuid, companyId);
	}

	public static boolean hasLock(long userId, String className, long key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.hasLock(userId, className, key);
	}

	public static boolean hasLock(long userId, String className, String key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.hasLock(userId, className, key);
	}

	public static boolean isLocked(String className, long key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.isLocked(className, key);
	}

	public static boolean isLocked(String className, String key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.isLocked(className, key);
	}

	public static Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.lock(
			userId, className, key, owner, inheritable, expirationTime, renew);
	}

	public static Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.lock(
			userId, className, key, owner, inheritable, expirationTime, renew);
	}

	public static Lock lock(String className, String key, String owner) {
		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.lock(className, key, owner);
	}

	public static Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.lock(className, key, expectedOwner, updatedOwner);
	}

	public static Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		LockManager lockManager = _lockManagerSnapshot.get();

		return lockManager.refresh(uuid, companyId, expirationTime);
	}

	public static void unlock(String className, long key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		lockManager.unlock(className, key);
	}

	public static void unlock(String className, String key) {
		LockManager lockManager = _lockManagerSnapshot.get();

		lockManager.unlock(className, key);
	}

	public static void unlock(String className, String key, String owner) {
		LockManager lockManager = _lockManagerSnapshot.get();

		lockManager.unlock(className, key, owner);
	}

	private static final Snapshot<LockManager> _lockManagerSnapshot =
		new Snapshot<>(LockManagerUtil.class, LockManager.class);

}