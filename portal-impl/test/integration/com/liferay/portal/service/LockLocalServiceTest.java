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

package com.liferay.portal.service;

import com.liferay.portal.dao.db.SybaseDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.Lock;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.log.ExpectedLog;
import com.liferay.portal.test.log.ExpectedLogs;
import com.liferay.portal.test.log.ExpectedType;

import java.sql.BatchUpdateException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.util.JDBCExceptionReporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LockLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() {
		LockLocalServiceUtil.unlock("className", "key");
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedLog =
					"Deadlock found when trying to get lock; try restarting " +
						"transaction",
				expectedType = ExpectedType.EXACT
			)
		},
		level = "ERROR", loggerClass = JDBCExceptionReporter.class
	)
	@Test
	public void testMutualExcludeLockingParallel() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<LockingJob> lockingJobs = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			LockingJob lockingJob = new LockingJob(
				"className", "key", "owner-" + i, 10);

			lockingJobs.add(lockingJob);

			executorService.execute(lockingJob);
		}

		executorService.shutdown();

		Assert.assertTrue(
			executorService.awaitTermination(600, TimeUnit.SECONDS));

		for (LockingJob lockingJob : lockingJobs) {
			SystemException systemException = lockingJob.getSystemException();

			if (systemException != null) {
				Assert.fail(systemException.getMessage());
			}
		}

		Assert.assertFalse(LockLocalServiceUtil.isLocked("className", "key"));
	}

	@Test
	public void testMutualExcludeLockingSerial() throws Exception {
		String className = "testClassName";
		String key = "testKey";
		String owner1 = "testOwner1";

		Lock lock1 = LockLocalServiceUtil.lock(className, key, owner1);

		Assert.assertEquals(owner1, lock1.getOwner());
		Assert.assertTrue(lock1.isNew());

		String owner2 = "owner2";

		Lock lock2 = LockLocalServiceUtil.lock(className, key, owner2);

		Assert.assertEquals(owner1, lock2.getOwner());
		Assert.assertFalse(lock2.isNew());

		LockLocalServiceUtil.unlock(className, key, owner1);

		lock2 = LockLocalServiceUtil.lock(className, key, owner2);

		Assert.assertEquals(owner2, lock2.getOwner());
		Assert.assertTrue(lock2.isNew());

		LockLocalServiceUtil.unlock(className, key, owner2);
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

		@Override
		public void run() {
			int count = 0;

			while (true) {
				try {
					Lock lock = LockLocalServiceUtil.lock(
						_className, _key, _owner);

					if (lock.isNew()) {

						// The lock creator is responsible for unlocking. Try to
						// unlock many times because some databases like SQL
						// Server may randomly choke and rollback an unlock.

						while (true) {
							try {
								LockLocalServiceUtil.unlock(
									_className, _key, _owner);

								if (++count >= _requiredSuccessCount) {
									return;
								}

								break;
							}
							catch (SystemException se) {
								if (_isExpectedException(se)) {
									continue;
								}

								_systemException = se;

								return;
							}
						}
					}
				}
				catch (SystemException se) {
					if (_isExpectedException(se)) {
						continue;
					}

					_systemException = se;

					break;
				}
			}
		}

		private boolean _isExpectedException(SystemException se) {
			Throwable cause = se.getCause();

			if (!(cause instanceof ORMException)) {
				return false;
			}

			cause = cause.getCause();

			if ((cause instanceof ConstraintViolationException) ||
				(cause instanceof LockAcquisitionException)) {

				return true;
			}

			DB db = DBFactoryUtil.getDB();

			if ((db instanceof SybaseDB) &&
				(cause instanceof GenericJDBCException)) {

				cause = cause.getCause();

				String message = cause.getMessage();

				if ((cause instanceof BatchUpdateException) &&
					message.equals(
						"Attempt to insert duplicate key row in object " +
							"'Lock_' with unique index 'IX_228562AD'\n")) {

					return true;
				}
			}

			return false;
		}

		private final String _className;
		private final String _key;
		private final String _owner;
		private final int _requiredSuccessCount;
		private SystemException _systemException;

	}

}