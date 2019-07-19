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

package com.liferay.portal.lock.service.impl;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.aop.MasterDataSource;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.LockListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.lock.exception.DuplicateLockException;
import com.liferay.portal.lock.exception.ExpiredLockException;
import com.liferay.portal.lock.exception.NoSuchLockException;
import com.liferay.portal.lock.model.Lock;
import com.liferay.portal.lock.service.base.LockLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockAcquisitionException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.portal.lock.model.Lock",
	service = AopService.class
)
public class LockLocalServiceImpl extends LockLocalServiceBaseImpl {

	@Override
	public void clear() {
		lockPersistence.removeByLtExpirationDate(new Date());
	}

	@Override
	public Lock fetchLock(String className, long key) {
		return fetchLock(className, String.valueOf(key));
	}

	@Override
	public Lock fetchLock(String className, String key) {
		if (lockPersistence.countByClassName(className) == 0) {
			return null;
		}

		Lock lock = lockPersistence.fetchByC_K(className, key);

		if ((lock != null) && lock.isExpired()) {
			expireLock(lock);

			lock = null;
		}

		return lock;
	}

	@Override
	public Lock getLock(String className, long key) throws PortalException {
		return getLock(className, String.valueOf(key));
	}

	@Override
	public Lock getLock(String className, String key) throws PortalException {
		if (lockPersistence.countByClassName(className) == 0) {
			throw new NoSuchLockException(
				StringBundler.concat(
					"No Lock exists with the key {className=", className,
					", key=", key, "}"));
		}

		Lock lock = lockPersistence.findByC_K(className, key);

		if (lock.isExpired()) {
			expireLock(lock);

			throw new ExpiredLockException();
		}

		return lock;
	}

	@Override
	public Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		List<Lock> locks = lockPersistence.findByUuid_C(uuid, companyId);

		if (locks.isEmpty()) {
			StringBundler sb = new StringBundler(5);

			sb.append("{uuid=");
			sb.append(uuid);
			sb.append(", companyId=");
			sb.append(companyId);
			sb.append("}");

			throw new NoSuchLockException(sb.toString());
		}

		return locks.get(0);
	}

	@Override
	public boolean hasLock(long userId, String className, long key) {
		return hasLock(userId, className, String.valueOf(key));
	}

	@Override
	public boolean hasLock(long userId, String className, String key) {
		Lock lock = fetchLock(className, key);

		if ((lock != null) && (lock.getUserId() == userId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLocked(String className, long key) {
		return isLocked(className, String.valueOf(key));
	}

	@Override
	public boolean isLocked(String className, String key) {
		Lock lock = fetchLock(className, key);

		if (lock == null) {
			return false;
		}

		return true;
	}

	@Override
	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException {

		return lock(
			userId, className, String.valueOf(key), owner, inheritable,
			expirationTime);
	}

	@Override
	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws PortalException {

		return lock(
			userId, className, String.valueOf(key), owner, inheritable,
			expirationTime, renew);
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

		boolean isNew = false;

		if (lock == null) {
			User user = userLocalService.getUser(userId);

			long lockId = counterLocalService.increment();

			lock = lockPersistence.create(lockId);

			lock.setCompanyId(user.getCompanyId());
			lock.setUserId(user.getUserId());
			lock.setUserName(user.getFullName());
			lock.setClassName(className);
			lock.setKey(key);
			lock.setOwner(owner);
			lock.setInheritable(inheritable);

			isNew = true;
		}
		else if (!renew) {
			return lock;
		}

		Date now = new Date();

		lock.setCreateDate(now);

		if (expirationTime == 0) {
			lock.setExpirationDate(null);
		}
		else {
			lock.setExpirationDate(new Date(now.getTime() + expirationTime));
		}

		lock = lockPersistence.update(lock);

		if (isNew) {
			lock.setNew(true);
		}

		return lock;
	}

	@MasterDataSource
	@Override
	public Lock lock(String className, String key, String owner) {
		return lock(className, key, null, owner);
	}

	@MasterDataSource
	@Override
	public Lock lock(
		final String className, final String key, final String expectedOwner,
		final String updatedOwner) {

		while (true) {
			try {
				return TransactionInvokerUtil.invoke(
					_transactionConfig,
					new Callable<Lock>() {

						@Override
						public Lock call() {
							Lock lock = lockPersistence.fetchByC_K(
								className, key, false);

							if (lock == null) {
								long lockId = counterLocalService.increment();

								lock = lockPersistence.create(lockId);

								lock.setCreateDate(new Date());
								lock.setClassName(className);
								lock.setKey(key);
								lock.setOwner(updatedOwner);

								lockPersistence.update(lock);

								lock.setNew(true);
							}
							else if (Objects.equals(
										lock.getOwner(), expectedOwner)) {

								lock.setCreateDate(new Date());
								lock.setClassName(className);
								lock.setKey(key);
								lock.setOwner(updatedOwner);

								lockPersistence.update(lock);

								lock.setNew(true);
							}

							return lock;
						}

					});
			}
			catch (Throwable t) {
				Throwable cause = t;

				if (t instanceof ORMException) {
					cause = t.getCause();
				}

				if (cause instanceof ConstraintViolationException ||
					cause instanceof LockAcquisitionException) {

					continue;
				}

				ReflectionUtil.throwException(t);
			}
		}
	}

	@Override
	public Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException {

		Date now = new Date();

		List<Lock> locks = lockPersistence.findByUuid_C(uuid, companyId);

		if (locks.isEmpty()) {
			StringBundler sb = new StringBundler(5);

			sb.append("{uuid=");
			sb.append(uuid);
			sb.append(", companyId=");
			sb.append(companyId);
			sb.append("}");

			throw new NoSuchLockException(sb.toString());
		}

		Lock lock = locks.get(0);

		LockListener lockListener = _getLockListener(lock.getClassName());

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
	public void unlock(String className, long key) {
		unlock(className, String.valueOf(key));
	}

	@Override
	public void unlock(String className, String key) {
		Lock lock = lockPersistence.fetchByC_K(className, key);

		if (lock != null) {
			lockPersistence.remove(lock);
		}
	}

	@MasterDataSource
	@Override
	public void unlock(
		final String className, final String key, final String owner) {

		while (true) {
			try {
				TransactionInvokerUtil.invoke(
					_transactionConfig,
					new Callable<Void>() {

						@Override
						public Void call() {
							Lock lock = lockPersistence.fetchByC_K(
								className, key, false);

							if (lock == null) {
								return null;
							}

							if (Objects.equals(lock.getOwner(), owner)) {
								lockPersistence.remove(lock);
							}

							return null;
						}

					});

				return;
			}
			catch (Throwable t) {
				Throwable cause = t;

				if (t instanceof ORMException) {
					cause = t.getCause();
				}

				if (cause instanceof ConstraintViolationException ||
					cause instanceof LockAcquisitionException) {

					continue;
				}

				ReflectionUtil.throwException(t);
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}
	}

	protected void expireLock(Lock lock) {
		LockListener lockListener = _getLockListener(lock.getClassName());

		String key = lock.getKey();

		if (lockListener != null) {
			lockListener.onBeforeExpire(key);
		}

		try {
			lockPersistence.remove(lock);

			lockPersistence.flush();
		}
		finally {
			if (lockListener != null) {
				lockListener.onAfterExpire(key);
			}
		}
	}

	private LockListener _getLockListener(String className) {
		if (_serviceTrackerMap == null) {
			Bundle bundle = FrameworkUtil.getBundle(LockLocalServiceImpl.class);

			BundleContext bundleContext = bundle.getBundleContext();

			_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, LockListener.class, null,
				(serviceReference, emitter) -> {
					LockListener lockListener = bundleContext.getService(
						serviceReference);

					emitter.emit(lockListener.getClassName());
				});
		}

		return _serviceTrackerMap.getService(className);
	}

	private ServiceTrackerMap<String, LockListener> _serviceTrackerMap;
	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

}