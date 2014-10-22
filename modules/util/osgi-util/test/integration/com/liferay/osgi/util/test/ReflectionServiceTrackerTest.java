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

package com.liferay.osgi.util.test;

import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.osgi.util.service.ReflectionServiceTracker;
import com.liferay.osgi.util.service.UnavailableServiceException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Enclosed.class)
public class ReflectionServiceTrackerTest {

	@BndFile("test/integration/bnd.bnd")
	@RunWith(Arquillian.class)
	public static class WhenTrackingClasses {

		@Before
		public void setUp() throws BundleException {
			_bundle.start();

			_bundleContext = _bundle.getBundleContext();
		}

		@Test
		public void shouldInjectNullWhenNoServicesAreRegistered() {
			TestInstance testInstance = new TestInstance();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInstance);

			Assert.assertNull(testInstance.getTrackedOne());
			Assert.assertNull(testInstance.getTrackedTwo());

			reflectionServiceTracker.close();
		}

		@Test
		public void shouldInjectNullWhenUnregisteringServices() {
			TestInstance testInstance = new TestInstance();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInstance);

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerServiceWithRanking(
					_bundleContext, TrackedOne.class, trackedOne, 0);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<TrackedTwo> serviceRegistration2 =
				registerServiceWithRanking(
					_bundleContext, TrackedTwo.class, trackedTwo, 0);

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			Assert.assertNull(testInstance.getTrackedOne());
			Assert.assertNull(testInstance.getTrackedTwo());

			reflectionServiceTracker.close();
		}

		@After
		public void tearDown() throws BundleException {
			_bundle.stop();
		}

		@ArquillianResource
		private final Bundle _bundle;

		private BundleContext _bundleContext;

	}

	@BndFile("test/integration/bnd.bnd")
	@RunWith(Arquillian.class)
	public static class WhenTrackingInterfaces {

		@Before
		public void setUp() throws BundleException {
			_bundle.start();

			_bundleContext = _bundle.getBundleContext();
		}

		@Test
		public void
			shouldInjectHighestRankingWhenSeveralServicesAreRegistered() {

			TestInterface testInterface = new TestInterface();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface);

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 2);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 2);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration3 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne2, 1);

			TrackedTwo trackedTwo2 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration4 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo2, 1);

			Assert.assertEquals(trackedOne, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo, testInterface.getTrackedTwo());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
			serviceRegistration3.unregister();
			serviceRegistration4.unregister();

			reflectionServiceTracker.close();
		}

		@Test
		public void
			shouldInjectNextServiceWithHighestRankingWhenUnregisteringServices() {

			TestInterface testInterface = new TestInterface();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface);

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 3);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 3);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration3 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne2, 2);

			TrackedTwo trackedTwo2 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration4 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo2, 2);

			TrackedOne trackedOne3 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration5 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne3, 1);

			TrackedTwo trackedTwo3 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration6 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo3, 1);

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			Assert.assertEquals(trackedOne2, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo2, testInterface.getTrackedTwo());

			serviceRegistration3.unregister();
			serviceRegistration4.unregister();
			serviceRegistration5.unregister();
			serviceRegistration6.unregister();

			reflectionServiceTracker.close();
		}

		@Test
		public void shouldInjectServicesWhenTheyAreRegistered() {
			TestInterface testInterface = new TestInterface();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface);

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 0);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 0);

			Assert.assertEquals(trackedOne, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo, testInterface.getTrackedTwo());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			reflectionServiceTracker.close();
		}

		@Test
		public void
			shouldInjectUnavailableServiceProxyWhenNoServicesAreRegistered() {

			TestInterface testInterface = new TestInterface();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface);

			Assert.assertNotNull(testInterface.getTrackedOne());
			Assert.assertNotNull(testInterface.getTrackedTwo());

			try {
				testInterface.getTrackedOne().noop1();

				Assert.fail("Should throw UnavailableServiceException");
			}
			catch (UnavailableServiceException sue) {
				Assert.assertEquals(
					InterfaceOne.class, sue.getUnavailableServiceClass());
			}

			try {
				testInterface.getTrackedTwo().noop2();

				Assert.fail("Should throw UnavailableServiceException");
			}
			catch (UnavailableServiceException sue) {
				Assert.assertEquals(
					InterfaceTwo.class, sue.getUnavailableServiceClass());
			}

			reflectionServiceTracker.close();
		}

		@Test
		public void
			shouldInjectUnavailableServiceProxyWhenUnregisteringServices() {

			TestInterface testInterface = new TestInterface();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface);

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 0);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 0);

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			Assert.assertNotNull(testInterface.getTrackedOne());
			Assert.assertNotNull(testInterface.getTrackedTwo());

			try {
				testInterface.getTrackedOne().noop1();

				Assert.fail("Should throw UnavailableServiceException");
			}
			catch (UnavailableServiceException sue) {
				Assert.assertEquals(
					InterfaceOne.class, sue.getUnavailableServiceClass());
			}

			try {
				testInterface.getTrackedTwo().noop2();

				Assert.fail("Should throw UnavailableServiceException");
			}
			catch (UnavailableServiceException sue) {
				Assert.assertEquals(
					InterfaceTwo.class, sue.getUnavailableServiceClass());
			}

			reflectionServiceTracker.close();
		}

		@Test
		public void shouldUpdateInjectionPointWhenChangingServiceProperties() {
			TestInterface testInterface = new TestInterface();

			ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface);

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 2);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 2);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration3 =
				registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne2, 1);

			TrackedTwo trackedTwo2 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration4 =
				registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo2, 1);

			Dictionary<String, Object> properties =
				new Hashtable<String, Object>();

			properties.put("service.ranking", 3);

			serviceRegistration3.setProperties(properties);
			serviceRegistration4.setProperties(properties);

			Assert.assertEquals(trackedOne2, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo2, testInterface.getTrackedTwo());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
			serviceRegistration3.unregister();
			serviceRegistration4.unregister();

			reflectionServiceTracker.close();
		}

		@After
		public void tearDown() throws BundleException {
			_bundle.stop();
		}

		@ArquillianResource
		private final Bundle _bundle;

		private BundleContext _bundleContext;

	}

	protected static <T> ServiceRegistration<T> registerServiceWithRanking(
		BundleContext bundleContext, Class<T> clazz, T service, int ranking) {

		Dictionary<String, Integer> properties =
			new Hashtable<String, Integer>();

		properties.put("service.ranking", ranking);

		return bundleContext.registerService(clazz, service, properties);
	}

}