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

package com.liferay.portal.aop.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class AopServiceManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAopService() {
		Bundle bundle = FrameworkUtil.getBundle(AopServiceManagerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("key", "value");
		properties.put(Constants.SERVICE_RANKING, 1);

		ServiceRegistration<AopService> aopServiceServiceRegistration =
			bundleContext.registerService(
				AopService.class, new TestServiceImpl(), properties);

		TestTransactionExecutor testTransactionExecutor =
			new TestTransactionExecutor();

		ServiceRegistration<TransactionExecutor>
			transactionExecutorServiceRegistration =
				bundleContext.registerService(
					TransactionExecutor.class, testTransactionExecutor,
					properties);

		ServiceReference<TestService> testServiceServiceReference =
			bundleContext.getServiceReference(TestService.class);

		Assert.assertNotNull(testServiceServiceReference);

		TestService testService = bundleContext.getService(
			testServiceServiceReference);

		try {
			Assert.assertEquals(
				"value", testServiceServiceReference.getProperty("key"));

			Assert.assertTrue(ProxyUtil.isProxyClass(testService.getClass()));

			Assert.assertFalse(testTransactionExecutor._executeCalled);

			Assert.assertSame(testService, testService.getEnclosingAopProxy());

			Assert.assertTrue(testTransactionExecutor._executeCalled);

			properties.put("key", "value2");

			aopServiceServiceRegistration.setProperties(properties);

			Assert.assertEquals(
				"value2", testServiceServiceReference.getProperty("key"));
		}
		finally {
			bundleContext.ungetService(testServiceServiceReference);
		}

		transactionExecutorServiceRegistration.unregister();

		aopServiceServiceRegistration.unregister();
	}

	private static class TestServiceImpl implements AopService, TestService {

		@Override
		public TestService getEnclosingAopProxy() {
			return _testService;
		}

		@Override
		public void setAopProxy(Object aopProxy) {
			_testService = (TestService)aopProxy;
		}

		private TestService _testService;

	}

	private static class TestTransactionExecutor
		implements TransactionExecutor {

		@Override
		public <T> T execute(
				TransactionAttributeAdapter transactionAttributeAdapter,
				UnsafeSupplier<T, Throwable> unsafeSupplier)
			throws Throwable {

			_executeCalled = true;

			return unsafeSupplier.get();
		}

		@Override
		public PlatformTransactionManager getPlatformTransactionManager() {
			throw new UnsupportedOperationException();
		}

		private boolean _executeCalled;

	}

	@Transactional(isolation = Isolation.PORTAL, rollbackFor = Exception.class)
	private interface TestService {

		public TestService getEnclosingAopProxy();

	}

}