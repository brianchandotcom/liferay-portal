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

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.Transactional;

import java.lang.reflect.Method;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BaseTransactionalTestRuleTest {

	@BeforeClass
	public static void setUpClass1() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@BeforeClass
	@Transactional
	public static void setUpClass2() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@BeforeClass
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public static void setUpClass3() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@AfterClass
	public static void tearDownClass1() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@AfterClass
	@Transactional
	public static void tearDownClass2() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@AfterClass
	@Transactional(
		isolation = Isolation.REPEATABLE_READ, readOnly = true,
		rollbackFor = Exception.class
	)
	public static void tearDownClass3() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@Before
	public void setUp1() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@Before
	@Transactional
	public void setUp2() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@Before
	@Transactional(enabled = false)
	public void setUp3() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@After
	public void tearDown1() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@After
	@Transactional
	public void tearDown2() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@After
	@Transactional(noRollbackFor = Exception.class, timeout = 10)
	public void tearDown3() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@Test
	public void test1() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@Test
	@Transactional
	public void test2() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	@Test
	@Transactional(
		noRollbackForClassName = "RuntimeException",
		propagation = Propagation.SUPPORTS
	)
	public void test3() throws ReflectiveOperationException {
		assertTransactionConfig();
	}

	protected static void assertTransactionConfig()
		throws ReflectiveOperationException {

		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		StackTraceElement stackTraceElement = stackTraceElements[2];

		Method method = BaseTransactionalTestRuleTest.class.getMethod(
			stackTraceElement.getMethodName());

		Deque<TransactionConfig> transactionConfigs =
			transactionConfigThreadLocal.get();

		TransactionConfig transactionConfig = transactionConfigs.peek();

		Transactional transactional = method.getAnnotation(Transactional.class);

		if (transactional == null) {
			Assert.assertEquals(
				TransactionConfig.Factory.create(
					Propagation.SUPPORTS,
					new Class<?>[] {
						PortalException.class, SystemException.class
					}),
				transactionConfig);

			return;
		}

		Assert.assertEquals(
			TransactionConfig.Factory.create(
				transactional.isolation(), transactional.propagation(),
				transactional.readOnly(), transactional.timeout(),
				transactional.rollbackFor(),
				transactional.rollbackForClassName(),
				transactional.noRollbackFor(),
				transactional.noRollbackForClassName()),
			transactionConfig);
	}

	protected static final ThreadLocal<Deque<TransactionConfig>>
		transactionConfigThreadLocal =
			new ThreadLocal<Deque<TransactionConfig>>() {

				@Override
				protected Deque<TransactionConfig> initialValue() {
					return new ArrayDeque<>();
				}

			};

}