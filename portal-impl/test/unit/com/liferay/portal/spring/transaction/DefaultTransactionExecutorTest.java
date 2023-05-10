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

package com.liferay.portal.spring.transaction;

import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionLifecycleManager;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class DefaultTransactionExecutorTest
	extends CounterTransactionExecutorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		TransactionLifecycleManager.register(
			_recordTransactionLifecycleListener);
	}

	@After
	public void tearDown() {
		TransactionLifecycleManager.unregister(
			_recordTransactionLifecycleListener);
	}

	@Override
	@Test
	public void testCommit() throws Throwable {
		super.testCommit();

		_recordTransactionLifecycleListener.verify(null);
	}

	@Override
	@Test
	public void testCommitWithAppException() throws Throwable {
		super.testCommitWithAppException();

		_recordTransactionLifecycleListener.verify(null);
	}

	@Override
	@Test
	public void testCommitWithAppExceptionWithCommitException()
		throws Throwable {

		super.testCommitWithAppExceptionWithCommitException();

		_recordTransactionLifecycleListener.verify(commitException);
	}

	@Override
	@Test
	public void testCommitWithCommitException() throws Throwable {
		super.testCommitWithCommitException();

		_recordTransactionLifecycleListener.verify(commitException);
	}

	@Test
	public void testFailingTransactionLifecycleListeners() {
		FailingTransactionLifecycleListener
			failingTransactionLifecycleListener =
				new FailingTransactionLifecycleListener();

		TransactionLifecycleManager.register(
			failingTransactionLifecycleListener);

		try {
			TransactionExecutor transactionExecutor = createTransactionExecutor(
				new RecordPlatformTransactionManager());

			try {
				transactionExecutor.execute(
					new TestTransactionAttributeAdapter(false), () -> null);

				Assert.fail();
			}
			catch (Throwable throwable) {
				Assert.assertEquals("createThrowable", throwable.getMessage());

				Throwable[] throwables = throwable.getSuppressed();

				Assert.assertEquals(
					Arrays.toString(throwables), 1, throwables.length);
				Assert.assertEquals(
					"commitThrowable", throwables[0].getMessage());
			}

			Exception exception1 = new Exception();

			try {
				transactionExecutor.execute(
					new TestTransactionAttributeAdapter(false),
					() -> {
						throw exception1;
					});

				Assert.fail();
			}
			catch (Throwable throwable) {
				Assert.assertSame(exception1, throwable);

				Throwable[] throwables = throwable.getSuppressed();

				Assert.assertEquals(
					Arrays.toString(throwables), 1, throwables.length);
				Assert.assertSame(
					"createThrowable", throwables[0].getMessage());

				throwables = throwables[0].getSuppressed();

				Assert.assertEquals(
					Arrays.toString(throwables), 1, throwables.length);
				Assert.assertSame(
					"commitThrowable", throwables[0].getMessage());
			}

			try {
				transactionExecutor.execute(
					new TestTransactionAttributeAdapter(true), () -> null);

				Assert.fail();
			}
			catch (Throwable throwable) {
				Assert.assertEquals("createThrowable", throwable.getMessage());

				Throwable[] throwables = throwable.getSuppressed();

				Assert.assertEquals(
					Arrays.toString(throwables), 1, throwables.length);
				Assert.assertEquals(
					"commitThrowable", throwables[0].getMessage());
			}

			Exception exception2 = new Exception();

			try {
				transactionExecutor.execute(
					new TestTransactionAttributeAdapter(true),
					() -> {
						throw exception2;
					});

				Assert.fail();
			}
			catch (Throwable throwable) {
				Assert.assertSame(exception2, throwable);

				Throwable[] throwables = throwable.getSuppressed();

				Assert.assertEquals(
					Arrays.toString(throwables), 1, throwables.length);
				Assert.assertSame(
					"createThrowable", throwables[0].getMessage());

				throwables = throwables[0].getSuppressed();

				Assert.assertEquals(
					Arrays.toString(throwables), 1, throwables.length);
				Assert.assertSame(
					"rollbackThrowable", throwables[0].getMessage());
			}
		}
		finally {
			TransactionLifecycleManager.unregister(
				failingTransactionLifecycleListener);
		}
	}

	@Override
	@Test
	public void testRollbackOnAppException() throws Throwable {
		super.testRollbackOnAppException();

		_recordTransactionLifecycleListener.verify(appException);
	}

	@Override
	@Test
	public void testRollbackOnAppExceptionWithRollbackException()
		throws Throwable {

		super.testRollbackOnAppExceptionWithRollbackException();

		_recordTransactionLifecycleListener.verify(appException);
	}

	@Override
	protected void assertTransactionExecutorThreadLocal(
		TransactionExecutor transactionExecutor, boolean inTransaction) {

		if (inTransaction) {
			Assert.assertSame(
				transactionExecutor,
				TransactionExecutorThreadLocal.getCurrentTransactionExecutor());
		}
		else {
			Assert.assertNull(
				TransactionExecutorThreadLocal.getCurrentTransactionExecutor());
		}
	}

	@Override
	protected TransactionExecutor createTransactionExecutor(
		PlatformTransactionManager platformTransactionManager) {

		return new DefaultTransactionExecutor(platformTransactionManager);
	}

	private final RecordTransactionLifecycleListener
		_recordTransactionLifecycleListener =
			new RecordTransactionLifecycleListener();

	private static class FailingTransactionLifecycleListener
		implements TransactionLifecycleListener {

		@Override
		public void committed(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			throw new RuntimeException("commitThrowable");
		}

		@Override
		public void created(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			throw new RuntimeException("createThrowable");
		}

		@Override
		public void rollbacked(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus, Throwable throwable) {

			throw new RuntimeException("rollbackThrowable");
		}

	}

	private static class RecordTransactionLifecycleListener
		implements TransactionLifecycleListener {

		@Override
		public void committed(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			_committed = true;
		}

		@Override
		public void created(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			_created = true;
		}

		@Override
		public void rollbacked(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus, Throwable throwable) {

			_throwable = throwable;
		}

		public void verify(Throwable throwable) {
			Assert.assertTrue(_created);

			if (throwable == null) {
				Assert.assertTrue(_committed);
			}
			else {
				Assert.assertFalse(_committed);
				Assert.assertSame(throwable, _throwable);
			}
		}

		private boolean _committed;
		private boolean _created;
		private Throwable _throwable;

	}

	private static class TestTransactionAttributeAdapter
		extends TransactionAttributeAdapter {

		@Override
		public boolean rollbackOn(Throwable throwable) {
			return _rollback;
		}

		private TestTransactionAttributeAdapter(boolean rollback) {
			super(null);

			_rollback = rollback;
		}

		private final boolean _rollback;

	}

}