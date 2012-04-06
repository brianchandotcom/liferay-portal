/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.LockLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class LockLocalServiceImpl extends LockLocalServiceBaseImpl {

	public void clear() throws SystemException {
		lockPersistence.removeByLtExpirationDate(new Date());
	}

	public Lock getLock(String className, long key)
		throws PortalException, SystemException {

		return getLock(className, String.valueOf(key));
	}

	public Lock getLock(String className, String key)
		throws PortalException, SystemException {

		Lock lock = lockPersistence.findByC_K(className, key);

		if (lock.isExpired()) {
			lockPersistence.remove(lock);

			throw new ExpiredLockException();
		}

		return lock;
	}

	public Lock getLockByUuid(String uuid)
		throws PortalException, SystemException {

		List<Lock> locks = lockPersistence.findByUuid(uuid);

		if (locks.isEmpty()) {
			throw new NoSuchLockException();
		}

		return locks.get(0);
	}

	public boolean hasLock(long userId, String className, long key)
		throws SystemException {

		return hasLock(userId, className, String.valueOf(key));
	}

	public boolean hasLock(long userId, String className, String key)
		throws SystemException {

		Lock lock = fetchLock(className, key);

		if ((lock != null) && (lock.getUserId() == userId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLocked(String className, long key) throws SystemException {
		return isLocked(className, String.valueOf(key));
	}

	public boolean isLocked(String className, String key)
		throws SystemException {

		Lock lock = fetchLock(className, key);

		if (lock == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException, SystemException {

		return lock(
			userId, className, String.valueOf(key), owner, inheritable,
			expirationTime);
	}

	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException, SystemException {

		Date now = new Date();

		Lock lock = lockPersistence.fetchByC_K(className, key);

		if (lock != null) {
			if (lock.isExpired()) {
				lockPersistence.remove(lock);

				lock = null;
			}
			else if (lock.getUserId() != userId) {
				throw new DuplicateLockException(lock);
			}
		}

		if (lock == null) {
			User user = userPersistence.findByPrimaryKey(userId);

			long lockId = counterLocalService.increment();

			lock = lockPersistence.create(lockId);

			lock.setCompanyId(user.getCompanyId());
			lock.setUserId(user.getUserId());
			lock.setUserName(user.getFullName());
			lock.setClassName(className);
			lock.setKey(key);
			lock.setOwner(owner);
			lock.setInheritable(inheritable);
		}

		lock.setCreateDate(now);

		if (expirationTime == 0) {
			lock.setExpirationDate(null);
		}
		else {
			lock.setExpirationDate(new Date(now.getTime() + expirationTime));
		}

		lockPersistence.update(lock, false);

		return lock;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Lock lock(
			String className, String key, String owner,
			boolean retrieveFromCache)
		throws SystemException {

		return lock(className, key, null, owner, retrieveFromCache);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Lock lock(
			String className, String key, String expectedOwner,
			String updatedOwner, boolean retrieveFromCache)
		throws SystemException {

		Lock lock = lockFinder.fetchByC_K(className, key, LockMode.UPGRADE);

		if (lock == null) {
			long lockId = counterLocalService.increment();

			lock = lockPersistence.create(lockId);

			lock.setCreateDate(new Date());
			lock.setClassName(className);
			lock.setKey(key);
			lock.setOwner(updatedOwner);

			lockPersistence.update(lock, false);

			lock.setNew(true);
		}
		else if (Validator.equals(lock.getOwner(), expectedOwner)) {
			lock.setCreateDate(new Date());
			lock.setClassName(className);
			lock.setKey(key);
			lock.setOwner(updatedOwner);

			lockPersistence.update(lock, false);

			lock.setNew(true);
		}

		return lock;
	}

	public Lock refresh(String uuid, long expirationTime)
		throws PortalException, SystemException {

		Date now = new Date();

		List<Lock> locks = lockPersistence.findByUuid(uuid);

		if (locks.isEmpty()) {
			throw new NoSuchLockException();
		}

		Lock lock = locks.get(0);

		lock.setCreateDate(now);

		if (expirationTime == 0) {
			lock.setExpirationDate(null);
		}
		else {
			lock.setExpirationDate(new Date(now.getTime() + expirationTime));
		}

		lockPersistence.update(lock, false);

		return lock;
	}

	public void unlock(String className, long key) throws SystemException {
		unlock(className, String.valueOf(key));
	}

	public void unlock(String className, String key) throws SystemException {
		try {
			lockPersistence.removeByC_K(className, key);
		}
		catch (NoSuchLockException nsle) {
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void unlock(
			String className, String key, String owner,
			boolean retrieveFromCache)
		throws SystemException {

		Lock lock = lockFinder.fetchByC_K(className, key, LockMode.UPGRADE);

		if (lock == null) {
			return;
		}

		if (Validator.equals(lock.getOwner(), owner)) {
			deleteLock(lock);
		}
	}

	protected Lock fetchLock(String className, String key)
		throws SystemException {

		Lock lock = lockPersistence.fetchByC_K(className, key);

		if (lock != null) {
			if (lock.isExpired()) {
				lockPersistence.remove(lock);

				lock = null;
			}
		}

		return lock;
	}

}