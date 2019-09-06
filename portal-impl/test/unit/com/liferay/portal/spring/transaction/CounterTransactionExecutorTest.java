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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 */
public class CounterTransactionExecutorTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCommit() throws Throwable {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager();

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> false);

		transactionExecutor.execute(transactionAttributeAdapter, () -> null);

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, _transactionStatus, null);
	}

	@Test
	public void testCommitWithAppException() throws Throwable {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager();

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> false);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable t) {
			Assert.assertSame(appException, t);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, _transactionStatus, null);
	}

	@Test
	public void testCommitWithAppExceptionWithCommitException()
		throws Throwable {

		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager() {

				@Override
				public void commit(TransactionStatus transactionStatus) {
					ReflectionUtil.throwException(commitException);
				}

			};

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> false);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable t) {
			Assert.assertSame(commitException, t);

			Throwable[] throwables = commitException.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 1, throwables.length);
			Assert.assertEquals(appException, throwables[0]);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, null);
	}

	@Test
	public void testCommitWithCommitException() throws Throwable {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager() {

				@Override
				public void commit(TransactionStatus transactionStatus) {
					ReflectionUtil.throwException(commitException);
				}

			};

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> false);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter, () -> null);

			Assert.fail();
		}
		catch (Throwable t) {
			Assert.assertSame(commitException, t);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, null);
	}

	@Test
	public void testGetPlatformTransactionManager() {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager();

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		Assert.assertSame(
			recordPlatformTransactionManager,
			transactionExecutor.getPlatformTransactionManager());
	}

	@Test
	public void testRollbackOnAppException() throws Throwable {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager();

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> t == appException);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable t) {
			Assert.assertSame(appException, t);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, _transactionStatus);
	}

	@Test
	public void testRollbackOnAppExceptionWithRollbackException()
		throws Throwable {

		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager() {

				@Override
				public void rollback(TransactionStatus transactionStatus) {
					ReflectionUtil.throwException(rollbackException);
				}

			};

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> t == appException);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable t) {
			Assert.assertSame(rollbackException, t);

			Throwable[] throwables = rollbackException.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 1, throwables.length);
			Assert.assertEquals(appException, throwables[0]);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, null);
	}

	@Test
	public void testTransactionHandlerMethods() throws Throwable {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager();

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> t == appException);

		TransactionHandler transactionHandler =
			(TransactionHandler)transactionExecutor;

		assertTransactionExecutorThreadLocal(transactionHandler, false);

		TransactionStatusAdapter transactionStatusAdapter =
			transactionHandler.start(transactionAttributeAdapter);

		assertTransactionExecutorThreadLocal(transactionHandler, true);

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, null);

		try {
			transactionHandler.rollback(
				appException, transactionAttributeAdapter,
				transactionStatusAdapter);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(appException, e);
		}

		assertTransactionExecutorThreadLocal(transactionHandler, false);

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, _transactionStatus);

		recordPlatformTransactionManager._rollbackTransactionStatus = null;

		transactionStatusAdapter = transactionHandler.start(
			transactionAttributeAdapter);

		assertTransactionExecutorThreadLocal(transactionHandler, true);

		transactionHandler.commit(
			transactionAttributeAdapter, transactionStatusAdapter);

		assertTransactionExecutorThreadLocal(transactionHandler, false);

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, _transactionStatus, null);
	}

	protected void assertTransactionExecutorThreadLocal(
		TransactionHandler transactionHandler, boolean inTransaction) {

		Assert.assertNull(
			TransactionExecutorThreadLocal.getCurrentTransactionExecutor());
	}

	protected TransactionExecutor createTransactionExecutor(
		PlatformTransactionManager platformTransactionManager) {

		return new CounterTransactionExecutor(platformTransactionManager);
	}

	protected final Exception appException = new Exception();
	protected final Exception commitException = new Exception();
	protected final Exception rollbackException = new Exception();

	protected class RecordPlatformTransactionManager
		implements PlatformTransactionManager {

		public RecordPlatformTransactionManager() {
			_transactionStatus =
				CounterTransactionExecutorTest._transactionStatus;
		}

		@Override
		public void commit(TransactionStatus transactionStatus) {
			_commitTransactionStatus = transactionStatus;
		}

		@Override
		public TransactionStatus getTransaction(
			TransactionDefinition transactionDefinition) {

			_transactionDefinition = transactionDefinition;

			return _transactionStatus;
		}

		@Override
		public void rollback(TransactionStatus transactionStatus) {
			_rollbackTransactionStatus = transactionStatus;
		}

		public void verify(
			TransactionDefinition transactionDefinition,
			TransactionStatus commitTransactionStatus,
			TransactionStatus rollbackTransactionStatus) {

			Assert.assertSame(transactionDefinition, _transactionDefinition);
			Assert.assertSame(
				commitTransactionStatus, _commitTransactionStatus);
			Assert.assertSame(
				rollbackTransactionStatus, _rollbackTransactionStatus);
		}

		private TransactionStatus _commitTransactionStatus;
		private TransactionStatus _rollbackTransactionStatus;
		private TransactionDefinition _transactionDefinition;
		private final TransactionStatus _transactionStatus;

	}

	private static TransactionAttributeAdapter _newTransactionAttributeAdapter(
		Predicate<Throwable> predicate) {

		return new TransactionAttributeAdapter(
			(TransactionAttribute)ProxyUtil.newProxyInstance(
				TransactionAttribute.class.getClassLoader(),
				new Class<?>[] {TransactionAttribute.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						if (Objects.equals(method.getName(), "rollbackOn")) {
							return predicate.test((Throwable)args[0]);
						}

						throw new UnsupportedOperationException(
							method.toString());
					}

				}));
	}

	private static final TransactionStatus _transactionStatus =
		(TransactionStatus)ProxyUtil.newProxyInstance(
			TransactionStatus.class.getClassLoader(),
			new Class<?>[] {TransactionStatus.class},
			new InvocationHandler() {

				@Override
				public Object invoke(
					Object proxy, Method method, Object[] args) {

					throw new UnsupportedOperationException(method.toString());
				}

			});

}