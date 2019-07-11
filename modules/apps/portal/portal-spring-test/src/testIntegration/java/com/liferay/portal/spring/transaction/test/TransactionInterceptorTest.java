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

package com.liferay.portal.spring.transaction.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.spring.hibernate.PortletTransactionManager;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class TransactionInterceptorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFailOnCommit() {
		CacheRegistryUtil.clear();

		long classNameId = _counterLocalService.increment();

		TransactionExecutor transactionExecutor =
			(TransactionExecutor)PortalBeanLocatorUtil.locate(
				"transactionExecutor");

		PlatformTransactionManager platformTransactionManager =
			ReflectionTestUtil.getAndSetFieldValue(
				transactionExecutor, "_platformTransactionManager",
				new MockPlatformTransactionManager(
					(HibernateTransactionManager)
						InfrastructureUtil.getTransactionManager()));

		try {
			_classNameLocalService.addClassName(
				_classNamePersistence.create(classNameId));

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"MockPlatformTransactionManager", re.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				transactionExecutor, "_platformTransactionManager",
				platformTransactionManager);
		}

		Assert.assertNull(
			_entityCache.getResult(true, ClassNameImpl.class, classNameId));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ClassNamePersistence _classNamePersistence;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private EntityCache _entityCache;

	private static class MockPlatformTransactionManager
		extends PortletTransactionManager {

		public MockPlatformTransactionManager(
			HibernateTransactionManager hibernateTransactionManager) {

			super(
				hibernateTransactionManager,
				hibernateTransactionManager.getSessionFactory());

			_platformTransactionManager = hibernateTransactionManager;
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