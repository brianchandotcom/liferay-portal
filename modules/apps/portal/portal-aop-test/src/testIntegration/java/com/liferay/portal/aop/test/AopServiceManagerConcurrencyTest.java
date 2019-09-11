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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionHandler;
import com.liferay.portal.spring.transaction.TransactionStatusAdapter;
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

		for (Bundle currentBundle : _bundleContext.getBundles()) {
			String symbolicName = currentBundle.getSymbolicName();

			if (symbolicName.equals("com.liferay.portal.aop.impl")) {
				_aopImplBundle = currentBundle;

				break;
			}
		}

		Assert.assertNotNull(_aopImplBundle);
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
				TestTransactionHandler.class.getName() + nameSuffix);

			TestTransactionHandler testTransactionHandler =
				(TestTransactionHandler)
					testTransactionExecutorClass.newInstance();

			int index = i;

			callables.add(
				() -> {
					int expectedMinServiceRanking = nameSuffix + 1;

					ServiceRegistration<TransactionHandler>
						transactionHandlerServiceRegistration =
							_bundleContext.registerService(
								TransactionHandler.class,
								testTransactionHandler,
								MapUtil.singletonDictionary(
									Constants.SERVICE_RANKING,
									expectedMinServiceRanking));

					_assertUsingBundles(transactionHandlerServiceRegistration);

					ServiceRegistration<AopService>
						aopServiceServiceRegistration =
							_bundleContext.registerService(
								AopService.class, aopService,
								MapUtil.singletonDictionary("index", index));

					_assertUsingBundles(aopServiceServiceRegistration);

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

						if (testService == null) {
							Assert.assertSame(
								aopService,
								_bundleContext.getService(
									aopServiceServiceRegistration.
										getReference()));

							_bundleContext.ungetService(
								aopServiceServiceRegistration.getReference());

							_assertUsingBundles(aopServiceServiceRegistration);

							Assert.assertSame(
								testTransactionHandler,
								_bundleContext.getService(
									transactionHandlerServiceRegistration.
										getReference()));

							_bundleContext.ungetService(
								transactionHandlerServiceRegistration.
									getReference());

							_assertUsingBundles(
								transactionHandlerServiceRegistration);

							serviceReferences = null;

							while (serviceReferences == null) {
								serviceReferences =
									_bundleContext.getServiceReferences(
										TestService.class.getName(),
										"(index=" + index + ")");
							}
						}
					}

					int executorServiceRanking = 0;

					try {
						testService.getTransactionExecutor();

						Assert.fail();
					}
					catch (RuntimeException re) {
						String message = re.getMessage();

						Assert.assertTrue(
							message,
							message.startsWith(
								TestTransactionHandler.class.getName()));

						executorServiceRanking = GetterUtil.getInteger(
							message.substring(message.length() - 1));
					}

					_bundleContext.ungetService(serviceReferences[0]);

					executorServiceRanking++;

					Assert.assertTrue(
						StringBundler.concat(
							"Actual ranking ", executorServiceRanking,
							" is not equal to or greater than ",
							expectedMinServiceRanking),
						executorServiceRanking >= expectedMinServiceRanking);

					aopServiceServiceRegistration.unregister();

					transactionHandlerServiceRegistration.unregister();

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

	public static class TestTransactionHandler0 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler1 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler2 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler3 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler4 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler5 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler6 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler7 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler8 extends TestTransactionHandler {
	}

	public static class TestTransactionHandler9 extends TestTransactionHandler {
	}

	private void _assertUsingBundles(
		ServiceRegistration<?> serviceRegistration) {

		ServiceReference<?> serviceReference =
			serviceRegistration.getReference();

		Bundle[] usingBundles = serviceReference.getUsingBundles();

		Assert.assertNotNull(usingBundles);
		Assert.assertEquals(
			Arrays.toString(usingBundles), 1, usingBundles.length);
		Assert.assertSame(_aopImplBundle, usingBundles[0]);
	}

	private Bundle _aopImplBundle;
	private BundleContext _bundleContext;
	private ExecutorService _executorService;

	private static class TestServiceImpl implements AopService, TestService {

		@Override
		public Class<?>[] getAopInterfaces() {
			return _AOP_INTERFACES;
		}

		@Override
		public void getTransactionExecutor() {
		}

		private static final Class<?>[] _AOP_INTERFACES = new Class<?>[] {
			TestService.class
		};

	}

	private static class TestTransactionHandler implements TransactionHandler {

		@Override
		public void commit(
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter) {

			Assert.assertSame(
				_transactionStatusAdapter, transactionStatusAdapter);

			Class<? extends TestTransactionHandler> clazz = getClass();

			throw new RuntimeException(clazz.getName());
		}

		@Override
		public void rollback(
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter) {

			Assert.assertSame(
				_transactionStatusAdapter, transactionStatusAdapter);
		}

		@Override
		public TransactionStatusAdapter start(
			TransactionAttributeAdapter transactionAttributeAdapter) {

			return _transactionStatusAdapter;
		}

		private final TransactionStatusAdapter _transactionStatusAdapter =
			new TransactionStatusAdapter(null);

	}

	@Transactional(isolation = Isolation.PORTAL, rollbackFor = Exception.class)
	private interface TestService {

		public void getTransactionExecutor();

	}

}