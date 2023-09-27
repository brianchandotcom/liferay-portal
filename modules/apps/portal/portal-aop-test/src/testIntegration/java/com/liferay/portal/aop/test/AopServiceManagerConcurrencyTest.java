/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.aop.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionExecutor;
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

		_aopImplBundle = BundleUtil.getBundle(
			_bundleContext, "com.liferay.portal.aop.impl");

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

					_assertUsingBundles(transactionExecutorServiceRegistration);

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

						if (testService != null) {
							continue;
						}

						Assert.assertSame(
							aopService,
							_bundleContext.getService(
								aopServiceServiceRegistration.getReference()));

						_bundleContext.ungetService(
							aopServiceServiceRegistration.getReference());

						_assertUsingBundles(aopServiceServiceRegistration);

						Assert.assertSame(
							testTransactionExecutor,
							_bundleContext.getService(
								transactionExecutorServiceRegistration.
									getReference()));

						_bundleContext.ungetService(
							transactionExecutorServiceRegistration.
								getReference());

						_assertUsingBundles(
							transactionExecutorServiceRegistration);

						serviceReferences = null;

						while (serviceReferences == null) {
							serviceReferences =
								_bundleContext.getServiceReferences(
									TestService.class.getName(),
									"(index=" + index + ")");
						}
					}

					int executorServiceRanking = 0;

					try {
						testService.getTransactionExecutor();

						Assert.fail();
					}
					catch (RuntimeException runtimeException) {
						String message = runtimeException.getMessage();

						Assert.assertTrue(
							message,
							message.startsWith(
								TestTransactionExecutor.class.getName()));

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

	private static class TestTransactionExecutor
		implements TransactionExecutor {

		@Override
		public void commit(
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter) {

			Assert.assertSame(
				_transactionStatusAdapter, transactionStatusAdapter);

			Class<? extends TestTransactionExecutor> clazz = getClass();

			throw new RuntimeException(clazz.getName());
		}

		@Override
		public <T> T execute(
			TransactionAttributeAdapter transactionAttributeAdapter,
			UnsafeSupplier<T, Throwable> unsafeSupplier) {

			return null;
		}

		@Override
		public PlatformTransactionManager getPlatformTransactionManager() {
			return null;
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