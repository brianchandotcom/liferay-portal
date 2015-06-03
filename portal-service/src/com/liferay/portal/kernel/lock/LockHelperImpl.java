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
import com.liferay.portal.service.LockLocalServiceUtil;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = LockHelper.class)
public class LockHelperImpl implements LockHelper {

	@Override
	public void clear() {
		LockLocalServiceUtil.clear();
	}

	@Override
	public Lock createLock(
		long lockId, long companyId, long userId, String userName) {

		com.liferay.portal.model.Lock lock = LockLocalServiceUtil.createLock(
			lockId);

		lock.setCompanyId(companyId);
		lock.setUserId(userId);
		lock.setUserName(userName);
		lock.setCreateDate(new Date());

		return new LockImpl(lock);
	}

	@Override
	public Lock getLock(String className, long key) throws PortalException {
		try {
			return new LockImpl(LockLocalServiceUtil.getLock(className, key));
		}
		catch (PortalException pe) {
			throw transfer(pe);
		}
	}

	@Override
	public Lock getLock(String className, String key) throws PortalException {
		try {
			return new LockImpl(LockLocalServiceUtil.getLock(className, key));
		}
		catch (PortalException pe) {
			throw transfer(pe);
		}
	}

	@Override
	public Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		try {
			return new LockImpl(
				LockLocalServiceUtil.getLockByUuidAndCompanyId(
					uuid, companyId));
		}
		catch (PortalException pe) {
			throw transfer(pe);
		}
	}

	@Override
	public boolean hasLock(long userId, String className, long key) {
		return LockLocalServiceUtil.hasLock(userId, className, key);
	}

	@Override
	public boolean hasLock(long userId, String className, String key) {
		return LockLocalServiceUtil.hasLock(userId, className, key);
	}

	@Override
	public boolean isLocked(String className, long key) {
		return LockLocalServiceUtil.isLocked(className, key);
	}

	@Override
	public boolean isLocked(String className, String key) {
		return LockLocalServiceUtil.isLocked(className, key);
	}

	@Override
	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		try {
			return new LockImpl(
				LockLocalServiceUtil.lock(
					userId, className, key, owner, inheritable,
					expirationTime));
		}
		catch (PortalException pe) {
			throw transfer(pe);
		}
	}

	@Override
	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		try {
			return new LockImpl(
				LockLocalServiceUtil.lock(
					userId, className, key, owner, inheritable,
					expirationTime));
		}
		catch (PortalException pe) {
			throw transfer(pe);
		}
	}

	@Override
	public Lock lock(String className, String key, String owner) {
		return new LockImpl(LockLocalServiceUtil.lock(className, key, owner));
	}

	@Override
	public Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		return new LockImpl(
			LockLocalServiceUtil.lock(
				className, key, expectedOwner, updatedOwner));
	}

	@Override
	public Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		try {
			return new LockImpl(
				LockLocalServiceUtil.refresh(uuid, companyId, expirationTime));
		}
		catch (PortalException pe) {
			throw transfer(pe);
		}
	}

	@Override
	public void unlock(String className, long key) {
		LockLocalServiceUtil.unlock(className, key);
	}

	@Override
	public void unlock(String className, String key) {
		LockLocalServiceUtil.unlock(className, key);
	}

	@Override
	public void unlock(String className, String key, String owner) {
		LockLocalServiceUtil.unlock(className, key, owner);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		LockLocalServiceUtil.clear();
	}

	protected PortalException transfer(PortalException portalException) {
		if (portalException instanceof
				com.liferay.portal.DuplicateLockException) {

			com.liferay.portal.DuplicateLockException duplicateLockException =
				(com.liferay.portal.DuplicateLockException)portalException;

			return new DuplicateLockException(
				new LockImpl(duplicateLockException.getLock()));
		}

		Throwable cause = portalException.getCause();
		String message = portalException.getMessage();

		if (portalException instanceof
				com.liferay.portal.ExpiredLockException) {

			if (cause == null) {
				return new ExpiredLockException(message);
			}

			return new ExpiredLockException(message, cause);
		}
		else if (portalException instanceof
					com.liferay.portal.InvalidLockException) {

			if (cause == null) {
				return new InvalidLockException(message);
			}

			return new InvalidLockException(message, cause);
		}
		else if (portalException instanceof
					com.liferay.portal.NoSuchLockException) {

			if (cause == null) {
				return new NoSuchLockException(message);
			}

			return new NoSuchLockException(message, cause);
		}

		return portalException;
	}

}