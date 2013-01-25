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

package com.liferay.portal.test;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Miguel Pastor
 */
public class TransactionalExecutionTestListener
	extends AbstractExecutionTestListener {

	public TransactionalExecutionTestListener() {
		_platformTransactionManager =
			(PlatformTransactionManager)PortalBeanLocatorUtil.locate(
				"liferayTransactionManager");

		_transactionAttributeSource =
			(TransactionAttributeSource)PortalBeanLocatorUtil.locate(
				"transactionAttributeSource");
	}

	@Override
	public void runAfterTest(TestContext testContext) {
		Method testMethod = testContext.getMethod();

		TransactionContext transactionContext = _transactionContextCache.remove(
			testMethod);

		if ((transactionContext != null) &&
			!transactionContext._transactionStatus.isCompleted()) {

			rollbackTransaction(transactionContext);
		}
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		Method testMethod = testContext.getMethod();

		if (_transactionContextCache.get(testMethod) != null) {
			throw new IllegalArgumentException(
				"Current transaction open. " +
					"Close the opening transaction before opening a new one");
		}

		TransactionAttribute transactionAttribute =
			_transactionAttributeSource.getTransactionAttribute(
				testMethod, testContext.getClazz());

		if (transactionAttribute != null) {
			TransactionContext transactionContext = new TransactionContext(
				_platformTransactionManager, transactionAttribute);

			startNewTransaction(transactionContext);

			_transactionContextCache.put(testMethod, transactionContext);
		}
	}

	protected void rollbackTransaction(TransactionContext transactionContext) {
		transactionContext.rollBackTransaction();
	}

	protected void startNewTransaction(TransactionContext transactionContext) {
		transactionContext.startTransaction();
	}

	protected static class TransactionContext {

		public TransactionContext(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttribute transactionAttribute) {

			_platformTransactionManager = platformTransactionManager;

			_transactionAttribute = transactionAttribute;
		}

		public void startTransaction() {
			_transactionStatus = _platformTransactionManager.getTransaction(
				_transactionAttribute);
		}

		public void rollBackTransaction() {
			_platformTransactionManager.rollback(_transactionStatus);
		}

		private PlatformTransactionManager _platformTransactionManager;
		private TransactionAttribute _transactionAttribute;
		private TransactionStatus _transactionStatus;
	}

	private PlatformTransactionManager _platformTransactionManager;
	private TransactionAttributeSource _transactionAttributeSource;
	private Map<Method, TransactionContext> _transactionContextCache =
		new ConcurrentHashMap<Method, TransactionContext>();

}