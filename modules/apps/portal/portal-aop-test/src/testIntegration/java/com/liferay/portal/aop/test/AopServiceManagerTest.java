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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopCacheManager;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

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
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceException;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;

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

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(AopServiceManagerTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testAopService() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("key", "value");
		properties.put(Constants.SERVICE_RANKING, 1);

		ServiceRegistration<AopService> aopServiceServiceRegistration =
			_bundleContext.registerService(
				AopService.class, new TestServiceImpl(), properties);

		TestTransactionExecutor testTransactionExecutor =
			new TestTransactionExecutor();

		ServiceRegistration<TransactionExecutor>
			transactionExecutorServiceRegistration =
				_bundleContext.registerService(
					TransactionExecutor.class, testTransactionExecutor,
					properties);

		ServiceReference<TestService> testServiceServiceReference =
			_bundleContext.getServiceReference(TestService.class);

		Assert.assertNotNull(testServiceServiceReference);

		TestService testService = _bundleContext.getService(
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
			_bundleContext.ungetService(testServiceServiceReference);
		}

		transactionExecutorServiceRegistration.unregister();

		aopServiceServiceRegistration.unregister();
	}

	@Test
	public void testAopServiceFactory() {
		ServiceRegistration<?> aopServiceServiceRegistration =
			_bundleContext.registerService(
				AopService.class.getName(), new TestPrototypeServiceFactory(),
				null);

		ServiceReference<TestService> serviceReference =
			_bundleContext.getServiceReference(TestService.class);

		ServiceObjects<TestService> serviceObjects =
			_bundleContext.getServiceObjects(serviceReference);

		TestService testService1 = serviceObjects.getService();
		TestService testService2 = serviceObjects.getService();

		Assert.assertNotSame(testService1, testService2);

		AopInvocationHandler aopInvocationHandler1 =
			ProxyUtil.fetchInvocationHandler(
				testService1, AopInvocationHandler.class);

		AopInvocationHandler aopInvocationHandler2 =
			ProxyUtil.fetchInvocationHandler(
				testService2, AopInvocationHandler.class);

		Assert.assertNotNull(aopInvocationHandler1);

		Assert.assertNotNull(aopInvocationHandler2);

		Assert.assertNotSame(
			aopInvocationHandler1.getTarget(),
			aopInvocationHandler2.getTarget());

		Set<AopInvocationHandler> aopInvocationHandlers =
			ReflectionTestUtil.getFieldValue(
				AopCacheManager.class, "_aopInvocationHandlers");

		Assert.assertTrue(
			aopInvocationHandlers.toString(),
			aopInvocationHandlers.contains(aopInvocationHandler1));
		Assert.assertTrue(
			aopInvocationHandlers.toString(),
			aopInvocationHandlers.contains(aopInvocationHandler2));

		serviceObjects.ungetService(testService1);

		Assert.assertFalse(
			aopInvocationHandlers.toString(),
			aopInvocationHandlers.contains(aopInvocationHandler1));
		Assert.assertTrue(
			aopInvocationHandlers.toString(),
			aopInvocationHandlers.contains(aopInvocationHandler2));

		serviceObjects.ungetService(testService2);

		Assert.assertFalse(
			aopInvocationHandlers.toString(),
			aopInvocationHandlers.contains(aopInvocationHandler2));

		_bundleContext.ungetService(serviceReference);

		aopServiceServiceRegistration.unregister();
	}

	@Test
	public void testInvalidAopServiceFactory() throws Exception {
		ServiceRegistration<?> aopServiceServiceRegistration =
			_bundleContext.registerService(
				AopService.class.getName(), new TestPrototypeServiceFactory(),
				null);

		ServiceReference<TestService> serviceReference =
			_bundleContext.getServiceReference(TestService.class);

		ServiceObjects<TestService> serviceObjects =
			_bundleContext.getServiceObjects(serviceReference);

		CountDownLatch countDownLatch = new CountDownLatch(1);

		LogListener logListener = logEntry -> {
			if (logEntry.getLogLevel() == LogLevel.ERROR) {
				countDownLatch.countDown();
			}
		};

		_logReaderService.addLogListener(logListener);

		Class<?>[] aopInterfaces = TestServiceImpl._aopInterfaces;

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"osgi.logging.com_liferay_portal_aop_test", Level.ERROR)) {

			TestServiceImpl._aopInterfaces = new Class<?>[] {AopService.class};

			Assert.assertNull(serviceObjects.getService());

			countDownLatch.await();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			Throwable throwable = throwableInformation.getThrowable();

			Assert.assertTrue(
				throwable.toString(), throwable instanceof ServiceException);

			Throwable cause = throwable.getCause();

			Assert.assertTrue(
				cause.toString(), cause instanceof IllegalArgumentException);

			String message = cause.getMessage();

			Assert.assertTrue(
				message, message.startsWith("Prototype AopService "));
		}
		finally {
			_logReaderService.removeLogListener(logListener);

			TestServiceImpl._aopInterfaces = aopInterfaces;

			aopServiceServiceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@Inject
	private LogReaderService _logReaderService;

	private static class TestPrototypeServiceFactory
		implements PrototypeServiceFactory<AopService> {

		@Override
		public AopService getService(
			Bundle bundle,
			ServiceRegistration<AopService> serviceRegistration) {

			return new TestServiceImpl();
		}

		@Override
		public void ungetService(
			Bundle bundle, ServiceRegistration<AopService> serviceRegistration,
			AopService aopService) {
		}

	}

	private static class TestServiceImpl implements AopService, TestService {

		@Override
		public Class<?>[] getAopInterfaces() {
			return _aopInterfaces;
		}

		@Override
		public TestService getEnclosingAopProxy() {
			return _testService;
		}

		@Override
		public void setAopProxy(Object aopProxy) {
			_testService = (TestService)aopProxy;
		}

		private static Class<?>[] _aopInterfaces = new Class<?>[] {
			TestService.class
		};

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