/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.persistence.ClassNameUtil;
import com.liferay.portal.test.CaptureAppender;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Log4JLoggerTestUtil;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Shuyang Zhou
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class TransactionInterceptorTest {

	@Test
	public void testFailOnCommit() throws SystemException {
		CaptureAppender captureAppender =
			Log4JLoggerTestUtil.configureLog4JLogger(
				DefaultTransactionExecutor.class.getName(), Level.ERROR);

		long classNameId = CounterLocalServiceUtil.increment();

		ClassName className = ClassNameUtil.create(classNameId);

		PlatformTransactionManager platformTransactionManager =
			(PlatformTransactionManager)
				InfrastructureUtil.getTransactionManager();

		MockPlatformTransactionManager platformTransactionManagerWrapper =
			new MockPlatformTransactionManager(platformTransactionManager);

		TransactionInterceptor transactionInterceptor =
			(TransactionInterceptor)PortalBeanLocatorUtil.locate(
				"transactionAdvice");

		transactionInterceptor.setPlatformTransactionManager(
			platformTransactionManagerWrapper);

		try {
			ClassNameLocalServiceUtil.addClassName(className);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"MockPlatformTransactionManager", re.getMessage());
		}
		finally {
			transactionInterceptor.setPlatformTransactionManager(
				platformTransactionManager);

			captureAppender.close();
		}

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(1, loggingEvents.size());

		LoggingEvent loggingEvent = loggingEvents.get(0);

		Assert.assertEquals(
			"Application exception overridden by commit exception",
			loggingEvent.getMessage());

		ClassName cachedClassName = (ClassName)EntityCacheUtil.getResult(
			true, ClassNameImpl.class, classNameId);

		Assert.assertNull(cachedClassName);
	}

	private class MockPlatformTransactionManager
		implements PlatformTransactionManager {

		public MockPlatformTransactionManager(
			PlatformTransactionManager platformTransactionManager) {

			_platformTransactionManager = platformTransactionManager;
		}

		public void commit(TransactionStatus transactionStatus)
			throws TransactionException {

			_platformTransactionManager.rollback(transactionStatus);

			throw new RuntimeException("MockPlatformTransactionManager");
		}

		public TransactionStatus getTransaction(
				TransactionDefinition transactionDefinition)
			throws TransactionException {

			return _platformTransactionManager.getTransaction(
				transactionDefinition);
		}

		public void rollback(TransactionStatus transactionStatus)
			throws TransactionException {

			_platformTransactionManager.rollback(transactionStatus);
		}

		private PlatformTransactionManager _platformTransactionManager;

	}

}