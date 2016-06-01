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
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("src/testIntegration/resources/bnd.bnd")
@RunWith(Arquillian.class)
public class TrackingInterfacesTest {

	@Before
	public void setUp() throws BundleException {
		_bundle.start();

		_bundleContext = _bundle.getBundleContext();
	}

	@After
	public void tearDown() throws BundleException {
		_bundle.stop();
	}

	@Test
	public void testInjectHighestRankingWhenSeveralServicesAreRegistered() {
		TestInterface testInterface = new TestInterface();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface)) {

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 2);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 2);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration3 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne2, 1);

			TrackedTwo trackedTwo2 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration4 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo2, 1);

			Assert.assertEquals(trackedOne, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo, testInterface.getTrackedTwo());

			serviceRegistration1.unregister();

			serviceRegistration2.unregister();

			serviceRegistration3.unregister();

			serviceRegistration4.unregister();
		}
	}

	@Test
	public void
		testInjectNextServiceWithHighestRankingWhenUnregisteringServices() {

		TestInterface testInterface = new TestInterface();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface)) {

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 3);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 3);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration3 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne2, 2);

			TrackedTwo trackedTwo2 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration4 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo2, 2);

			TrackedOne trackedOne3 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration5 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne3, 1);

			TrackedTwo trackedTwo3 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration6 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo3, 1);

			serviceRegistration1.unregister();

			serviceRegistration2.unregister();

			Assert.assertEquals(trackedOne2, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo2, testInterface.getTrackedTwo());

			serviceRegistration3.unregister();

			serviceRegistration4.unregister();

			serviceRegistration5.unregister();

			serviceRegistration6.unregister();
		}
	}

	@Test
	public void testInjectServicesWhenTheyAreRegistered() {
		TestInterface testInterface = new TestInterface();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface)) {

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 0);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 0);

			Assert.assertEquals(trackedOne, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo, testInterface.getTrackedTwo());

			serviceRegistration1.unregister();

			serviceRegistration2.unregister();
		}
	}

	@Test
	public void testInjectUnavailableServiceProxyWhenNoServicesAreRegistered() {
		TestInterface testInterface = new TestInterface();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface)) {

			Assert.assertNotNull(testInterface.getTrackedOne());
			Assert.assertNotNull(testInterface.getTrackedTwo());

			try {
				InterfaceOne interfaceOne = testInterface.getTrackedOne();

				interfaceOne.noop1();

				Assert.fail();
			}
			catch (UnavailableServiceException use) {
				Assert.assertEquals(
					InterfaceOne.class, use.getUnavailableServiceClass());
			}

			try {
				InterfaceTwo interfaceTwo = testInterface.getTrackedTwo();

				interfaceTwo.noop2();

				Assert.fail();
			}
			catch (UnavailableServiceException use) {
				Assert.assertEquals(
					InterfaceTwo.class, use.getUnavailableServiceClass());
			}
		}
	}

	@Test
	public void testInjectUnavailableServiceProxyWhenUnregisteringServices() {
		TestInterface testInterface = new TestInterface();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface)) {

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 0);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 0);

			serviceRegistration1.unregister();

			serviceRegistration2.unregister();

			Assert.assertNotNull(testInterface.getTrackedOne());
			Assert.assertNotNull(testInterface.getTrackedTwo());

			try {
				InterfaceOne interfaceOne = testInterface.getTrackedOne();

				interfaceOne.noop1();

				Assert.fail("Should throw UnavailableServiceException");
			}
			catch (UnavailableServiceException use) {
				Assert.assertEquals(
					InterfaceOne.class, use.getUnavailableServiceClass());
			}

			try {
				InterfaceTwo interfaceTwo = testInterface.getTrackedTwo();

				interfaceTwo.noop2();

				Assert.fail("Should throw UnavailableServiceException");
			}
			catch (UnavailableServiceException use) {
				Assert.assertEquals(
					InterfaceTwo.class, use.getUnavailableServiceClass());
			}
		}
	}

	@Test
	public void testUpdateInjectionPointWhenChangingServiceProperties() {
		TestInterface testInterface = new TestInterface();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInterface)) {

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration1 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne, 2);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration2 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo, 2);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<InterfaceOne> serviceRegistration3 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceOne.class, trackedOne2, 1);

			TrackedTwo trackedTwo2 = new TrackedTwo();

			ServiceRegistration<InterfaceTwo> serviceRegistration4 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, InterfaceTwo.class, trackedTwo2, 1);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("service.ranking", 3);

			serviceRegistration3.setProperties(properties);

			serviceRegistration4.setProperties(properties);

			Assert.assertEquals(trackedOne2, testInterface.getTrackedOne());
			Assert.assertEquals(trackedTwo2, testInterface.getTrackedTwo());

			serviceRegistration1.unregister();

			serviceRegistration2.unregister();

			serviceRegistration3.unregister();

			serviceRegistration4.unregister();
		}
	}

	@ArquillianResource
	private Bundle _bundle;

	private BundleContext _bundleContext;

}