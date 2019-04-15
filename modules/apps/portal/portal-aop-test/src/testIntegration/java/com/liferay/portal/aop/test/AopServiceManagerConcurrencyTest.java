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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class AopServiceManagerConcurrencyTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			AopServiceManagerConcurrencyTest.class);

		_bundleContext = bundle.getBundleContext();

		Runtime runtime = Runtime.getRuntime();

		_executorService = Executors.newFixedThreadPool(
			runtime.availableProcessors());
	}

	@After
	public void tearDown() {
		_executorService.shutdownNow();
	}

	@Test
	public void testConcurrentRegistrations() throws Exception {
		List<Callable<Void>> callables = new ArrayList<>(1000);

		for (int i = 0; i < 1000; i++) {
			int nameSuffix = i % 10;

			ClassLoader classLoader =
				AopServiceManagerConcurrencyTest.class.getClassLoader();

			Class<?> aopServiceClass = classLoader.loadClass(
				TestService.class.getName() + nameSuffix);

			AopService aopService = (AopService)aopServiceClass.newInstance();

			Class<?> testTransactionExecutorClass = classLoader.loadClass(
				TestTransactionExecutor.class.getName() + nameSuffix);

			TestTransactionExecutor testTransactionExecutor =
				(TestTransactionExecutor)
					testTransactionExecutorClass.newInstance();

			int index = i;

			callables.add(
				() -> {
					int expectedMinServiceRanking = nameSuffix + 1;

					ServiceRegistration<TransactionExecutor>
						transactionExecutorServiceRegistration =
							_bundleContext.registerService(
								TransactionExecutor.class,
								testTransactionExecutor,
								MapUtil.singletonDictionary(
									Constants.SERVICE_RANKING,
									expectedMinServiceRanking));

					ServiceRegistration<AopService>
						aopServiceServiceRegistration =
							_bundleContext.registerService(
								AopService.class, aopService,
								MapUtil.singletonDictionary("index", index));

					ServiceReference<?>[] serviceReferences = null;

					while (serviceReferences == null) {
						serviceReferences = _bundleContext.getServiceReferences(
							TestService.class.getName(),
							"(index=" + index + ")");
					}

					Assert.assertEquals(
						Arrays.toString(serviceReferences), 1,
						serviceReferences.length);

					TestService testService = null;

					while (testService == null) {
						testService = (TestService)_bundleContext.getService(
							serviceReferences[0]);
					}

					TestTransactionExecutor actualTestTransactionExecutor =
						testService.getTransactionExecutor();

					_bundleContext.ungetService(serviceReferences[0]);

					Class<?> clazz = actualTestTransactionExecutor.getClass();

					String name = clazz.getName();

					int executorServiceRanking =
						GetterUtil.getInteger(
							name.substring(name.length() - 1)) + 1;

					Assert.assertTrue(
						StringBundler.concat(
							"Actual ranking ", executorServiceRanking,
							" is not equal to or greater than ",
							expectedMinServiceRanking),
						executorServiceRanking >= expectedMinServiceRanking);

					aopServiceServiceRegistration.unregister();

					transactionExecutorServiceRegistration.unregister();

					return null;
				});
		}

		Collections.shuffle(callables);

		List<Future<Void>> futures = _executorService.invokeAll(callables);

		for (Future<Void> future : futures) {
			future.get();
		}
	}

	public static class TestService0 extends TestServiceImpl {
	}

	public static class TestService1 extends TestServiceImpl {
	}

	public static class TestService2 extends TestServiceImpl {
	}

	public static class TestService3 extends TestServiceImpl {
	}

	public static class TestService4 extends TestServiceImpl {
	}

	public static class TestService5 extends TestServiceImpl {
	}

	public static class TestService6 extends TestServiceImpl {
	}

	public static class TestService7 extends TestServiceImpl {
	}

	public static class TestService8 extends TestServiceImpl {
	}

	public static class TestService9 extends TestServiceImpl {
	}

	public static class TestTransactionExecutor0
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor1
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor2
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor3
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor4
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor5
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor6
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor7
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor8
		extends TestTransactionExecutor {
	}

	public static class TestTransactionExecutor9
		extends TestTransactionExecutor {
	}

	private BundleContext _bundleContext;
	private ExecutorService _executorService;

	private static class TestServiceImpl implements AopService, TestService {

		@Override
		public Class<?>[] getAopInterfaces() {
			return _AOP_INTERFACES;
		}

		@Override
		public TestTransactionExecutor getTransactionExecutor() {
			throw new UnsupportedOperationException();
		}

		private static final Class<?>[] _AOP_INTERFACES = new Class<?>[] {
			TestService.class
		};

	}

	private static class TestTransactionExecutor
		implements TransactionExecutor {

		@Override
		public <T> T execute(
			TransactionAttributeAdapter transactionAttributeAdapter,
			UnsafeSupplier<T, Throwable> unsafeSupplier) {

			Assert.assertSame(
				this, transactionAttributeAdapter.getTransactionExecutor());

			return (T)this;
		}

		@Override
		public PlatformTransactionManager getPlatformTransactionManager() {
			throw new UnsupportedOperationException();
		}

	}

	@Transactional(isolation = Isolation.PORTAL, rollbackFor = Exception.class)
	private interface TestService {

		public TestTransactionExecutor getTransactionExecutor();

	}

}