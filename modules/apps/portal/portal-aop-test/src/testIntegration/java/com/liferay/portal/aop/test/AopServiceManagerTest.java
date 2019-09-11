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
import com.liferay.petra.concurrent.DefaultNoticeableFuture;
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
import com.liferay.portal.spring.transaction.TransactionHandler;
import com.liferay.portal.spring.transaction.TransactionStatusAdapter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.Dictionary;
import java.util.Set;

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
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

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
	public void setUp() throws ReflectiveOperationException {
		Bundle bundle = FrameworkUtil.getBundle(AopServiceManagerTest.class);

		_bundleContext = bundle.getBundleContext();

		_getServiceObjectsMethod = BundleContext.class.getMethod(
			"getServiceObjects", ServiceReference.class);

		Class<?> serviceObjectsClass = bundle.loadClass(
			"org.osgi.framework.ServiceObjects");

		_getServiceMethod = serviceObjectsClass.getMethod("getService");
		_ungetServiceMethod = serviceObjectsClass.getMethod(
			"ungetService", Object.class);
	}

	@Test
	public void testAopService() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("key", "value");
		properties.put(Constants.SERVICE_RANKING, 1);

		ServiceRegistration<AopService> aopServiceServiceRegistration =
			_bundleContext.registerService(
				AopService.class, new TestServiceImpl(), properties);

		TestTransactionHandler testTransactionHandler =
			new TestTransactionHandler();

		ServiceRegistration<TransactionHandler>
			transactionExecutorServiceRegistration =
				_bundleContext.registerService(
					TransactionHandler.class, testTransactionHandler,
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

			Assert.assertFalse(testTransactionHandler._called);

			Assert.assertSame(testService, testService.getEnclosingAopProxy());

			Assert.assertTrue(testTransactionHandler._called);

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
	public void testAopServiceFactory() throws Exception {
		ServiceRegistration<?> aopServiceServiceRegistration =
			_bundleContext.registerService(
				AopService.class.getName(), new TestPrototypeServiceFactory(),
				null);

		ServiceReference<TestService> serviceReference =
			_bundleContext.getServiceReference(TestService.class);

		Object serviceObjects = _getServiceObjectsMethod.invoke(
			_bundleContext, serviceReference);

		TestService testService1 = (TestService)_getServiceMethod.invoke(
			serviceObjects);
		TestService testService2 = (TestService)_getServiceMethod.invoke(
			serviceObjects);

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

		_ungetServiceMethod.invoke(serviceObjects, testService1);

		Assert.assertFalse(
			aopInvocationHandlers.toString(),
			aopInvocationHandlers.contains(aopInvocationHandler1));
		Assert.assertTrue(
			aopInvocationHandlers.toString(),
			aopInvocationHandlers.contains(aopInvocationHandler2));

		_ungetServiceMethod.invoke(serviceObjects, testService2);

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

		Object serviceObjects = _getServiceObjectsMethod.invoke(
			_bundleContext, serviceReference);

		DefaultNoticeableFuture<Throwable> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		LogListener logListener = logEntry -> {
			if (logEntry.getLevel() == LogService.LOG_ERROR) {
				defaultNoticeableFuture.set(logEntry.getException());
			}
		};

		Object factory = ReflectionTestUtil.getFieldValue(
			_logReaderService, "factory");

		Object listeners = ReflectionTestUtil.getFieldValue(
			factory, "listeners");

		Class<?> listenersClass = listeners.getClass();

		Constructor<?> constructor = listenersClass.getConstructor(int.class);

		Object newListeners = constructor.newInstance(0);

		ReflectionTestUtil.setFieldValue(factory, "listeners", newListeners);

		_logReaderService.addLogListener(logListener);

		Class<?> aopInterface = TestServiceImpl._AOP_INTERFACES[0];

		try {
			TestServiceImpl._AOP_INTERFACES[0] = AopService.class;

			Assert.assertNull(_getServiceMethod.invoke(serviceObjects));

			Throwable throwable = defaultNoticeableFuture.get();

			Assert.assertNotNull(throwable);

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

			TestServiceImpl._AOP_INTERFACES[0] = aopInterface;

			aopServiceServiceRegistration.unregister();

			ReflectionTestUtil.setFieldValue(factory, "listeners", listeners);
		}
	}

	private BundleContext _bundleContext;
	private Method _getServiceMethod;
	private Method _getServiceObjectsMethod;

	@Inject
	private LogReaderService _logReaderService;

	private Method _ungetServiceMethod;

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
			return _AOP_INTERFACES;
		}

		@Override
		public TestService getEnclosingAopProxy() {
			return _testService;
		}

		@Override
		public void setAopProxy(Object aopProxy) {
			_testService = (TestService)aopProxy;
		}

		private static final Class<?>[] _AOP_INTERFACES = new Class<?>[] {
			TestService.class
		};

		private TestService _testService;

	}

	private static class TestTransactionHandler implements TransactionHandler {

		@Override
		public void commit(
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter) {
		}

		@Override
		public void rollback(
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter) {
		}

		@Override
		public TransactionStatusAdapter start(
			TransactionAttributeAdapter transactionAttributeAdapter) {

			_called = true;

			return null;
		}

		private boolean _called;

	}

	@Transactional(isolation = Isolation.PORTAL, rollbackFor = Exception.class)
	private interface TestService {

		public TestService getEnclosingAopProxy();

	}

}