/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	public void testMutualExcludeLockingSerial() throws Exception {
		String testClassName = "testClassName";
		String testKey = "testKey";

		String owner1 = "owner1";
		String owner2 = "owner2";

		// Owner1 create the lock
		Lock lock1 = LockLocalServiceUtil.lock(
			testClassName, testKey, owner1, false);

		assertEquals(owner1, lock1.getOwner());
		assertTrue(lock1.isNew());

		// Owner2 fetch back owner1's lock
		Lock lock2 = LockLocalServiceUtil.lock(
			testClassName, testKey, owner2, false);

		assertEquals(owner1, lock2.getOwner());
		assertFalse(lock2.isNew());

		// Owner1 release the lock
		LockLocalServiceUtil.unlock(testClassName, testKey, owner1, false);

		// Owner2 create the lock again

		lock2 = LockLocalServiceUtil.lock(
			testClassName, testKey, owner2, false);

		assertEquals(owner2, lock2.getOwner());
		assertTrue(lock2.isNew());

		// Owner2 release the lock
		LockLocalServiceUtil.unlock(testClassName, testKey, owner2, false);
	}

	public void testMutualExcludeLockingParallel() throws Exception {
		String testClassName = "testClassName";
		String testKey = "testKey";
		String testOwner = "owner";

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<LockingJob> lockingJobList = new ArrayList<LockingJob>();

		for (int i = 0; i < 10; i++) {
			LockingJob lockingJob = new LockingJob(
				testClassName, testKey, testOwner, 10);

			lockingJobList.add(lockingJob);

			executorService.execute(lockingJob);
		}

		executorService.shutdown();
		boolean success = executorService.awaitTermination(
			600, TimeUnit.SECONDS);

		assertTrue(success);

		for (LockingJob lockingJob : lockingJobList) {
			SystemException systemException = lockingJob.getSystemException();

			if (systemException != null) {
				fail(systemException.getMessage());
			}
		}
	}

	private class LockingJob implements Runnable {

		public LockingJob(
			String testClassName, String testKey, String testOwner,
			int requiredSuccessCount) {
			_testClassName = testClassName;
			_testKey = testKey;
			_testOwner = testOwner;
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
						_testClassName, _testKey, _testOwner, false);

					if (lock.isNew()) {
						LockLocalServiceUtil.unlock(_testClassName, _testKey);

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

						// PostgreSQL fails to do row/table level locking.
						// To enforce the mutual exclude locking an unique index
						// is required. So it could end up with failing
						// inserting by violating unique index constraint
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

		private int _requiredSuccessCount;
		private SystemException _systemException;
		private String _testClassName;
		private String _testKey;
		private String _testOwner;

	}

}