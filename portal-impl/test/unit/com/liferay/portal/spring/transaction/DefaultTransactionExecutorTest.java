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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Shuyang Zhou
 */
public class DefaultTransactionExecutorTest
	extends CounterTransactionExecutorTest {

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
	public void testCommit() throws Throwable {
		super.testCommit();

		_recordTransactionLifecycleListener.verify(null);
	}

	@Override
	public void testCommitWithAppException() throws Throwable {
		super.testCommitWithAppException();

		_recordTransactionLifecycleListener.verify(null);
	}

	@Override
	public void testCommitWithAppExceptionWithCommitException()
		throws Throwable {

		super.testCommitWithAppExceptionWithCommitException();

		_recordTransactionLifecycleListener.verify(commitException);
	}

	@Override
	public void testCommitWithCommitException() throws Throwable {
		super.testCommitWithCommitException();

		_recordTransactionLifecycleListener.verify(commitException);
	}

	@Override
	public void testRollbackOnAppException() throws Throwable {
		super.testRollbackOnAppException();

		_recordTransactionLifecycleListener.verify(appException);
	}

	@Override
	public void testRollbackOnAppExceptionWithRollbackException()
		throws Throwable {

		super.testRollbackOnAppExceptionWithRollbackException();

		_recordTransactionLifecycleListener.verify(appException);
	}

	@Override
	protected TransactionExecutor createTransactionExecutor() {
		return new DefaultTransactionExecutor();
	}

	private final RecordTransactionLifecycleListener
		_recordTransactionLifecycleListener =
			new RecordTransactionLifecycleListener();

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
		}

		@Override
		public void rollbacked(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus, Throwable throwable) {

			_throwable = throwable;
		}

		public void verify(Throwable throwable) {
			if (throwable == null) {
				Assert.assertTrue(_committed);
			}
			else {
				Assert.assertFalse(_committed);
				Assert.assertSame(throwable, _throwable);
			}
		}

		private boolean _committed;
		private Throwable _throwable;

	}

}