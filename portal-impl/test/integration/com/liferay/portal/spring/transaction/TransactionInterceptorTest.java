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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.persistence.ClassNameUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFailOnCommit() {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					DefaultTransactionExecutor.class.getName(), Level.ERROR)) {

			CacheRegistryUtil.clear();

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
			}

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Application exception overridden by commit exception",
				loggingEvent.getMessage());

			ClassName cachedClassName = (ClassName)EntityCacheUtil.getResult(
				true, ClassNameImpl.class, classNameId);

			Assert.assertNull(cachedClassName);
		}
	}

	private static class MockPlatformTransactionManager
		implements PlatformTransactionManager {

		public MockPlatformTransactionManager(
			PlatformTransactionManager platformTransactionManager) {

			_platformTransactionManager = platformTransactionManager;
		}

		@Override
		public void commit(TransactionStatus transactionStatus)
			throws TransactionException {

			_platformTransactionManager.rollback(transactionStatus);

			throw new RuntimeException("MockPlatformTransactionManager");
		}

		@Override
		public TransactionStatus getTransaction(
				TransactionDefinition transactionDefinition)
			throws TransactionException {

			return _platformTransactionManager.getTransaction(
				transactionDefinition);
		}

		@Override
		public void rollback(TransactionStatus transactionStatus)
			throws TransactionException {

			_platformTransactionManager.rollback(transactionStatus);
		}

		private final PlatformTransactionManager _platformTransactionManager;

	}

}