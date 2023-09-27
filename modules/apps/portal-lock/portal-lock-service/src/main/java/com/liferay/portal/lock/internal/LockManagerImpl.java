/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lock.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.ExpiredLockException;
import com.liferay.portal.kernel.lock.InvalidLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.lock.NoSuchLockException;
import com.liferay.portal.lock.service.LockLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(service = LockManager.class)
public class LockManagerImpl implements LockManager {

	@Override
	public Lock createLock(
		long lockId, long companyId, long userId, String userName) {

		com.liferay.portal.lock.model.Lock lock = _lockLocalService.createLock(
			lockId);

		lock.setCompanyId(companyId);
		lock.setUserId(userId);
		lock.setUserName(userName);
		lock.setCreateDate(new Date());

		return new LockImpl(lock);
	}

	@Override
	public Lock fetchLock(String className, long key) {
		com.liferay.portal.lock.model.Lock lock = _lockLocalService.fetchLock(
			className, key);

		if (lock == null) {
			return null;
		}

		return new LockImpl(lock);
	}

	@Override
	public Lock fetchLock(String className, String key) {
		com.liferay.portal.lock.model.Lock lock = _lockLocalService.fetchLock(
			className, key);

		if (lock == null) {
			return null;
		}

		return new LockImpl(lock);
	}

	@Override
	public Lock getLock(String className, long key) throws PortalException {
		try {
			return new LockImpl(_lockLocalService.getLock(className, key));
		}
		catch (PortalException portalException) {
			throw _translate(portalException);
		}
	}

	@Override
	public Lock getLock(String className, String key) throws PortalException {
		try {
			return new LockImpl(_lockLocalService.getLock(className, key));
		}
		catch (PortalException portalException) {
			throw _translate(portalException);
		}
	}

	@Override
	public Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		try {
			return new LockImpl(
				_lockLocalService.getLockByUuidAndCompanyId(uuid, companyId));
		}
		catch (PortalException portalException) {
			throw _translate(portalException);
		}
	}

	@Override
	public boolean hasLock(long userId, String className, long key) {
		return _lockLocalService.hasLock(userId, className, key);
	}

	@Override
	public boolean hasLock(long userId, String className, String key) {
		return _lockLocalService.hasLock(userId, className, key);
	}

	@Override
	public boolean isLocked(String className, long key) {
		return _lockLocalService.isLocked(className, key);
	}

	@Override
	public boolean isLocked(String className, String key) {
		return _lockLocalService.isLocked(className, key);
	}

	@Override
	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return lock(
			userId, className, key, owner, inheritable, expirationTime, true);
	}

	@Override
	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws PortalException {

		try {
			return new LockImpl(
				_lockLocalService.lock(
					userId, className, key, owner, inheritable, expirationTime,
					renew));
		}
		catch (PortalException portalException) {
			throw _translate(portalException);
		}
	}

	@Override
	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return lock(
			userId, className, key, owner, inheritable, expirationTime, true);
	}

	@Override
	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws PortalException {

		try {
			return new LockImpl(
				_lockLocalService.lock(
					userId, className, key, owner, inheritable, expirationTime,
					renew));
		}
		catch (PortalException portalException) {
			throw _translate(portalException);
		}
	}

	@Override
	public Lock lock(String className, String key, String owner) {
		return new LockImpl(_lockLocalService.lock(className, key, owner));
	}

	@Override
	public Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		return new LockImpl(
			_lockLocalService.lock(
				className, key, expectedOwner, updatedOwner));
	}

	@Override
	public Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		try {
			return new LockImpl(
				_lockLocalService.refresh(uuid, companyId, expirationTime));
		}
		catch (PortalException portalException) {
			throw _translate(portalException);
		}
	}

	@Override
	public void unlock(String className, long key) {
		_lockLocalService.unlock(className, key);
	}

	@Override
	public void unlock(String className, String key) {
		_lockLocalService.unlock(className, key);
	}

	@Override
	public void unlock(String className, String key, String owner) {
		_lockLocalService.unlock(className, key, owner);
	}

	private PortalException _translate(PortalException portalException) {
		if (portalException instanceof
				com.liferay.portal.lock.exception.DuplicateLockException) {

			com.liferay.portal.lock.exception.DuplicateLockException
				duplicateLockException =
					(com.liferay.portal.lock.exception.DuplicateLockException)
						portalException;

			return new DuplicateLockException(
				new LockImpl(duplicateLockException.getLock()));
		}

		Throwable throwable = portalException.getCause();
		String message = portalException.getMessage();

		if (portalException instanceof
				com.liferay.portal.lock.exception.ExpiredLockException) {

			if (throwable == null) {
				return new ExpiredLockException(message);
			}

			return new ExpiredLockException(message, throwable);
		}
		else if (portalException instanceof
					com.liferay.portal.lock.exception.InvalidLockException) {

			if (throwable == null) {
				return new InvalidLockException(message);
			}

			return new InvalidLockException(message, throwable);
		}
		else if (portalException instanceof
					com.liferay.portal.lock.exception.NoSuchLockException) {

			if (throwable == null) {
				return new NoSuchLockException(message);
			}

			return new NoSuchLockException(message, throwable);
		}

		return portalException;
	}

	@Reference
	private LockLocalService _lockLocalService;

}