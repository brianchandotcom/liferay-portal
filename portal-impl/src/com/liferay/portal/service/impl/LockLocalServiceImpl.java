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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lock.LockListener;
import com.liferay.portal.kernel.lock.LockListenerRegistryUtil;
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

	@Override
	public void clear() throws SystemException {
		lockPersistence.removeByLtExpirationDate(new Date());
	}

	@Override
	public Lock getLock(String className, long key)
		throws PortalException, SystemException {

		return getLock(className, String.valueOf(key));
	}

	@Override
	public Lock getLock(String className, String key)
		throws PortalException, SystemException {

		Lock lock = lockPersistence.findByC_K(className, key);

		if (lock.isExpired()) {
			expireLock(lock);

			throw new ExpiredLockException();
		}

		return lock;
	}

	@Override
	public Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException, SystemException {

		List<Lock> locks = lockPersistence.findByUuid_C(uuid, companyId);

		if (locks.isEmpty()) {
			throw new NoSuchLockException();
		}

		return locks.get(0);
	}

	@Override
	public boolean hasLock(long userId, String className, long key)
		throws SystemException {

		return hasLock(userId, className, String.valueOf(key));
	}

	@Override
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

	@Override
	public boolean isLocked(String className, long key) throws SystemException {
		return isLocked(className, String.valueOf(key));
	}

	@Override
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

	@Override
	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException, SystemException {

		return lock(
			userId, className, String.valueOf(key), owner, inheritable,
			expirationTime);
	}

	@Override
	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException, SystemException {

		Date now = new Date();

		Lock lock = lockPersistence.fetchByC_K(className, key);

		if (lock != null) {
			if (lock.isExpired()) {
				expireLock(lock);

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

		lockPersistence.update(lock);

		return lock;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Lock lock(
			String className, String key, String owner,
			boolean retrieveFromCache)
		throws SystemException {

		return lock(className, key, null, owner, retrieveFromCache);
	}

	@Override
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

			lockPersistence.update(lock);

			lock.setNew(true);

			lockPersistence.flush();
		}
		else if (Validator.equals(lock.getOwner(), expectedOwner)) {
			lock.setCreateDate(new Date());
			lock.setClassName(className);
			lock.setKey(key);
			lock.setOwner(updatedOwner);

			lockPersistence.update(lock);

			lock.setNew(true);

			lockPersistence.flush();
		}

		return lock;
	}

	@Override
	public Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException, SystemException {

		Date now = new Date();

		List<Lock> locks = lockPersistence.findByUuid_C(uuid, companyId);

		if (locks.isEmpty()) {
			throw new NoSuchLockException();
		}

		Lock lock = locks.get(0);

		LockListener lockListener = LockListenerRegistryUtil.getLockListener(
			lock.getClassName());

		String key = lock.getKey();

		if (lockListener != null) {
			lockListener.onBeforeRefresh(key);
		}

		try {
			lock.setCreateDate(now);

			if (expirationTime == 0) {
				lock.setExpirationDate(null);
			}
			else {
				lock.setExpirationDate(
					new Date(now.getTime() + expirationTime));
			}

			lockPersistence.update(lock);

			return lock;
		}
		finally {
			if (lockListener != null) {
				lockListener.onAfterRefresh(key);
			}
		}
	}

	@Override
	public void unlock(String className, long key) throws SystemException {
		unlock(className, String.valueOf(key));
	}

	@Override
	public void unlock(String className, String key) throws SystemException {
		try {
			lockPersistence.removeByC_K(className, key);
		}
		catch (NoSuchLockException nsle) {
		}
	}

	@Override
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
			lockPersistence.remove(lock);
			lockPersistence.flush();
		}
	}

	protected void expireLock(Lock lock) throws SystemException {
		LockListener lockListener = LockListenerRegistryUtil.getLockListener(
			lock.getClassName());

		String key = lock.getKey();

		if (lockListener != null) {
			lockListener.onBeforeExpire(key);
		}

		try {
			lockPersistence.remove(lock);
		}
		finally {
			if (lockListener != null) {
				lockListener.onAfterExpire(key);
			}
		}
	}

	protected Lock fetchLock(String className, String key)
		throws SystemException {

		Lock lock = lockPersistence.fetchByC_K(className, key);

		if (lock != null) {
			if (lock.isExpired()) {
				expireLock(lock);

				lock = null;
			}
		}

		return lock;
	}

}