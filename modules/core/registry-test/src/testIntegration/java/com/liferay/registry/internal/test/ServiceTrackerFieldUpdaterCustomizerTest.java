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

package com.liferay.registry.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerFieldUpdaterCustomizer;

import java.io.Closeable;

import java.lang.reflect.Field;

import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ServiceTrackerFieldUpdaterCustomizerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			ServiceTrackerFieldUpdaterCustomizerTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testFieldUpdate() throws Exception {
		try (TestServiceUsage testServiceUsage = new StaticTestServiceUsage()) {
			doTestFieldUpdate(testServiceUsage, _defaultTestService);
		}

		try (TestServiceUsage testServiceUsage =
				new NonStaticTestServiceUsage()) {

			doTestFieldUpdate(testServiceUsage, null);
		}
	}

	@Test
	public void testWrongDeclaration() throws NoSuchFieldException {
		Field field =
			ServiceTrackerFieldUpdaterCustomizerTest.class.getDeclaredField(
				"_bundleContext");

		try {
			new ServiceTrackerFieldUpdaterCustomizer(field, this, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(field + " is not volatile", iae.getMessage());
		}
	}

	protected void doTestFieldUpdate(
		TestServiceUsage testServiceUsage, TestService defaultTestService) {

		// Starting with null

		Assert.assertNull(testServiceUsage.getTestService());

		// Update field to use testService1

		TestService testService1 = new TestService1();

		ServiceRegistration<TestService> serviceRegistration1 =
			_registerService(testService1, 0);

		Assert.assertSame(testService1, testServiceUsage.getTestService());

		// Update field to use testService2, by out ranking testService1

		TestService testService2 = new TestService2();

		ServiceRegistration<TestService> serviceRegistration2 =
			_registerService(testService2, 1);

		Assert.assertSame(testService2, testServiceUsage.getTestService());

		// Upgrade testService1 ranking to suppress testService2

		Hashtable<String, Object> hashtable = new Hashtable<>();

		hashtable.put("service.ranking", 2);

		serviceRegistration1.setProperties(hashtable);

		Assert.assertSame(testService1, testServiceUsage.getTestService());

		// Unregister testService1 and testService2 to fallback to default

		serviceRegistration1.unregister();
		serviceRegistration2.unregister();

		Assert.assertSame(
			defaultTestService, testServiceUsage.getTestService());
	}

	private ServiceRegistration<TestService> _registerService(
		TestService testService, int ranking) {

		Hashtable<String, Object> hashtable = new Hashtable<>();

		if (ranking != 0) {
			hashtable.put("service.ranking", ranking);
		}

		return _bundleContext.registerService(
			TestService.class, testService, hashtable);
	}

	private static final TestService _defaultTestService =
		new TestServiceDefault();

	private BundleContext _bundleContext;

	private static class NonStaticTestServiceUsage implements TestServiceUsage {

		@Override
		public void close() {
			_serviceTracker.close();
		}

		public TestService getTestService() {
			return _testService;
		}

		private NonStaticTestServiceUsage() throws NoSuchFieldException {
			Registry registry = RegistryUtil.getRegistry();

			Field field = NonStaticTestServiceUsage.class.getDeclaredField(
				"_testService");

			field.setAccessible(true);

			_serviceTracker = registry.trackServices(
				TestService.class,
				new ServiceTrackerFieldUpdaterCustomizer
					<TestService, TestService>(field, this, null));

			_serviceTracker.open();
		}

		private final ServiceTracker<TestService, TestService> _serviceTracker;
		private volatile TestService _testService;

	}

	private static class StaticTestServiceUsage implements TestServiceUsage {

		@Override
		public void close() {
			_serviceTracker.close();
		}

		public TestService getTestService() {
			return _testService;
		}

		private static final ServiceTracker<TestService, TestService>
			_serviceTracker;
		private static volatile TestService _testService;

		static {
			Registry registry = RegistryUtil.getRegistry();

			try {
				Field field = StaticTestServiceUsage.class.getDeclaredField(
					"_testService");

				field.setAccessible(true);

				_serviceTracker = registry.trackServices(
					TestService.class,
					new ServiceTrackerFieldUpdaterCustomizer
						<TestService, TestService>(
							field, null, _defaultTestService));

				_serviceTracker.open();
			}
			catch (NoSuchFieldException nsfe) {
				throw new ExceptionInInitializerError(nsfe);
			}
		}

	}

	private static class TestService1 implements TestService {
	}

	private static class TestService2 implements TestService {
	}

	private static class TestServiceDefault implements TestService {
	}

	private interface TestService {
	}

	private interface TestServiceUsage extends Closeable {

		public TestService getTestService();

	}

}