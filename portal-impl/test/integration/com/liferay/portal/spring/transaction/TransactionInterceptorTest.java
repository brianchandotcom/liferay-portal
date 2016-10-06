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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.ClassNameUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

		ClassName cachedClassName = (ClassName)EntityCacheUtil.getResult(
			true, ClassNameImpl.class, classNameId);

		Assert.assertNull(cachedClassName);
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