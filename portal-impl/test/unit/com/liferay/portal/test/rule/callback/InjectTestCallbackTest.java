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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.test.rule.Inject;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

/**
 * @author Preston Crary
 */
public class InjectTestCallbackTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testInjectBaseTestCase() throws Exception {
		Description description = Description.createTestDescription(
			TestCase.class, TestCase.class.getName());

		Service1 service1 = new Service1();
		Service2 service2 = new Service2();

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(Service1.class, service1);

		registry.registerService(Service2.class, service2);

		TestCase testCase = new TestCase();

		Assert.assertNull(TestCase.getService1());
		Assert.assertNull(testCase.getService2());

		InjectTestBag classInjectTestBag =
			InjectTestCallback.INSTANCE.beforeClass(description);

		Assert.assertSame(service1, TestCase.getService1());
		Assert.assertNull(testCase.getService2());

		InjectTestBag methodInjectTestBag =
			InjectTestCallback.INSTANCE.beforeMethod(description, testCase);

		Assert.assertSame(service1, TestCase.getService1());
		Assert.assertSame(service2, testCase.getService2());

		InjectTestCallback.INSTANCE.afterMethod(
			description, methodInjectTestBag, testCase);

		Assert.assertSame(service1, TestCase.getService1());
		Assert.assertNull(testCase.getService2());

		InjectTestCallback.INSTANCE.afterClass(description, classInjectTestBag);

		Assert.assertNull(TestCase.getService1());
		Assert.assertNull(testCase.getService2());
	}

	@Test
	public void testInjectBlockingStaticWithoutFilter() throws Exception {
		Description description = Description.createTestDescription(
			TestClass.class, TestClass.class.getName());

		Assert.assertNull(TestClass._service1);

		Thread mainThread = Thread.currentThread();

		Service1 service1 = new Service1();

		Thread watchingThread = new Thread(
			() -> {
				while (true) {
					if (mainThread.getState() == Thread.State.TIMED_WAITING) {
						Assert.assertNull(TestClass._service1);

						Registry registry = RegistryUtil.getRegistry();

						registry.registerService(Service1.class, service1);

						return;
					}
				}
			});

		watchingThread.start();

		InjectTestBag injectTestBag = InjectTestCallback.INSTANCE.beforeClass(
			description);

		watchingThread.join();

		Assert.assertSame(service1, TestClass._service1);

		AtomicBoolean ungetServiceCalled = new AtomicBoolean();

		RegistryUtil.setRegistry(
			new BasicRegistryImpl() {

				@Override
				public <T> boolean ungetService(
					ServiceReference<T> serviceReference) {

					ungetServiceCalled.set(true);

					return false;
				}

			});

		InjectTestCallback.INSTANCE.afterClass(description, injectTestBag);

		Assert.assertNull(TestClass._service1);

		Assert.assertTrue(ungetServiceCalled.get());
	}

	@Test
	public void testInjectNonBlockingNonStaticWithFilter() throws Exception {
		Description description = Description.createTestDescription(
			TestClass.class, TestClass.class.getName());

		TestClass testClass = new TestClass();

		InjectTestBag injectTestBag = InjectTestCallback.INSTANCE.beforeMethod(
			description, testClass);

		Assert.assertNull(testClass._service2);

		Registry registry = RegistryUtil.getRegistry();

		Service2 service2 = new Service2();

		registry.registerService(Service2.class, service2);

		injectTestBag.injectFields();

		Assert.assertNull(testClass._service2);

		Service3 service3a = new Service3();

		registry.registerService(Service3.class, service3a);

		injectTestBag.injectFields();

		Assert.assertNull(testClass._service2);

		Service3 service3b = new Service3();

		Map<String, Object> properties = new HashMap<>();

		properties.put("inject.test.rule.test", true);

		registry.registerService(Service3.class, service3b, properties);

		injectTestBag.injectFields();

		Assert.assertSame(service3b, testClass._service2);

		Assert.assertNull(TestClass._service1);
		Assert.assertNull(testClass._service3);

		InjectTestCallback.INSTANCE.afterMethod(
			description, injectTestBag, testClass);

		Assert.assertNull(testClass._service2);
	}

	private static class BaseTestCase {

		public static Service1 getService1() {
			return _service1;
		}

		public Service2 getService2() {
			return _service2;
		}

		@Inject
		private static Service1 _service1;

		@Inject
		private Service2 _service2;

	}

	private static class TestCase extends BaseTestCase {
	}

	private static class TestClass {

		@Inject
		private static Service1 _service1;

		@Inject(
			blocking = false, filter = "inject.test.rule.test=true",
			type = Service3.class
		)
		private Service2 _service2;

		private final Service3 _service3 = null;

	}

	private class Service1 {
	}

	private class Service2 {
	}

	private class Service3 extends Service2 {
	}

}