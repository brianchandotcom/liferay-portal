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

import com.liferay.portal.kernel.internal.spring.transaction.ReadOnlyTransactionThreadLocal;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.transaction.TransactionLifecycleManager;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Preston Crary
 */
public class ReadOnlyTransactionThreadLocalTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(ReadOnlyTransactionThreadLocal.class);

				assertClasses.add(
					ReadOnlyTransactionThreadLocal.
						TRANSACTION_LIFECYCLE_LISTENER.getClass());
			}

		};

	@Before
	public void setUp() {
		TransactionLifecycleManager.register(
			ReadOnlyTransactionThreadLocal.TRANSACTION_LIFECYCLE_LISTENER);

		TransactionStatus transactionStatus =
			(TransactionStatus)ProxyUtil.newProxyInstance(
				TransactionStatus.class.getClassLoader(),
				new Class<?>[] {TransactionStatus.class},
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "isNewTransaction")) {
						return true;
					}

					throw new UnsupportedOperationException(method.toString());
				});

		_transactionExecutor = new DefaultTransactionExecutor(
			new RecordPlatformTransactionManager() {

				@Override
				public TransactionStatus getTransaction(
					TransactionDefinition transactionDefinition) {

					super.getTransaction(transactionDefinition);

					return transactionStatus;
				}

			});
	}

	@After
	public void tearDown() {
		TransactionLifecycleManager.unregister(
			ReadOnlyTransactionThreadLocal.TRANSACTION_LIFECYCLE_LISTENER);
	}

	@Test
	public void testConstructor() {
		new ReadOnlyTransactionThreadLocal();
	}

	@Test
	public void testNestedReadOnly() throws Throwable {
		boolean readOnly = _transactionExecutor.execute(
			new TestTransactionAttributeAdapter(false),
			() -> _transactionExecutor.execute(
				new TestTransactionAttributeAdapter(true),
				ReadOnlyTransactionThreadLocal::isReadOnly));

		Assert.assertTrue(readOnly);
	}

	@Test
	public void testNestedReadWrite() throws Throwable {
		boolean readOnly = _transactionExecutor.execute(
			new TestTransactionAttributeAdapter(true),
			() -> _transactionExecutor.execute(
				new TestTransactionAttributeAdapter(false),
				ReadOnlyTransactionThreadLocal::isReadOnly));

		Assert.assertFalse(readOnly);
	}

	@Test
	public void testReadOnly() throws Throwable {
		boolean readOnly = _transactionExecutor.execute(
			new TestTransactionAttributeAdapter(true),
			ReadOnlyTransactionThreadLocal::isReadOnly);

		Assert.assertTrue(readOnly);
	}

	@Test
	public void testReadOnlyWithRollback() {
		Exception exception = new Exception();

		try {
			_transactionExecutor.execute(
				new TestTransactionAttributeAdapter(true),
				() -> {
					Assert.assertTrue(
						ReadOnlyTransactionThreadLocal.isReadOnly());

					throw exception;
				});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(exception, throwable);
		}

		Assert.assertFalse(ReadOnlyTransactionThreadLocal.isReadOnly());
	}

	@Test
	public void testReadWrite() throws Throwable {
		boolean readOnly = _transactionExecutor.execute(
			new TestTransactionAttributeAdapter(false),
			ReadOnlyTransactionThreadLocal::isReadOnly);

		Assert.assertFalse(readOnly);
	}

	@Test
	public void testStrictReadOnlyBadCombination() throws Throwable {
		try {
			_transactionExecutor.execute(
				new TestTransactionAttributeAdapter(false, true),
				ReadOnlyTransactionThreadLocal::isReadOnly);

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Strict-readonly transaction is not readonly",
				illegalStateException.getMessage());
		}
	}

	@Test
	public void testStrictReadOnlyNestedNotReadOnly() throws Throwable {
		try {
			_transactionExecutor.execute(
				new TestTransactionAttributeAdapter(true, true),
				() -> _transactionExecutor.execute(
					new TestTransactionAttributeAdapter(false),
					ReadOnlyTransactionThreadLocal::isReadOnly));

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Denied non-readonly nested transaction under " +
					"strict-readonly transaction",
				illegalStateException.getMessage());
		}
	}

	@Test
	public void testStrictReadOnlyNestedReadOnly() throws Throwable {
		boolean readOnly = _transactionExecutor.execute(
			new TestTransactionAttributeAdapter(true, true),
			() -> _transactionExecutor.execute(
				new TestTransactionAttributeAdapter(true),
				ReadOnlyTransactionThreadLocal::isReadOnly));

		Assert.assertTrue(readOnly);
	}

	@Test
	public void testWithoutTransaction() {
		Assert.assertFalse(ReadOnlyTransactionThreadLocal.isReadOnly());
	}

	private TransactionExecutor _transactionExecutor;

	private static class TestTransactionAttributeAdapter
		extends TransactionAttributeAdapter {

		@Override
		public boolean isReadOnly() {
			return _readOnly;
		}

		@Override
		public boolean rollbackOn(Throwable throwable) {
			return true;
		}

		private TestTransactionAttributeAdapter(boolean readOnly) {
			super(null);

			_readOnly = readOnly;
		}

		private TestTransactionAttributeAdapter(
			boolean readOnly, boolean strictReadOnly) {

			super(null, strictReadOnly);

			_readOnly = readOnly;
		}

		private final boolean _readOnly;

	}

}