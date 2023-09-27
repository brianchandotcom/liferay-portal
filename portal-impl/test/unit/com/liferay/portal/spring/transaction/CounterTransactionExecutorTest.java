/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.transaction;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 */
public class CounterTransactionExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

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
			transactionAttributeAdapter,
			RecordPlatformTransactionManager.TRANSACTION_STATUS, null);
	}

	@Test
	public void testCommitWithAppException() throws Throwable {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager();

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(throwable -> false);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(appException, throwable);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter,
			RecordPlatformTransactionManager.TRANSACTION_STATUS, null);
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
			_newTransactionAttributeAdapter(throwable -> false);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(commitException, throwable);

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
			_newTransactionAttributeAdapter(throwable -> false);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter, () -> null);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(commitException, throwable);
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
			_newTransactionAttributeAdapter(
				throwable -> throwable == appException);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(appException, throwable);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null,
			RecordPlatformTransactionManager.TRANSACTION_STATUS);
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
			_newTransactionAttributeAdapter(
				throwable -> throwable == appException);

		try {
			transactionExecutor.execute(
				transactionAttributeAdapter,
				() -> {
					throw appException;
				});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(rollbackException, throwable);

			Throwable[] throwables = rollbackException.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 1, throwables.length);
			Assert.assertEquals(appException, throwables[0]);
		}

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, null);
	}

	@Test
	public void testTransactionExecutorMethods() throws Throwable {
		RecordPlatformTransactionManager recordPlatformTransactionManager =
			new RecordPlatformTransactionManager();

		TransactionExecutor transactionExecutor = createTransactionExecutor(
			recordPlatformTransactionManager);

		TransactionAttributeAdapter transactionAttributeAdapter =
			_newTransactionAttributeAdapter(t -> t == appException);

		assertTransactionExecutorThreadLocal(transactionExecutor, false);

		TransactionStatusAdapter transactionStatusAdapter =
			transactionExecutor.start(transactionAttributeAdapter);

		assertTransactionExecutorThreadLocal(transactionExecutor, true);

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null, null);

		try {
			transactionExecutor.rollback(
				appException, transactionAttributeAdapter,
				transactionStatusAdapter);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(appException, exception);
		}

		assertTransactionExecutorThreadLocal(transactionExecutor, false);

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter, null,
			RecordPlatformTransactionManager.TRANSACTION_STATUS);

		recordPlatformTransactionManager.setRollbackTransactionStatus(null);

		transactionStatusAdapter = transactionExecutor.start(
			transactionAttributeAdapter);

		assertTransactionExecutorThreadLocal(transactionExecutor, true);

		transactionExecutor.commit(
			transactionAttributeAdapter, transactionStatusAdapter);

		assertTransactionExecutorThreadLocal(transactionExecutor, false);

		recordPlatformTransactionManager.verify(
			transactionAttributeAdapter,
			RecordPlatformTransactionManager.TRANSACTION_STATUS, null);
	}

	protected void assertTransactionExecutorThreadLocal(
		TransactionExecutor transactionExecutor, boolean inTransaction) {

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

	private TransactionAttributeAdapter _newTransactionAttributeAdapter(
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

}