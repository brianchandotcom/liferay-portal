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
		_instance._clear();
	}

	public static Lock createLock(
		long lockId, long companyId, long userId, String userName) {

		return _instance._createLock(lockId, companyId, userId, userName);
	}

	public static Lock getLock(String className, long key)
		throws PortalException {

		return _instance._getLock(className, key);
	}

	public static Lock getLock(String className, String key)
		throws PortalException {

		return _instance._getLock(className, key);
	}

	public static Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		return _instance._getLockByUuidAndCompanyId(uuid, companyId);
	}

	public static boolean hasLock(long userId, String className, long key) {
		return _instance._hasLock(userId, className, key);
	}

	public static boolean hasLock(long userId, String className, String key) {
		return _instance._hasLock(userId, className, key);
	}

	public static boolean isLocked(String className, long key) {
		return _instance._isLocked(className, key);
	}

	public static boolean isLocked(String className, String key) {
		return _instance._isLocked(className, key);
	}

	public static Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _instance._lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _instance._lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static Lock lock(String className, String key, String owner) {
		return _instance._lock(className, key, owner);
	}

	public static Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		return _instance._lock(className, key, expectedOwner, updatedOwner);
	}

	public static Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		return _instance._refresh(uuid, companyId, expirationTime);
	}

	public static void unlock(String className, long key) {
		_instance._unlock(className, key);
	}

	public static void unlock(String className, String key) {
		_instance._unlock(className, key);
	}

	public static void unlock(String className, String key, String owner) {
		_instance._unlock(className, key, owner);
	}

	private LockManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<LockManager, LockManager> serviceTracker =
			registry.trackServices(LockManager.class);

		serviceTracker.open();

		try {
			_lockHelper = serviceTracker.waitForService(0);
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to initialize lock manager util", e);
		}
		finally {
			serviceTracker.close();
		}
	}

	private void _clear() {
		_lockHelper.clear();
	}

	private Lock _createLock(
		long lockId, long companyId, long userId, String userName) {

		return _lockHelper.createLock(lockId, companyId, userId, userName);
	}

	private Lock _getLock(String className, long key) throws PortalException {
		return _lockHelper.getLock(className, key);
	}

	private Lock _getLock(String className, String key) throws PortalException {
		return _lockHelper.getLock(className, key);
	}

	private Lock _getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		return _lockHelper.getLockByUuidAndCompanyId(uuid, companyId);
	}

	private boolean _hasLock(long userId, String className, long key) {
		return _lockHelper.hasLock(userId, className, key);
	}

	private boolean _hasLock(long userId, String className, String key) {
		return _lockHelper.hasLock(userId, className, key);
	}

	private boolean _isLocked(String className, long key) {
		return _lockHelper.isLocked(className, key);
	}

	private boolean _isLocked(String className, String key) {
		return _lockHelper.isLocked(className, key);
	}

	private Lock _lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _lockHelper.lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	private Lock _lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return _lockHelper.lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	private Lock _lock(String className, String key, String owner) {
		return _lockHelper.lock(className, key, owner);
	}

	private Lock _lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		return _lockHelper.lock(className, key, expectedOwner, updatedOwner);
	}

	private Lock _refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		return _lockHelper.refresh(uuid, companyId, expirationTime);
	}

	private void _unlock(String className, long key) {
		_lockHelper.unlock(className, key);
	}

	private void _unlock(String className, String key) {
		_lockHelper.unlock(className, key);
	}

	private void _unlock(String className, String key, String owner) {
		_lockHelper.unlock(className, key, owner);
	}

	private static final LockManagerUtil _instance = new LockManagerUtil();

	private final LockManager _lockHelper;

}