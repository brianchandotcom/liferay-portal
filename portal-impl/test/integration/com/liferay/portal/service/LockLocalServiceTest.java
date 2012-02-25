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

package com.liferay.portal.service;

import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockAcquisitionException;

/**
 * @author Shuyang Zhou
 */
public class LockLocalServiceTest extends BaseServiceTestCase {

	public void testMutualExcludeLockingParallel() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<LockingJob> lockingJobs = new ArrayList<LockingJob>();

		for (int i = 0; i < 10; i++) {
			LockingJob lockingJob = new LockingJob(
				"className", "key", "owner", 10);

			lockingJobs.add(lockingJob);

			executorService.execute(lockingJob);
		}

		executorService.shutdown();

		assertTrue(executorService.awaitTermination(600, TimeUnit.SECONDS));

		for (LockingJob lockingJob : lockingJobs) {
			SystemException systemException = lockingJob.getSystemException();

			if (systemException != null) {
				fail(systemException.getMessage());
			}
		}
	}

	public void testMutualExcludeLockingSerial() throws Exception {
		String className = "testClassName";
		String key = "testKey";
		String owner1 = "testOwner1";

		Lock lock1 = LockLocalServiceUtil.lock(className, key, owner1, false);

		assertEquals(owner1, lock1.getOwner());
		assertTrue(lock1.isNew());

		String owner2 = "owner2";

		Lock lock2 = LockLocalServiceUtil.lock(className, key, owner2, false);

		assertEquals(owner1, lock2.getOwner());
		assertFalse(lock2.isNew());

		LockLocalServiceUtil.unlock(className, key, owner1, false);

		lock2 = LockLocalServiceUtil.lock(className, key, owner2, false);

		assertEquals(owner2, lock2.getOwner());
		assertTrue(lock2.isNew());

		LockLocalServiceUtil.unlock(className, key, owner2, false);
	}

	private class LockingJob implements Runnable {

		public LockingJob(
			String className, String key, String owner,
			int requiredSuccessCount) {

			_className = className;
			_key = key;
			_owner = owner;
			_requiredSuccessCount = requiredSuccessCount;
		}

		public SystemException getSystemException() {
			return _systemException;
		}

		public void run() {
			int count = 0;

			while (true) {
				try {
					Lock lock = LockLocalServiceUtil.lock(
						_className, _key, _owner, false);

					if (lock.isNew()) {
						LockLocalServiceUtil.unlock(_className, _key);

						if (++count >= _requiredSuccessCount) {
							break;
						}
					}
				}
				catch (SystemException se) {
					Throwable cause = se.getCause();

					if (cause instanceof ORMException) {
						cause = cause.getCause();

						if (cause instanceof LockAcquisitionException) {
							continue;
						}

						// PostgreSQL fails to do row or table level locking.
						// A unique index is required to enforce mutual exclude
						// locking, but it may do so by violating a unique index
						// constraint.

						DB db = DBFactoryUtil.getDB();

						if ((db instanceof PostgreSQLDB) &&
							(cause instanceof ConstraintViolationException)) {

							continue;
						}
					}

					_systemException = se;

					break;
				}
			}
		}

		private String _className;
		private String _key;
		private String _owner;
		private int _requiredSuccessCount;
		private SystemException _systemException;

	}

}